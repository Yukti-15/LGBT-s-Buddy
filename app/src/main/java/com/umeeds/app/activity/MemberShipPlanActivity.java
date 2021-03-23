package com.umeeds.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.umeeds.app.MainActivity;
import com.umeeds.app.R;
import com.umeeds.app.adapter.MemberShipPlanAdapter;
import com.umeeds.app.model.MemberShipPlanModel;
import com.umeeds.app.model.UserStatusModel;
import com.umeeds.app.model.YardsItem;
import com.umeeds.app.network.UtilsMethod;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.umeeds.app.network.networking.Constant.LOGIN_ID;

public class MemberShipPlanActivity extends AppCompatActivity {

    Button bt_upgrade;
    ImageView iv_back, iv_home;
    RecyclerView membership_plan_recy;
    ProgressBar progressCircular;
    MemberShipPlanAdapter memberShipPlanAdapter;
    List<MemberShipPlanModel> customerListList = new ArrayList<>();
    YardsItem yardsItem;
    private ApiInterface apiInterface;
    private UtilsMethod progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_ship_plan);

        apiInterface = ApiClient.getInterface();
        progress = new UtilsMethod(this);

        membership_plan_recy = findViewById(R.id.membership_plan_recy);
        iv_back = findViewById(R.id.iv_back);
        iv_home = findViewById(R.id.iv_home);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        membership_plan_recy.setLayoutManager(layoutManager);
        memberShipPlanAdapter = new MemberShipPlanAdapter(this);
        membership_plan_recy.setAdapter(memberShipPlanAdapter);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemberShipPlanActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TimeZone tz = TimeZone.getDefault();
        System.out.println("TimeZone   " + tz.getDisplayName(false, TimeZone.SHORT) + " Timezone id :: " + tz.getID());
        String timeZone = tz.getID();
        if (timeZone.equals("Asia/Kolkata") || timeZone.equals("Asia/Calcutta")) {
            getPlan();
        } else {
            getPlansForInternational();
        }

        progress.showDialog();

    }

    private void getPlan() {
        String id = SharedPrefsManager.getInstance().getString(LOGIN_ID);
        apiInterface.getPlans(id).enqueue(new Callback<MemberShipPlanModel>() {
            @Override
            public void onResponse(Call<MemberShipPlanModel> call, Response<MemberShipPlanModel> response) {
                if (response.isSuccessful()) {
                    MemberShipPlanModel memberShipPlanModel = response.body();
                    if (memberShipPlanModel != null) {
                        progress.cancleDialog();
                        String respons = memberShipPlanModel.getResponse();
                        if (respons.equals("true")) {
                            List<MemberShipPlanModel.MemberShipPlanData> memberShipPlanDataList = memberShipPlanModel.getMemberShipPlanList();
                            if (memberShipPlanDataList.size() > 0) {
                                memberShipPlanAdapter.addCustomerList(memberShipPlanDataList);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MemberShipPlanModel> call, Throwable t) {
                Toast.makeText(MemberShipPlanActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress.cancleDialog();
            }
        });
    }


    private void getPlansForInternational() {
        String id = SharedPrefsManager.getInstance().getString(LOGIN_ID);
        apiInterface.getPlansForInternational(id).enqueue(new Callback<MemberShipPlanModel>() {
            @Override
            public void onResponse(Call<MemberShipPlanModel> call, Response<MemberShipPlanModel> response) {
                if (response.isSuccessful()) {
                    MemberShipPlanModel memberShipPlanModel = response.body();
                    if (memberShipPlanModel != null) {
                        progress.cancleDialog();
                        String respons = memberShipPlanModel.getResponse();
                        if (respons.equals("true")) {
                            List<MemberShipPlanModel.MemberShipPlanData> memberShipPlanDataList = memberShipPlanModel.getMemberShipPlanList();
                            if (memberShipPlanDataList.size() > 0) {
                                memberShipPlanAdapter.addCustomerList(memberShipPlanDataList);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MemberShipPlanModel> call, Throwable t) {
                Toast.makeText(MemberShipPlanActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress.cancleDialog();
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
                        String respons = userStatusModel.getResponse();
                        String status = userStatusModel.getStatus();
                        if (status.equals("Active")) {

                        } else {
                            SharedPrefsManager.getInstance().clearPrefs();
                            Intent intent = new Intent(MemberShipPlanActivity.this, LoginActivity.class);
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
                Toast.makeText(MemberShipPlanActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

}
