package com.umeeds.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.umeeds.app.MainActivity;
import com.umeeds.app.R;
import com.umeeds.app.adapter.VisitedUserProfileAdapter;
import com.umeeds.app.helper.AppHelperNew;
import com.umeeds.app.model.MyVisitedModel;
import com.umeeds.app.network.UtilsMethod;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.paytm.pgsdk.easypay.manager.PaytmAssist.getContext;

public class MyProfileVisitorActivity extends AppCompatActivity {
    private VisitedUserProfileAdapter visitedUserProfileAdapter;
    private RecyclerView visit_user_rcv;
    private List<MyVisitedModel.DataBean> dataBeans;
    private UtilsMethod progress;
    private ImageView iv_home, iv_back;
    private int last = 0;
    private int offset = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_visitor);
        visit_user_rcv = findViewById(R.id.visit_user_rcv);
        iv_back = findViewById(R.id.iv_back);
        iv_home = findViewById(R.id.iv_home);
        iv_back.setOnClickListener(v -> finish());

        iv_home.setOnClickListener(v -> {
            startActivity(new Intent(MyProfileVisitorActivity.this, MainActivity.class));
            // finish();
        });

        dataBeans = new ArrayList<>();
        progress = new UtilsMethod(this);
        visitedUserProfileAdapter = new VisitedUserProfileAdapter(dataBeans, this);

        getVisitedUsers(offset);
        AppHelperNew.setupLoadMore(visit_user_rcv, lastVisibleItem -> {
            if (last != lastVisibleItem) {
                last = lastVisibleItem;
                offset = offset + 5;
                getVisitedUsers(offset);
            }
        });
        visit_user_rcv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        visit_user_rcv.setAdapter(visitedUserProfileAdapter);
    }

    public void getVisitedUsers(int OFFSET) {
//        progress.showDialog();
        if (OFFSET == 0) {
            dataBeans.clear();
            visitedUserProfileAdapter.notifyDataSetChanged();
        }
        ApiClient.getInterface().myProfileVisitedUsers(SharedPrefsManager.getInstance().getString(Constant.MATRI_ID), String.valueOf(offset)).enqueue(new Callback<MyVisitedModel>() {
            @Override
            public void onResponse(Call<MyVisitedModel> call, Response<MyVisitedModel> response) {
                if (response.isSuccessful()) {
                    MyVisitedModel visitedModel = response.body();
                    assert visitedModel != null;
                    if (visitedModel.getResponse()) {
                        List<MyVisitedModel.DataBean> dataBeansLocale;
                        dataBeansLocale = visitedModel.getData();
                        if (dataBeansLocale != null && dataBeansLocale.size() > 0) {
                            dataBeans.addAll(dataBeansLocale);
                            visitedUserProfileAdapter.notifyDataSetChanged();
                        } else {
                            if (offset > 0) {
                                --offset;
                            }
                            last = 0;
                        }
                    } else {
                        if (offset > 0) {
                            --offset;
                        }
                        last = 0;
                    }
                } else {
                    Toast.makeText(MyProfileVisitorActivity.this, "No views available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MyVisitedModel> call, Throwable t) {
                Toast.makeText(MyProfileVisitorActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                progress.cancleDialog();
            }
        });
    }
}