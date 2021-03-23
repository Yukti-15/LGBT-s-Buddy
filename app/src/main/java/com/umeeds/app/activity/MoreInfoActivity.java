package com.umeeds.app.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.umeeds.app.R;
import com.umeeds.app.model.LoginModel;
import com.umeeds.app.model.MemberProfileModel;
import com.umeeds.app.model.ReadMessageModel;
import com.umeeds.app.model.UserStatusModel;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.umeeds.app.network.networking.Constant.IMAGE_LOAD_USER1;
import static com.umeeds.app.network.networking.Constant.IMAGE_LOAD_USER2;
import static com.umeeds.app.network.networking.Constant.IMAGE_LOAD_USER3;
import static com.umeeds.app.network.networking.Constant.IMAGE_LOAD_USER4;
import static com.umeeds.app.network.networking.Constant.IMAGE_LOAD_USER5;
import static com.umeeds.app.network.networking.Constant.IMAGE_LOAD_USER6;
import static com.umeeds.app.network.networking.Constant.IMAGE_LOAD_USER7;
import static com.umeeds.app.network.networking.Constant.LOGIN_ID;
import static com.umeeds.app.network.networking.Constant.MATRI_ID;
import static com.umeeds.app.network.networking.Constant.STATUS;

public class MoreInfoActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener {

    //SliderLayout slider;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    TextView tv_personalInfo, tv_expectation, tv_location, tv_perofessions, tv_salary,
            tv_expgender, tv_professional, tv_hight, tv_bio, tv_expdec;
    LinearLayout li_prfessional, li_exepectation;
    ArrayList<String> imagelist = new ArrayList<>();
    ProgressBar progress_bar;
    Button bt_sendrequest, bt_containview;
    String matriID, status, noContact, email, mobileNo, bio, contactview, sendrequest = "";
    ImageView iv_back;
    private CirclePageIndicator circleIndicator;
    private ApiInterface apiInterface;
    private String profileStatus = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        tv_personalInfo = findViewById(R.id.tv_personalInfo);
        tv_expectation = findViewById(R.id.tv_expectation);
        li_prfessional = findViewById(R.id.li_prfessional);
        li_exepectation = findViewById(R.id.li_exepectation);
        progress_bar = findViewById(R.id.progress_bar);
        // slider = findViewById(R.id.slider);
        mPager = findViewById(R.id.mPager);
        circleIndicator = findViewById(R.id.indicator);

        tv_location = findViewById(R.id.tv_location);
        tv_perofessions = findViewById(R.id.tv_perofessions);
        tv_salary = findViewById(R.id.tv_salary);
        tv_expgender = findViewById(R.id.tv_expgender);
        tv_professional = findViewById(R.id.tv_professional);
        tv_hight = findViewById(R.id.tv_hight);
        bt_containview = findViewById(R.id.bt_containview);
        bt_sendrequest = findViewById(R.id.bt_sendrequest);
        tv_bio = findViewById(R.id.tv_bio);
        tv_expdec = findViewById(R.id.tv_expdec);
        iv_back = findViewById(R.id.iv_back);

        status = SharedPrefsManager.getInstance().getString(STATUS);
        apiInterface = ApiClient.getInterface();
        progress_bar.setVisibility(VISIBLE);


        Intent intent = getIntent();
        if (intent != null) {
            String martId = intent.getStringExtra("martId");
            // profileStatus = intent.getStringExtra("blur_status");
            member_profile_details(SharedPrefsManager.getInstance().getString(MATRI_ID), martId);
        }

        // String arrayImage[] = martList.getData().get(0).getProduct_image().split(",");
        // setSlider(arrayImage);

        tv_personalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                li_prfessional.setVisibility(VISIBLE);
                li_exepectation.setVisibility(GONE);

                tv_personalInfo.setBackgroundResource(R.drawable.ractangle_colour);
                tv_personalInfo.setTextColor(getResources().getColor(R.color.white));

