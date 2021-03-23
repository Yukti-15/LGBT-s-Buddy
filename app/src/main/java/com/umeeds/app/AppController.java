package com.umeeds.app;

import android.app.Application;
import android.os.Bundle;

import com.google.gson.Gson;
import com.umeeds.app.network.database.SharedPrefsManager;

public class AppController extends Application {
    //private  AllDataModel.Messages messages;
    public static Gson mGson;
    public static Bundle bundle;

    public static Gson getGson() {
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }

    public static Bundle getBundle() {
        if (bundle == null) {
            bundle = new Bundle();
        }
        return bundle;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //MultiDex.install(this);
        SharedPrefsManager.initialize(this);

    }
}
