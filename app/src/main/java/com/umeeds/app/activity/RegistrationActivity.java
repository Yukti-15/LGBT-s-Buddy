package com.umeeds.app.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.hbb20.CountryCodePicker;
import com.umeeds.app.R;
import com.umeeds.app.model.RegisterModel;
import com.umeeds.app.model.usermodel.VeryfyEmailRegisterModel;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.umeeds.app.network.networking.Constant.CONFIRM_EMAIL;
import static com.umeeds.app.network.networking.Constant.LOGIN_ID;
import static com.umeeds.app.network.networking.Constant.MATRI_ID;
import static com.umeeds.app.network.networking.Constant.MOBILE;
import static com.umeeds.app.network.networking.Constant.SHOW_GENDER;
import static com.umeeds.app.network.networking.Constant.STATUS;
import static com.umeeds.app.network.networking.Constant.USER_GENDER;

public class RegistrationActivity extends AppCompatActivity {

    Button btn_continue;
    ImageView iv_back;
    Toolbar toolbar;
    // private ProgressDailog progress;
    ProgressBar progress_bar;
    EditText et_firstName, et_phone, et_emailId, password, et_hobbies;
    TextView et_dob;
    CheckBox ch_terms;
    RelativeLayout li_dob;
    Spinner gender_category, sp_day, sp_month, sp_year;
    List<String> genderlist = new ArrayList<>();
    List<String> daylist = new ArrayList<>();
    List<String> monthlist = new ArrayList<>();
    List<String> yearlist = new ArrayList<>();
    String otp, Gender, day, month, year, newToken;
    String dobDate, dobMonth, dobYear;
    Date date;
    AppCompatButton gmailsignup, facebooksignup;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;
    TextView tv_termsCondition;
    private ApiInterface apiInterface;
    private CountryCodePicker ccp;
    private SpinnerAdapter genderAdapter;
    private SpinnerAdapter dayAdapter;
    private SpinnerAdapter monthAdapter;
    private SpinnerAdapter yearAdapter;
    private String mCountryCode = "";

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        btn_continue = findViewById(R.id.btn_continue);
        iv_back = findViewById(R.id.iv_back);
        toolbar = findViewById(R.id.toolbar);

        et_firstName = findViewById(R.id.et_firstName);
        et_phone = findViewById(R.id.et_phone);
        et_emailId = findViewById(R.id.et_emailId);
        password = findViewById(R.id.password);
        et_dob = findViewById(R.id.et_dob);
        et_hobbies = findViewById(R.id.et_hobbies);
        ch_terms = findViewById(R.id.ch_terms);

