package com.umeeds.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.FacebookSdk;
import com.umeeds.app.MainActivity;
import com.umeeds.app.R;
import com.umeeds.app.network.database.SharedPrefsManager;

import static com.umeeds.app.network.networking.Constant.KEY_LOGIN_STATUS;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            FacebookSdk.sdkInitialize(getApplicationContext());

            if (SharedPrefsManager.getInstance().getBoolean(KEY_LOGIN_STATUS)) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

        }, 3000);
    }
}
