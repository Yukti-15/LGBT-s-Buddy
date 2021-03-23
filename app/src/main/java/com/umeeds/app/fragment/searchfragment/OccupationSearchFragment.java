package com.umeeds.app.fragment.searchfragment;


import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.umeeds.app.MainActivity;
import com.umeeds.app.R;
import com.umeeds.app.adapter.MultiSelectOccupationAdapter;
import com.umeeds.app.model.serachmodel.OccupicationModel;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.Constant;
import com.umeeds.app.network.networking.MySingleton;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class OccupationSearchFragment extends Fragment implements View.OnClickListener {


    RangeSeekBar ageSelectorRsb;
    TextView tvMinAge, tvMaxAge;

    RelativeLayout rl_search;
    CheckBox cbProfileWithPhoto;
    Button btn_submit_dialog;
    Dialog dialog;
    Button submit_dialog;
    RecyclerView recyclerView;
    MultiSelectOccupationAdapter multiSelectAdapter;
    String occuArray;
    LinearLayout ll_select_occup;
    TextView tvSelectOccup;
    private final ArrayList<OccupicationModel> occupicationModelArrayList = new ArrayList<>();

    public OccupationSearchFragment() {
        // Required empty public constructor
    }

    public static OccupationSearchFragment newInstance() {
        return new OccupationSearchFragment();
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_occupation_search, container, false);


        rl_search = view.findViewById(R.id.rl_search);
        ll_select_occup = view.findViewById(R.id.ll_select_occup);
        btn_submit_dialog = view.findViewById(R.id.btn_submit_dialog);
        ageSelectorRsb = view.findViewById(R.id.age_selector_rsb);
        tvMinAge = view.findViewById(R.id.tv_min_age);
        tvMaxAge = view.findViewById(R.id.tv_max_age);
        cbProfileWithPhoto = view.findViewById(R.id.cb_profile_with_photo);
        tvSelectOccup = view.findViewById(R.id.tv_select_occup);

        ageSelectorRsb.setRangeValues(18, 60);
        ageSelectorRsb.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                tvMinAge.setText("Min " + minValue + " yrs");
                tvMaxAge.setText("Min " + maxValue + " yrs");
            }
        });
        occupicationApi();

        ll_select_occup.setOnClickListener(this);
        rl_search.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_select_occup:
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.search_dialog);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                recyclerView = dialog.findViewById(R.id.rv_books);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                multiSelectAdapter = new MultiSelectOccupationAdapter(occupicationModelArrayList, getContext());
                recyclerView.setAdapter(multiSelectAdapter);
                submit_dialog = dialog.findViewById(R.id.btn_submit_dialog);
                submit_dialog.setOnClickListener(this);
                dialog.show();
                break;
            case R.id.rl_search:

                Bundle bundle = new Bundle();
                if (cbProfileWithPhoto.isChecked()) {
                    bundle.putString("photo", "Show profiles with Photo");
                } else {
                    bundle.putString("photo", "");
                }
                bundle.putString("occu", occuArray);
                bundle.putString("type", "occupation");
                bundle.putString("min", ageSelectorRsb.getSelectedMinValue().toString());
                bundle.putString("max", ageSelectorRsb.getSelectedMaxValue().toString());
                MainActivity activity = (MainActivity) getActivity();
                assert activity != null;
                activity.loadFragment(Constant.SEARCH_RESULT_FRAGMENT, bundle);
                break;

            case R.id.btn_submit_dialog:
                String str = "";
                occuArray = "";
                for (OccupicationModel model : occupicationModelArrayList) {
                    if (model.isSelected()) {
                        str = str + model.getOccupation() + ", ";
                        occuArray = occuArray + "\"" + model.getOccupation() + "\"" + ",";
                    }
                    tvSelectOccup.setText(str);
                }
                try {
                    occuArray = removeLastChar(occuArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                occuArray = "[" + occuArray + "]";
                dialog.dismiss();
                Log.d("dataReligion", occuArray);
                break;

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void occupicationApi() {
        //progress_bar.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, ApiClient.BASE_URL + "get_occupation",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //=========check response are coming or not after api hit ===========//
                        if (response != null) {
                            try {
                                Log.v("uploading", response);
                                JSONObject jsonObject = new JSONObject(response);
                                //=========login api response setup method====//

                                try {
                                    String success = jsonObject.getString("response");
                                    if (success.equals("true")) {

                                        OccupicationModel occupicationModel = new OccupicationModel("", "Occupation", "", "");
                                        occupicationModelArrayList.add(occupicationModel);
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                                        if (jsonArray.length() == 0) {
                                            OccupicationModel occupicationModel1 = new OccupicationModel("", "No Record Found", "", "");
                                            occupicationModelArrayList.add(occupicationModel1);
                                            //      progress_bar.setVisibility(View.INVISIBLE);

                                        }
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                            OccupicationModel occupicationModel1 = new OccupicationModel(jsonObject1.getString("occupation_id"),
                                                    jsonObject1.getString("occupation"),
                                                    jsonObject1.getString("status"),
                                                    jsonObject1.getString("sortorder"), false);


                                            occupicationModelArrayList.add(occupicationModel1);
                                            //  progress_bar.setVisibility(View.INVISIBLE);

                                        }
                                    } else {
                                        // progress_bar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getContext(), "Error1!!!..", Toast.LENGTH_LONG).show();

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //spinnerOccupation.setAdapter(new OccupicationAdapter(getContext(), occupicationModelArrayList));


                            } catch (OutOfMemoryError | NullPointerException e) {
                                //    progress_bar.setVisibility(View.INVISIBLE);
                                // TODO: handle exception
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            //   progress_bar.setVisibility(View.INVISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //===if response error then dismiss progress==========//

                Log.v("errrr", String.valueOf(error));
                // progress_bar.setVisibility(View.INVISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(postRequest);
    }

}
