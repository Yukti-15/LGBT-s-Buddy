package com.umeeds.app;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.fangxu.allangleexpandablebutton.ButtonData;
import com.fangxu.allangleexpandablebutton.ButtonEventListener;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.umeeds.app.activity.BookMarkedActivity;
import com.umeeds.app.activity.ChatActivity;
import com.umeeds.app.activity.LoginActivity;
import com.umeeds.app.activity.NotificationActivity;
import com.umeeds.app.fragment.MainFragment;
import com.umeeds.app.fragment.MyProfileFragment;
import com.umeeds.app.fragment.searchfragment.AdvancedSearchFragment;
import com.umeeds.app.fragment.searchfragment.EducationSearchFragment;
import com.umeeds.app.fragment.searchfragment.LocationSearchFragment;
import com.umeeds.app.fragment.searchfragment.MatrimonyIDSearchFragment;
import com.umeeds.app.fragment.searchfragment.OccupationSearchFragment;
import com.umeeds.app.fragment.searchfragment.ResultSearchFragment;
import com.umeeds.app.fragment.searchfragment.SearchSelectFragment;
import com.umeeds.app.fragment.searchfragment.SmartSearchFragment;
import com.umeeds.app.fragment.searchfragment.SpecialSearchFragment;
import com.umeeds.app.model.ProfileModel;
import com.umeeds.app.model.UserStatusModel;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;
import com.umeeds.app.network.networking.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;
import static com.umeeds.app.network.networking.Constant.LOGIN_ID;
import static com.umeeds.app.network.networking.Constant.NO_CHAT_CONTACT;
import static com.umeeds.app.network.networking.Constant.STATUS;

public class MainActivity extends AppCompatActivity {
    ImageView edit_profile, iv_back;
    TextView tv_menu;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    AppUpdateManager appUpdateManager;
    int MY_REQUEST_CODE = 200;
    private Toolbar toolbar;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = ApiClient.getInterface();

        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        fragment = new MainFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        installButton90to90();

        String afterLogIn = SharedPrefsManager.getInstance().getString(STATUS);

