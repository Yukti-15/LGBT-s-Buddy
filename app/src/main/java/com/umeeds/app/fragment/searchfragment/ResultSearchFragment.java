package com.umeeds.app.fragment.searchfragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.umeeds.app.R;
import com.umeeds.app.adapter.SearchAdapter;
import com.umeeds.app.helper.AppHelper;
import com.umeeds.app.model.LoginModel;
import com.umeeds.app.model.serachmodel.SmartSearchModel;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static com.umeeds.app.network.networking.Constant.MATRI_ID;
import static com.umeeds.app.network.networking.Constant.SHOW_GENDER;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultSearchFragment extends Fragment implements View.OnClickListener {

    RecyclerView rv_search_result;
    //private ProgressDailog progress;
    List<SmartSearchModel.SmartSearchData> smartSearchDataList = new ArrayList<>();
    ProgressBar progress_bar;
    SearchAdapter searchAdapter;
    String types = "";
    ImageView iv_logo;
    String min, max, religion, profileCreated, withPhoto, education, special, country, state, city, occupation,
            marital_status, Gender, iam;
    private ApiInterface apiInterface;
    private int last = 0;
    private int offset = 0;

    public ResultSearchFragment() {
        // Required empty public constructor
    }

    public static ResultSearchFragment newInstance() {
        return new ResultSearchFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        rv_search_result = view.findViewById(R.id.rv_search_result);
        iv_logo = view.findViewById(R.id.iv_logo);
        progress_bar = view.findViewById(R.id.progress_bar);

        apiInterface = ApiClient.getInterface();
        //  progress = new ProgressDailog(getContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_search_result.setLayoutManager(layoutManager);

        searchAdapter = new SearchAdapter(getActivity(), ResultSearchFragment.this, smartSearchDataList);
        rv_search_result.setAdapter(searchAdapter);

        final Bundle bundle = getArguments();

        types = bundle.getString("type");

        if (bundle.getString("type").equals("smart")) {
            min = bundle.getString("min");
            max = bundle.getString("max");
            religion = bundle.getString("religion");
            profileCreated = bundle.getString("profile");
            withPhoto = bundle.getString("photo");
            //  OFFSET = 0;
            smartSearch(SharedPrefsManager.getInstance().getString(MATRI_ID), SharedPrefsManager.getInstance().getString(SHOW_GENDER), min, max, withPhoto, withPhoto, religion, offset, "");

            AppHelper.setupLoadMore(rv_search_result, new AppHelper.OnScrollToEnd() {
                @Override
                public void scrolledToEnd(int lastVisibleItem) {
                    if (last != lastVisibleItem) {
                        last = lastVisibleItem;
                        offset = offset + 15;
                        smartSearch(SharedPrefsManager.getInstance().getString(MATRI_ID), SharedPrefsManager.getInstance().getString(SHOW_GENDER), min, max, withPhoto, withPhoto, religion, offset, "");
                    }
                }
            });

        }

        if (bundle.getString("type").equals("Matrimonial")) {
            String matriId = bundle.getString("id");
            //  OFFSET = 0;
            matrimonyIdSearch(matriId, SharedPrefsManager.getInstance().getString(SHOW_GENDER), "0", SharedPrefsManager.getInstance().getString(MATRI_ID));
        }
        if (bundle.getString("type").equals("edu")) {
            min = bundle.getString("min");
            max = bundle.getString("max");
            withPhoto = bundle.getString("photo");
            education = bundle.getString("edu");
            // OFFSET = 0;
            Log.i("rhl...", min + "  " + max + " W " + withPhoto + " E " + education);
            educationalSearch("0", SharedPrefsManager.getInstance().getString(SHOW_GENDER), min, max, education, withPhoto, SharedPrefsManager.getInstance().getString(MATRI_ID));
        }

        if (bundle.getString("type").equals("special")) {
            min = bundle.getString("min");
            max = bundle.getString("max");
            religion = bundle.getString("religion");
            profileCreated = bundle.getString("profile");
            withPhoto = bundle.getString("photo");
            special = bundle.getString("special");
            // OFFSET = 0;
            Log.i("rhl...SP", min + "  " + max + " W " + withPhoto + "  R" + religion + " S " + special);
            specialCaseSearch("0", SharedPrefsManager.getInstance().getString(SHOW_GENDER), min, max, withPhoto, special, religion, SharedPrefsManager.getInstance().getString(MATRI_ID));

        }

        if (bundle.getString("type").equals("location")) {
            min = bundle.getString("min");
            max = bundle.getString("max");
            withPhoto = bundle.getString("photo");
            country = bundle.getString("country");
            state = bundle.getString("state");
            city = bundle.getString("city");
            iam = bundle.getString("sexualOrientation");
            // OFFSET = 0;

            locationSearch(offset, "", "", "", "", country, state, city, SharedPrefsManager.getInstance().getString(MATRI_ID), iam);

            AppHelper.setupLoadMore(rv_search_result, new AppHelper.OnScrollToEnd() {
                @Override
                public void scrolledToEnd(int lastVisibleItem) {
                    if (last != lastVisibleItem) {
                        last = lastVisibleItem;
                        offset = offset + 15;
                        locationSearch(offset, "", "", "", "", country, state, city, SharedPrefsManager.getInstance().getString(MATRI_ID), iam);
                    }
                }
            });

        }

        if (bundle.getString("type").equals("occupation")) {
            min = bundle.getString("min");
            max = bundle.getString("max");
            withPhoto = bundle.getString("photo");
            occupation = bundle.getString("occu");
            //  OFFSET = 0;
            occupationSearch("0", SharedPrefsManager.getInstance().getString(SHOW_GENDER), min, max, withPhoto, occupation, SharedPrefsManager.getInstance().getString(MATRI_ID));
        }

        if (bundle.getString("type").equals("advanced")) {
            min = bundle.getString("min");
            max = bundle.getString("max");
            withPhoto = bundle.getString("photo");
            country = bundle.getString("country");
            state = bundle.getString("state");
            city = bundle.getString("city");
            education = bundle.getString("edu");
            religion = bundle.getString("religion");
            occupation = bundle.getString("occu");
            marital_status = bundle.getString("marital_status");
            Gender = bundle.getString("Gender");
            iam = bundle.getString("iam");
            // OFFSET = 0;
            advancedSearch(offset, Gender, min, max, withPhoto, marital_status, religion, country, state, city, education, occupation, SharedPrefsManager.getInstance().getString(MATRI_ID), iam);
            Log.i("rhl...AD", Gender + " g " + min + "  " + max + " W " + withPhoto + "Re " + religion + "M " + marital_status + " C  " + country + "S  " + state + "  E" + education + "  O" + occupation);

            AppHelper.setupLoadMore(rv_search_result, new AppHelper.OnScrollToEnd() {
                @Override
                public void scrolledToEnd(int lastVisibleItem) {
                    if (last != lastVisibleItem) {
                        last = lastVisibleItem;
                        offset = offset + 15;

                        advancedSearch(offset, Gender, min, max, withPhoto, marital_status, religion, country, state, city, education, occupation, SharedPrefsManager.getInstance().getString(MATRI_ID), iam);
                    }
                }
            });
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rv_search_result:

                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private void smartSearch(String MatriID, String gender, String Fage, String Tage, String txtphoto, String religion, String txtprofile, int pagecount, String caste) {

        progress_bar.setVisibility(View.VISIBLE);
        apiInterface.smartSearch(MatriID, gender, Fage, Tage, txtphoto, religion, txtprofile, pagecount + "", "").enqueue(new Callback<SmartSearchModel>() {
            @Override
            public void onResponse(Call<SmartSearchModel> call, Response<SmartSearchModel> response) {
                if (response.isSuccessful()) {
                    SmartSearchModel smartSearchModel = response.body();
                    if (smartSearchModel != null) {
                        progress_bar.setVisibility(View.GONE);
                        String response1 = smartSearchModel.getResponse();
                        if (response1.equals("true")) {
                           /*
                           smartSearchDataList = smartSearchModel.getSmartSearchDataList();
                            if (smartSearchDataList!= null && smartSearchDataList.size() > 0) {
                                searchAdapter.addCustomerList(smartSearchDataList);
                                iv_logo.setVisibility(View.GONE);
                            }
                           */
                            if (smartSearchModel.getSmartSearchDataList().size() > 0) {
                                iv_logo.setVisibility(GONE);
                                smartSearchDataList.addAll(smartSearchModel.getSmartSearchDataList());
                                searchAdapter.notifyDataSetChanged();
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
                            if (offset == 0) {
                                Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_LONG).show();
                                iv_logo.setVisibility(View.VISIBLE);
                            }
                            if (offset > 0) {
                                --offset;
                            }
                            last = 0;
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<SmartSearchModel> call, Throwable t) {
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(View.GONE);
                if (offset > 0) {
                    --offset;
                }
                last = 0;
            }
        });

    }


    private void matrimonyIdSearch(String MatriID, String gender, String pagecount, String matrilogIn) {
        progress_bar.setVisibility(View.VISIBLE);

        apiInterface.matrimonyIdSearch(MatriID, gender, pagecount, matrilogIn).enqueue(new Callback<SmartSearchModel>() {
            @Override
            public void onResponse(Call<SmartSearchModel> call, Response<SmartSearchModel> response) {
                if (response.isSuccessful()) {
                    SmartSearchModel smartSearchModel = response.body();
                    if (smartSearchModel != null) {
                        progress_bar.setVisibility(View.GONE);
                        String response1 = smartSearchModel.getResponse();
                        if (response1.equals("true")) {
                            List<SmartSearchModel.SmartSearchData> smartSearchDataList = smartSearchModel.getSmartSearchDataList();
                            if (smartSearchDataList != null && smartSearchDataList.size() > 0) {
                                searchAdapter.addCustomerList(smartSearchDataList);
                                iv_logo.setVisibility(View.GONE);
                            } else {
                                iv_logo.setVisibility(View.VISIBLE);
                                Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            iv_logo.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SmartSearchModel> call, Throwable t) {
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(View.GONE);
            }
        });
    }


    private void educationalSearch(String pagecount, String gender, String fAge, String toAge, String education, String txtPhoto, String loginmatriid) {
        progress_bar.setVisibility(View.VISIBLE);

        apiInterface.educationalSearch(pagecount, gender, fAge, toAge, education, txtPhoto, loginmatriid).enqueue(new Callback<SmartSearchModel>() {
            @Override
            public void onResponse(Call<SmartSearchModel> call, Response<SmartSearchModel> response) {
                if (response.isSuccessful()) {
                    SmartSearchModel smartSearchModel = response.body();
                    if (smartSearchModel != null) {
                        progress_bar.setVisibility(View.GONE);
                        String response1 = smartSearchModel.getResponse();
                        if (response1.equals("true")) {
                            List<SmartSearchModel.SmartSearchData> smartSearchDataList = smartSearchModel.getSmartSearchDataList();
                            if (smartSearchDataList != null && smartSearchDataList.size() > 0) {
                                searchAdapter.addCustomerList(smartSearchDataList);
                                iv_logo.setVisibility(View.GONE);
                            } else {
                                iv_logo.setVisibility(View.VISIBLE);
                                Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            iv_logo.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SmartSearchModel> call, Throwable t) {
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(View.GONE);
            }
        });
    }


    private void specialCaseSearch(String pagecount, String gender, String fAge, String toAge, String txtPhoto, String special, String religion, String loginmatriid) {
        progress_bar.setVisibility(View.VISIBLE);
        apiInterface.specialSearch(pagecount, gender, fAge, toAge, txtPhoto, special, religion, loginmatriid, "").enqueue(new Callback<SmartSearchModel>() {
            @Override
            public void onResponse(Call<SmartSearchModel> call, Response<SmartSearchModel> response) {
                if (response.isSuccessful()) {
                    SmartSearchModel smartSearchModel = response.body();
                    if (smartSearchModel != null) {
                        progress_bar.setVisibility(View.GONE);
                        String response1 = smartSearchModel.getResponse();
                        if (response1.equals("true")) {
                            List<SmartSearchModel.SmartSearchData> smartSearchDataList = smartSearchModel.getSmartSearchDataList();
                            if (smartSearchDataList != null && smartSearchDataList.size() > 0) {
                                searchAdapter.addCustomerList(smartSearchDataList);
                                iv_logo.setVisibility(View.GONE);
                            } else {
                                iv_logo.setVisibility(View.VISIBLE);
                                Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            iv_logo.setVisibility(View.VISIBLE);

                            Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_LONG).show();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<SmartSearchModel> call, Throwable t) {
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(View.GONE);
            }
        });
    }


    private void locationSearch(int pagecount, String gender, String fAge, String toAge, String txtPhoto, String country, String satte, String city, String loginmatriid, String iam) {
        progress_bar.setVisibility(View.VISIBLE);
        apiInterface.locationSearch(pagecount + "", gender, "", "", "", country, satte, city, loginmatriid, iam, "").enqueue(new Callback<SmartSearchModel>() {
            @Override
            public void onResponse(Call<SmartSearchModel> call, Response<SmartSearchModel> response) {
                if (response.isSuccessful()) {
                    SmartSearchModel smartSearchModel = response.body();
                    if (smartSearchModel != null) {
                        progress_bar.setVisibility(View.GONE);
                        String response1 = smartSearchModel.getResponse();
                        if (response1.equals("true")) {
                            /*
                             smartSearchDataList = smartSearchModel.getSmartSearchDataList();
                            if (smartSearchDataList!= null && smartSearchDataList.size() > 0) {
                                searchAdapter.addCustomerList(smartSearchDataList);
                                iv_logo.setVisibility(View.GONE);
                            }*/
                            if (smartSearchModel.getSmartSearchDataList().size() > 0) {
                                iv_logo.setVisibility(GONE);
                                smartSearchDataList.addAll(smartSearchModel.getSmartSearchDataList());
                                searchAdapter.notifyDataSetChanged();
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
                            if (offset == 0) {
                                Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_LONG).show();
                                iv_logo.setVisibility(View.VISIBLE);
                            }
                            if (offset > 0) {
                                --offset;
                            }
                            last = 0;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SmartSearchModel> call, Throwable t) {
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(View.GONE);
                if (offset > 0) {
                    --offset;
                }
                last = 0;
            }
        });
    }


    private void occupationSearch(String pagecount, String gender, String fAge, String toAge, String txtPhoto, String occupation, String loginmatriid) {
        progress_bar.setVisibility(View.VISIBLE);

        apiInterface.occupationSearch(pagecount, gender, fAge, toAge, txtPhoto, occupation, loginmatriid, "").enqueue(new Callback<SmartSearchModel>() {
            @Override
            public void onResponse(Call<SmartSearchModel> call, Response<SmartSearchModel> response) {
                if (response.isSuccessful()) {
                    SmartSearchModel smartSearchModel = response.body();
                    if (smartSearchModel != null) {
                        progress_bar.setVisibility(View.GONE);

                        String response1 = smartSearchModel.getResponse();
                        if (response1.equals("true")) {
                            List<SmartSearchModel.SmartSearchData> smartSearchDataList = smartSearchModel.getSmartSearchDataList();
                            if (smartSearchDataList != null && smartSearchDataList.size() > 0) {
                                searchAdapter.addCustomerList(smartSearchDataList);
                                iv_logo.setVisibility(View.GONE);

                            } else {
                                iv_logo.setVisibility(View.VISIBLE);
                                Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            iv_logo.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SmartSearchModel> call, Throwable t) {
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(View.GONE);
            }
        });
    }


    private void advancedSearch(int pagecount, String gender, String fAge, String toAge, String txtPhoto, String mStatus, String religion, String countrey, String state, String city, String education, String occupation, String loginmatriid, String iam) {
        progress_bar.setVisibility(View.VISIBLE);

        apiInterface.advancedSearch(pagecount + "", gender, fAge, toAge, txtPhoto, mStatus, religion, countrey, state, city, education, occupation, loginmatriid, "", iam).enqueue(new Callback<SmartSearchModel>() {
            @Override
            public void onResponse(Call<SmartSearchModel> call, Response<SmartSearchModel> response) {
                if (response.isSuccessful()) {
                    SmartSearchModel smartSearchModel = response.body();
                    if (smartSearchModel != null) {
                        progress_bar.setVisibility(View.GONE);
                        String response1 = smartSearchModel.getResponse();
                        if (response1.equals("true")) {
                            /*List<SmartSearchModel.SmartSearchData> smartSearchDataList = smartSearchModel.getSmartSearchDataList();
                            if (smartSearchDataList!= null && smartSearchDataList.size() > 0) {
                                searchAdapter.addCustomerList(smartSearchDataList);
                                iv_logo.setVisibility(View.GONE);
                            }*/
                            if (smartSearchModel.getSmartSearchDataList().size() > 0) {
                                iv_logo.setVisibility(GONE);
                                smartSearchDataList.addAll(smartSearchModel.getSmartSearchDataList());
                                searchAdapter.notifyDataSetChanged();
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
                            if (offset == 0) {
                                Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_LONG).show();
                                iv_logo.setVisibility(View.VISIBLE);
                            }
                            if (offset > 0) {
                                --offset;
                            }
                            last = 0;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SmartSearchModel> call, Throwable t) {
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(View.GONE);
            }
        });
    }


    public void sendheartlist(final String matriId, String loginmatriid, String status) {
        apiInterface.sendheartlist(matriId, loginmatriid, status).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel homeModel = response.body();
                    if (homeModel != null) {
                        boolean respnse = homeModel.isResponse();
                        if (respnse) {
                            searchAdapter.notifyDataSetChanged();
                            if (types.equals("smart")) {
                                smartSearch(SharedPrefsManager.getInstance().getString(MATRI_ID), SharedPrefsManager.getInstance().getString(SHOW_GENDER), min, max, withPhoto, withPhoto, religion, offset, "");
                            } else if (types.equals("Matrimonial")) {
                                matrimonyIdSearch(SharedPrefsManager.getInstance().getString(MATRI_ID), SharedPrefsManager.getInstance().getString(SHOW_GENDER), "0", SharedPrefsManager.getInstance().getString(MATRI_ID));
                            } else if (types.equals("edu")) {
                                educationalSearch("0", SharedPrefsManager.getInstance().getString(SHOW_GENDER), min, max, education, withPhoto, SharedPrefsManager.getInstance().getString(MATRI_ID));
                            } else if (types.equals("special")) {
                                specialCaseSearch("0", SharedPrefsManager.getInstance().getString(SHOW_GENDER), min, max, withPhoto, special, religion, SharedPrefsManager.getInstance().getString(MATRI_ID));
                            } else if (types.equals("location")) {
                                locationSearch(offset, SharedPrefsManager.getInstance().getString(SHOW_GENDER), min, max, withPhoto, country, state, city, SharedPrefsManager.getInstance().getString(MATRI_ID), iam);
                            } else if (types.equals("occupation")) {
                                occupationSearch("0", SharedPrefsManager.getInstance().getString(SHOW_GENDER), min, max, withPhoto, occupation, SharedPrefsManager.getInstance().getString(MATRI_ID));
                            } else if (types.equals("advanced")) {
                                advancedSearch(offset, Gender, min, max, withPhoto, marital_status, religion, country, state, city, education, occupation, SharedPrefsManager.getInstance().getString(MATRI_ID), iam);
                            }
                        } else {
                            //   Toast.makeText(getContext(),"UnLike",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
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
                            searchAdapter.notifyDataSetChanged();
                            if (types.equals("smart")) {
                                smartSearch(SharedPrefsManager.getInstance().getString(MATRI_ID), SharedPrefsManager.getInstance().getString(SHOW_GENDER), min, max, withPhoto, withPhoto, religion, offset, "");
                            } else if (types.equals("Matrimonial")) {
                                matrimonyIdSearch(SharedPrefsManager.getInstance().getString(MATRI_ID), SharedPrefsManager.getInstance().getString(SHOW_GENDER), "0", SharedPrefsManager.getInstance().getString(MATRI_ID));
                            } else if (types.equals("edu")) {
                                educationalSearch("0", SharedPrefsManager.getInstance().getString(SHOW_GENDER), min, max, education, withPhoto, SharedPrefsManager.getInstance().getString(MATRI_ID));
                            } else if (types.equals("special")) {
                                specialCaseSearch("0", SharedPrefsManager.getInstance().getString(SHOW_GENDER), min, max, withPhoto, special, religion, SharedPrefsManager.getInstance().getString(MATRI_ID));
                            } else if (types.equals("location")) {
                                locationSearch(offset, SharedPrefsManager.getInstance().getString(SHOW_GENDER), min, max, withPhoto, country, state, city, SharedPrefsManager.getInstance().getString(MATRI_ID), iam);
                            } else if (types.equals("occupation")) {
                                occupationSearch("0", SharedPrefsManager.getInstance().getString(SHOW_GENDER), min, max, withPhoto, occupation, SharedPrefsManager.getInstance().getString(MATRI_ID));
                            } else if (types.equals("advanced")) {
                                advancedSearch(offset, Gender, min, max, withPhoto, marital_status, religion, country, state, city, education, occupation, SharedPrefsManager.getInstance().getString(MATRI_ID), iam);
                            }
                        } else {
                            Toast.makeText(getContext(), "Already sent request", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
            }
        });

    }


}
