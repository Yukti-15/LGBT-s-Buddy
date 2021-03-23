package com.umeeds.app.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.umeeds.app.MainActivity;
import com.umeeds.app.R;
import com.umeeds.app.model.MemberProfileModel;
import com.umeeds.app.model.ProfileImageModel;
import com.umeeds.app.model.ProfileModel;
import com.umeeds.app.model.ProfileStatusModel;
import com.umeeds.app.model.UserStatusModel;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;
import com.umeeds.app.utility.FilePath;

import java.io.File;
import java.io.IOException;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.umeeds.app.network.networking.Constant.IMAGE_LOAD_USER1;
import static com.umeeds.app.network.networking.Constant.IMAGE_LOAD_USER2;
import static com.umeeds.app.network.networking.Constant.IMAGE_LOAD_USER3;
import static com.umeeds.app.network.networking.Constant.IMAGE_LOAD_USER4;
import static com.umeeds.app.network.networking.Constant.IMAGE_LOAD_USER5;
import static com.umeeds.app.network.networking.Constant.IMAGE_LOAD_USER6;
import static com.umeeds.app.network.networking.Constant.IMAGE_LOAD_USER7;
import static com.umeeds.app.network.networking.Constant.LOGIN_ID;
import static com.umeeds.app.network.networking.Constant.MATRI_ID;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    public static String profileDetailApiCall = "No";
    ImageView iv_back, iv_home, iv_image1, iv_image2, iv_image3, iv_image4, iv_image5, iv_image6,
            iv_image7, iv_attachment;
    TextView tv_attachment;
    TextView tv_account, tv_personal, tv_professional, tv_name, tv_gender, tv_hight, tv_dob, tv_age,
            tv_material, tv_motherTongue, tv_interest, tv_country, tv_city, tv_nationality, tv_contact,
            tv_email, tv_education, tv_perofessions, tv_salary, tv_bio, tv_upgrade;
    LinearLayout li_viewPlan;
    TextView hide;
    Button bt_editprofile;
    LinearLayout li_account, li_prfessional, li_personal;
    String imagePath, clickImage;
    int RESULT_LOAD_IMAGE = 100;
    int RESULT_LOAD_DOC = 200;

    ProgressBar progress_bar;
    String status = "hide";
    private ApiInterface apiInterface;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        iv_back = findViewById(R.id.iv_back);
        iv_home = findViewById(R.id.iv_home);
        bt_editprofile = findViewById(R.id.bt_editprofile);
        tv_account = findViewById(R.id.tv_account);
        tv_personal = findViewById(R.id.tv_personal);
        tv_professional = findViewById(R.id.tv_professional);
        li_account = findViewById(R.id.li_account);
        li_prfessional = findViewById(R.id.li_prfessional);
        li_personal = findViewById(R.id.li_personal);

        tv_name = findViewById(R.id.tv_name);
        tv_gender = findViewById(R.id.tv_gender);
        tv_hight = findViewById(R.id.tv_hight);
        tv_dob = findViewById(R.id.tv_dob);
        tv_age = findViewById(R.id.tv_age);
        tv_material = findViewById(R.id.tv_material);
        tv_motherTongue = findViewById(R.id.tv_motherTongue);
        tv_interest = findViewById(R.id.tv_interest);
        tv_country = findViewById(R.id.tv_country);
        tv_city = findViewById(R.id.tv_city);
        tv_nationality = findViewById(R.id.tv_nationality);
        tv_contact = findViewById(R.id.tv_contact);
        tv_email = findViewById(R.id.tv_email);
        tv_education = findViewById(R.id.tv_education);
        tv_perofessions = findViewById(R.id.tv_perofessions);
        tv_bio = findViewById(R.id.tv_bio);
        tv_salary = findViewById(R.id.tv_salary);
        iv_image1 = findViewById(R.id.iv_image1);
        iv_image2 = findViewById(R.id.iv_image2);
        iv_image3 = findViewById(R.id.iv_image3);
        iv_image4 = findViewById(R.id.iv_image4);
        iv_image5 = findViewById(R.id.iv_image5);
        iv_image6 = findViewById(R.id.iv_image6);
        iv_image7 = findViewById(R.id.iv_image7);
        progress_bar = findViewById(R.id.progress_bar);
        hide = findViewById(R.id.hide);
        tv_upgrade = findViewById(R.id.tv_upgrade);
        li_viewPlan = findViewById(R.id.li_viewPlan);

        iv_attachment = findViewById(R.id.iv_attachment);
        tv_attachment = findViewById(R.id.tv_attachment);

        apiInterface = ApiClient.getInterface();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bt_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        tv_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                li_account.setVisibility(VISIBLE);
                li_prfessional.setVisibility(GONE);
                li_personal.setVisibility(GONE);

                tv_account.setBackgroundResource(R.drawable.ractangle_colour);
                tv_account.setTextColor(getResources().getColor(R.color.white));

                tv_personal.setBackgroundResource(R.drawable.ractangle_white);
                tv_personal.setTextColor(getResources().getColor(R.color.red));
                tv_professional.setBackgroundResource(R.drawable.ractangle_white);
                tv_professional.setTextColor(getResources().getColor(R.color.red));
            }
        });

        tv_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                li_personal.setVisibility(VISIBLE);
                li_account.setVisibility(GONE);
                li_prfessional.setVisibility(GONE);

                tv_personal.setBackgroundResource(R.drawable.ractangle_colour);
                tv_personal.setTextColor(getResources().getColor(R.color.white));

                tv_account.setBackgroundResource(R.drawable.ractangle_white);
                tv_account.setTextColor(getResources().getColor(R.color.red));
                tv_professional.setBackgroundResource(R.drawable.ractangle_white);
                tv_professional.setTextColor(getResources().getColor(R.color.red));
            }
        });

        tv_professional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                li_prfessional.setVisibility(VISIBLE);
                li_account.setVisibility(GONE);
                li_personal.setVisibility(GONE);

                tv_professional.setBackgroundResource(R.drawable.ractangle_colour);
                tv_professional.setTextColor(getResources().getColor(R.color.white));

                tv_personal.setBackgroundResource(R.drawable.ractangle_white);
                tv_personal.setTextColor(getResources().getColor(R.color.red));
                tv_account.setBackgroundResource(R.drawable.ractangle_white);
                tv_account.setTextColor(getResources().getColor(R.color.red));
            }
