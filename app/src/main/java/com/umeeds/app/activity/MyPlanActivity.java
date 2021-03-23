package com.umeeds.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.umeeds.app.MainActivity;
import com.umeeds.app.R;
import com.umeeds.app.model.MyPlanModel;
import com.umeeds.app.model.UserStatusModel;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.umeeds.app.network.networking.Constant.LOGIN_ID;
import static com.umeeds.app.network.networking.Constant.MATRI_ID;
import static com.umeeds.app.network.networking.Constant.STATUS;

public class MyPlanActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView iv_back, iv_home;
    Button bt_upgrade;
    ProgressBar progress_bar;
    TextView tv_memberShip, tv_status, tv_noofContactAllowed, tv_noofContact, tv_expiryDate;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plan);

        iv_back = findViewById(R.id.iv_back);
        iv_home = findViewById(R.id.iv_home);
        bt_upgrade = findViewById(R.id.bt_upgrade);
        progress_bar = findViewById(R.id.progress_bar);
        tv_memberShip = findViewById(R.id.tv_plandisplayname);
        tv_status = findViewById(R.id.tv_status);
        tv_noofContactAllowed = findViewById(R.id.tv_noofContactAllowed);
        tv_noofContact = findViewById(R.id.tv_noofContact);
        tv_expiryDate = findViewById(R.id.tv_expiryDate);

        apiInterface = ApiClient.getInterface();

        get_my_current_plan(SharedPrefsManager.getInstance().getString(MATRI_ID));

        iv_back.setOnClickListener(this);
        iv_home.setOnClickListener(this);
        bt_upgrade.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_back:
                onBackPressed();
                break;

            case R.id.iv_home:
                startActivity(new Intent(MyPlanActivity.this, MainActivity.class));
                break;

            case R.id.bt_upgrade:
                startActivity(new Intent(MyPlanActivity.this, MemberShipPlanActivity.class));
                break;
        }
    }

    private void get_my_current_plan(String matriId) {
        progress_bar.setVisibility(VISIBLE);
        apiInterface.get_my_current_plan(matriId).enqueue(new Callback<MyPlanModel>() {
            @Override
            public void onResponse(Call<MyPlanModel> call, Response<MyPlanModel> response) {
                if (response.isSuccessful()) {
                    MyPlanModel myPlanModel = response.body();
                    if (myPlanModel != null) {
                        progress_bar.setVisibility(GONE);
                        String respons = myPlanModel.getResponse();
                        if (respons.equals("true")) {
                            MyPlanModel.MyPlanData myPlanData = myPlanModel.getMyPlanData();
                            SharedPrefsManager.getInstance().setString(STATUS, myPlanData.getStatus());
//                            String planName = myPlanData.getPlandisplayname();
                            tv_memberShip.setText("MemberShip : " + myPlanModel.getMyPlanData().getPlandisplayname());
                            tv_status.setText("Status : " + myPlanData.getStatus());
                            tv_expiryDate.setText("Expiry Date : " + myPlanData.getMemshipExpiryDate());
                            tv_noofContactAllowed.setText("No of Contacts Allowed : " + myPlanData.getNoofcontacts());
                            tv_noofContact.setText("No of Chats : " + myPlanData.getChatcontact());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MyPlanModel> call, Throwable t) {
                Toast.makeText(MyPlanActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(GONE);
            }
        });
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
                        String status = userStatusModel.getStatus();
                        if (!status.equals("Active")) {
                            SharedPrefsManager.getInstance().clearPrefs();
                            Intent intent = new Intent(MyPlanActivity.this, LoginActivity.class);
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
                Toast.makeText(MyPlanActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
            }
        });
    }
}
