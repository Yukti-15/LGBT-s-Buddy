package com.umeeds.app.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.umeeds.app.R;
import com.umeeds.app.model.LoginModel;
import com.umeeds.app.model.usermodel.CityModel;
import com.umeeds.app.model.usermodel.CountryModel;
import com.umeeds.app.model.usermodel.EducationModel;
import com.umeeds.app.model.usermodel.MotherToungModel;
import com.umeeds.app.model.usermodel.ProfessionModel;
import com.umeeds.app.model.usermodel.ReligiousModel;
import com.umeeds.app.model.usermodel.StateModel;
import com.umeeds.app.network.UtilsMethod;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;
import com.umeeds.app.network.networking.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.umeeds.app.network.networking.Constant.LOGIN_ID;

public class TellusaboutActivity extends AppCompatActivity {

    Button btn_getStart;
    ImageView iv_back;

    Spinner sp_feet, sp_inch, sp_material, sp_education, sp_religon, sp_motherToung, sp_profession,
            sp_country, sp_state, sp_city;
    String MotherToung;

    List<String> materiallist = new ArrayList<>();
    List<String> heightfromlist = new ArrayList<>();
    List<String> heighttolist = new ArrayList<>();
    List<String> educationlist = new ArrayList<>();
    String mstatus, matstatus, hightF, countryId, countryName, stateName, stateId, cityId, cityName, motherToungID, motherToungName,
            educationName, educationID, professionName, professionID, religiousIDName, religiousID;
    EditText et_annualIncome, et_zipcode, et_nationality, et_bio;
    RadioButton rb_typeyes, rb_typeno;
    String childrenlivingstatus = "No";
    RadioGroup radioGroup;
    private SpinnerAdapter materialAdapter;
    private SpinnerAdapter fromheightAdapter;
    private SpinnerAdapter toheightAdapter;
    private SpinnerAdapter educationAdapter;
    private ArrayList<MotherToungModel> motherToungModelArrayList;
    private ArrayList<ReligiousModel> religiousModelArrayList;
    private ArrayList<EducationModel> educationModelArrayList;
    private ArrayList<ProfessionModel> professionModelArrayList;
    private ArrayList<CountryModel> countryModelArrayList;
    private ArrayList<StateModel> stateModelArrayList;
    private ArrayList<CityModel> cityModelArrayList;
    private ApiInterface apiInterface;
    private UtilsMethod progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tellusabout);

        btn_getStart = findViewById(R.id.btn_getStart);
        iv_back = findViewById(R.id.iv_back);
        sp_feet = findViewById(R.id.sp_feet);
        sp_inch = findViewById(R.id.sp_inch);
        sp_material = findViewById(R.id.sp_material);
        sp_education = findViewById(R.id.sp_education);
        sp_religon = findViewById(R.id.sp_religon);
        sp_motherToung = findViewById(R.id.sp_motherToung);
        sp_profession = findViewById(R.id.sp_profession);
        sp_country = findViewById(R.id.sp_country);
        sp_state = findViewById(R.id.sp_state);
        sp_city = findViewById(R.id.sp_city);
        et_bio = findViewById(R.id.et_bio);

        et_annualIncome = findViewById(R.id.et_annualIncome);
        et_zipcode = findViewById(R.id.et_zipcode);
        et_nationality = findViewById(R.id.et_nationality);
        rb_typeyes = findViewById(R.id.rb_typeyes);
        rb_typeno = findViewById(R.id.rb_typeno);
        radioGroup = findViewById(R.id.radioGroup);

        apiInterface = ApiClient.getInterface();
        progress = new UtilsMethod(this);

        motherToungModelArrayList = new ArrayList<>();
        religiousModelArrayList = new ArrayList<>();
        educationModelArrayList = new ArrayList<>();
        professionModelArrayList = new ArrayList<>();
        countryModelArrayList = new ArrayList<>();
        stateModelArrayList = new ArrayList<>();
        cityModelArrayList = new ArrayList<>();

        SpinMotherToung();
        spinGetreligion();
        educationApi();
        occupation();
        countryCode();
        init();

        btn_getStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validationSuccess()) {
                    tellusaboutApi(hightF, matstatus, religiousIDName, motherToungName, educationName, professionName, countryName, stateName, cityId,
                            SharedPrefsManager.getInstance().getString(LOGIN_ID), et_zipcode.getText().toString(), et_nationality.getText().toString()
                            , et_annualIncome.getText().toString(), childrenlivingstatus, et_bio.getText().toString());
                }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogeback();
            }
        });

        sp_motherToung.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String bandId = (String) parent.getItemAtPosition(position);

                if (motherToungModelArrayList.size() > 0) {
                    motherToungID = motherToungModelArrayList.get(position).getMother_tongue_id();
                    motherToungName = motherToungModelArrayList.get(position).getMother_tongue();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_education.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String bandId = (String) parent.getItemAtPosition(position);

                if (educationModelArrayList.size() > 0) {
                    educationID = educationModelArrayList.get(position).getEducation_id();
                    educationName = educationModelArrayList.get(position).getEducation();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_profession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String bandId = (String) parent.getItemAtPosition(position);

                if (professionModelArrayList.size() > 0) {
                    professionID = professionModelArrayList.get(position).getOccupation_id();
                    professionName = professionModelArrayList.get(position).getOccupation();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_religon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String bandId = (String) parent.getItemAtPosition(position);

                if (religiousModelArrayList.size() > 0) {
                    religiousID = religiousModelArrayList.get(position).getId();
                    religiousIDName = religiousModelArrayList.get(position).getName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String bandId = (String) parent.getItemAtPosition(position);

                if (countryModelArrayList.size() > 0) {
                    countryId = countryModelArrayList.get(position).getCountry_id();
                    countryName = countryModelArrayList.get(position).getCountry();
                    if (countryId != null) {
                        getState(countryId);
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
                    Log.i("rhl....stateName", stateName);
                    //  Log.i("rhl....Name",countryName);
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
        // onRadioButtonClicked();
        // radioGroup.clearCheck();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb) {
                    //   Toast.makeText(TellusaboutActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                    childrenlivingstatus = rb.getText().toString();
                }
            }
        });

    }

    private void dialogeback() {
        final Dialog dialog = new Dialog(TellusaboutActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.back_page_dialogbox);

        Button ok = dialog.findViewById(R.id.ok);
        Button no = dialog.findViewById(R.id.no);
        final EditText text_dialog = dialog.findViewById(R.id.text_dialog);

        no.setOnClickListener(v -> dialog.dismiss());

        ok.setOnClickListener(v -> {
            finish();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void init() {

        //--------------------------Material----------------------------
        materialAdapter = new SpinnerAdapter(this);
        sp_material.setAdapter(materialAdapter);
        materiallist.add("Select");
        materiallist.add("Married awaiting divorce");
        materiallist.add("Divorce");
        materiallist.add("Never been married");

        materialAdapter.addTaxRateList(materiallist);
        sp_material.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (materiallist.size() > 0) {
                    int position1 = sp_material.getSelectedItemPosition();
                    matstatus = materiallist.get(position);

                    if (position1 == 0) {
                        mstatus = "1";
                    } else if (position1 == 1) {
                        mstatus = "2";
                    } else if (position1 == 2) {
                        mstatus = "3";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //-------------------------------Height from-----------------------------------------------
        fromheightAdapter = new SpinnerAdapter(this);
        sp_feet.setAdapter(fromheightAdapter);
        heightfromlist.add("4ft  (121cm)");
        heightfromlist.add("4ft 1in (124cm)");
        heightfromlist.add("4ft 2in (127cm)");
        heightfromlist.add("4ft 3in (129cm) ");
        heightfromlist.add("4ft 4in (132cm)");
        heightfromlist.add("4ft 5in (134cm)");
        heightfromlist.add("4ft 6in (137cm)");
        heightfromlist.add("4ft 7in (139cm) ");
        heightfromlist.add("4ft 8in (142cm)");
        heightfromlist.add("4ft 9in (144cm)");
        heightfromlist.add("4ft 10in (147cm)");
        heightfromlist.add("4ft 11in (149cm) ");
        heightfromlist.add("5ft (152cm)");
        heightfromlist.add("5ft 1in (154cm)");
        heightfromlist.add("5ft 2in (157cm)");
        heightfromlist.add("5ft 3in (160cm) ");
        heightfromlist.add("5ft 4in (162cm)");
        heightfromlist.add("5ft 5in (165cm)");
        heightfromlist.add("5ft 6in (167cm)");
        heightfromlist.add("5ft 7in (170cm) ");
        heightfromlist.add("5ft 8in (172cm)");
        heightfromlist.add("5ft 9in (175cm)");
        heightfromlist.add("5ft 10in (177cm)");
        heightfromlist.add("5ft 11in (180cm) ");
        heightfromlist.add("6ft  (182cm)");
        heightfromlist.add("6ft 1in (185cm)");
        heightfromlist.add("6ft 2in (187cm)");
        heightfromlist.add("6ft 3in (190cm) ");
        heightfromlist.add("6ft 4in (193cm)");
        heightfromlist.add("6ft 5in (195cm)");
        heightfromlist.add("6ft 6in (198cm)");
        heightfromlist.add("6ft 7in (200cm) ");
        heightfromlist.add("6ft 8in (203cm)");
        heightfromlist.add("6ft 9in (205cm)");
        heightfromlist.add("6ft 10in (208cm)");
        heightfromlist.add("6ft 11in (210cm) ");
        heightfromlist.add("7ft  (213cm)");
        fromheightAdapter.addTaxRateList(heightfromlist);
        sp_feet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (heightfromlist.size() > 0) {
                    int position1 = sp_feet.getSelectedItemPosition();
                    hightF = String.valueOf(position1);
                    //  hightF = heightfromlist.get(position);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//------------------------------------Height To-----------------------------------------
        toheightAdapter = new SpinnerAdapter(this);
        sp_inch.setAdapter(toheightAdapter);
        heighttolist.add("6ft  (182cm)");
        heighttolist.add("6ft 1in (185cm)");
        heighttolist.add("6ft 2in (187cm)");
        heighttolist.add("6ft 3in (190cm) ");
        heighttolist.add("6ft 4in (193cm)");
        heighttolist.add("6ft 5in (195cm)");
        heighttolist.add("6ft 6in (198cm)");
        heighttolist.add("6ft 7in (200cm) ");
        heighttolist.add("6ft 8in (203cm)");
        heighttolist.add("6ft 9in (205cm)");
        heighttolist.add("6ft 10in (208cm)");
        heighttolist.add("6ft 11in (210cm) ");
        heighttolist.add("7ft  (213cm)");
        toheightAdapter.addTaxRateList(heighttolist);
        sp_inch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (heighttolist.size() > 0) {
                    int position1 = sp_inch.getSelectedItemPosition();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void spinGetreligion() {

        // progress_bar.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, ApiClient.BASE_URL + "get_religion",
                response -> {
                    //=========check response are coming or not after api hit ===========//
                    if (response != null) {
                        try {
                            Log.v("uploading..", response);
                            JSONObject jsonObject = new JSONObject(response);
                            //=========login api response setup method====//

                            try {
                                String success = jsonObject.getString("response");
                                if (success.equals("true")) {

                                    ReligiousModel religiousModel = new ReligiousModel("", "Religious", "", "");
                                    religiousModelArrayList.add(religiousModel);
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                                    if (jsonArray.length() == 0) {
                                        ReligiousModel studentDetailClassModel1 = new ReligiousModel("", "No Record Found", "", "");
                                        religiousModelArrayList.add(studentDetailClassModel1);
                                        //      progress_bar.setVisibility(View.INVISIBLE);

                                    }
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                        ReligiousModel studentDetailClassMode = new ReligiousModel(jsonObject1.getString("id"),
                                                jsonObject1.getString("name"),
                                                jsonObject1.getString("sortorder"),
                                                jsonObject1.getString("status"));

                                        religiousModelArrayList.add(studentDetailClassMode);
                                        //       progress_bar.setVisibility(View.INVISIBLE);
                                    }
                                } else {
                                    //      progress_bar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(TellusaboutActivity.this, "Error1!!!..", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            sp_religon.setAdapter(new ReligiousAdapter(TellusaboutActivity.this, religiousModelArrayList));

                        } catch (OutOfMemoryError | NullPointerException e) {
                            //   progress_bar.setVisibility(View.INVISIBLE);
                            // TODO: handle exception
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //    progress_bar.setVisibility(View.INVISIBLE);
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
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest);
    }


    private void SpinMotherToung() {
        // progress_bar.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, ApiClient.BASE_URL + "mother_tounge",
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
                                        MotherToungModel studentDetailClassModel = new MotherToungModel("", "Mother Tounge", "", "");
                                        motherToungModelArrayList.add(studentDetailClassModel);
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                                        if (jsonArray.length() == 0) {
                                            MotherToungModel studentDetailClassModel1 = new MotherToungModel("", "No Record Found", "", "");
                                            motherToungModelArrayList.add(studentDetailClassModel1);
                                            //  progress_bar.setVisibility(View.INVISIBLE);
                                        }
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                            MotherToungModel studentDetailClassMode = new MotherToungModel
                                                    (jsonObject1.getString("mother_tongue_id"),
                                                            jsonObject1.getString("mother_tongue"),
                                                            jsonObject1.getString("sortorder"),
                                                            jsonObject1.getString("status"));

                                            motherToungModelArrayList.add(studentDetailClassMode);
                                            //  progress_bar.setVisibility(View.INVISIBLE);

                                        }
                                    } else {
                                        //  progress_bar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(TellusaboutActivity.this, "Error1!!!..", Toast.LENGTH_LONG).show();

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                sp_motherToung.setAdapter(new MOtherToungAdapter(TellusaboutActivity.this, motherToungModelArrayList));

                                if (motherToungModelArrayList.size() > 0) {
                                    for (int y = 0; y < motherToungModelArrayList.size(); y++) {
                                        String mothertoung = motherToungModelArrayList.get(y).getMother_tongue();
                                        if (MotherToung.equals(mothertoung)) {
                                            sp_motherToung.setSelection(y);
                                        }
                                    }
                                }
                            } catch (OutOfMemoryError | NullPointerException | JSONException e) {
                                //   progress_bar.setVisibility(View.INVISIBLE);
                                // TODO: handle exception
                                e.printStackTrace();
                            }
                        }  // progress_bar.setVisibility(View.INVISIBLE);

                    }
                }, error -> {
            //===if response error then dismiss progress==========//

            Log.v("errrr", String.valueOf(error));
            // progress_bar.setVisibility(View.INVISIBLE);
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest);
    }


    private void educationApi() {
        //  progress_bar.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, ApiClient.BASE_URL + "get_education",
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

                                        EducationModel educationModel = new EducationModel("", "Education", "", "");
                                        educationModelArrayList.add(educationModel);
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                                        if (jsonArray.length() == 0) {
                                            EducationModel educationModel1 = new EducationModel("", "No Record Found", "", "");
                                            educationModelArrayList.add(educationModel1);
                                            // progress_bar.setVisibility(View.INVISIBLE);

                                        }
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                            EducationModel educationModel1 = new EducationModel(jsonObject1.getString("education_id"),
                                                    jsonObject1.getString("education"),
                                                    jsonObject1.getString("status"),
                                                    jsonObject1.getString("sortorder"));


                                            educationModelArrayList.add(educationModel1);
                                            //  progress_bar.setVisibility(View.INVISIBLE);

                                        }
                                    } else {
                                        //    progress_bar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(TellusaboutActivity.this, "Error1!!!..", Toast.LENGTH_LONG).show();

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                sp_education.setAdapter(new EducationAdapter(TellusaboutActivity.this, educationModelArrayList));


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
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest);
    }


    private void occupation() {
        //  progress_bar.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, ApiClient.BASE_URL + "get_occupation",
                response -> {
                    //=========check response are coming or not after api hit ===========//
                    if (response != null) {

                        try {
                            Log.v("uploading", response);
                            JSONObject jsonObject = new JSONObject(response);
                            //=========login api response setup method====//

                            try {
                                String success = jsonObject.getString("response");
                                if (success.equals("true")) {

                                    ProfessionModel professionModel = new ProfessionModel("", "Profession", "", "");
                                    professionModelArrayList.add(professionModel);
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                                    if (jsonArray.length() == 0) {
                                        ProfessionModel professionModel1 = new ProfessionModel("", "No Record Found", "", "");
                                        professionModelArrayList.add(professionModel1);
                                        // progress_bar.setVisibility(View.INVISIBLE);

                                    }
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                        ProfessionModel professionModel1 = new ProfessionModel(
                                                jsonObject1.getString("occupation_id"),
                                                jsonObject1.getString("occupation"),
                                                jsonObject1.getString("status"),
                                                jsonObject1.getString("sortorder"));

                                        professionModelArrayList.add(professionModel1);
                                        //  progress_bar.setVisibility(View.INVISIBLE);

                                    }
                                } else {
                                    //    progress_bar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(TellusaboutActivity.this, "Error1!!!..", Toast.LENGTH_LONG).show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            sp_profession.setAdapter(new ProfessionAdapter(TellusaboutActivity.this, professionModelArrayList));


                        } catch (OutOfMemoryError | NullPointerException | JSONException e) {
                            //   progress_bar.setVisibility(View.INVISIBLE);
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                    }  //  progress_bar.setVisibility(View.INVISIBLE);

                }, error -> {
            //===if response error then dismiss progress==========//

            Log.v("errrr", String.valueOf(error));
            //  progress_bar.setVisibility(View.INVISIBLE);
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest);
    }


    private void countryCode() {
        //  progress_bar.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, ApiClient.BASE_URL + "get_country",
                response -> {
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
                                    Toast.makeText(TellusaboutActivity.this, "Error1!!!..", Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            sp_country.setAdapter(new CountryAdapter(TellusaboutActivity.this, countryModelArrayList));

                        } catch (OutOfMemoryError | NullPointerException | JSONException e) {
                            //   progress_bar.setVisibility(View.INVISIBLE);
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                    }  //  progress_bar.setVisibility(View.INVISIBLE);

                }, error -> {
            //===if response error then dismiss progress==========//
            Log.v("errrr", String.valueOf(error));
            //  progress_bar.setVisibility(View.INVISIBLE);
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest);
    }


    private void getState(final String countryIdd) {
        stateModelArrayList.clear();
        //  progress_bar.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, ApiClient.BASE_URL + "get_state",
                response -> {
                    //=========check response are coming or not after api hit ===========//
                    if (response != null) {

                        try {
                            Log.v("uploading..!", response);
                            JSONObject jsonObject = new JSONObject(response);
                            //=========login api response setup method====//
                            try {
                                String success = jsonObject.getString("response");
                                if (success.equals("true")) {

                                    //  StateModel stateModel = new StateModel("", "Country", "", "","","");
                                    //  stateModelArrayList.add(stateModel);
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
                                    //    progress_bar.setVisibility(View.INVISIBLE);
                                    //  Toast.makeText(TellusaboutActivity.this, "No Found!..", Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            sp_state.setAdapter(new StateAdapter(TellusaboutActivity.this, stateModelArrayList));

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
                }, error -> {
            //===if response error then dismiss progress==========//

            Log.v("errrr", String.valueOf(error));
            //  progress_bar.setVisibility(View.INVISIBLE);
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("country_id", countryIdd);

                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest);
    }

    private void getCity(final String stateNames) {
        cityModelArrayList.clear();
        //  progress_bar.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, ApiClient.BASE_URL + "get_city",
                response -> {
                    //=========check response are coming or not after api hit ===========//
                    if (response != null) {

                        try {
                            Log.v("uploading", response);
                            JSONObject jsonObject = new JSONObject(response);
                            //=========login api response setup method====//
                            try {
                                String success = jsonObject.getString("response");
                                if (success.equals("true")) {

                                    //  StateModel stateModel = new StateModel("", "Country", "", "","","");
                                    //  stateModelArrayList.add(stateModel);
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
                            sp_city.setAdapter(new CityAdapter(TellusaboutActivity.this, cityModelArrayList));

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
                }, error -> {
            //===if response error then dismiss progress==========//

            Log.v("errrr", String.valueOf(error));
            //  progress_bar.setVisibility(View.INVISIBLE);
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("state_name", stateNames);

                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest);
    }


    //---------------Base Spinner Adapter-------------

    private void tellusaboutApi(String height, String meritalStatus, String religion, String motherTongue, String education, String profession
            , String country, String state, String city, String userId, String zipcode, String nationality, String income
            , String childrenlivingstatus, String ShortBio) {

        apiInterface.tellusAbout(height, meritalStatus, religion, motherTongue, education, profession, country
                , state, city, userId, zipcode, nationality, income, childrenlivingstatus, ShortBio).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel loginModel = response.body();
                    if (loginModel != null) {
                        progress.cancleDialog();
                        boolean respons = loginModel.isResponse();
                        //  String message = loginModel.getMessage();
                        if (respons) {
                            //  Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(TellusaboutActivity.this, SetpreferenceActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(TellusaboutActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress.cancleDialog();

            }

        });

    }

    private Boolean validationSuccess() {

        if (et_nationality.getText().toString().length() == 0) {
            et_nationality.setError("Please enter nationality !");
            et_nationality.requestFocus();
            return false;
        }
        if (et_zipcode.getText().toString().length() == 0) {
            et_zipcode.setError("Please enter zip code !");
            et_zipcode.requestFocus();
            return false;
        }


        if (matstatus.equals("Select")) {
            Toast.makeText(this, "Please select Marital Status", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (motherToungName.equals("Mother Tounge")) {
            Toast.makeText(this, "Please select mother tounge", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (religiousIDName.equals("Religious")) {
            Toast.makeText(this, "Please select Religious", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (educationName.equals("Education")) {
            Toast.makeText(this, "Please select Education", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (professionName.equals("Profession")) {
            Toast.makeText(this, "Please select Profession", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (stateName == null) {
            Toast.makeText(this, "Please select State", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (cityName == null) {
            Toast.makeText(this, "Please select City", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (et_annualIncome.getText().toString().length() == 0) {
            et_annualIncome.setError("Please enter Annual Income!");
            et_annualIncome.requestFocus();
            return false;
        }
        if (et_bio.getText().toString().length() < 30 || et_bio.getText().toString().length() > 150) {
            et_bio.setError("Please enter bio minimum 30 characters and maximum 150!");
            return false;
        }

        return true;
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

    // ---------------------Education Adapter ------------------------------

    //-------------------Religious---------------------
    public class ReligiousAdapter extends BaseAdapter {
        LayoutInflater inflator;
        ArrayList<ReligiousModel> spinnerArrayList;

        public ReligiousAdapter(Context context, ArrayList<ReligiousModel> spinnerArrayList) {
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
            deposit_channerl_.setText(spinnerArrayList.get(position).getName());
            return convertView;
        }
    }


    //------------------------------ProfessionAdapter-----------------------

    // -------------------MotherToungAdapter----------------
    public class MOtherToungAdapter extends BaseAdapter {
        LayoutInflater inflator;
        ArrayList<MotherToungModel> spinnerArrayList;

        public MOtherToungAdapter(Context context, ArrayList<MotherToungModel> spinnerArrayList) {
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
            deposit_channerl_.setText(spinnerArrayList.get(position).getMother_tongue());
            return convertView;
        }

    }


//--------------------CountryAdapter-----------------------------

    public class EducationAdapter extends BaseAdapter {
        LayoutInflater inflator;
        ArrayList<EducationModel> spinnerArrayList;

        public EducationAdapter(Context context, ArrayList<EducationModel> spinnerArrayList) {
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
            deposit_channerl_.setText(spinnerArrayList.get(position).getEducation());
            return convertView;
        }
    }

    public class ProfessionAdapter extends BaseAdapter {
        LayoutInflater inflator;
        ArrayList<ProfessionModel> spinnerArrayList;

        public ProfessionAdapter(Context context, ArrayList<ProfessionModel> spinnerArrayList) {
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
            deposit_channerl_.setText(spinnerArrayList.get(position).getOccupation());
            return convertView;
        }
    }

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
