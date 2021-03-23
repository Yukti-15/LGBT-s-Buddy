package com.umeeds.app.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.umeeds.app.R;
import com.umeeds.app.adapter.HomeAdapter;
import com.umeeds.app.helper.GridSpacingItemDecoration;
import com.umeeds.app.model.HomeModel;
import com.umeeds.app.model.LoginModel;
import com.umeeds.app.model.YardsItem;
import com.umeeds.app.network.UtilsMethod;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.umeeds.app.network.UtilsMethod.dpToPx;
import static com.umeeds.app.network.networking.Constant.LOGIN_ID;

public class MainFragment extends Fragment {


    HomeAdapter homeAdapter;
    List<YardsItem> customerListList = new ArrayList<>();
    YardsItem yardsItem;
    RecyclerView main_recycler_view;
    ProgressBar progressCircular;
    ProgressBar progress_bar;
    ImageView iv_logo;
    String userId;
    //  RecyclerView.LayoutManager layoutManager;
    int OFFSET = 0;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    List<HomeModel.HomeData> homeDataList = new ArrayList<>();
    private ApiInterface apiInterface;
    private UtilsMethod progress;
    private GridLayoutManager layoutManager;
    private boolean loading = true;

    public MainFragment() {
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        main_recycler_view = view.findViewById(R.id.main_recycler_view);
        progressCircular = view.findViewById(R.id.progress_circular);
        iv_logo = view.findViewById(R.id.iv_logo);
        progress_bar = view.findViewById(R.id.progress_bar);

        userId = SharedPrefsManager.getInstance().getString(LOGIN_ID);

        Log.i("user..id", userId);
        layoutManager = new GridLayoutManager(main_recycler_view.getContext(), 1);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        main_recycler_view.setHasFixedSize(true);
        main_recycler_view.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(4), true));
        main_recycler_view.setLayoutManager(layoutManager);

        //  visitedProfileAdapter = new VisitedProfileAdapter(getActivity(), visitedModelListArrayList);
        // recyclerview.setAdapter(visitedProfileAdapter);
        //  layoutManager = new LinearLayoutManager(getContext());
        //  main_recycler_view.setLayoutManager(layoutManager);

        homeAdapter = new HomeAdapter(getActivity(), MainFragment.this, homeDataList);
        main_recycler_view.setAdapter(homeAdapter);

        apiInterface = ApiClient.getInterface();
        progress = new UtilsMethod(getContext());

        OFFSET = 0;
        homeMatch(userId, OFFSET);

        main_recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                //  super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        Log.d("off", " on Scroll mList Size==>>" + homeDataList.size());
                        Log.d("offset", "cnt>>" + OFFSET);
                        Log.d("offset", "visible>>" + visibleItemCount + "past>>" + pastVisiblesItems + "total>>" + totalItemCount);
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            Log.e("Last Item Wow !", "Last Item Wow !");
                            loading = false;
                            OFFSET = OFFSET + 5;
                            homeMatch(userId, OFFSET);
                        }
                    }
                }
            }

        });

        // setList();

        return view;
    }


    private void homeMatch(String userId, final int OFFSET) {
        Log.i("...userid", userId);
        Log.i("...of", String.valueOf(OFFSET));
        progress_bar.setVisibility(View.VISIBLE);
        apiInterface.homeMatch(userId, OFFSET + "").enqueue(new Callback<HomeModel>() {
            @Override
            public void onResponse(@NonNull Call<HomeModel> call, @NonNull Response<HomeModel> response) {
                if (response.isSuccessful()) {
                    HomeModel homeModel = response.body();
                    if (homeModel != null) {
                        progress_bar.setVisibility(View.GONE);
                        if (OFFSET == 0) {
                            homeDataList.clear();
                        }
                        loading = true;
                        homeDataList = homeModel.getHomeDataList();
                        if (homeDataList != null && homeDataList.size() > 0) {
                            homeAdapter.addAll(homeDataList);
                            iv_logo.setVisibility(View.GONE);
                        } else {
                            if (OFFSET == 0) {
                                iv_logo.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<HomeModel> call, @NonNull Throwable t) {
                progress_bar.setVisibility(View.GONE);
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void sendRequest(String matriId, String loginmatriid) {
        apiInterface.sendFriendRequest(matriId, loginmatriid).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel homeModel = response.body();
                    if (homeModel != null) {
                        boolean respnse = homeModel.isResponse();
                        if (respnse) {
                            Toast.makeText(getContext(), "Sent request", Toast.LENGTH_SHORT).show();
                            // homeMatch(userId,OFFSET);
                            //  homeAdapter.notifyDataSetChanged();
                        } else {
                            //  Toast.makeText(getContext(),"Already sent request",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
                progress.cancleDialog();
            }
        });

    }


    public void sendheartlist(String matriId, String loginmatriid, String status) {
        apiInterface.sendheartlist(matriId, loginmatriid, status).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel homeModel = response.body();
                    if (homeModel != null) {
                        boolean respnse = homeModel.isResponse();
                        if (respnse) {
                            //  homeMatch(userId,OFFSET);
                            //  homeAdapter.notifyDataSetChanged();
                        } else {
                            //   Toast.makeText(getContext(),"UnLike",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
                progress.cancleDialog();
            }
        });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //  unbinder.unbind();
    }


}
