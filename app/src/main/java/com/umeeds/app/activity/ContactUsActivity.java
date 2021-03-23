package com.umeeds.app.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.umeeds.app.MainActivity;
import com.umeeds.app.R;
import com.umeeds.app.model.ContactUsModel;
import com.umeeds.app.model.UserStatusModel;
import com.umeeds.app.network.UtilsMethod;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.umeeds.app.network.networking.Constant.LOGIN_ID;
import static com.umeeds.app.network.networking.Constant.MATRI_ID;

public class ContactUsActivity extends AppCompatActivity {

    ImageView iv_back, iv_home;
    TextView tv_title, tv_contant;
    EditText et_emailId, et_message;
    Button bt_submit, bt_call;
    LinearLayout li_mail;
    String contact = "";
    private ApiInterface apiInterface;
    private UtilsMethod progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        iv_back = findViewById(R.id.iv_back);
        iv_home = findViewById(R.id.iv_home);
        tv_title = findViewById(R.id.tv_title);
        tv_contant = findViewById(R.id.tv_contant);

        et_emailId = findViewById(R.id.et_emailId);
        et_message = findViewById(R.id.et_message);
        bt_submit = findViewById(R.id.bt_submit);
        bt_call = findViewById(R.id.bt_call);
        li_mail = findViewById(R.id.li_mail);

        apiInterface = ApiClient.getInterface();
        progress = new UtilsMethod(this);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bt_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String contact = "+91 8760887608"; // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                try {
                    PackageManager pm = ContactUsActivity.this.getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(ContactUsActivity.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
               /* Intent intent1 = new Intent(Intent.ACTION_DIAL);
                intent1.setData(Uri.parse("tel:" +contact));
                startActivity(intent1);*/
            }
        });
        li_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "umeedmatrimony@yahoo.com"));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "");
                    intent.putExtra(Intent.EXTRA_TEXT, "");
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    //TODO smth
                }
            }
        });

        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactUsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validationSuccess()) {
                    sencontactUsApi(et_emailId.getText().toString(), et_message.getText().toString(), SharedPrefsManager.getInstance().getString(MATRI_ID));

                }

            }
        });

        contactUsApi();

    }

    private void contactUsApi() {

        apiInterface.contectUs().enqueue(new Callback<ContactUsModel>() {
            @Override
            public void onResponse(Call<ContactUsModel> call, Response<ContactUsModel> response) {
                if (response.isSuccessful()) {
                    ContactUsModel contactUsModel = response.body();
                    if (contactUsModel != null) {
                        progress.cancleDialog();
                        boolean respons = contactUsModel.isResponse();
                        if (respons) {
                            ContactUsModel.ContactUsData contactUsData = contactUsModel.getContactUsData();
                            tv_title.setText(contactUsData.getTitle());

                            Spanned str = HtmlCompat.fromHtml(contactUsData.getContent(),
                                    HtmlCompat.FROM_HTML_MODE_LEGACY);

                            tv_contant.setText(str.toString());
                            contact = contactUsData.getContent();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ContactUsModel> call, Throwable t) {
                Toast.makeText(ContactUsActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress.cancleDialog();
            }
        });
    }

    private void sencontactUsApi(String emailId, String message, String name) {

        apiInterface.sencontectUs(emailId, message, name).enqueue(new Callback<ContactUsModel>() {
            @Override
            public void onResponse(Call<ContactUsModel> call, Response<ContactUsModel> response) {
                if (response.isSuccessful()) {
                    ContactUsModel contactUsModel = response.body();
                    if (contactUsModel != null) {
                        progress.cancleDialog();
                        boolean respons = contactUsModel.isResponse();
                        if (respons) {
                            et_emailId.getText().clear();
                            et_message.getText().clear();
                            Toast.makeText(ContactUsActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ContactUsModel> call, Throwable t) {
                Toast.makeText(ContactUsActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress.cancleDialog();
            }
        });
    }

    private Boolean validationSuccess() {


        if (et_emailId.getText().toString().length() == 0) {
            et_emailId.setError("Please enter email Id !");
            et_emailId.requestFocus();
            return false;
        }

        if (et_message.getText().toString().length() == 0) {
            et_message.setError("Please enter new message");
            et_message.requestFocus();
            return false;
        }

        return true;
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
                            Intent intent = new Intent(ContactUsActivity.this, LoginActivity.class);
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
                Toast.makeText(ContactUsActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

}
