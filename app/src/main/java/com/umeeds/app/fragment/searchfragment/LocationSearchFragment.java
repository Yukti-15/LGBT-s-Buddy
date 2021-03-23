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
import com.umeeds.app.adapter.MultiStateSelectAdapter;
import com.umeeds.app.model.serachmodel.CityLivingInModel;
import com.umeeds.app.model.serachmodel.CountryLivingInModel;
import com.umeeds.app.model.serachmodel.StateLivingInModel;
import com.umeeds.app.model.usermodel.CityModel;
import com.umeeds.app.model.usermodel.CountryModel;
import com.umeeds.app.model.usermodel.StateModel;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationSearchFragment extends Fragment implements View.OnClickListener {


    RangeSeekBar ageSelectorRsb;
    TextView tvMinAge, tvMaxAge;
    CheckBox cbProfileWithPhoto;
    RelativeLayout rl_search;
    LinearLayout ll_select_state, ll_select_country;
    TextView tvSelectCountry, tvSelectState;
    String countryId, countryName, stateName, stateId, cityId, cityName;
    RecyclerView recyclerView;
    Dialog dialog;
    int dialogOpenFor;
    String gender;
    Button submit_dialog;
    String countryArray, stateArray, cityArray;
    MultiSelectCountryAdapter multiSelectAdapter;
    MultiStateSelectAdapter multiStateAdapter;
    MultiCitySelectAdapter multiCitySelectAdapter;
    Button btn_submit_dialog;
    ProgressBar progress_bar;
    List<String> sexualOrientationlist = new ArrayList<>();
    String sexualOrientation;
    Spinner sp_country, sp_state, sp_city, sp_sexualOrientation;
    private final ArrayList<CountryLivingInModel> countryLivingInModelArrayList = new ArrayList<>();
    private final ArrayList<StateLivingInModel> stateLivingInModelArrayList = new ArrayList<>();
    private final ArrayList<CityLivingInModel> cityLivingInModelArrayList = new ArrayList<>();
    private ArrayList<CountryModel> countryModelArrayList;
    private ArrayList<StateModel> stateModelArrayList;
    private ArrayList<CityModel> cityModelArrayList;
    private SpinnerAdapter sexualOrientationAdapter;


    public LocationSearchFragment() {
        // Required empty public constructor
    }

    public static LocationSearchFragment newInstance() {
        return new LocationSearchFragment();
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location_search, container, false);

        rl_search = view.findViewById(R.id.rl_search);
        ageSelectorRsb = view.findViewById(R.id.age_selector_rsb);
        tvMinAge = view.findViewById(R.id.tv_min_age);
        tvMaxAge = view.findViewById(R.id.tv_max_age);
        cbProfileWithPhoto = view.findViewById(R.id.cb_profile_with_photo);
        ll_select_country = view.findViewById(R.id.ll_select_country);
        ll_select_state = view.findViewById(R.id.ll_select_state);
        tvSelectCountry = view.findViewById(R.id.tv_select_country);
        tvSelectState = view.findViewById(R.id.tv_select_state);
        progress_bar = view.findViewById(R.id.progress_bar);
        btn_submit_dialog = view.findViewById(R.id.btn_submit_dialog);
        sp_sexualOrientation = view.findViewById(R.id.sp_sexualOrientation);


        sp_country = view.findViewById(R.id.sp_country);
        sp_state = view.findViewById(R.id.sp_state);
        sp_city = view.findViewById(R.id.sp_city);
        countryModelArrayList = new ArrayList<>();
        stateModelArrayList = new ArrayList<>();
        cityModelArrayList = new ArrayList<>();

        ageSelectorRsb.setRangeValues(18, 60);
        ageSelectorRsb.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                tvMinAge.setText("Min " + minValue + " yrs");
                tvMaxAge.setText("Min " + maxValue + " yrs");
            }
        });

        init();

        //countryLivingInApi();
        countryCode();

        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String bandId = (String) parent.getItemAtPosition(position);

                if (countryModelArrayList.size() > 0) {
                    countryId = countryModelArrayList.get(position).getCountry_id();
                    countryName = countryModelArrayList.get(position).getCountry();

                    if (countryId != null) {
                        getState(countryId);
                        getCity("");

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String bandId = (String) parent.getItemAtPosition(position);
                if (stateModelArrayList.size() > 0) {
                    stateId = stateModelArrayList.get(position).getState_id();
                    stateName = stateModelArrayList.get(position).getState();
                    cityModelArrayList.clear();
                    if (stateName != null) {
                        getCity(stateName);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String bandId = (String) parent.getItemAtPosition(position);

                if (cityModelArrayList.size() > 0) {
                    cityId = cityModelArrayList.get(position).getId();
                    cityName = cityModelArrayList.get(position).getCity();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ll_select_country.setOnClickListener(this);
        ll_select_state.setOnClickListener(this);
        rl_search.setOnClickListener(this);
        // btn_submit_dialog.setOnClickListener(this);

        return view;
    }

    public void init() {

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
            case R.id.ll_select_country:
                dialogOpenFor = 1;
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.search_dialog);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                recyclerView = dialog.findViewById(R.id.rv_books);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                multiSelectAdapter = new MultiSelectCountryAdapter(countryLivingInModelArrayList, getContext());
                recyclerView.setAdapter(multiSelectAdapter);
                submit_dialog = dialog.findViewById(R.id.btn_submit_dialog);
                submit_dialog.setOnClickListener(this);
                dialog.show();
                break;
            case R.id.ll_select_state:
                dialogOpenFor = 2;
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
           /* case R.id.ll_select_city:
                dialogOpenFor = 3;
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.search_dialog);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                recyclerView = dialog.findViewById(R.id.rv_books);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                multiCitySelectAdapter = new MultiCitySelectAdapter (cityLivingInModelArrayList, getContext());
                recyclerView.setAdapter(multiCitySelectAdapter);
                submit_dialog = dialog.findViewById(R.id.btn_submit_dialog);
                submit_dialog.setOnClickListener(this);
                dialog.show();
                break;
*/

            case R.id.rl_search:
                if (sexualOrientation.equals("Select")) {
                    Toast.makeText(getContext(), "Please select Sexual Orientation", Toast.LENGTH_SHORT).show();
                } else if (countryName == null || countryName.equals("")) {
                    Toast.makeText(getContext(), "Please select Country", Toast.LENGTH_SHORT).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "location");
                    bundle.putString("min", ageSelectorRsb.getSelectedMinValue().toString());
                    bundle.putString("max", ageSelectorRsb.getSelectedMaxValue().toString());
                    if (countryArray == null || countryArray.equalsIgnoreCase("")) {
                        countryArray = "[\"Any\"]";
                    }
                    if (stateArray == null || stateArray.equalsIgnoreCase("")) {
                        stateArray = "[\"Any\"]";
                    }
                    bundle.putString("country", countryId);
                    bundle.putString("state", stateId);
                    bundle.putString("city", cityName);
                    if (cbProfileWithPhoto.isChecked()) {
                        bundle.putString("photo", "Show profiles with Photo");
                    } else {
                        bundle.putString("photo", "");
                    }
                    bundle.putString("sexualOrientation", sexualOrientation);
                    MainActivity activity = (MainActivity) getActivity();
                    assert activity != null;
                    activity.loadFragment(Constant.SEARCH_RESULT_FRAGMENT, bundle);
                }


                break;

            case R.id.btn_submit_dialog:
                String str = "";
                if (dialogOpenFor == 1) {
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
                if (dialogOpenFor == 2) {
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
                if (dialogOpenFor == 3) {
                    for (CityLivingInModel m : cityLivingInModelArrayList) {
                        if (m.isSelected()) {
                            str = str + m.getCity() + ", ";
                            cityArray = cityArray + "\"" + m.getId() + "\"" + ",";
                        }
                        // tvSelectCity.setText(str);
                    }
                    try {
                        cityArray = removeLastChar(cityArray);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    cityArray = "[" + cityArray + "]";
                    Log.d("dataCast", cityArray);
                }
                dialog.dismiss();
                break;

        }
    }
    //----------------StateLivignIn Api-------------------------

    @Override
    public void onDestroyView() {
        super.onDestroyView();

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

                Log.v("params....", String.valueOf(params));

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

    private void countryCode() {
        //  progress_bar.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, ApiClient.BASE_URL + "get_country",
                new com.android.volley.Response.Listener<String>() {
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

                                        // CountryModel countryModel = new CountryModel("", "Country", "", "","");
                                        // countryModelArrayList.add(countryModel);
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                                        if (jsonArray.length() == 0) {
                                            CountryModel countryModel1 = new CountryModel("", "No Record Found", "", "", "");
                                            countryModelArrayList.add(countryModel1);
                                            // progress_bar.setVisibility(View.INVISIBLE);
                                        }
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                            CountryModel professionModel1 = new CountryModel(
                                                    jsonObject1.getString("country_id"),
                                                    jsonObject1.getString("country"),
                                                    jsonObject1.getString("code"),
                                                    jsonObject1.getString("status"),
                                                    jsonObject1.getString("sortorder"));
                                            countryModelArrayList.add(professionModel1);
                                            //  progress_bar.setVisibility(View.INVISIBLE);
                                        }
                                    } else {
                                        //    progress_bar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getContext(), "Error1!!!..", Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                sp_country.setAdapter(new CountryAdapter(getContext(), countryModelArrayList));

                            } catch (OutOfMemoryError | NullPointerException e) {
                                //   progress_bar.setVisibility(View.INVISIBLE);
                                // TODO: handle exception
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            //  progress_bar.setVisibility(View.INVISIBLE);
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //===if response error then dismiss progress==========//
                Log.v("errrr", String.valueOf(error));
                //  progress_bar.setVisibility(View.INVISIBLE);
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

    private void getState(final String countryIdd) {
        stateModelArrayList.clear();
        //  progress_bar.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, ApiClient.BASE_URL + "get_state",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //=========check response are coming or not after api hit ===========//
                        if (response != null) {

                            try {
                                Log.v("uploading..!", response);
                                JSONObject jsonObject = new JSONObject(response);
                                //=========login api response setup method====//
                                try {
                                    String success = jsonObject.getString("response");
                                    if (success.equals("true")) {
                                        StateModel stateModel = new StateModel("", "Select State", "", "", "", "");
                                        stateModelArrayList.add(stateModel);
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                                        if (jsonArray.length() == 0) {
                                            StateModel stateModel1 = new StateModel("", "", "", "", "", "");
                                            stateModelArrayList.add(stateModel1);
                                            // progress_bar.setVisibility(View.INVISIBLE);
                                        }
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                            StateModel professionModel1 = new StateModel(
                                                    jsonObject1.getString("state_id"),
                                                    jsonObject1.getString("state"),
                                                    jsonObject1.getString("code"),
                                                    jsonObject1.getString("country_id"),
                                                    jsonObject1.getString("status"),
                                                    jsonObject1.getString("sortorder"));

                                            stateModelArrayList.add(professionModel1);
                                            //  progress_bar.setVisibility(View.INVISIBLE);
                                        }
                                    } else {
                                        stateName = "";
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                sp_state.setAdapter(new StateAdapter(getContext(), stateModelArrayList));

                            } catch (OutOfMemoryError | NullPointerException e) {
                                //   progress_bar.setVisibility(View.INVISIBLE);
                                // TODO: handle exception
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            //  progress_bar.setVisibility(View.INVISIBLE);
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //===if response error then dismiss progress==========//

                Log.v("errrr", String.valueOf(error));
                //  progress_bar.setVisibility(View.INVISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("country_id", countryIdd);

                return params;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(postRequest);
    }

    private void getCity(final String stateNames) {
        cityModelArrayList.clear();
        //  progress_bar.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, ApiClient.BASE_URL + "get_city",
                new com.android.volley.Response.Listener<String>() {
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
                                        StateModel stateModel = new StateModel("", "Select City", "", "", "", "");
                                        stateModelArrayList.add(stateModel);
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                                        if (jsonArray.length() == 0) {
                                            CityModel cityModel1 = new CityModel("", "", "", "", "", "");
                                            cityModelArrayList.add(cityModel1);
                                            // progress_bar.setVisibility(View.INVISIBLE);
                                        }
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                            CityModel professionModel1 = new CityModel(
                                                    jsonObject1.getString("id"),
                                                    jsonObject1.getString("city"),
                                                    jsonObject1.getString("state"),
                                                    jsonObject1.getString("country"),
                                                    jsonObject1.getString("sortorder"),
                                                    jsonObject1.getString("status"));

                                            cityModelArrayList.add(professionModel1);
                                            //  progress_bar.setVisibility(View.INVISIBLE);
                                        }
                                    } else {
                                        //    progress_bar.setVisibility(View.INVISIBLE);
                                        cityModelArrayList.clear();
                                        //       Toast.makeText(TellusaboutActivity.this, "No  Found", Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                sp_city.setAdapter(new CityAdapter(getContext(), cityModelArrayList));

                            } catch (OutOfMemoryError | NullPointerException e) {
                                //   progress_bar.setVisibility(View.INVISIBLE);
                                // TODO: handle exception
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            //  progress_bar.setVisibility(View.INVISIBLE);
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //===if response error then dismiss progress==========//

                Log.v("errrr", String.valueOf(error));
                //  progress_bar.setVisibility(View.INVISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("state_name", stateNames);
                return params;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(postRequest);
    }

    public class SpinnerAdapter extends BaseAdapter {

        private final LayoutInflater layoutInflater;
        private List<String> spinnerList;

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


    //--------------------CountryAdapter-----------------------------

    public class CountryAdapter extends BaseAdapter {
        LayoutInflater inflator;
        ArrayList<CountryModel> spinnerArrayList;

        public CountryAdapter(Context context, ArrayList<CountryModel> spinnerArrayList) {
            inflator = LayoutInflater.from(context);
            this.spinnerArrayList = spinnerArrayList;
        }

        @Override
        public int getCount() {
            return spinnerArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflator.inflate(R.layout.item_spinner, null);
            TextView deposit_channerl_ = (TextView) convertView.findViewById(R.id.spinner_text_view);
            deposit_channerl_.setText(spinnerArrayList.get(position).getCountry());
            return convertView;
        }
    }


    //----------------------------satate code--------------------------------
    public class StateAdapter extends BaseAdapter {
        LayoutInflater inflator;
        ArrayList<StateModel> spinnerArrayList;

        public StateAdapter(Context context, ArrayList<StateModel> spinnerArrayList) {
            inflator = LayoutInflater.from(context);
            this.spinnerArrayList = spinnerArrayList;
        }

        @Override
        public int getCount() {
            return spinnerArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflator.inflate(R.layout.item_spinner, null);
            TextView deposit_channerl_ = (TextView) convertView.findViewById(R.id.spinner_text_view);
            deposit_channerl_.setText(spinnerArrayList.get(position).getState());
            return convertView;
        }
    }


    //-------------------------city adapter---------------
    public class CityAdapter extends BaseAdapter {
        LayoutInflater inflator;
        ArrayList<CityModel> spinnerArrayList;

        public CityAdapter(Context context, ArrayList<CityModel> spinnerArrayList) {
            inflator = LayoutInflater.from(context);
            this.spinnerArrayList = spinnerArrayList;
        }

        @Override
        public int getCount() {
            return spinnerArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflator.inflate(R.layout.item_spinner, null);
            TextView deposit_channerl_ = (TextView) convertView.findViewById(R.id.spinner_text_view);
            deposit_channerl_.setText(spinnerArrayList.get(position).getCity());
            return convertView;
        }
    }

}
