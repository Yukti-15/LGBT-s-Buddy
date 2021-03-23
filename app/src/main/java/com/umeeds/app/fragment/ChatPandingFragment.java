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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.umeeds.app.R;
import com.umeeds.app.adapter.ChatPendingAdapter;
import com.umeeds.app.helper.AppHelper;
import com.umeeds.app.model.PendingModel;
import com.umeeds.app.model.YardsItem;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.umeeds.app.network.networking.Constant.MATRI_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatPandingFragment extends Fragment {

    ImageView iv_back, iv_logo, search_iv;
    RecyclerView pending_recy;
    ProgressBar progressCircular;
    boolean isFilter = true;
    List<PendingModel.PendingData> homeDataList = new ArrayList<>();
    ChatPendingAdapter chatPendingAdapter;
    List<YardsItem> customerListList = new ArrayList<>();
    YardsItem yardsItem;
    ProgressBar progress_bar;
    int OFFSET = 0;
    private final boolean loading = true;
    RecyclerView.LayoutManager layoutManager;
    //private ProgressDailog progress;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private EditText search_et;
    private ApiInterface apiInterface;
    private int last = 0;
    private int offset = 0;

    public ChatPandingFragment() {
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
        View view = inflater.inflate(R.layout.fragment_chat_panding, container, false);

        iv_back = view.findViewById(R.id.iv_back);
        pending_recy = view.findViewById(R.id.pending_recy);
        progressCircular = view.findViewById(R.id.progress_circular);
        iv_logo = view.findViewById(R.id.iv_logo);
        progress_bar = view.findViewById(R.id.progress_bar);
        apiInterface = ApiClient.getInterface();
        search_iv = view.findViewById(R.id.search_iv);
        search_et = view.findViewById(R.id.search_et);

        layoutManager = new LinearLayoutManager(getActivity());
        pending_recy.setLayoutManager(layoutManager);
        pending_recy.setHasFixedSize(true);
        pending_recy.setNestedScrollingEnabled(false);

        chatPendingAdapter = new ChatPendingAdapter(getActivity(), ChatPandingFragment.this, homeDataList);
        pending_recy.setAdapter(chatPendingAdapter);

        pendingList(SharedPrefsManager.getInstance().getString(MATRI_ID), offset);

        AppHelper.setupLoadMore(pending_recy, new AppHelper.OnScrollToEnd() {
            @Override
            public void scrolledToEnd(int lastVisibleItem) {
                if (last != lastVisibleItem) {
                    last = lastVisibleItem;
                    offset = offset + 5;
                    if (!isFilter) {
                        pendingList(SharedPrefsManager.getInstance().getString(MATRI_ID), offset);
                    }
                }
            }
        });

        search_iv.setOnClickListener(v -> {
            isFilter = true;
            List<PendingModel.PendingData> homeDataListFilter = new ArrayList<>();
            String query = search_et.getText().toString();
            if (query != null && !query.isEmpty()) {
                for (int i = 0; i < homeDataList.size(); i++) {
                    String matriid = homeDataList.get(i).getMatriId();
                    Log.e("idd", "Matriid " + matriid + " query " + query);
                    if (homeDataList.get(i).getMatriId().contains(query.trim())) {
                        homeDataListFilter.add(homeDataList.get(i));
                        Log.e("match", "Matriid ");
                    }
                }
//                Collections.sort(acceptDataListFilter, (s1, s2) ->
//                        s2.getMsg().getSendDate().compareToIgnoreCase(s1.getMsg().getSendDate()));
                chatPendingAdapter.addCustomerList(homeDataListFilter);

            } else {
                chatPendingAdapter.addCustomerList(homeDataList);

            }
        });
        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    chatPendingAdapter.addCustomerList(homeDataList);
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
        //  unbinder.unbind();
    }


    private void pendingList(String matriID, int OFFSET) {
        isFilter = false;
        Log.i("rhl...", String.valueOf(OFFSET));
        progress_bar.setVisibility(View.VISIBLE);
        apiInterface.pendingList(matriID, OFFSET + "").enqueue(new Callback<PendingModel>() {
            @Override
            public void onResponse(Call<PendingModel> call, Response<PendingModel> response) {
                if (response.isSuccessful()) {
                    PendingModel homeModel = response.body();
                    if (homeModel != null) {
                        String responses = homeModel.getResponse();
                        progress_bar.setVisibility(View.GONE);
                        if (responses.equals("true")) {
                            iv_logo.setVisibility(View.GONE);
                            if (homeModel.getPendingData().size() > 0) {
                                homeDataList.addAll(homeModel.getPendingData());
                                chatPendingAdapter.notifyDataSetChanged();
                            }
                        } else {
                            if (offset == 0) {
                                Toast.makeText(getContext(), "No pending request", Toast.LENGTH_LONG).show();
                                iv_logo.setVisibility(View.VISIBLE);
                            }
                            //  Toast.makeText(getContext(), "No pending request", Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<PendingModel> call, Throwable t) {
                if (offset > 0) {
                    --offset;
                }
                last = 0;
                progress_bar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
            }
        });

    }


    public void cancel_send_request(String req_id) {
        apiInterface.cancel_send_request(req_id).enqueue(new Callback<PendingModel>() {
            @Override
            public void onResponse(Call<PendingModel> call, Response<PendingModel> response) {
                if (response.isSuccessful()) {
                    PendingModel homeModel = response.body();
                    if (homeModel != null) {
                        String respons = homeModel.getResponse();
                        if (respons.equals("true")) {
                            // Toast.makeText(getContext(), "accepted", Toast.LENGTH_LONG).show();
                            pendingList(SharedPrefsManager.getInstance().getString(MATRI_ID), offset);
                            chatPendingAdapter.notifyDataSetChanged();
                        } else {
                            //  Toast.makeText(getContext(), "Already accepted", Toast.LENGTH_LONG).show();
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

}
