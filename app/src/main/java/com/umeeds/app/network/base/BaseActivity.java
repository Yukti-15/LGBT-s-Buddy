package com.umeeds.app.network.base;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.umeeds.app.network.UtilsMethod;
import com.umeeds.app.network.basenetwork.ServerResponseListener;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BaseActivity extends AppCompatActivity implements ServerResponseListener {
    UtilsMethod utils;
    private Gson mGson;
    private Dialog dialog;
    private int sum;

    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] arr = baos.toByteArray();
        return Base64.encodeToString(arr, Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static String getOrderID() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
        Log.e("orderID", sdf.format(new Date()));
        return sdf.format(new Date());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     /*   mInternetAvailabilityChecker = InternetAvailabilityChecker.getInstance();
        mInternetAvailabilityChecker.addInternetConnectivityListener(this);*/
        dialog = new Dialog(this);
        utils = new UtilsMethod(this);
    }

    protected Gson getGson() {
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

   /* @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected) {

            if (dialog != null && dialog.isShowing())

                dialog.cancel();

        } else {

            if (dialog != null)

                showNoConnectionDilog();
        }
    }*/

    @Override
    public void serverError(String serverResponse, int paramInt, int errorCode) {
        utils.cancleDialog();
        Log.e("server", serverResponse);
    }

    @Override
    public void onFailure(Throwable t, int requestId) {
        utils.cancleDialog();
        t.getMessage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // mInternetAvailabilityChecker.removeInternetConnectivityChangeListener(this);
    }

    private void showNoConnectionDilog() {
       /* try {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.no_internet_connection);
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Button btnRetry = (Button) dialog.findViewById(R.id.btnRetry);

            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });
        } catch (Exception ex) {

            ex.printStackTrace();
        }

        dialog.show();*/
    }

    public void turn2Activity(Class<?> cls) {
        Intent i = new Intent(this, cls);
        startActivity(i);
    }

    public void turn2Activity(Class<?> cls, Bundle bundle) {
        Intent i = new Intent(this, cls);
        i.putExtras(bundle);
        startActivity(i);
    }


}
