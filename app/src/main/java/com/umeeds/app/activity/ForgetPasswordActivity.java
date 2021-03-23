package com.umeeds.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.umeeds.app.R;
import com.umeeds.app.model.ForgotModel;
import com.umeeds.app.network.UtilsMethod;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {

    ImageView iv_back;
    Button forget_btn;
    EditText emailId;
    private ApiInterface apiInterface;
    private UtilsMethod progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        iv_back = findViewById(R.id.iv_back);
        forget_btn = findViewById(R.id.forget_btn);
        emailId = findViewById(R.id.emailId);

        apiInterface = ApiClient.getInterface();
        progress = new UtilsMethod(this);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        forget_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPassApi(emailId.getText().toString());
            }
        });

    }

    private void forgetPassApi(String emailId) {
        progress.showDialog();

        apiInterface.forgetPass(emailId).enqueue(new Callback<ForgotModel>() {
            @Override
            public void onResponse(Call<ForgotModel> call, Response<ForgotModel> response) {
                if (response.isSuccessful()) {
                    ForgotModel forgotModel = response.body();
                    if (forgotModel != null) {
                        progress.cancleDialog();
                        String respons = forgotModel.getResponse();
                        if (respons.equals("true")) {
                            Toast.makeText(ForgetPasswordActivity.this, forgotModel.getData(), Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(ForgetPasswordActivity.this, forgotModel.getData(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ForgotModel> call, Throwable t) {
                Toast.makeText(ForgetPasswordActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress.cancleDialog();

            }

        });


    }

}
