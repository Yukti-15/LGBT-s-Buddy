package com.umeeds.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.umeeds.app.R;
import com.umeeds.app.model.LoginModel;
import com.umeeds.app.model.UserStatusModel;
import com.umeeds.app.network.UtilsMethod;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.umeeds.app.network.networking.Constant.LOGIN_ID;
import static com.umeeds.app.network.networking.Constant.MATRI_ID;

public class ChangePasswordActivity extends AppCompatActivity {

    ImageView iv_back, iv_home;
    EditText et_oldPassword, et_newPassword;
    Button bt_submit;
    private ApiInterface apiInterface;
    private UtilsMethod progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        iv_back = findViewById(R.id.iv_back);
        iv_home = findViewById(R.id.iv_home);

        et_oldPassword = findViewById(R.id.et_oldPassword);
        et_newPassword = findViewById(R.id.et_newPassword);
        bt_submit = findViewById(R.id.bt_submit);

        apiInterface = ApiClient.getInterface();
        progress = new UtilsMethod(this);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validationSuccess()) {
                    progress.showDialog();
                    changepasswordApi(et_oldPassword.getText().toString(), et_newPassword.getText().toString(), SharedPrefsManager.getInstance().getString(MATRI_ID));
                }
            }
        });

    }

    private void changepasswordApi(String newPass, String confPass, String MatriID) {

        apiInterface.changePassword(newPass, confPass, MatriID).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel loginModel = response.body();
                    if (loginModel != null) {
                        progress.cancleDialog();
                        boolean respons = loginModel.isResponse();
                        //  String message = loginModel.getMessage();
                        if (respons) {
                            Toast.makeText(ChangePasswordActivity.this, "Your password has been successfully changed", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(ChangePasswordActivity.this, "Your password has been not successfully changed", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress.cancleDialog();

            }

        });


    }

    private Boolean validationSuccess() {

/*
        if (et_oldpassword.getText().toString().length() == 0) {
            et_oldpassword.setError("Please enter old password");
            et_oldpassword.requestFocus();
            return false;
        }
*/
        if (et_oldPassword.getText().toString().length() == 0) {
            et_oldPassword.setError("Please enter new password");
            et_oldPassword.requestFocus();
            return false;
        }
        if (et_newPassword.getText().toString().length() == 0) {
            et_newPassword.setError("Please enter old password");
            et_newPassword.requestFocus();
            return false;
        } else if (et_oldPassword.getText().toString().equals(et_newPassword.getText().toString())) {
        } else {
            Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();

        user_status(SharedPrefsManager.getInstance().getString(LOGIN_ID));
    }

    private void user_status(String user_id) {
        apiInterface.user_status(user_id).enqueue(new Callback<UserStatusModel>() {
            @Override
            public void onResponse(Call<UserStatusModel> call, Response<UserStatusModel> response) {
                if (response.isSuccessful()) {
                    UserStatusModel userStatusModel = response.body();
                    if (userStatusModel != null) {
                        String respons = userStatusModel.getResponse();
                        String status = userStatusModel.getStatus();
                        if (status.equals("Active")) {

                        } else {
                            SharedPrefsManager.getInstance().clearPrefs();
                            Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserStatusModel> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

}
