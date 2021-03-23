package com.umeeds.app.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.umeeds.app.MainActivity;
import com.umeeds.app.R;
import com.umeeds.app.model.LoginModel;
import com.umeeds.app.model.usermodel.VeryfyEmailRegisterModel;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.umeeds.app.network.networking.Constant.CONFIRM_EMAIL;
import static com.umeeds.app.network.networking.Constant.KEY_LOGIN_STATUS;
import static com.umeeds.app.network.networking.Constant.LOGIN_ID;
import static com.umeeds.app.network.networking.Constant.MATRI_ID;
import static com.umeeds.app.network.networking.Constant.MOBILE;
import static com.umeeds.app.network.networking.Constant.SHOW_GENDER;
import static com.umeeds.app.network.networking.Constant.STATUS;
import static com.umeeds.app.network.networking.Constant.USER_GENDER;

public class LoginActivity extends AppCompatActivity {
    TextView tv_forget_password, tv_register, tv_termsCondition;
    Button bt_registration, btn_login;

    EditText et_emailId, et_mobile;

    AppCompatEditText et_password;
    ProgressBar progress_bar;
    // private ProgressDailog progress;
    String newToken;
    String otp;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_forget_password = findViewById(R.id.tv_forget_password);
     //   bt_registration = findViewById(R.id.bt_registration);
        tv_register = findViewById(R.id.tv_register);
        btn_login = findViewById(R.id.btn_login);

        et_emailId = findViewById(R.id.et_emailId);
//        et_mobile = findViewById(R.id.et_mobile);
//        et_password = findViewById(R.id.et_password);
        progress_bar = findViewById(R.id.progress_bar);
   //     tv_termsCondition = findViewById(R.id.tv_termsCondition);

        apiInterface = ApiClient.getInterface();

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(LoginActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                newToken = instanceIdResult.getToken();
                Log.e("newToken", newToken);
            }
        });


        tv_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

//        bt_registration.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
//                startActivity(intent);
//            }
//        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
//        tv_termsCondition.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, TermsConditionActivity.class);
//                startActivity(intent);
//            }
//        });
        et_mobile.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() > 1) {
                    btn_login.setText("SEND OTP");
                    et_password.setText("");
                    et_emailId.setText("");
                }
            }
        });

//        et_password.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//                if (s.length() > 1) {
//                    btn_login.setText("SIGN IN");
//                    et_mobile.setText("");
//                }
//
//            }
//        });


        btn_login.setOnClickListener(view -> {

            if (btn_login.getText().toString().equalsIgnoreCase("SIGN IN")) {
                if (validationSuccess()) {
                    progress_bar.setVisibility(View.VISIBLE);
                    LoginApi(et_emailId.getText().toString(), et_password.getText().toString(), newToken);
                }
            } else {
                if (validationSuccess2()) {
                    progress_bar.setVisibility(View.VISIBLE);
                    verifyEmailMobile("a@gmail.com", et_mobile.getText().toString());
                }
            }
        });

    }


    private void LoginApi(String emailId, String passWord, String newToken) {
        Log.i("rhl....", emailId + "   " + passWord + "    " + newToken);
        apiInterface.userLogin(emailId, passWord, newToken).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel loginModel = response.body();
                    if (loginModel != null) {
                        progress_bar.setVisibility(View.GONE);
                        boolean respons = loginModel.isResponse();
                        //  String message = loginModel.getMessage();
                        if (respons) {
                            List<LoginModel.LoginData> loginDataList = loginModel.getLoginDataList();
                            SharedPrefsManager.getInstance().setString(LOGIN_ID, loginDataList.get(0).getLoginId());
                            SharedPrefsManager.getInstance().setString(MATRI_ID, loginDataList.get(0).getMatriId());
                            SharedPrefsManager.getInstance().setString(USER_GENDER, loginDataList.get(0).getGenderUser());
                            SharedPrefsManager.getInstance().setString(CONFIRM_EMAIL, loginDataList.get(0).getConfirmEmail());
                            SharedPrefsManager.getInstance().setString(MOBILE, loginDataList.get(0).getMobileUser());
                            SharedPrefsManager.getInstance().setString(STATUS, loginDataList.get(0).getStatus());
                            SharedPrefsManager.getInstance().setString(SHOW_GENDER, loginDataList.get(0).getShow_gender());
                            SharedPrefsManager.getInstance().setBoolean(KEY_LOGIN_STATUS, true);

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast toast = Toast.makeText(LoginActivity.this, "Email Id and password does not match", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(View.GONE);
            }
        });
    }


    private void verifyEmailMobile(String userEmail, String phone) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://umeed.app/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //  RetrofitService api = retrofit.create(RetrofitService.class);
        ApiInterface api = retrofit.create(ApiInterface.class);
        //  ApiInterface api   = ApiClient.getInterface();
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
                            Toast toast = Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<VeryfyEmailRegisterModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(View.GONE);

            }
        });

    }


    private void dialogeOTP() {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialogbox2);


        Button ok = dialog.findViewById(R.id.ok);
        final EditText text_dialog = dialog.findViewById(R.id.text_dialog);
        ok.setOnClickListener(v -> {
            if (text_dialog.getText().toString().equals(otp)) {
                progress_bar.setVisibility(View.VISIBLE);
                loginwidthmobile(et_mobile.getText().toString());
            } else {
                Toast toast = Toast.makeText(LoginActivity.this, "Enter Valid Otp", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            dialog.dismiss();
        });
        dialog.show();
    }

    private void loginwidthmobile(String mobile) {

        apiInterface.loginwidthmobile(mobile).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel loginModel = response.body();
                    if (loginModel != null) {
                        progress_bar.setVisibility(View.GONE);
                        boolean respons = loginModel.isResponse();
                        //  String message = loginModel.getMessage();
                        if (respons) {
                            List<LoginModel.LoginData> loginDataList = loginModel.getLoginDataList();
                            //  Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                            SharedPrefsManager.getInstance().setString(LOGIN_ID, loginDataList.get(0).getLoginId());
                            SharedPrefsManager.getInstance().setString(MATRI_ID, loginDataList.get(0).getMatriId());
                            SharedPrefsManager.getInstance().setString(USER_GENDER, loginDataList.get(0).getGenderUser());
                            SharedPrefsManager.getInstance().setString(CONFIRM_EMAIL, loginDataList.get(0).getConfirmEmail());
                            SharedPrefsManager.getInstance().setString(MOBILE, loginDataList.get(0).getMobileUser());

                            SharedPrefsManager.getInstance().setString(STATUS, loginDataList.get(0).getStatus());
                            SharedPrefsManager.getInstance().setBoolean(KEY_LOGIN_STATUS, true);
                            SharedPrefsManager.getInstance().setString(SHOW_GENDER, loginDataList.get(0).getShow_gender());

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast toast = Toast.makeText(LoginActivity.this, "Please enter correct mobile no.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(View.GONE);
            }
        });
    }


    private Boolean validationSuccess() {

        if (et_emailId.getText().toString().length() == 0) {
            et_emailId.setError("Please enter Email Id!");
            et_emailId.requestFocus();
            return false;
        }

        if (et_password.getText().toString().length() == 0) {
            et_password.setError("Please enter password!");
            et_password.requestFocus();
            return false;
        }

        return true;
    }

    private Boolean validationSuccess2() {

        if (et_mobile.getText().toString().length() < 10) {
            et_mobile.setError("Please enter phone number!");
            et_mobile.requestFocus();
            return false;
        }
        return true;
    }


}
