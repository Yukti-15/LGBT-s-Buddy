package com.umeeds.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.umeeds.app.R;
import com.umeeds.app.adapter.ChatAdapter;
import com.umeeds.app.fragment.ChatFragment;
import com.umeeds.app.fragment.ChatNewRequestFragment;
import com.umeeds.app.fragment.ChatPandingFragment;
import com.umeeds.app.model.UserStatusModel;
import com.umeeds.app.model.YardsItem;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.umeeds.app.network.networking.Constant.LOGIN_ID;

public class ChatActivity extends AppCompatActivity {

    ImageView iv_back, iv_home;
    RecyclerView chat_recy;
    ProgressBar progressCircular;

    ChatAdapter chatAdapter;
    List<YardsItem> customerListList = new ArrayList<>();
    YardsItem yardsItem;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    TextView tv_accepted, tv_newrequest, tv_panding;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        iv_back = findViewById(R.id.iv_back);
        iv_home = findViewById(R.id.iv_home);
        chat_recy = findViewById(R.id.chat_recy);
        progressCircular = findViewById(R.id.progress_circular);
        tv_accepted = findViewById(R.id.tv_accepted);
        tv_newrequest = findViewById(R.id.tv_newrequest);
        tv_panding = findViewById(R.id.tv_panding);

        apiInterface = ApiClient.getInterface();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        chat_recy.setLayoutManager(layoutManager);

        // chatAdapter=new ChatAdapter(this);
        // chat_recy.setAdapter(chatAdapter);

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


        fragment = new ChatFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        //  setList();

        tv_accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new ChatFragment();
                replaceFragment();

                tv_accepted.setBackgroundResource(R.drawable.ractangle_colour);
                tv_accepted.setTextColor(getResources().getColor(R.color.white));


                tv_panding.setBackgroundResource(R.drawable.ractangle_white);
                tv_panding.setTextColor(getResources().getColor(R.color.red));
                tv_newrequest.setBackgroundResource(R.drawable.ractangle_white);
                tv_newrequest.setTextColor(getResources().getColor(R.color.red));

            }
        });

        tv_panding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new ChatPandingFragment();
                replaceFragment();

                tv_panding.setBackgroundResource(R.drawable.ractangle_colour);
                tv_panding.setTextColor(getResources().getColor(R.color.white));

                tv_accepted.setBackgroundResource(R.drawable.ractangle_white);
                tv_accepted.setTextColor(getResources().getColor(R.color.red));
                tv_newrequest.setBackgroundResource(R.drawable.ractangle_white);
                tv_newrequest.setTextColor(getResources().getColor(R.color.red));

            }
        });

        tv_newrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new ChatNewRequestFragment();
                replaceFragment();
                tv_newrequest.setBackgroundResource(R.drawable.ractangle_colour);
                tv_newrequest.setTextColor(getResources().getColor(R.color.white));

                tv_panding.setBackgroundResource(R.drawable.ractangle_white);
                tv_panding.setTextColor(getResources().getColor(R.color.red));
                tv_accepted.setBackgroundResource(R.drawable.ractangle_white);
                tv_accepted.setTextColor(getResources().getColor(R.color.red));
            }
        });

    }

    private void replaceFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    private void setList() {
        for (int i = 0; i < 10; i++) {
            yardsItem = new YardsItem("Elina De Souza", "ward12@cityofchicago.org", "773-523-8250", "Hours: Monday - 9:00am-6:00pm, Ward Night\n" +
                    "Tuesday - Friday - 9:00am-5:00pm", "3476 S. Archer", "IL 60608");
            customerListList.add(yardsItem);
        }
        //  chatAdapter.addCustomerList(customerListList);

        Log.e("customerListList size", "" + customerListList.size());
        // adapter=new PollListAdapter(getActivity(),customerListList);
        // yard_recycler_view.setAdapter(adapter);
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
                            Intent intent = new Intent(ChatActivity.this, LoginActivity.class);
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
                Toast.makeText(ChatActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

}
