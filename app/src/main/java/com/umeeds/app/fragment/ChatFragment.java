package com.umeeds.app.fragment;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.umeeds.app.R;
import com.umeeds.app.adapter.ChatAdapter;
import com.umeeds.app.helper.AppHelper;
import com.umeeds.app.model.AcceptModel;
import com.umeeds.app.model.LoginModel;
import com.umeeds.app.model.ProfileModel;
import com.umeeds.app.model.YardsItem;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static com.umeeds.app.network.networking.Constant.LOGIN_ID;
import static com.umeeds.app.network.networking.Constant.MATRI_ID;
import static com.umeeds.app.network.networking.Constant.NO_CHAT_CONTACT;


public class ChatFragment extends Fragment {

    // private ProgressDailog progress;
    public static String matriID, status, nochatContact, email, mobileNo;
    RecyclerView chat_recy;
    ProgressBar progressCircular;
    ChatAdapter chatAdapter;
    List<YardsItem> customerListList = new ArrayList<>();
    YardsItem yardsItem;
    ImageView iv_logo;
    boolean isFilter = true;
    ProgressBar progress_bar;
    List<AcceptModel.AcceptData> acceptDataList = new ArrayList<>();
    private ImageView iv_back, search_iv;
    private ApiInterface apiInterface;
    private EditText search_et;
    private int last = 0;
    private int offset = 0;

    public ChatFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  Api.getInstance().call(ApiClient.getInterface().AllPostApi(SharedPrefsManager.getInstance().getString(WORD_ID),"0"), this, 4);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        iv_back = view.findViewById(R.id.iv_back);
        chat_recy = view.findViewById(R.id.chat_recy);
        progressCircular = view.findViewById(R.id.progress_circular);
        iv_logo = view.findViewById(R.id.iv_logo);
        progress_bar = view.findViewById(R.id.progress_bar);
        search_iv = view.findViewById(R.id.search_iv);
        search_et = view.findViewById(R.id.search_et);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        chat_recy.setLayoutManager(layoutManager);
        chatAdapter = new ChatAdapter(getActivity(), ChatFragment.this, acceptDataList, progress_bar);
        chat_recy.setAdapter(chatAdapter);

        apiInterface = ApiClient.getInterface();
        // progress = new ProgressDailog(getContext());

        chatList(SharedPrefsManager.getInstance().getString(MATRI_ID), offset);
        profileDetailApi(SharedPrefsManager.getInstance().getString(LOGIN_ID));


        AppHelper.setupLoadMore(chat_recy, lastVisibleItem -> {
            if (last != lastVisibleItem) {
                last = lastVisibleItem;
                offset = offset + 5;
                if (!isFilter) {
                    chatList(SharedPrefsManager.getInstance().getString(MATRI_ID), offset);
                }
            }
        });


        search_iv.setOnClickListener(v -> {
            isFilter = true;
            List<AcceptModel.AcceptData> acceptDataListFilter = new ArrayList<>();
            String query = search_et.getText().toString();
            if (query != null && !query.isEmpty()) {
                for (int i = 0; i < acceptDataList.size(); i++) {
                    String matriid = acceptDataList.get(i).getMatriId();
                    Log.e("idd", "Matriid " + matriid + " query " + query);
                    if (acceptDataList.get(i).getMatriId().contains(query.trim())) {
                        acceptDataListFilter.add(acceptDataList.get(i));
                        Log.e("match", "Matriid ");
                    }
                }
//                Collections.sort(acceptDataListFilter, (s1, s2) ->
//                        s2.getMsg().getSendDate().compareToIgnoreCase(s1.getMsg().getSendDate()));
                chatAdapter.addCustomerList(acceptDataListFilter);

            } else {
                chatAdapter.addCustomerList(acceptDataList);

            }
        });
        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    chatAdapter.addCustomerList(acceptDataList);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void chatList(String matriID, int pagecount) {
        isFilter = false;
        progress_bar.setVisibility(View.VISIBLE);
        apiInterface.myChatList(matriID, pagecount + "").enqueue(new Callback<AcceptModel>() {
            @Override
            public void onResponse(@NonNull Call<AcceptModel> call, @NonNull Response<AcceptModel> response) {
                if (response.isSuccessful()) {
                    AcceptModel acceptModel = response.body();
                    if (acceptModel != null) {
                        progress_bar.setVisibility(GONE);
                        if (acceptModel.getPendingData().size() > 0) {
                            iv_logo.setVisibility(GONE);

                            acceptDataList.addAll(acceptModel.getPendingData());

                            Collections.sort(acceptDataList, (s1, s2) ->
                                    s2.getMsg().getSendDate().compareToIgnoreCase(s1.getMsg().getSendDate()));
                            //  acceptDataList.addAll(acceptDataList);

                            chatAdapter.addCustomerList(acceptDataList);

//                            chatAdapter.notifyDataSetChanged();


                        } else {
                            if (offset == 0) {
                                Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_LONG).show();
                                iv_logo.setVisibility(View.VISIBLE);
                            }
                            if (offset > 0) {
                                --offset;
                            }
                            last = 0;
                        }
                    } else {
                        progress_bar.setVisibility(GONE);
                        if (offset > 0) {
                            --offset;
                        }
                        last = 0;
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AcceptModel> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(GONE);
                if (offset > 0) {
                    --offset;
                }
                last = 0;
            }
        });
    }


    public void user_chat_count_save(String matriId, String loginmatriid) {
        apiInterface.user_chat_count_save(matriId, loginmatriid).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel homeModel = response.body();
                    if (homeModel != null) {
                        boolean respnse = homeModel.isResponse();
                        if (respnse) {
                            //  Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            // Toast.makeText(MoreInfoActivity.this,"Already Success",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {

            }
        });
    }


    private void profileDetailApi(String userId) {
        apiInterface.profileDetail(userId).enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<ProfileModel> call, @NonNull Response<ProfileModel> response) {
                if (response.isSuccessful()) {
                    ProfileModel profileModel = response.body();
                    if (profileModel != null) {
                        boolean respons = profileModel.isResponse();
                        if (respons) {
                            ProfileModel.ProfileData profileData = profileModel.getProfileData();
                            matriID = profileData.getMatriID();
                            status = profileData.getStatus();
                            nochatContact = profileData.getChatcontact();
                            email = profileData.getConfirmEmail();
                            mobileNo = profileData.getMobile();
                            SharedPrefsManager.getInstance().setString(NO_CHAT_CONTACT, nochatContact);
                        } else {
                            if (getContext() != null) {
                                Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProfileModel> call, @NonNull Throwable t) {
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
