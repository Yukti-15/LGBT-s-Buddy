package com.umeeds.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.umeeds.app.MainActivity;
import com.umeeds.app.R;
import com.umeeds.app.model.NotifiDeletModel;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.umeeds.app.network.networking.Constant.LOGIN_ID;

public class DeleteAccountActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView iv_back, iv_home;
    EditText et_description;
    TextView tv_dec;
    RadioButton rb_notSatisfied, rb_foundPartner, rb_Noneoftheabove, rb_other;
    TextView tv_deleteAccount;
    String reason = "Not Satisfied";

    ProgressBar progress_bar;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        iv_back = findViewById(R.id.iv_back);
        iv_home = findViewById(R.id.iv_home);
        et_description = findViewById(R.id.et_description);

        rb_notSatisfied = findViewById(R.id.rb_notSatisfied);
        rb_foundPartner = findViewById(R.id.rb_foundPartner);
        rb_Noneoftheabove = findViewById(R.id.rb_Noneoftheabove);
        rb_other = findViewById(R.id.rb_other);
        tv_deleteAccount = findViewById(R.id.tv_deleteAccount);
        tv_dec = findViewById(R.id.tv_dec);
        progress_bar = findViewById(R.id.progress_bar);

        apiInterface = ApiClient.getInterface();

        iv_back.setOnClickListener(this);
        rb_notSatisfied.setOnClickListener(this);
        rb_foundPartner.setOnClickListener(this);
        rb_Noneoftheabove.setOnClickListener(this);
        rb_other.setOnClickListener(this);
        tv_deleteAccount.setOnClickListener(this);
        iv_home.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;

            case R.id.rb_notSatisfied:
                reason = "Not Satisfied";
                tv_dec.setVisibility(View.GONE);
                et_description.setVisibility(View.GONE);
                rb_foundPartner.setChecked(false);
                rb_Noneoftheabove.setChecked(false);
                rb_other.setChecked(false);
                rb_notSatisfied.setChecked(true);
                break;

            case R.id.rb_foundPartner:
                reason = "Found my Partner";
                tv_dec.setVisibility(View.GONE);
                et_description.setVisibility(View.GONE);
                rb_Noneoftheabove.setChecked(false);
                rb_notSatisfied.setChecked(false);
                rb_other.setChecked(false);
                rb_foundPartner.setChecked(true);
                break;

            case R.id.rb_Noneoftheabove:
                reason = "None of the above";
                tv_dec.setVisibility(View.GONE);
                et_description.setVisibility(View.GONE);
                rb_notSatisfied.setChecked(false);
                rb_foundPartner.setChecked(false);
                rb_other.setChecked(false);
                rb_Noneoftheabove.setChecked(true);
                break;

            case R.id.rb_other:
                reason = "Other";
                tv_dec.setVisibility(View.VISIBLE);
                et_description.setVisibility(View.VISIBLE);
                rb_notSatisfied.setChecked(false);
                rb_foundPartner.setChecked(false);
                rb_Noneoftheabove.setChecked(false);
                rb_other.setChecked(true);

                break;

            case R.id.tv_deleteAccount:
                Log.i("rhl....ac", reason);
                if (reason.equals("Other")) {
                    if (validationSuccess()) {
                        deleteAccount(SharedPrefsManager.getInstance().getString(LOGIN_ID), et_description.getText().toString());
                    }
                } else {
                    deleteAccount(SharedPrefsManager.getInstance().getString(LOGIN_ID), reason);
                }

                break;

            case R.id.iv_home:
                startActivity(new Intent(DeleteAccountActivity.this, MainActivity.class));
                finish();
                break;
        }
    }

    private Boolean validationSuccess() {

        if (et_description.getText().toString().length() == 0) {
            et_description.setError("Please write a description!");
            et_description.requestFocus();
            return false;
        }
        return true;
    }

    private void deleteAccount(String user_id, String reason) {
        Log.i("rhl....ac", reason);
        progress_bar.setVisibility(View.VISIBLE);
        apiInterface.delete_profile(user_id, reason).enqueue(new Callback<NotifiDeletModel>() {
            @Override
            public void onResponse(Call<NotifiDeletModel> call, Response<NotifiDeletModel> response) {
                if (response.isSuccessful()) {
                    NotifiDeletModel loginModel = response.body();
                    if (loginModel != null) {
                        progress_bar.setVisibility(View.GONE);
                        String respons = loginModel.getResponse();
                        if (respons.equals("true")) {
                            SharedPrefsManager.getInstance().clearPrefs();
                            Intent intent = new Intent(DeleteAccountActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<NotifiDeletModel> call, Throwable t) {
                Toast.makeText(DeleteAccountActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(View.GONE);
            }
        });
    }
}