                tv_expectation.setBackgroundResource(R.drawable.ractangle_white);
                tv_expectation.setTextColor(getResources().getColor(R.color.red));
            }
        });

        tv_expectation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                li_exepectation.setVisibility(VISIBLE);
                li_prfessional.setVisibility(GONE);

                tv_expectation.setBackgroundResource(R.drawable.ractangle_colour2);
                tv_expectation.setTextColor(getResources().getColor(R.color.white));

                tv_personalInfo.setBackgroundResource(R.drawable.ractangle_white2);
                tv_personalInfo.setTextColor(getResources().getColor(R.color.red));
            }
        });

        bt_sendrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_bar.setVisibility(VISIBLE);
                sendRequest(matriID, SharedPrefsManager.getInstance().getString(MATRI_ID));
            }
        });

        bt_containview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_bar.setVisibility(VISIBLE);

                checkValidity();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void contain() {
        final Dialog dialog = new Dialog(MoreInfoActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.contain_view);

        TextView tv_email = dialog.findViewById(R.id.tv_email);
        TextView tv_Mobile = dialog.findViewById(R.id.tv_Mobile);
        Button ok = dialog.findViewById(R.id.ok);

        tv_email.setText(email);
        tv_Mobile.setText(mobileNo);

        ok.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void popupcontain() {
        final Dialog dialog = new Dialog(MoreInfoActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.contain_viewpopup);

        Button ok = dialog.findViewById(R.id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void notPaidcontain() {
        final Dialog dialog = new Dialog(MoreInfoActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.contain_notpaid_popup);

        Button ok = dialog.findViewById(R.id.ok);
        Button bt_upgrade = dialog.findViewById(R.id.bt_upgrade);

        bt_upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new Intent(MoreInfoActivity.this, MemberShipPlanActivity.class));
                startActivity(intent);
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
    }

    private void member_profile_details(String loginMatriID, String usermatriID) {
        apiInterface.member_profile_details(loginMatriID, usermatriID).enqueue(new Callback<MemberProfileModel>() {
            @Override
            public void onResponse(Call<MemberProfileModel> call, Response<MemberProfileModel> response) {
                if (response.isSuccessful()) {
                    MemberProfileModel memberProfileModel = response.body();
                    if (memberProfileModel != null) {
                        progress_bar.setVisibility(GONE);
                        boolean respons = memberProfileModel.isResponse();
                        if (respons) {
                            List<MemberProfileModel.MemberProfileData> memberProfileDataList = memberProfileModel.getMemberProfileDataList();
                            ArrayList<String> SliderList = new ArrayList<>();
                            matriID = memberProfileDataList.get(0).getMatriID();
                            status = memberProfileDataList.get(0).getStatus();
                            noContact = memberProfileDataList.get(0).getNoofcontacts();
                            email = memberProfileDataList.get(0).getConfirmEmail();
                            mobileNo = memberProfileDataList.get(0).getMobile();
                            sendrequest = memberProfileDataList.get(0).getSendrequest();
                            contactview = memberProfileDataList.get(0).getContactview();

                            profileStatus = memberProfileDataList.get(0).getProfile_status();

                            String photo1 = IMAGE_LOAD_USER1 + memberProfileDataList.get(0).getPhoto1();
                            SliderList.add(photo1);
                            String photo2 = IMAGE_LOAD_USER2 + memberProfileDataList.get(0).getPhoto2();
                            SliderList.add(photo2);
                            String photo3 = IMAGE_LOAD_USER3 + memberProfileDataList.get(0).getPhoto3();
                            SliderList.add(photo3);
                            String photo4 = IMAGE_LOAD_USER4 + memberProfileDataList.get(0).getPhoto4();
                            SliderList.add(photo4);
                            String photo5 = IMAGE_LOAD_USER5 + memberProfileDataList.get(0).getPhoto5();
                            SliderList.add(photo5);
                            String photo6 = IMAGE_LOAD_USER6 + memberProfileDataList.get(0).getPhoto6();
                            SliderList.add(photo6);
                            String photo7 = IMAGE_LOAD_USER7 + memberProfileDataList.get(0).getPhoto7();
                            SliderList.add(photo7);

                            if (SliderList.size() > 0) {
                                mPager.setAdapter(new SlidingImage_Adapter(MoreInfoActivity.this, SliderList));
                                circleIndicator.setViewPager(mPager);
                                final float density = getResources().getDisplayMetrics().density;
                                circleIndicator.setRadius(5 * density);

                                NUM_PAGES = imagelist.size();
                                final Handler handler = new Handler();
                                final Runnable Update = new Runnable() {
                                    public void run() {
                                        if (currentPage == NUM_PAGES) {
                                            currentPage = 0;
                                        }
                                        mPager.setCurrentItem(currentPage++, true);
                                    }
                                };
                            }
                            tv_location.setText(memberProfileDataList.get(0).getCity_name() + "," + memberProfileDataList.get(0).getState()
                                    + "," + memberProfileDataList.get(0).getCountry());
                            tv_perofessions.setText(memberProfileDataList.get(0).getOccupation());
                            tv_salary.setText(memberProfileDataList.get(0).getAnnualincome());
                            tv_expgender.setText(memberProfileDataList.get(0).getShow_gender());
                            tv_professional.setText(memberProfileDataList.get(0).getOccupation());
                            tv_hight.setText(memberProfileDataList.get(0).getHeight());
                            tv_bio.setText(memberProfileDataList.get(0).getProfile());
                            tv_expdec.setText(memberProfileDataList.get(0).getPartnerExpectations());
                        } else {
                            Toast.makeText(MoreInfoActivity.this, "No Data Found", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MemberProfileModel> call, Throwable t) {
                Toast.makeText(MoreInfoActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(GONE);
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
                        progress_bar.setVisibility(GONE);
                        boolean respnse = homeModel.isResponse();
                        Log.i("rhl...Re", String.valueOf(respnse));
                        if (respnse) {
                            Toast.makeText(MoreInfoActivity.this, "Sent Request", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(MoreInfoActivity.this, "Already Sent Request", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(MoreInfoActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(GONE);
            }
        });

    }

    public void user_count_save(String matriId, String loginmatriid) {
        apiInterface.user_count_save(matriId, loginmatriid).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel homeModel = response.body();
                    if (homeModel != null) {
                        progress_bar.setVisibility(GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(MoreInfoActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
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
                            Intent intent = new Intent(MoreInfoActivity.this, LoginActivity.class);
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
                Toast.makeText(MoreInfoActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void checkValidity() {
        ApiClient.getInterface().checkContactVisit(SharedPrefsManager.getInstance().getString(MATRI_ID), matriID).enqueue(new Callback<ReadMessageModel>() {
            @Override
            public void onResponse(Call<ReadMessageModel> call, Response<ReadMessageModel> response) {
                if (response.isSuccessful()) {
                    ReadMessageModel readMessageModel = response.body();
                    if (readMessageModel.getResponse().equalsIgnoreCase("true")) {
                        user_count_save(matriID, SharedPrefsManager.getInstance().getString(MATRI_ID));
                        progress_bar.setVisibility(GONE);
                        contain();
                    } else {
                        if (readMessageModel.getMessage().equals("request_not_accept")) {
                            progress_bar.setVisibility(GONE);
                            popupcontain();
                        } else {
                            progress_bar.setVisibility(GONE);
                            notPaidcontain();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ReadMessageModel> call, Throwable t) {
                progress_bar.setVisibility(GONE);

            }
        });

    }

    public class SlidingImage_Adapter extends PagerAdapter {
        private final ArrayList<String> imageModelList;
        private final Context context;
        private LayoutInflater inflater;


        public SlidingImage_Adapter(Context context, ArrayList<String> imageModelList) {
            this.context = context;
            this.imageModelList = imageModelList;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return imageModelList.size();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View imageLayout = inflater.inflate(R.layout.item_view_pager, view, false);
            assert imageLayout != null;
            final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.app_logo);

            if (profileStatus.equals("show")) {
                Glide.with(context)
                        .load(imageModelList.get(position))
                        .apply(requestOptions)
                        .into(imageView);
            } else {
                Glide.with(context)
                        .load(imageModelList.get(position))
                        .apply(bitmapTransform(new BlurTransformation(35)))
                        .into(imageView);
            }
            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

    }


}
