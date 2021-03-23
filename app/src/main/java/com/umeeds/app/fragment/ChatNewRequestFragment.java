package com.umeeds.app.fragment;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.umeeds.app.R;
import com.umeeds.app.adapter.ChatNewRequestAdapter;
import com.umeeds.app.helper.AppHelper;
import com.umeeds.app.model.NewRequestModel;
import com.umeeds.app.model.PendingModel;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.umeeds.app.network.networking.Constant.MATRI_ID;

public class ChatNewRequestFragment extends Fragment {

    private final List<NewRequestModel.NewRequestData> newRequestDataList = new ArrayList<>();
    private RecyclerView request_recy;
    boolean isFilter = true;
    private ChatNewRequestAdapter chatNewRequestAdapter;
    private ApiInterface apiInterface;
    private ProgressBar progress_bar;
    private int last = 0;
    private int offset = 0;
    private ImageView iv_logo, search_iv;
    private EditText search_et;

    public ChatNewRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_newrequest, container, false);

        request_recy = view.findViewById(R.id.request_recy);
        iv_logo = view.findViewById(R.id.iv_logo);
        progress_bar = view.findViewById(R.id.progress_bar);
        search_iv = view.findViewById(R.id.search_iv);
        search_et = view.findViewById(R.id.search_et);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        request_recy.setLayoutManager(layoutManager);

        chatNewRequestAdapter = new ChatNewRequestAdapter(getContext(), ChatNewRequestFragment.this, newRequestDataList);
        request_recy.setAdapter(chatNewRequestAdapter);
        apiInterface = ApiClient.getInterface();

        chatNewRequest(SharedPrefsManager.getInstance().getString(MATRI_ID), offset);

        AppHelper.setupLoadMore(request_recy, lastVisibleItem -> {
            if (last != lastVisibleItem) {
                last = lastVisibleItem;
                offset = offset + 5;
                if (!isFilter) {
                    chatNewRequest(SharedPrefsManager.getInstance().getString(MATRI_ID), offset);
                }
            }
        });


        search_iv.setOnClickListener(v -> {
            isFilter = true;
            List<NewRequestModel.NewRequestData> newRequestDataListFilter = new ArrayList<>();
            String query = search_et.getText().toString();
            if (query != null && !query.isEmpty()) {
                for (int i = 0; i < newRequestDataList.size(); i++) {
                    String matriid = newRequestDataList.get(i).getMatriId();
                    if (newRequestDataList.get(i).getMatriId().contains(query.trim())) {
                        newRequestDataListFilter.add(newRequestDataList.get(i));
                    }
                }
//                Collections.sort(acceptDataListFilter, (s1, s2) ->
//                        s2.getMsg().getSendDate().compareToIgnoreCase(s1.getMsg().getSendDate()));
                chatNewRequestAdapter.addCustomerList(newRequestDataListFilter);

            } else {
                chatNewRequestAdapter.addCustomerList(newRequestDataList);

            }
        });
        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    chatNewRequestAdapter.addCustomerList(newRequestDataList);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }


    private void chatNewRequest(String matriID, int pagecount) {
        isFilter = false;

        progress_bar.setVisibility(View.VISIBLE);
        apiInterface.requestedList(matriID, pagecount + "").enqueue(new Callback<NewRequestModel>() {
            @Override
            public void onResponse(Call<NewRequestModel> call, Response<NewRequestModel> response) {
                if (response.isSuccessful()) {
                    NewRequestModel newRequestModel = response.body();
                    if (newRequestModel != null) {
                        progress_bar.setVisibility(View.GONE);
                        String responses = newRequestModel.getResponse();
                        if (responses.equals("true")) {
                            iv_logo.setVisibility(View.GONE);
                            /* newRequestDataList = newRequestModel.getNewRequestDataList();
                            if (newRequestDataList.size() > 0  && newRequestDataList != null) {
                                chatNewRequestAdapter.addCustomerList(newRequestDataList);
                            }*/
                            if (newRequestModel.getNewRequestDataList().size() > 0) {
                                newRequestDataList.addAll(newRequestModel.getNewRequestDataList());
                                chatNewRequestAdapter.notifyDataSetChanged();
                            }

                        } else {
                            if (offset == 0) {
                                iv_logo.setVisibility(View.VISIBLE);
                                Toast.makeText(getContext(), "No new request", Toast.LENGTH_LONG).show();
                            }
                            if (offset > 0) {
                                --offset;
                            }
                            last = 0;
                        }
                    } else {
                        progress_bar.setVisibility(View.GONE);
                        if (offset > 0) {
                            --offset;
                        }
                        last = 0;
                    }
                }
            }

            @Override
            public void onFailure(Call<NewRequestModel> call, Throwable t) {
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(View.GONE);
                if (offset > 0) {
                    --offset;
                }
                last = 0;
            }
        });
    }

    public void acceptRequest(String req_id) {

        apiInterface.acceptRequest(req_id).enqueue(new Callback<PendingModel>() {
            @Override
            public void onResponse(Call<PendingModel> call, Response<PendingModel> response) {
                if (response.isSuccessful()) {
                    PendingModel homeModel = response.body();
                    if (homeModel != null) {
                        String respons = homeModel.getResponse();
                        if (respons.equals("true")) {
                            Toast.makeText(getContext(), "accepted", Toast.LENGTH_LONG).show();
                            chatNewRequestAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "Already accepted", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PendingModel> call, Throwable t) {
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
