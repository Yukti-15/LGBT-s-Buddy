package com.umeeds.app.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.umeeds.app.R;
import com.umeeds.app.activity.AboutUsActivity;
import com.umeeds.app.activity.ChangePasswordActivity;
import com.umeeds.app.activity.ContactUsActivity;
import com.umeeds.app.activity.DeleteAccountActivity;
import com.umeeds.app.activity.LoginActivity;
import com.umeeds.app.activity.MemberShipPlanActivity;
import com.umeeds.app.activity.MyProfileVisitorActivity;
import com.umeeds.app.activity.ProfileActivity;
import com.umeeds.app.activity.TermsConditionActivity;
import com.umeeds.app.model.ProfileModel;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.umeeds.app.network.networking.Constant.IMAGE_LOAD_USER1;
import static com.umeeds.app.network.networking.Constant.KEY_LOGIN_STATUS;
import static com.umeeds.app.network.networking.Constant.LOGIN_ID;

public class MyProfileFragment extends Fragment {
    GoogleSignInClient mGoogleSignInClient;
    ImageView iv_back, iv_userprofile;
    Button bt_profile;
    ProgressBar progress_bar;
    TextView tv_contactUs, tv_aboutUs, tv_termsCondition, tv_Membership, tv_changePassword, tv_logout,
            tv_username, tv_professional, tv_deleteAccount, profile_visitor, visit_count;
    private ApiInterface apiInterface;

    public MyProfileFragment() {
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
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        iv_back = view.findViewById(R.id.iv_back);
        iv_userprofile = view.findViewById(R.id.iv_userprofile);
        tv_username = view.findViewById(R.id.tv_username);
        tv_professional = view.findViewById(R.id.tv_professional);
        bt_profile = view.findViewById(R.id.bt_profile);
        tv_contactUs = view.findViewById(R.id.tv_contactUs);
        tv_aboutUs = view.findViewById(R.id.tv_aboutUs);
        tv_termsCondition = view.findViewById(R.id.tv_termsCondition);
        tv_changePassword = view.findViewById(R.id.tv_changePassword);
        tv_logout = view.findViewById(R.id.tv_logout);
        tv_Membership = view.findViewById(R.id.tv_Membership);
        progress_bar = view.findViewById(R.id.progress_bar);
        tv_deleteAccount = view.findViewById(R.id.tv_deleteAccount);
        profile_visitor = view.findViewById(R.id.profile_visitor);
        visit_count = view.findViewById(R.id.visit_count);

        apiInterface = ApiClient.getInterface();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        bt_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        tv_contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ContactUsActivity.class);
                startActivity(intent);
            }
        });

        tv_aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AboutUsActivity.class);
                startActivity(intent);
            }
        });

        tv_termsCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TermsConditionActivity.class);
                startActivity(intent);
            }
        });

        tv_Membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MemberShipPlanActivity.class);
                startActivity(intent);
            }
        });

        tv_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
                startActivity(intent);

            }
        });

        tv_deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DeleteAccountActivity.class);
                startActivity(intent);
            }
        });

        profile_visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyProfileVisitorActivity.class);
                startActivity(intent);
            }
        });


        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signOut();
                SharedPrefsManager.getInstance().setBoolean(KEY_LOGIN_STATUS, false);
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();

            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        profileDetailApi(SharedPrefsManager.getInstance().getString(LOGIN_ID));

    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Toast.makeText(MainActivity.this,"Successfully signed out",Toast.LENGTH_SHORT).show();
                        // startActivity(new Intent(Main2Activity.this, MainActivity.class));
                        //  finish();
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //  unbinder.unbind();
    }

    private void profileDetailApi(String userId) {
        progress_bar.setVisibility(View.VISIBLE);

        apiInterface.profileDetail(userId).enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<ProfileModel> call, @NonNull Response<ProfileModel> response) {
                if (response.isSuccessful()) {
                    ProfileModel profileModel = response.body();
                    if (profileModel != null) {
                        progress_bar.setVisibility(View.GONE);
                        boolean respons = profileModel.isResponse();
                        //  String message = loginModel.getMessage();
                        if (respons) {
                            ProfileModel.ProfileData profileData = profileModel.getProfileData();
                            tv_username.setText(profileData.getName() + " (" + profileData.getMatriID() + ")");
                            tv_professional.setText(profileData.getOccupation());
                            visit_count.setText(profileData.getPagecount());

                            RequestOptions options = new RequestOptions()
                                    .centerCrop()
                                    .placeholder(R.drawable.no_photo)
                                    .error(R.drawable.no_photo)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .priority(Priority.HIGH).dontAnimate()
                                    .dontTransform();

                            if (profileData.getPhoto1() != null) {
                                Glide.with(Objects.requireNonNull(getContext()))
                                        .load(IMAGE_LOAD_USER1 + profileData.getPhoto1())
                                        .apply(options)
                                        .into(iv_userprofile);
                            }
                        } else {
                            if (getContext() != null) {
                                Toast.makeText(getContext(), "Data not Found", Toast.LENGTH_LONG).show();
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
                progress_bar.setVisibility(View.GONE);
            }
        });
    }

}
