package com.umeeds.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.umeeds.app.R;
import com.umeeds.app.adapter.NotificationAdapter;
import com.umeeds.app.model.NotifiDeletModel;
import com.umeeds.app.model.NotificationModel;
import com.umeeds.app.model.UserStatusModel;
import com.umeeds.app.network.UtilsMethod;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.umeeds.app.network.networking.Constant.LOGIN_ID;
import static com.umeeds.app.network.networking.Constant.MATRI_ID;

public class NotificationActivity extends AppCompatActivity {


    private ImageView iv_back, iv_home, iv_logo, delete_all_iv;
    private RecyclerView notification_recy;
    private NotificationAdapter notificationAdapter;

    private ApiInterface apiInterface;
    private UtilsMethod progress;
    private List<NotificationModel.NotificationData> notificationDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        iv_back = findViewById(R.id.iv_back);
        iv_home = findViewById(R.id.iv_home);
        iv_logo = findViewById(R.id.iv_logo);
        delete_all_iv = findViewById(R.id.delete_all_iv);
        notification_recy = findViewById(R.id.notification_recy);

        apiInterface = ApiClient.getInterface();
        progress = new UtilsMethod(this);
        notificationDataList = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        notification_recy.setLayoutManager(layoutManager);

        notificationAdapter = new NotificationAdapter(this, NotificationActivity.this);
        notification_recy.setAdapter(notificationAdapter);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        delete_all_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_all_notification();
            }
        });

        all_notification_list(SharedPrefsManager.getInstance().getString(MATRI_ID));
    }

    private void all_notification_list(String MatriID) {
        progress.showDialog();
        apiInterface.all_notification_list(MatriID).enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                if (response.isSuccessful()) {
                    NotificationModel notificationModel = response.body();
                    if (notificationModel != null) {
                        progress.cancleDialog();
                        notificationDataList = notificationModel.getNotificationDataList();
                        if (notificationDataList != null && notificationDataList.size() > 0) {
                            iv_logo.setVisibility(View.GONE);
                            notificationAdapter.addCustomerList(notificationDataList);
                        } else {
                            iv_logo.setVisibility(View.VISIBLE);
                            Toast.makeText(NotificationActivity.this, "No notification", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {
                Toast.makeText(NotificationActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress.cancleDialog();
                iv_logo.setVisibility(View.VISIBLE);
            }
        });
    }


    public void delete_notification(String nid, final int position, final List<NotificationModel.NotificationData> customerList) {
        apiInterface.delete_notification(nid).enqueue(new Callback<NotifiDeletModel>() {
            @Override
            public void onResponse(Call<NotifiDeletModel> call, Response<NotifiDeletModel> response) {
                if (response.isSuccessful()) {
                    NotifiDeletModel notificationModel = response.body();
                    if (notificationModel != null) {
                        progress.cancleDialog();
                        String respon = notificationModel.getResponse();
                        if (respon.equals("true")) {
                            Toast.makeText(NotificationActivity.this, "Delete", Toast.LENGTH_LONG).show();
                            customerList.remove(position);
                            notificationAdapter.notifyItemRemoved(position);
                            notificationAdapter.notifyDataSetChanged();
                            all_notification_list(SharedPrefsManager.getInstance().getString(MATRI_ID));

                        } else {
                            Toast.makeText(NotificationActivity.this, "Already Delete", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<NotifiDeletModel> call, Throwable t) {
                Toast.makeText(NotificationActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress.cancleDialog();
            }
        });
    }

    public void delete_all_notification() {
        apiInterface.delete_all_notification(SharedPrefsManager.getInstance().getString(MATRI_ID)).enqueue(new Callback<NotifiDeletModel>() {
            @Override
            public void onResponse(Call<NotifiDeletModel> call, Response<NotifiDeletModel> response) {
                if (response.isSuccessful()) {
                    NotifiDeletModel notificationModel = response.body();
                    if (notificationModel != null) {
                        progress.cancleDialog();
                        String respon = notificationModel.getResponse();
                        if (respon.equals("true")) {
                            Toast.makeText(NotificationActivity.this, "Notification Clear", Toast.LENGTH_LONG).show();
                            notificationDataList.clear();
                            notificationAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(NotificationActivity.this, "Already Delete", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<NotifiDeletModel> call, Throwable t) {
                Toast.makeText(NotificationActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress.cancleDialog();
            }
        });
    }


    public void read_notification(String nid, final int position, final List<NotificationModel.NotificationData> customerList) {
        apiInterface.read_notification(nid).enqueue(new Callback<NotifiDeletModel>() {
            @Override
            public void onResponse(Call<NotifiDeletModel> call, Response<NotifiDeletModel> response) {
                if (response.isSuccessful()) {
                    NotifiDeletModel notificationModel = response.body();
                    if (notificationModel != null) {
                        progress.cancleDialog();
                        String respon = notificationModel.getResponse();
                        if (respon.equals("true")) {
                            //  Toast.makeText(NotificationActivity.this, "Read", Toast.LENGTH_LONG).show();
                            notificationAdapter.notifyDataSetChanged();
                            all_notification_list(SharedPrefsManager.getInstance().getString(MATRI_ID));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<NotifiDeletModel> call, Throwable t) {
                Toast.makeText(NotificationActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
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
                        String status = userStatusModel.getStatus();
                        if (!status.equals("Active")) {
                            SharedPrefsManager.getInstance().clearPrefs();
                            Intent intent = new Intent(NotificationActivity.this, LoginActivity.class);
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
                Toast.makeText(NotificationActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
            }
        });
    }


}
