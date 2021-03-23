package com.umeeds.app.fragment.searchfragment;


import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.umeeds.app.SpinnerAdapter;
import com.umeeds.app.adapter.MultiSelectAdapter;
import com.umeeds.app.model.usermodel.ReligiousModel;
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
public class SmartSearchFragment extends Fragment implements View.OnClickListener {
    RelativeLayout rl_search;
    TextView tv_select_religion;
    ProgressBar progress_bar;

    RangeSeekBar ageSelectorRsb;

    TextView tvMinAge, tvMaxAge, tvSelectReligion;
    ArrayList<String> createdList = new ArrayList<>();
    ArrayList<String> religion = new ArrayList<>();
    MultiSelectAdapter multiSelectAdapter;
    int dialogOpenFor;
    Dialog dialog;
    RecyclerView recyclerView;
    Button submit_dialog;
    Spinner spinnerProfileCreatedOn;
    String days;
    String religionArray;
    LinearLayout ll_select_religion;
    CheckBox cbProfileWithPhoto;
    private final ArrayList<ReligiousModel> religiousModelArrayList = new ArrayList<>();


    public SmartSearchFragment() {
        // Required empty public constructor
    }

    public static SmartSearchFragment newInstance() {
        return new SmartSearchFragment();
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_smart_search, container, false);

        rl_search = view.findViewById(R.id.rl_search);
        tv_select_religion = view.findViewById(R.id.tv_select_religion);
        progress_bar = view.findViewById(R.id.progress_bar);

        ageSelectorRsb = view.findViewById(R.id.age_selector_rsb);
        tvMinAge = view.findViewById(R.id.tv_min_age);
        tvMaxAge = view.findViewById(R.id.tv_max_age);
        tvSelectReligion = view.findViewById(R.id.tv_select_religion);
        cbProfileWithPhoto = view.findViewById(R.id.cb_profile_with_photo);

        spinnerProfileCreatedOn = view.findViewById(R.id.spinner_profile_created_on);
        ll_select_religion = view.findViewById(R.id.ll_select_religion);

        createdList.clear();
        createdList.add("All");
        createdList.add("Last 1 week");
        createdList.add("Last 2 weeks");
        createdList.add("Last 3 weeks");
        createdList.add("Last 1 month");
        createdList.add("Last 2 months");
        createdList.add("Last 3 months");
        createdList.add("Last 6 months");
        createdList.add("Last 9 months");
        createdList.add("Last 1 year");


        spinnerProfileCreatedOn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        days = "0";
                        break;
                    case 1:
                        days = "7";
                        break;
                    case 2:
                        days = "14";
                        break;
                    case 3:
                        days = "21";
                        break;
                    case 4:
                        days = "30";
                        break;
                    case 5:
                        days = "60";
                        break;
                    case 6:
                        days = "90";
                        break;
                    case 7:
                        days = "180";
                        break;
                    case 8:
                        days = "270";
                        break;
                    case 9:
                        days = "365";
                        break;
                    default:
                        days = "0";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getContext(), createdList);
        spinnerProfileCreatedOn.setAdapter(spinnerAdapter);

        ageSelectorRsb.setNotifyWhileDragging(true);
        ageSelectorRsb.setRangeValues(18, 60);
        ageSelectorRsb.setOnRangeSeekBarChangeListener((bar, minValue, maxValue) -> {
            tvMinAge.setText("Min " + minValue + " yrs");
            tvMaxAge.setText("Min " + maxValue + " yrs");
        });

        rl_search.setOnClickListener(this);
        ll_select_religion.setOnClickListener(this);


        SpinReligioous();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_select_religion:
                dialogOpenFor = 1;
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.search_dialog);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                recyclerView = dialog.findViewById(R.id.rv_books);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                multiSelectAdapter = new MultiSelectAdapter(religiousModelArrayList, getContext());
                recyclerView.setAdapter(multiSelectAdapter);
                submit_dialog = dialog.findViewById(R.id.btn_submit_dialog);
                submit_dialog.setOnClickListener(this);
                dialog.show();

                break;

            case R.id.rl_search:

                Bundle bundle = new Bundle();
                bundle.putString("type", "smart");
                //   bundle.putString("gender", gender);
                bundle.putString("min", ageSelectorRsb.getSelectedMinValue().toString());
                bundle.putString("max", ageSelectorRsb.getSelectedMaxValue().toString());
                if (religionArray == null || religionArray.equalsIgnoreCase("")) {
                    religionArray = "[\"Any\"]";
                }
                Log.d("dataReligion", religionArray);
                bundle.putString("religion", religionArray);
                //  bundle.putString("caste", castArray);
                bundle.putString("profile", days);

                if (cbProfileWithPhoto.isChecked()) {
                    bundle.putString("photo", "Show profiles with Photo");
                } else {
                    bundle.putString("photo", "");
                }

                MainActivity activity = (MainActivity) getActivity();
                assert activity != null;
                activity.loadFragment(Constant.SEARCH_RESULT_FRAGMENT, bundle);
                break;


            case R.id.btn_submit_dialog:
                String str = "";

                if (dialogOpenFor == 1) {
                    religionArray = "";
                    for (ReligiousModel model : religiousModelArrayList) {
                        if (model.isSelected()) {
                            str = str + model.getName() + ", ";
                            religionArray = religionArray + "\"" + model.getId() + "\"" + ",";
                            //  castReligious(model.getId());
                        }
                        tvSelectReligion.setText(str);
                        // tvSelectCast.setText("Any Cast");
                    }
                    try {
                        religionArray = removeLastChar(religionArray);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    religionArray = "[" + religionArray + "]";
                    Log.d("dataReligion", religionArray);
                }


                dialog.dismiss();
                break;

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void SpinReligioous() {

        progress_bar.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, ApiClient.BASE_URL + "get_religion",
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

                                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                                        if (jsonArray.length() == 0) {
                                            ReligiousModel studentDetailClassModel1 = new ReligiousModel("", "No Record Found", "", "");
                                            religiousModelArrayList.add(studentDetailClassModel1);
                                            progress_bar.setVisibility(View.INVISIBLE);

                                        }
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                            ReligiousModel studentDetailClassMode = new ReligiousModel(jsonObject1.getString("id"),
                                                    jsonObject1.getString("name"),
                                                    jsonObject1.getString("sortorder"),
                                                    jsonObject1.getString("status"), false);

                                            religion.add(jsonObject1.getString("name"));
                                            religiousModelArrayList.add(studentDetailClassMode);
                                            progress_bar.setVisibility(View.INVISIBLE);

                                        }
                                        multiSelectAdapter.notifyDataSetChanged();

                                    } else {
                                        progress_bar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getContext(), "Error1!!!..", Toast.LENGTH_LONG).show();

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                multiSelectAdapter.notifyDataSetChanged();
                                Log.v("errrr", multiSelectAdapter.getItemCount() + "");
                                //spinnerReligiousDate.setItems(religion);
                                //spinnerReligiousDate.setAdapter(new ReligiousAdapter(getContext(), religiousModelArrayList));


                            } catch (OutOfMemoryError | NullPointerException e) {
                                //  progress_bar.setVisibility(View.INVISIBLE);
                                // TODO: handle exception
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            progress_bar.setVisibility(View.INVISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //===if response error then dismiss progress==========//

                Log.v("errrr", String.valueOf(error));
                progress_bar.setVisibility(View.INVISIBLE);
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
