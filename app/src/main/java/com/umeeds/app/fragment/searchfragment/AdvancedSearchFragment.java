package com.umeeds.app.fragment.searchfragment;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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
import com.umeeds.app.adapter.MultiCitySelectAdapter;
import com.umeeds.app.adapter.MultiSelectCountryAdapter;
import com.umeeds.app.adapter.MultiSelectEducationAdapter;
import com.umeeds.app.adapter.MultiSelectOccupationAdapter;
import com.umeeds.app.adapter.MultiSelectReligionAdapter;
import com.umeeds.app.adapter.MultiStateSelectAdapter;
import com.umeeds.app.model.serachmodel.CityLivingInModel;
import com.umeeds.app.model.serachmodel.CountryLivingInModel;
import com.umeeds.app.model.serachmodel.OccupicationModel;
import com.umeeds.app.model.serachmodel.StateLivingInModel;
import com.umeeds.app.model.usermodel.EducationModel;
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
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvancedSearchFragment extends Fragment implements View.OnClickListener {

    RangeSeekBar ageSelectorRsb;

    Unbinder unbinder;
    ArrayList<String> religion = new ArrayList<>();
    String gender;
    String marital_status;
    RecyclerView recyclerView;
    Dialog dialog;
    Button submit_dialog;
    MultiSelectReligionAdapter multiReligionAdapter;
    MultiSelectOccupationAdapter multiSelectOccupationAdapter;
    MultiSelectEducationAdapter multiSelectEducationAdapter;
    int dialogOpenFor;
    MultiCitySelectAdapter multiCitySelectAdapter;
    String castArray;
    String religionArray;
    MultiSelectCountryAdapter multiCountryAdapter;
    MultiStateSelectAdapter multiStateAdapter;
    String countryArray, stateArray, cityArray, eduArray, occuArray;
    ProgressBar progress_bar;
    RadioButton rbGroom;
    @BindView(R.id.rb_bride)
    RadioButton rbBride;
    RadioButton nevr_married_radio_btn;
    RadioButton widowed_radio_btn;
    RadioButton divorced_radio_btn;
    RadioButton seperated_radio_btn;
    TextView tvMinAge;
    TextView tvMaxAge;
    CheckBox cbProfileWithPhoto;
    RelativeLayout rlSearch;
    TextView tvSelectReligion;
    LinearLayout ll_select_religion;
    LinearLayout ll_select_state;
    LinearLayout ll_select_city;
    LinearLayout ll_select_occup;
    LinearLayout ll_select_edu;
    RelativeLayout rl_search;
    TextView tvSelectCountry;
    LinearLayout ll_select_country;
    TextView tvSelectState;
    TextView tvSelectEdu;
    LinearLayout llSelectEdu;
    TextView tvSelectCity;
    TextView tvSelectOccup;
    List<String> genderlist = new ArrayList<>();
    List<String> sexualOrientationlist = new ArrayList<>();
    private final ArrayList<ReligiousModel> religiousModelArrayList = new ArrayList<>();
    private final ArrayList<EducationModel> educationModelArrayList = new ArrayList<>();
    private final ArrayList<OccupicationModel> occupicationModelArrayList = new ArrayList<>();
    private final ArrayList<CountryLivingInModel> countryLivingInModelArrayList = new ArrayList<>();
    private final ArrayList<StateLivingInModel> stateLivingInModelArrayList = new ArrayList<>();
    private final ArrayList<CityLivingInModel> cityLivingInModelArrayList = new ArrayList<>();
    String Gender, sexualOrientation;
    Spinner sp_gender, sp_sexualOrientation;
    private SpinnerAdapter genderAdapter;
    private SpinnerAdapter sexualOrientationAdapter;

    public AdvancedSearchFragment() {
        // Required empty public constructor
    }

    public static AdvancedSearchFragment newInstance() {
        return new AdvancedSearchFragment();
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_advanced_search, container, false);
        unbinder = ButterKnife.bind(this, view);

        progress_bar = view.findViewById(R.id.progress_bar);
        ageSelectorRsb = view.findViewById(R.id.age_selector_rsb);
        ll_select_religion = view.findViewById(R.id.ll_select_religion);
        ll_select_country = view.findViewById(R.id.ll_select_country);
        ll_select_state = view.findViewById(R.id.ll_select_state);
        ll_select_city = view.findViewById(R.id.ll_select_city);
        ll_select_occup = view.findViewById(R.id.ll_select_occup);
        ll_select_edu = view.findViewById(R.id.ll_select_edu);
        nevr_married_radio_btn = view.findViewById(R.id.nevr_married_radio_btn);
        widowed_radio_btn = view.findViewById(R.id.widowed_radio_btn);
        divorced_radio_btn = view.findViewById(R.id.divorced_radio_btn);
        seperated_radio_btn = view.findViewById(R.id.seperated_radio_btn);
        tvMinAge = view.findViewById(R.id.tv_min_age);
        tvMaxAge = view.findViewById(R.id.tv_max_age);
        cbProfileWithPhoto = view.findViewById(R.id.cb_profile_with_photo);
        rlSearch = view.findViewById(R.id.rl_search);
        tvSelectReligion = view.findViewById(R.id.tv_select_religion);
        rbGroom = view.findViewById(R.id.rb_groom);
        tvSelectCountry = view.findViewById(R.id.tv_select_country);
        tvSelectState = view.findViewById(R.id.tv_select_state);
        tvSelectCity = view.findViewById(R.id.tv_select_city);
        tvSelectEdu = view.findViewById(R.id.tv_select_edu);
        ll_select_edu = view.findViewById(R.id.ll_select_edu);
        tvSelectOccup = view.findViewById(R.id.tv_select_occup);

        rl_search = view.findViewById(R.id.rl_search);
        sp_gender = view.findViewById(R.id.sp_gender);
        sp_sexualOrientation = view.findViewById(R.id.sp_sexualOrientation);

        SpinReligioous();
        educationApi();
        occupicationApi();
        countryLivingInApi();
        init();
        ageSelectorRsb.setRangeValues(18, 60);
        ageSelectorRsb.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                tvMinAge.setText("Min " + minValue + " yrs");
                tvMaxAge.setText("Min " + maxValue + " yrs");
            }
        });
        ll_select_religion.setOnClickListener(this);
        ll_select_country.setOnClickListener(this);
        ll_select_state.setOnClickListener(this);
        ll_select_city.setOnClickListener(this);
        ll_select_occup.setOnClickListener(this);
        ll_select_edu.setOnClickListener(this);
        rl_search.setOnClickListener(this);
        nevr_married_radio_btn.setOnClickListener(this);
        widowed_radio_btn.setOnClickListener(this);
        divorced_radio_btn.setOnClickListener(this);
        seperated_radio_btn.setOnClickListener(this);


        return view;
    }

    public void init() {

        genderAdapter = new SpinnerAdapter(getContext());
        sp_gender.setAdapter(genderAdapter);
        genderlist.add("Select");
        genderlist.add("Male");
        genderlist.add("Female");
        genderlist.add("Transgender");

        genderAdapter.addTaxRateList(genderlist);
        sp_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (genderlist.size() > 0) {
                    Gender = genderlist.get(position);
                    int position1 = sp_gender.getSelectedItemPosition();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sexualOrientationAdapter = new SpinnerAdapter(getContext());
        sp_sexualOrientation.setAdapter(sexualOrientationAdapter);
        sexualOrientationlist.add("Select");
        sexualOrientationlist.add("Gay");
        sexualOrientationlist.add("Lesbian");
        sexualOrientationlist.add("Bisexual (Man)");
        sexualOrientationlist.add("Bisexual (Woman)");
        sexualOrientationlist.add("Transgender Man");
        sexualOrientationlist.add("Transgender Woman");

        sexualOrientationAdapter.addTaxRateList(sexualOrientationlist);
        sp_sexualOrientation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sexualOrientationlist.size() > 0) {
                    sexualOrientation = sexualOrientationlist.get(position);
                    int position1 = sp_sexualOrientation.getSelectedItemPosition();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                multiReligionAdapter = new MultiSelectReligionAdapter(religiousModelArrayList, getContext());
                recyclerView.setAdapter(multiReligionAdapter);
                submit_dialog = dialog.findViewById(R.id.btn_submit_dialog);
                submit_dialog.setOnClickListener(this);
                dialog.show();
                break;

            case R.id.ll_select_country:
                dialogOpenFor = 3;
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.search_dialog);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                recyclerView = dialog.findViewById(R.id.rv_books);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                multiCountryAdapter = new MultiSelectCountryAdapter(countryLivingInModelArrayList, getContext());
                recyclerView.setAdapter(multiCountryAdapter);
                submit_dialog = dialog.findViewById(R.id.btn_submit_dialog);
                submit_dialog.setOnClickListener(this);
                dialog.show();
                break;
            case R.id.ll_select_state:
                dialogOpenFor = 4;
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.search_dialog);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                recyclerView = dialog.findViewById(R.id.rv_books);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                multiStateAdapter = new MultiStateSelectAdapter(stateLivingInModelArrayList, getContext());
                recyclerView.setAdapter(multiStateAdapter);
                submit_dialog = dialog.findViewById(R.id.btn_submit_dialog);
                submit_dialog.setOnClickListener(this);
                dialog.show();
                break;
            case R.id.ll_select_city:
                dialogOpenFor = 5;
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.search_dialog);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                recyclerView = dialog.findViewById(R.id.rv_books);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                multiCitySelectAdapter = new MultiCitySelectAdapter(cityLivingInModelArrayList, getContext());
                recyclerView.setAdapter(multiCitySelectAdapter);
                submit_dialog = dialog.findViewById(R.id.btn_submit_dialog);
                submit_dialog.setOnClickListener(this);
                dialog.show();
                break;
            case R.id.ll_select_occup:
                dialogOpenFor = 6;
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.search_dialog);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                recyclerView = dialog.findViewById(R.id.rv_books);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                multiSelectOccupationAdapter = new MultiSelectOccupationAdapter(occupicationModelArrayList, getContext());
                recyclerView.setAdapter(multiSelectOccupationAdapter);
                submit_dialog = dialog.findViewById(R.id.btn_submit_dialog);
                submit_dialog.setOnClickListener(this);
                dialog.show();
                break;
            case R.id.ll_select_edu:
                dialogOpenFor = 7;
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.search_dialog);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                recyclerView = dialog.findViewById(R.id.rv_books);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                multiSelectEducationAdapter = new MultiSelectEducationAdapter(educationModelArrayList, getContext());
                recyclerView.setAdapter(multiSelectEducationAdapter);
                submit_dialog = dialog.findViewById(R.id.btn_submit_dialog);
                submit_dialog.setOnClickListener(this);
                dialog.show();
                break;
            case R.id.nevr_married_radio_btn:
                if (nevr_married_radio_btn.isChecked()) {
                    marital_status = "never";
                    divorced_radio_btn.setChecked(false);
                    seperated_radio_btn.setChecked(false);
                    widowed_radio_btn.setChecked(false);
                }
                break;
            case R.id.widowed_radio_btn:
                if (widowed_radio_btn.isChecked()) {
                    marital_status = "widowed";
                    divorced_radio_btn.setChecked(false);
                    seperated_radio_btn.setChecked(false);
                    nevr_married_radio_btn.setChecked(false);
                }
                break;
            case R.id.divorced_radio_btn:
                if (divorced_radio_btn.isChecked()) {
                    marital_status = "divorced";
                    nevr_married_radio_btn.setChecked(false);
                    widowed_radio_btn.setChecked(false);
                    seperated_radio_btn.setChecked(false);
                }
                break;
            case R.id.seperated_radio_btn:
                if (seperated_radio_btn.isChecked()) {
                    marital_status = "separated";
                    nevr_married_radio_btn.setChecked(false);
                    widowed_radio_btn.setChecked(false);
                    divorced_radio_btn.setChecked(false);

                }
                break;
            case R.id.rl_search:

              /*  if (!(nevr_married_radio_btn.isChecked() | widowed_radio_btn.isChecked() | seperated_radio_btn.isChecked() | divorced_radio_btn.isChecked())) {
                    Toast.makeText(getContext(), "Please select a your marital status", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                if (Gender.equalsIgnoreCase("Select") || sexualOrientation.equals("Select")) {
                    Toast.makeText(getContext(), "Please select value", Toast.LENGTH_SHORT).show();
                } else {

                    if (countryArray == null || countryArray.equalsIgnoreCase("")) {
                        countryArray = "[\"Any\"]";
                    }
                    if (stateArray == null || stateArray.equalsIgnoreCase("")) {
                        stateArray = "[\"Any\"]";
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "advanced");
                    bundle.putString("min", ageSelectorRsb.getSelectedMinValue().toString());
                    bundle.putString("max", ageSelectorRsb.getSelectedMaxValue().toString());
                    bundle.putString("religion", religionArray);
                    bundle.putString("caste", castArray);
                    bundle.putString("country", countryArray);
                    bundle.putString("state", stateArray);
                    bundle.putString("city", cityArray);
                    bundle.putString("occu", occuArray);
                    bundle.putString("edu", eduArray);
                    if (nevr_married_radio_btn.isChecked()) {
                        marital_status = "Unmarried";
                    } else {
                        marital_status = "married";
                    }
                    if (cbProfileWithPhoto.isChecked()) {
                        bundle.putString("photo", "Show profiles with Photo");
                    } else {
                        bundle.putString("photo", "");
                    }
                    bundle.putString("marital_status", marital_status);
                    if (Gender.equals("Select")) {
                        bundle.putString("Gender", "");

                    } else {
                        bundle.putString("Gender", Gender);
                    }
                    if (sexualOrientation.equals("Select")) {
                        bundle.putString("iam", "");
                    } else {
                        bundle.putString("iam", sexualOrientation);
                    }

                    MainActivity activity = (MainActivity) getActivity();
                    assert activity != null;
                    activity.loadFragment(Constant.SEARCH_RESULT_FRAGMENT, bundle);
                }

                break;

            case R.id.btn_submit_dialog:
                String str = "";
                if (dialogOpenFor == 1) {
                    religionArray = "";
                    // castModelArrayList.clear();
                    for (ReligiousModel model : religiousModelArrayList) {
                        if (model.isSelected()) {
                            str = str + model.getName() + ", ";
                            religionArray = religionArray + "\"" + model.getId() + "\"" + ",";
                            //   castReligious(model.getId());
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
                dialog.dismiss();
                if (dialogOpenFor == 3) {
                    countryArray = "";
                    stateLivingInModelArrayList.clear();
                    for (CountryLivingInModel model : countryLivingInModelArrayList) {
                        if (model.isSelected()) {
                            str = str + model.getCountry() + ", ";
                            countryArray = countryArray + "\"" + model.getCountry_id() + "\"" + ",";
                            stateLivingIn(model.getCountry_id());
                        }
                        tvSelectCountry.setText(str);
                        tvSelectState.setText("Any State");
                    }
                    try {
                        countryArray = removeLastChar(countryArray);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    countryArray = "[" + countryArray + "]";
                    Log.d("dataReligion", countryArray);
                }
                str = "";
                stateArray = "";
                if (dialogOpenFor == 4) {
                    for (StateLivingInModel m : stateLivingInModelArrayList) {
                        if (m.isSelected()) {
                            str = str + m.getState() + ", ";
                            stateArray = stateArray + "\"" + m.getState_id() + "\"" + ",";
                            cityLivingIn(m.getState());
                        }
                        tvSelectState.setText(str);
                    }
                    try {
                        stateArray = removeLastChar(stateArray);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    stateArray = "[" + stateArray + "]";
                    Log.d("dataCast", stateArray);
                }
                str = "";
                cityArray = "";
                if (dialogOpenFor == 5) {
                    for (CityLivingInModel m : cityLivingInModelArrayList) {
                        if (m.isSelected()) {
                            str = str + m.getCity() + ", ";
                            cityArray = cityArray + "\"" + m.getId() + "\"" + ",";
                        }
                        tvSelectCity.setText(str);
                    }
                    try {
                        cityArray = removeLastChar(cityArray);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    cityArray = "[" + cityArray + "]";
                    Log.d("dataCast", cityArray);
                }
                str = "";
                occuArray = "";
                if (dialogOpenFor == 6) {
                    for (OccupicationModel m : occupicationModelArrayList) {
                        if (m.isSelected()) {
                            str = str + m.getOccupation() + ", ";
                            cityArray = cityArray + "\"" + m.getOccupation() + "\"" + ",";
                        }
                        tvSelectOccup.setText(str);
                    }
                    try {
                        occuArray = removeLastChar(occuArray);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    occuArray = "[" + occuArray + "]";
                    Log.d("dataCast", occuArray);

                }
                str = "";
                eduArray = "";
                if (dialogOpenFor == 7) {
                    for (EducationModel m : educationModelArrayList) {
                        if (m.isSelected()) {
                            str = str + m.getEducation() + ", ";
                            cityArray = cityArray + "\"" + m.getEducation() + "\"" + ",";
                        }
                        tvSelectEdu.setText(str);
                    }
                    try {
                        eduArray = removeLastChar(eduArray);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    eduArray = "[" + eduArray + "]";
                    Log.d("dataCast", eduArray);
                }
                dialog.dismiss();
                break;

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
                                        multiReligionAdapter.notifyDataSetChanged();

                                    } else {
                                        progress_bar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getContext(), "Error1!!!..", Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                multiReligionAdapter.notifyDataSetChanged();
                                Log.v("errrr", multiReligionAdapter.getItemCount() + "");
                                //spinnerReligiousDate.setItems(religion);
                                //spinnerReligiousDate.setAdapter(new ReligiousAdapter(getContext(), religiousModelArrayList));


                            } catch (OutOfMemoryError | NullPointerException e) {
                                progress_bar.setVisibility(View.INVISIBLE);
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

    private void stateLivingIn(final String stateID) {

        progress_bar.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, ApiClient.BASE_URL + "get_state",
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
                                        stateLivingInModelArrayList.clear();
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                                        if (jsonArray.length() == 0) {
                                            StateLivingInModel stateLivingInModel1 = new StateLivingInModel("", "No Record Found", "", "", "", "");
                                            stateLivingInModelArrayList.add(stateLivingInModel1);
                                            progress_bar.setVisibility(View.INVISIBLE);

                                        }
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                            StateLivingInModel stateLivingInModel1 = new StateLivingInModel(jsonObject1.getString("state_id"),
                                                    jsonObject1.getString("state"),
                                                    jsonObject1.getString("code"),
                                                    jsonObject1.getString("country_id"),
                                                    jsonObject1.getString("status"),
                                                    jsonObject1.getString("sortorder"), false);


                                            stateLivingInModelArrayList.add(stateLivingInModel1);
                                            progress_bar.setVisibility(View.INVISIBLE);

                                        }
                                    } else {
                                        progress_bar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getContext(), "Error1!!!..", Toast.LENGTH_LONG).show();

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //spinnerState.setAdapter(new StateLivingInAdapter(getContext(), stateLivingInModelArrayList));


                            } catch (OutOfMemoryError | NullPointerException e) {
                                progress_bar.setVisibility(View.INVISIBLE);
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
                params.put("country_id", stateID);

                return params;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(postRequest);
    }

    private void countryLivingInApi() {

        progress_bar.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, ApiClient.BASE_URL + "get_country",
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
                                            CountryLivingInModel countryLivingInModel1 = new CountryLivingInModel("", "No Record Found", "", "", "");
                                            countryLivingInModelArrayList.add(countryLivingInModel1);
                                            //progress_bar.setVisibility(View.INVISIBLE);

                                        }
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                            CountryLivingInModel countryLivingInModel1 = new CountryLivingInModel(jsonObject1.getString("country_id"),
                                                    jsonObject1.getString("country"),
                                                    jsonObject1.getString("code"),
                                                    jsonObject1.getString("status"),
                                                    jsonObject1.getString("sortorder"), false);


                                            countryLivingInModelArrayList.add(countryLivingInModel1);
                                            progress_bar.setVisibility(View.INVISIBLE);

                                        }
                                    } else {
                                        progress_bar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getContext(), "Error1!!!..", Toast.LENGTH_LONG).show();

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //spinnerCountry.setAdapter(new CountryLivingInAdapter(getContext(), countryLivingInModelArrayList));
                                //BF_country_living_id.setAdapter(new CountryLivingInAdapter(SignupThirdPageActivity.this, countryLivingInModelArrayList));


                            } catch (OutOfMemoryError | NullPointerException e) {
                                //progress_bar.setVisibility(View.INVISIBLE);
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

    private void cityLivingIn(final String state_name) {

        progress_bar.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, ApiClient.BASE_URL + "get_city",
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
                                        stateLivingInModelArrayList.clear();
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                                        if (jsonArray.length() == 0) {
                                            CityLivingInModel cityLivingInModel = new CityLivingInModel("", "No Record Found", "", "", "", "", false);
                                            cityLivingInModelArrayList.add(cityLivingInModel);
                                            progress_bar.setVisibility(View.INVISIBLE);

                                        }
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                            CityLivingInModel cityLivingInModel = new CityLivingInModel(
                                                    jsonObject1.getString("id"),
                                                    jsonObject1.getString("city"),
                                                    jsonObject1.getString("state"),
                                                    jsonObject1.getString("country"),
                                                    jsonObject1.getString("sortorder"),
                                                    jsonObject1.getString("status"), false);


                                            cityLivingInModelArrayList.add(cityLivingInModel);
                                            progress_bar.setVisibility(View.INVISIBLE);

                                        }
                                    } else {
                                        progress_bar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getContext(), "Error1!!!..", Toast.LENGTH_LONG).show();

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.d("cityList", "listsize>>" + cityLivingInModelArrayList.size());
                                //spinnerState.setAdapter(new StateLivingInAdapter(getContext(), stateLivingInModelArrayList));


                            } catch (OutOfMemoryError | NullPointerException e) {
                                progress_bar.setVisibility(View.INVISIBLE);
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
                params.put("state_name", state_name);

                return params;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(postRequest);
    }

    private void educationApi() {

        progress_bar.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, ApiClient.BASE_URL + "get_education",
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

                                        EducationModel educationModel = new EducationModel("", "Education", "", "");
                                        educationModelArrayList.add(educationModel);
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                                        if (jsonArray.length() == 0) {
                                            EducationModel educationModel1 = new EducationModel("", "No Record Found", "", "");
                                            educationModelArrayList.add(educationModel1);
                                            progress_bar.setVisibility(View.INVISIBLE);

                                        }
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                            EducationModel educationModel1 = new EducationModel(jsonObject1.getString("education_id"),
                                                    jsonObject1.getString("education"),
                                                    jsonObject1.getString("status"),
                                                    jsonObject1.getString("sortorder"), false);


                                            educationModelArrayList.add(educationModel1);
                                            progress_bar.setVisibility(View.INVISIBLE);

                                        }
                                    } else {
                                        progress_bar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getContext(), "Error1!!!..", Toast.LENGTH_LONG).show();

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            } catch (OutOfMemoryError | NullPointerException e) {
                                progress_bar.setVisibility(View.INVISIBLE);
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

    private void occupicationApi() {
        progress_bar.setVisibility(View.VISIBLE);
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
                                            progress_bar.setVisibility(View.INVISIBLE);

                                        }
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                            OccupicationModel occupicationModel1 = new OccupicationModel(jsonObject1.getString("occupation_id"),
                                                    jsonObject1.getString("occupation"),
                                                    jsonObject1.getString("status"),
                                                    jsonObject1.getString("sortorder"), false);


                                            occupicationModelArrayList.add(occupicationModel1);
                                            progress_bar.setVisibility(View.INVISIBLE);

                                        }
                                    } else {
                                        progress_bar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getContext(), "Error1!!!..", Toast.LENGTH_LONG).show();

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                multiSelectOccupationAdapter.notifyDataSetChanged();
                                //spinnerOccupation.setAdapter(new OccupicationAdapter(getContext(), occupicationModelArrayList));


                            } catch (OutOfMemoryError | NullPointerException e) {
                                progress_bar.setVisibility(View.INVISIBLE);
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

    public class SpinnerAdapter extends BaseAdapter {

        private List<String> spinnerList;
        private final LayoutInflater layoutInflater;

        public SpinnerAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return null == spinnerList ? 0 : spinnerList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public void addTaxRateList(List<String> spinnerList) {
            this.spinnerList = spinnerList;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = layoutInflater.inflate(R.layout.item_spinner, parent, false);
            TextView stateTxt = convertView.findViewById(R.id.spinner_text_view);
            stateTxt.setText(spinnerList.get(position));
            return convertView;
        }
    }

}