//            9584936236
        });
        isStoragePermissionGranted();
        iv_image1.setOnClickListener(this);
        iv_image2.setOnClickListener(this);
        iv_image3.setOnClickListener(this);
        iv_image4.setOnClickListener(this);
        iv_image5.setOnClickListener(this);
        iv_image6.setOnClickListener(this);
        iv_image7.setOnClickListener(this);
        hide.setOnClickListener(this);
        tv_upgrade.setOnClickListener(this);
        li_viewPlan.setOnClickListener(this);
        iv_attachment.setOnClickListener(this);
        profileDetailApi(SharedPrefsManager.getInstance().getString(MATRI_ID));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (profileDetailApiCall.equals("Yes")) {
            profileDetailApi(SharedPrefsManager.getInstance().getString(MATRI_ID));
            profileDetailApiCall = "No";
        }
        user_status(SharedPrefsManager.getInstance().getString(LOGIN_ID));
    }

    public void requestCameraAndStorage(int request) {
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        Permissions.check(ProfileActivity.this, permissions, null, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, request);
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_image1:
                clickImage = "1";

                requestCameraAndStorage(RESULT_LOAD_IMAGE);
//
//                Intent intent1 = new Intent();
//                intent1.setType("image/*");
//                intent1.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(Intent.createChooser(intent1, "Select Picture"), RESULT_LOAD_IMAGE);
                break;

            case R.id.iv_image2:
                clickImage = "2";
                requestCameraAndStorage(RESULT_LOAD_IMAGE);
                break;

            case R.id.iv_image3:
                clickImage = "3";
                requestCameraAndStorage(RESULT_LOAD_IMAGE);
                break;

            case R.id.iv_image4:
                clickImage = "4";
                requestCameraAndStorage(RESULT_LOAD_IMAGE);
                break;

            case R.id.iv_image5:
                clickImage = "5";
                requestCameraAndStorage(RESULT_LOAD_IMAGE);
                break;

            case R.id.iv_image6:
                clickImage = "6";
                requestCameraAndStorage(RESULT_LOAD_IMAGE);
                break;

            case R.id.iv_image7:
                clickImage = "7";
                requestCameraAndStorage(RESULT_LOAD_IMAGE);
                break;

            case R.id.iv_attachment:
                clickImage = "doc";
                requestCameraAndStorage(RESULT_LOAD_DOC);
                break;

            case R.id.hide:
                if (status.equals("hide")) {
                    status = "show";
                    hide.setText("Show");
                } else {
                    status = "hide";
                    hide.setText("Hide");
                }
                profile_status_saveApi(SharedPrefsManager.getInstance().getString(MATRI_ID), status);
                break;

            case R.id.tv_upgrade:
                startActivity(new Intent(ProfileActivity.this, MemberShipPlanActivity.class));
                break;

            case R.id.li_viewPlan:
                startActivity(new Intent(ProfileActivity.this, MyPlanActivity.class));
                break;
        }
    }

    private void profileDetailApi(String userId) {
        Log.i("rhl..userId", userId);
        progress_bar.setVisibility(VISIBLE);
        apiInterface.member_profile_details(userId, userId).enqueue(new Callback<MemberProfileModel>() {
            @Override
            public void onResponse(Call<MemberProfileModel> call, Response<MemberProfileModel> response) {
                if (response.isSuccessful()) {
                    MemberProfileModel profileModel = response.body();
                    if (profileModel != null) {
                        progress_bar.setVisibility(GONE);
                        boolean respons = profileModel.isResponse();
                        if (respons) {
                            List<MemberProfileModel.MemberProfileData> profileData = profileModel.getMemberProfileDataList();
                            tv_name.setText(profileData.get(0).getName());
                            tv_gender.setText(profileData.get(0).getGender());
                            tv_hight.setText(profileData.get(0).getUserHeight());
                            tv_dob.setText(profileData.get(0).getDOB());
                            tv_age.setText(profileData.get(0).getAge());
                            tv_material.setText(profileData.get(0).getMaritalstatus());
                            tv_motherTongue.setText(profileData.get(0).getLanguage());
                            tv_interest.setText(profileData.get(0).getHobbies());
                            tv_country.setText(profileData.get(0).getCountry());
                            if (profileData.get(0).getCity_name().equals("") || profileData.get(0).getCity_name() == null) {
                                tv_city.setText("Amloh");
                            } else {
                                tv_city.setText(profileData.get(0).getCity_name());
                            }
                            tv_nationality.setText(profileData.get(0).getNationality());
                            tv_contact.setText(profileData.get(0).getMobile());
                            tv_email.setText(profileData.get(0).getConfirmEmail());
                            tv_education.setText(profileData.get(0).getEducation());
                            tv_perofessions.setText(profileData.get(0).getOccupation());
                            tv_salary.setText(profileData.get(0).getAnnualincome());
                            tv_bio.setText(profileData.get(0).getProfile());
                            try {
                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .placeholder(R.drawable.no_photo)
                                        .error(R.drawable.no_photo)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .priority(Priority.HIGH).dontAnimate()
                                        .dontTransform();

                                Glide.with(ProfileActivity.this)
                                        .load(IMAGE_LOAD_USER1 + profileData.get(0).getPhoto1())
                                        .apply(options)
                                        .into(iv_image1);

                                Glide.with(ProfileActivity.this)
                                        .load(IMAGE_LOAD_USER2 + profileData.get(0).getPhoto2())
                                        .apply(options)
                                        .into(iv_image2);

                                Glide.with(ProfileActivity.this)
                                        .load(IMAGE_LOAD_USER3 + profileData.get(0).getPhoto3())
                                        .apply(options)
                                        .into(iv_image3);

                                Glide.with(ProfileActivity.this)
                                        .load(IMAGE_LOAD_USER4 + profileData.get(0).getPhoto4())
                                        .apply(options)
                                        .into(iv_image4);

                                Glide.with(ProfileActivity.this)
                                        .load(IMAGE_LOAD_USER5 + profileData.get(0).getPhoto5())
                                        .apply(options)
                                        .into(iv_image5);

                                Glide.with(ProfileActivity.this)
                                        .load(IMAGE_LOAD_USER6 + profileData.get(0).getPhoto6())
                                        .apply(options)
                                        .into(iv_image6);

                                Glide.with(ProfileActivity.this)
                                        .load(IMAGE_LOAD_USER7 + profileData.get(0).getPhoto7())
                                        .apply(options)
                                        .into(iv_image7);
                            } catch (Exception e) {

                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MemberProfileModel> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(GONE);
            }
        });
    }

    private void profile_status_saveApi(String matriId, String status) {
        progress_bar.setVisibility(VISIBLE);
        apiInterface.profile_status_save(matriId, status).enqueue(new Callback<ProfileStatusModel>() {
            @Override
            public void onResponse(Call<ProfileStatusModel> call, Response<ProfileStatusModel> response) {
                if (response.isSuccessful()) {
                    ProfileStatusModel profileModel = response.body();
                    if (profileModel != null) {
                        progress_bar.setVisibility(GONE);
                        String respons = profileModel.getResponse();
                        if (respons.equals("true")) {
                            Toast.makeText(ProfileActivity.this, profileModel.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileStatusModel> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(GONE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            cursor.close();
            imagePath = FilePath.getPath(this, selectedImage);

//            Uri selectedImage = data.getData();
//             imagePath = FilePath.getPath(this, selectedImage);


            iv_image2.setVisibility(View.VISIBLE);

            if (clickImage.equals("1")) {
                Glide.with(this).load(selectedImage.toString()).into(iv_image1);
            } else if (clickImage.equals("2")) {
                Glide.with(this).load(selectedImage.toString()).into(iv_image2);
            } else if (clickImage.equals("3")) {
                Glide.with(this).load(selectedImage.toString()).into(iv_image3);
            } else if (clickImage.equals("4")) {
                Glide.with(this).load(selectedImage.toString()).into(iv_image4);
            } else if (clickImage.equals("5")) {
                Glide.with(this).load(selectedImage.toString()).into(iv_image5);
            } else if (clickImage.equals("6")) {
                Glide.with(this).load(selectedImage.toString()).into(iv_image6);
            } else if (clickImage.equals("7")) {
                Glide.with(this).load(selectedImage.toString()).into(iv_image7);
            }

            progress_bar.setVisibility(VISIBLE);
            updateProfileImage(clickImage, SharedPrefsManager.getInstance().getString(LOGIN_ID));

        } else if (requestCode == RESULT_LOAD_DOC && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            cursor.close();
            String filePath = FilePath.getPath(this, selectedImage);

//
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            String filePath = FilePath.getPath(this, selectedImage);
//            cursor.close();
            tv_attachment.setText(filePath);
            Log.i("rhl....@@", "callll");
            upload_document(filePath, SharedPrefsManager.getInstance().getString(MATRI_ID));
        }
    }


    private void updateProfileImage(String imageno, String user_id) {

//        MultipartBody.Part imagePart;
//        if (imagePath == null || imagePath.isEmpty()) {
//            imagePart = MultipartBody.Part.createFormData("image", "");
//        } else {
//            File file = new File(imagePath);
//            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
//        }
        MultipartBody.Part imagenoPart = MultipartBody.Part.createFormData("imageno", imageno);


        File compressedImageFile;
        try {
            compressedImageFile = new Compressor(this).compressToFile(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            compressedImageFile = new File(imagePath);
        }

        MultipartBody.Part imagePart;
        if (imagePath == null || imagePath.isEmpty()) {
            imagePart = MultipartBody.Part.createFormData("image", "");
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), compressedImageFile);
            imagePart = MultipartBody.Part.createFormData("image", compressedImageFile.getPath(), requestBody);
        }


        MultipartBody.Part user_idPart = MultipartBody.Part.createFormData("user_id", user_id);

        apiInterface.updateProfileImage(imagenoPart, user_idPart, imagePart).enqueue(new Callback<ProfileImageModel>() {
            @Override
            public void onResponse(Call<ProfileImageModel> call, Response<ProfileImageModel> response) {
                if (response.isSuccessful()) {
                    progress_bar.setVisibility(GONE);
                    ProfileImageModel profileModel = response.body();
                    if (profileModel != null) {
                        String respons = profileModel.getResponse();
                        if (respons.equals("true")) {
//                            Toast.makeText(ProfileActivity.this, "Success", Toast.LENGTH_LONG).show();
                            profileDetail(SharedPrefsManager.getInstance().getString(LOGIN_ID));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileImageModel> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Some thing is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(GONE);
            }
        });
    }

    private void upload_document(String imagePath, String matriid) {
        progress_bar.setVisibility(VISIBLE);

//        MultipartBody.Part imagePart;
//        if (imagePath == null || imagePath.isEmpty()) {
//            imagePart = MultipartBody.Part.createFormData("image", "");
//        } else {
//            File file = new File(imagePath);
//            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
//        }


        File compressedImageFile;
        try {
            compressedImageFile = new Compressor(this).compressToFile(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            compressedImageFile = new File(imagePath);
        }

        MultipartBody.Part imagePart;
        if (imagePath == null || imagePath.isEmpty()) {
            imagePart = MultipartBody.Part.createFormData("image", "");
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), compressedImageFile);
            imagePart = MultipartBody.Part.createFormData("image", compressedImageFile.getPath(), requestBody);
        }


        MultipartBody.Part matriidPart = MultipartBody.Part.createFormData("matriid", matriid);

        apiInterface.upload_document(matriidPart, imagePart).enqueue(new Callback<ProfileImageModel>() {
            @Override
            public void onResponse(Call<ProfileImageModel> call, Response<ProfileImageModel> response) {
                if (response.isSuccessful()) {
                    progress_bar.setVisibility(GONE);
                    ProfileImageModel profileModel = response.body();
                    if (profileModel != null) {
                        String respons = profileModel.getResponse();
                        if (respons.equals("true")) {
                            Toast.makeText(ProfileActivity.this, "Success", Toast.LENGTH_LONG).show();
                            profileDetail(SharedPrefsManager.getInstance().getString(LOGIN_ID));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileImageModel> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Some thing is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(GONE);
            }
        });
    }

    private void profileDetail(String userId) {
        apiInterface.profileDetail(userId).enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if (response.isSuccessful()) {
                    ProfileModel profileModel = response.body();
                    if (profileModel != null) {
                        boolean respons = profileModel.isResponse();
                        if (respons) {
                            ProfileModel.ProfileData profileData = profileModel.getProfileData();
                            String editStatus = profileData.getEdit_status();
                            if (!editStatus.equals("Yes")) {
                                afterregisContain();
                            }
                        } else {
                            Toast.makeText(ProfileActivity.this, "No Data Found", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void afterregisContain() {
        final Dialog dialog = new Dialog(ProfileActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.after_regis_contain_view);

        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        Button ok = dialog.findViewById(R.id.ok);
        ok.setOnClickListener(v -> {
            dialog.dismiss();
//            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
//            finish();
        });
        dialog.show();
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
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
                            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
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
                Toast.makeText(ProfileActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

}
