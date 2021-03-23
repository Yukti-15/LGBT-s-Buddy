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
import com.umeeds.app.adapter.MultiSelectAdapter;
import com.umeeds.app.adapter.MultiSelectSpecialAdapter;
import com.umeeds.app.model.serachmodel.SpecialCaseModel;
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
public class SpecialSearchFragment extends Fragment implements View.OnClickListener {

    RangeSeekBar ageSelectorRsb;
    TextView tvMinAge, tvMaxAge, tvSelectReligion;
    CheckBox cbProfileWithPhoto;
    LinearLayout ll_special_case;
    RecyclerView recyclerView;
    Dialog dialog;
    Button submit_dialog;
    int dialogOpenFor;
    MultiSelectSpecialAdapter multiSelectSpecialAdapter;
    MultiSelectAdapter multiSelectAdapter;

    ArrayList<SpecialCaseModel> specialityList = new ArrayList<>();
    ProgressBar progress_bar;
    Spinner spinnerProfileCreatedOn;
    LinearLayout ll_select_religion;
    ArrayList<String> religion = new ArrayList<>();
    RelativeLayout rl_search;
    String religionArray;
    private final ArrayList<ReligiousModel> religiousModelArrayList = new ArrayList<>();
    TextView tvSpecialCase;
    String specialCaseArray = "";

    public SpecialSearchFragment() {
        // Required empty public constructor
    }

    public static SpecialSearchFragment newInstance() {
        return new SpecialSearchFragment();
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_special_search, container, false);

        ageSelectorRsb = view.findViewById(R.id.age_selector_rsb);
        tvMinAge = view.findViewById(R.id.tv_min_age);
        tvMaxAge = view.findViewById(R.id.tv_max_age);
        tvSelectReligion = view.findViewById(R.id.tv_select_religion);
        cbProfileWithPhoto = view.findViewById(R.id.cb_profile_with_photo);
        ll_special_case = view.findViewById(R.id.ll_special_case);
        progress_bar = view.findViewById(R.id.progress_bar);

        spinnerProfileCreatedOn = view.findViewById(R.id.spinner_profile_created_on);
        ll_select_religion = view.findViewById(R.id.ll_select_religion);
        rl_search = view.findViewById(R.id.rl_search);
        tvSpecialCase = view.findViewById(R.id.tv_special_case);

        specialityList.clear();
        // specialityList.add(new SpecialCaseModel("Physically challenged from birth", false));
        //  specialityList.add(new SpecialCaseModel("Physically challenged due to accident", false));
        //  specialityList.add(new SpecialCaseModel("Mentally challenged from birth", false));
        //  specialityList.add(new SpecialCaseModel("Mentally challenged due to accident", false));
        //  specialityList.add(new SpecialCaseModel("Physical abnormality affecting only looks", false));
        //  specialityList.add(new SpecialCaseModel("Physical abnormality affecting bodily functions", false));
        //  specialityList.add(new SpecialCaseModel("Physically and mentally challenged", false));
        //  specialityList.add(new SpecialCaseModel("HIV positive", false));
        specialityList.add(new SpecialCaseModel("Physically challenged", false));

        ageSelectorRsb.setRangeValues(18, 60);
        ageSelectorRsb.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                tvMinAge.setText("Min " + minValue + " yrs");
                tvMaxAge.setText("Min " + maxValue + " yrs");
            }
        });

        ll_select_religion.setOnClickListener(this);
        ll_special_case.setOnClickListener(this);
        rl_search.setOnClickListener(this);
        SpinReligioous();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

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
            case R.id.ll_special_case:
                dialogOpenFor = 3;
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.search_dialog);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                recyclerView = dialog.findViewById(R.id.rv_books);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                multiSelectSpecialAdapter = new MultiSelectSpecialAdapter(specialityList, getContext());
                recyclerView.setAdapter(multiSelectSpecialAdapter);
                submit_dialog = dialog.findViewById(R.id.btn_submit_dialog);
                submit_dialog.setOnClickListener(this);
                dialog.show();
                break;

            case R.id.rl_search:

                if (specialCaseArray == null | specialCaseArray.isEmpty()) {
                    Toast.makeText(getContext(), "Please select Special case", Toast.LENGTH_SHORT).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "special");
                    bundle.putString("min", ageSelectorRsb.getSelectedMinValue().toString());
                    bundle.putString("max", ageSelectorRsb.getSelectedMaxValue().toString());
                    if (religionArray == null || religionArray.equalsIgnoreCase("")) {
                        religionArray = "[\"Any\"]";
                    }

                    Log.d("dataReligion", religionArray);
                    bundle.putString("religion", religionArray);
                    if (cbProfileWithPhoto.isChecked()) {
                        bundle.putString("photo", "Show profiles with Photo");
                    } else {
                        bundle.putString("photo", "");
                    }
                    if (specialCaseArray == null | specialCaseArray.isEmpty()) {
                        specialCaseArray = "Any";
                    }
                    bundle.putString("special", specialCaseArray);
                    MainActivity activity = (MainActivity) getActivity();
                    assert activity != null;
                    activity.loadFragment(Constant.SEARCH_RESULT_FRAGMENT, bundle);

                }
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

                str = "";
                specialCaseArray = "";
                if (dialogOpenFor == 3) {
                    for (SpecialCaseModel m : specialityList) {
                        if (m.isSelected()) {
                            str = str + m.getSpecialCase() + ", ";
                            specialCaseArray = specialCaseArray + "\"" + m.getSpecialCase() + "\"" + ",";
                        }
                        tvSpecialCase.setText(str);
                    }
                    try {
                        specialCaseArray = removeLastChar(specialCaseArray);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    specialCaseArray = "[" + specialCaseArray + "]";
                    Log.d("dataCast", specialCaseArray);
                }

                dialog.dismiss();
                break;
        }

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
