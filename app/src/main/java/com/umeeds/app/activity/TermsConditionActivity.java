package com.umeeds.app.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.umeeds.app.R;
import com.umeeds.app.model.AboutUsModel;
import com.umeeds.app.network.UtilsMethod;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermsConditionActivity extends AppCompatActivity {
    ImageView iv_back;
    TextView tv_title;
    private ApiInterface apiInterface;
    private UtilsMethod progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_condition);

        iv_back = findViewById(R.id.iv_back);
        tv_title = findViewById(R.id.tv_title);

        apiInterface = ApiClient.getInterface();
        progress = new UtilsMethod(this);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        termsApi();

    }

    private void termsApi() {
        apiInterface.termsCondition().enqueue(new Callback<AboutUsModel>() {
            @Override
            public void onResponse(Call<AboutUsModel> call, Response<AboutUsModel> response) {
                if (response.isSuccessful()) {
                    AboutUsModel aboutUsModel = response.body();
                    if (aboutUsModel != null) {
                        progress.cancleDialog();
                        boolean respons = aboutUsModel.isResponse();
                        if (respons) {
                            AboutUsModel.AboutUsData loginData = aboutUsModel.getLoginData();
                            tv_title.setText(Html.fromHtml(Html.fromHtml(loginData.getContent()).toString()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AboutUsModel> call, Throwable t) {
                Toast.makeText(TermsConditionActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress.cancleDialog();

            }

        });
    }
}