        if (afterLogIn != null) {
            if (afterLogIn.equals("Inactive")) {
                afterregisContain("");
            } else if (afterLogIn.equals("Banned")) {
                afterregisContain("Your profile has been rejected due to inappropriate words.For more details email : umeedlgbt@gmail.com");
            }
        }


        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(MainActivity.this);

// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // For a flexible update, use AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {
                // Request the update.
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                            AppUpdateType.IMMEDIATE,
                            // The current activity making the update request.
                            this,
                            // Include a request code to later monitor this update request.
                            MY_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                    Log.e("sdfdf", "sdfdsf");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        user_status(SharedPrefsManager.getInstance().getString(LOGIN_ID));
        profileDetailApi(SharedPrefsManager.getInstance().getString(LOGIN_ID));
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {

                            if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                try {
                                    appUpdateManager.startUpdateFlowForResult(
                                            appUpdateInfo,
                                            IMMEDIATE,
                                            this,
                                            MY_REQUEST_CODE);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Log.i("Update flow failed: ", String.valueOf(resultCode));
            }
        }
    }

    private void afterregisContain(String text) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.after_regis_contain_view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        TextView text_dialog = dialog.findViewById(R.id.text_dialog);
        if (!text.isEmpty()) {
            text_dialog.setText(text);
        }
        Button ok = dialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        dialog.show();
    }

    private void installButton90to90() {
        final AllAngleExpandableButton button = (AllAngleExpandableButton) findViewById(R.id.button_expandable_90_180);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.drawable.plus, R.drawable.user_pink, R.drawable.ic_notification_pink, R.drawable.ic_chat_pink, R.drawable.ic_search_pink, R.drawable.ic_heart_pink};
        int[] color = {R.color.red, R.color.white, R.color.white, R.color.white, R.color.white, R.color.white};
        for (int i = 0; i < 6; i++) {
            ButtonData buttonData;
            if (i == 0) {
                buttonData = ButtonData.buildIconButton(this, drawable[i], 10);
            } else {
                buttonData = ButtonData.buildIconButton(this, drawable[i], 0);
            }
            buttonData.setBackgroundColorId(this, color[i]);
            buttonDatas.add(buttonData);
        }
        button.setButtonDatas(buttonDatas);
        setListener(button);
    }

    private void setListener(AllAngleExpandableButton button) {
        button.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {
                // showToast("clicked index:" + index);
                String index1 = String.valueOf(index);
                if (index == 1) {
                    fragment = new MyProfileFragment();
                } else if (index == 2) {
                    Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                    startActivity(intent);
                } else if (index == 3) {
                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                    startActivity(intent);
                } else if (index == 4) {
                    fragment = new SearchSelectFragment();
                } else if (index == 5) {
                    Intent intent = new Intent(MainActivity.this, BookMarkedActivity.class);
                    startActivity(intent);
                } else {
                    fragment = new MainFragment();
                }

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onExpand() {
//                showToast("onExpand");
            }

            @Override
            public void onCollapse() {
//                showToast("onCollapse");
            }
        });
    }


    public void loadFragment(String fragmentString, Bundle bundle) {

        switch (fragmentString) {

            case Constant.SEARCH_TYPE_FRAGMENT:
                callFragment(new ResultSearchFragment(), Constant.SEARCH_TYPE_FRAGMENT, null, null);
                break;
            case Constant.SMART_SEARCH_FRAGMENT:
                getSupportActionBar().setTitle(" Smart Search");
                callFragment(new SmartSearchFragment(), Constant.SMART_SEARCH_FRAGMENT, null, null);
                break;
            case Constant.ADVANCED_SEARCH_FRAGMENT:
                getSupportActionBar().setTitle(" Advanced Search");
                callFragment(new AdvancedSearchFragment(), Constant.ADVANCED_SEARCH_FRAGMENT, null, null);
                break;
            case Constant.OCCUP_SEARCH_FRAGMENT:
                getSupportActionBar().setTitle("Search by Occupational");
                callFragment(new OccupationSearchFragment(), Constant.OCCUP_SEARCH_FRAGMENT, null, null);
                break;
            case Constant.EDU_SEARCH_FRAGMENT:
                getSupportActionBar().setTitle("Search by Educational");
                callFragment(new EducationSearchFragment(), Constant.EDU_SEARCH_FRAGMENT, null, null);
                break;
            case Constant.LOCATION_SEARCH_FRAGMENT:
                getSupportActionBar().setTitle("Search by Location");
                callFragment(new LocationSearchFragment(), Constant.LOCATION_SEARCH_FRAGMENT, null, null);
                break;
            case Constant.SPECIAL_SEARCH_FRAGMENT:
                getSupportActionBar().setTitle("Search by Special case");
                callFragment(new SpecialSearchFragment(), Constant.SPECIAL_SEARCH_FRAGMENT, null, null);
                break;
            case Constant.MATRIMONIAL_SEARCH_FRAGMENT:
                getSupportActionBar().setTitle("Search by Matrimonial ID");
                callFragment(new MatrimonyIDSearchFragment(), Constant.MATRIMONIAL_SEARCH_FRAGMENT, null, null);
                break;

            case Constant.SEARCH_RESULT_FRAGMENT:
                callFragment(new ResultSearchFragment(), Constant.SEARCH_TYPE_FRAGMENT, null, bundle);
                break;
        }
    }

    private void callFragment(Fragment fragment, String tag, String addBackStack, Bundle bundle) {
        if (bundle != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, tag).addToBackStack(addBackStack).commit();
            fragment.setArguments(bundle);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, tag).addToBackStack(addBackStack).commit();
        }
    }

    private void showToast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
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
                        if (status.equals("Active") || status.equals("Inactive")) {

                        } else {
                            SharedPrefsManager.getInstance().clearPrefs();
                            afterregisContain("Your profile has been rejected due to inappropriate words.More details email : umeedlgbt@gmail.com");

//                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
//                            finish();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserStatusModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void profileDetailApi(String userId) {
        apiInterface.profileDetail(userId).enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if (response.isSuccessful()) {
                    ProfileModel profileModel = response.body();
                    if (profileModel != null) {
                        boolean respons = profileModel.isResponse();
                        //  String message = loginModel.getMessage();
                        if (respons) {
                            ProfileModel.ProfileData profileData = profileModel.getProfileData();
                            String editStatus = profileData.getEdit_status();
                            String nochatContact = profileData.getChatcontact();
                            SharedPrefsManager.getInstance().setString(NO_CHAT_CONTACT, nochatContact);
                            if (editStatus.equals("Yes")) {


                            } else {
//                                afterregisContain("");
                            }

                        } else {
                            Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
            }
        });
    }
}