        ccp = findViewById(R.id.ccp);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.umeeds.app",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException ignored) {

        }

        ccp.setOnCountryChangeListener(() -> {
            mCountryCode = ccp.getSelectedCountryCode();
        });

        sp_day = findViewById(R.id.sp_day);
        gender_category = findViewById(R.id.gender_category);
        sp_month = findViewById(R.id.sp_month);
        sp_year = findViewById(R.id.sp_year);
        li_dob = findViewById(R.id.li_dob);
        gmailsignup = findViewById(R.id.gmailsignup);
        facebooksignup = findViewById(R.id.facebooksignup);
        progress_bar = findViewById(R.id.progress_bar);
        tv_termsCondition = findViewById(R.id.tv_termsCondition);

        apiInterface = ApiClient.getInterface();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        init();

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(RegistrationActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                newToken = instanceIdResult.getToken();
                Log.e("newToken", newToken);
            }
        });

        tv_termsCondition.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrationActivity.this, TermsConditionActivity.class);
            startActivity(intent);
        });

        btn_continue.setOnClickListener(view -> {
            if (validationSuccess()) {
                if (isValidMobile(et_phone.getText().toString())) {
                    if (isValidEmail(et_emailId.getText().toString())) {
                        progress_bar.setVisibility(View.VISIBLE);
                        verifyEmailRegister(et_emailId.getText().toString(), et_phone.getText().toString());
                    } else {
                        et_emailId.setError("Please enter valid Email Id!");
                        et_emailId.requestFocus();
                    }
                } else {
                    et_phone.setError("Please enter valid mobile no.!");
                    et_phone.requestFocus();
                }
            }
        });

        iv_back.setOnClickListener(view -> finish());

        gmailsignup.setOnClickListener(v -> signIn());

        et_dob.setOnClickListener(v -> {
            if (Gender.equals("Lesbian") || Gender.equals("Bisexual (Woman)") || Gender.equals("Transgender Woman")) {
                final Calendar dob_date = Calendar.getInstance();
                final Calendar dob_currentDate = Calendar.getInstance();

                DatePickerDialog dob_atePickerDialog = new DatePickerDialog(RegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        dob_date.set(year, monthOfYear, dayOfMonth);
                        dobMonth = (monthOfYear + 1) + "";
                        dobDate = dayOfMonth + 1 + "";
                        dobYear = year + "";
                        date = dob_date.getTime();
                        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                        et_dob.setText(dateFormat.format(date));
                    }
                }, dob_currentDate.get(Calendar.YEAR), dob_currentDate.get(Calendar.MONTH), dob_currentDate.get(Calendar.DATE));

                dob_date.add(Calendar.YEAR, -18);
                dob_atePickerDialog.getDatePicker().setMaxDate(dob_date.getTimeInMillis());
                dob_atePickerDialog.show();
            } else {
                final Calendar dob_date = Calendar.getInstance();
                final Calendar dob_currentDate = Calendar.getInstance();
                DatePickerDialog dob_atePickerDialog = new DatePickerDialog(RegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        dob_date.set(year, monthOfYear, dayOfMonth);
                        dobMonth = (monthOfYear + 1) + "";
                        dobDate = dayOfMonth + 1 + "";
                        dobYear = year + "";
                        date = dob_date.getTime();
                        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                        et_dob.setText(dateFormat.format(date));
                    }
                }, dob_currentDate.get(Calendar.YEAR), dob_currentDate.get(Calendar.MONTH), dob_currentDate.get(Calendar.DATE));

                dob_date.add(Calendar.YEAR, -21);

                dob_atePickerDialog.getDatePicker().setMaxDate(dob_date.getTimeInMillis());
                //datePickerMode
                dob_atePickerDialog.show();
            }
        });
    }

    private void verifyEmailRegister(String userEmail, String phone) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://umeed.app/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface api = retrofit.create(ApiInterface.class);
        api.verifyEmail(userEmail, phone).enqueue(new Callback<VeryfyEmailRegisterModel>() {
            @Override
            public void onResponse(Call<VeryfyEmailRegisterModel> call, Response<VeryfyEmailRegisterModel> response) {
                if (response.isSuccessful()) {
                    VeryfyEmailRegisterModel registerModel = response.body();
                    if (registerModel != null) {
                        progress_bar.setVisibility(View.GONE);
                        String respons = registerModel.getResponse();
                        String message = registerModel.getMessage();
                        otp = registerModel.getOtp();
                        Log.i("rhl..otp", otp);
                        if (respons.equals("true")) {
                            dialogeOTP();
                        } else {
                            Toast.makeText(RegistrationActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<VeryfyEmailRegisterModel> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(View.GONE);
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(RegistrationActivity.this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personEmail = acct.getEmail();
                et_firstName.setText(personName);
                et_emailId.setText(personEmail);
            }

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(RegistrationActivity.this, "Failed", Toast.LENGTH_LONG).show();
        }
    }

    private void dialogeOTP() {
        final Dialog dialog = new Dialog(RegistrationActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialogbox2);

        Button ok = dialog.findViewById(R.id.ok);
        final EditText text_dialog = dialog.findViewById(R.id.text_dialog);
        ok.setOnClickListener(v -> {
            if (text_dialog.getText().toString().equals(otp)) {
                String dob = dobYear + "/" + dobMonth + "/" + dobDate;
                progress_bar.setVisibility(View.VISIBLE);
                registration(et_firstName.getText().toString(), "", et_hobbies.getText().toString(), dob,
                        Gender, et_phone.getText().toString(), et_emailId.getText().toString(), password.getText().toString(), newToken, "1");
            } else {
                Toast.makeText(RegistrationActivity.this, "Enter Valid Otp", Toast.LENGTH_LONG).show();
            }
            dialog.dismiss();
        });
        dialog.show();
    }

    private void registration(String userFname, String userLname, String hobbies, String dob, String gender, String phone, String email, String password, String fcmToken, String terms) {
        apiInterface.userRegister(userFname, userLname, hobbies, dob, gender, phone, email, password,
                fcmToken, terms, mCountryCode).enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                if (response.isSuccessful()) {
                    RegisterModel registerModel = response.body();
                    if (registerModel != null) {
                        progress_bar.setVisibility(View.GONE);
                        String respons = registerModel.getResponse();
                        String message = registerModel.getMessage();
                        if (respons.equals("true")) {
                            RegisterModel.RegisterData registerData = registerModel.getResult();
                            SharedPrefsManager.getInstance().setString(LOGIN_ID, registerData.getLoginId());
                            SharedPrefsManager.getInstance().setString(MATRI_ID, registerData.getMatriID());
                            SharedPrefsManager.getInstance().setString(CONFIRM_EMAIL, registerData.getConfirmEmail());
                            SharedPrefsManager.getInstance().setString(USER_GENDER, registerData.getGender());
                            SharedPrefsManager.getInstance().setString(MOBILE, registerData.getMobile());
                            SharedPrefsManager.getInstance().setString(STATUS, registerData.getStatus());
                            SharedPrefsManager.getInstance().setString(SHOW_GENDER, registerData.getShow_gender());
                            //  SharedPrefsManager.getInstance().setBoolean(KEY_LOGIN_STATUS,true);
                            Intent intent = new Intent(RegistrationActivity.this, TellusaboutActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegistrationActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //---------------Base Spinner Adapter-------------
    private void init() {
        //--------------------------Gender----------------------------
        genderAdapter = new SpinnerAdapter(this);
        gender_category.setAdapter(genderAdapter);
        genderlist.add("Select");
        genderlist.add("Gay");
        genderlist.add("Lesbian");
        genderlist.add("Bisexual (Man)");
        genderlist.add("Bisexual (Woman)");
        genderlist.add("Transgender Man");
        genderlist.add("Transgender Woman");


        genderAdapter.addTaxRateList(genderlist);
        gender_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (genderlist.size() > 0) {
                    int position1 = gender_category.getSelectedItemPosition();
                    Gender = genderlist.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //-------------------------------Day-----------------------------------------------
        dayAdapter = new SpinnerAdapter(this);
        sp_day.setAdapter(dayAdapter);
        daylist.add("1");
        daylist.add("2");
        daylist.add("3");
        daylist.add("4");
        daylist.add("5");
        daylist.add("6");
        daylist.add("7");
        daylist.add("8");
        daylist.add("9");
        daylist.add("10");
        daylist.add("11");
        daylist.add("12");
        daylist.add("13");
        daylist.add("14");
        daylist.add("15");
        daylist.add("16");
        daylist.add("17");
        daylist.add("18");
        daylist.add("19");
        daylist.add("20");
        daylist.add("21");
        daylist.add("22");
        daylist.add("23");
        daylist.add("24");
        daylist.add("25");
        daylist.add("26");
        daylist.add("27");
        daylist.add("28");
        daylist.add("29");
        daylist.add("30");
        daylist.add("31");

        dayAdapter.addTaxRateList(daylist);
        sp_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (daylist.size() > 0) {
                    day = daylist.get(position);
                    int position1 = sp_day.getSelectedItemPosition();

                    Log.i("position....", String.valueOf(position1));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//------------------------------------Month-----------------------------------------
        monthAdapter = new SpinnerAdapter(this);
        sp_month.setAdapter(monthAdapter);
        monthlist.add("Jun");
        monthlist.add("Feb");
        monthlist.add("Mar");
        monthlist.add("Apr");
        monthlist.add("May");
        monthlist.add("Jun");
        monthlist.add("Jul");
        monthlist.add("Aug");
        monthlist.add("She");
        monthlist.add("Oct");
        monthlist.add("Nov");
        monthlist.add("Dec");

        monthAdapter.addTaxRateList(monthlist);
        sp_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (monthlist.size() > 0) {
                    // month = monthlist.get(position);
                    int position1 = sp_month.getSelectedItemPosition();
                    if (position1 == 0) {
                        month = "1";
                    } else if (position1 == 1) {
                        month = "2";
                    } else if (position1 == 2) {
                        month = "3";
                    } else if (position1 == 3) {
                        month = "4";
                    } else if (position1 == 4) {
                        month = "5";
                    } else if (position1 == 5) {
                        month = "6";
                    } else if (position1 == 6) {
                        month = "7";
                    } else if (position1 == 7) {
                        month = "8";
                    } else if (position1 == 8) {
                        month = "9";
                    } else if (position1 == 9) {
                        month = "10";
                    } else if (position1 == 10) {
                        month = "11";
                    } else if (position1 == 11) {
                        month = "12";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //------------------------------------Year-----------------------------------------
        yearAdapter = new SpinnerAdapter(this);
        sp_year.setAdapter(yearAdapter);
        yearlist.add("1985");
        yearlist.add("1986");
        yearlist.add("1987");
        yearlist.add("1988");
        yearlist.add("1989");
        yearlist.add("1990");
        yearlist.add("1991");
        yearlist.add("1992");
        yearlist.add("1993");
        yearlist.add("1994");
        yearlist.add("1995");
        yearlist.add("1996");
        yearlist.add("1997");
        yearlist.add("1998");
        yearlist.add("1999");
        yearlist.add("2000");
        yearlist.add("2001");

        yearAdapter.addTaxRateList(yearlist);
        sp_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (yearlist.size() > 0) {
                    int position1 = sp_year.getSelectedItemPosition();
                    year = yearlist.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private Boolean validationSuccess() {

        if (et_firstName.getText().toString().length() == 0) {
            et_firstName.setError("Please enter name!");
            et_firstName.requestFocus();
            return false;
        }

        if (Gender.equals("Select")) {
            Toast.makeText(this, "Please select your sexual orientation", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (et_hobbies.getText().toString().length() == 0) {
            et_hobbies.setError("Please enter Hobbies!");
            et_hobbies.requestFocus();
            return false;
        }

        if (et_dob.getText().toString().length() == 0) {
            Toast.makeText(RegistrationActivity.this, "Please Select Date of birth", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mCountryCode.equals("91")) {
            if (et_phone.getText().toString().length() == 10) {
                // et_phone.setError("Please enter valid mobile no.!");
                // et_phone.requestFocus();
                // return false;
            } else {
                et_phone.setError("Please enter valid mobile no.!");
                et_phone.requestFocus();
                return false;
            }
        } else {
            if (et_phone.getText().toString().length() == 0) {
                et_phone.setError("Please enter valid mobile no.!");
                et_phone.requestFocus();
                return false;
            }
        }
        if (password.getText().toString().length() == 0) {
            password.setError("Please enter password!");
            password.requestFocus();
            return false;
        }

        if (ch_terms.isChecked() == false) {
            Toast.makeText(RegistrationActivity.this, "Please agree with terms & condition!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
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
}
