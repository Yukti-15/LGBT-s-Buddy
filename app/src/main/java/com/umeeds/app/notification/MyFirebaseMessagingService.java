package com.umeeds.app.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.umeeds.app.R;
import com.umeeds.app.activity.ChatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private static final String CHANNEL_NAME = "FCM";
    private static final String CHANNEL_DESC = "Firebase Cloud Messaging";
    private int numMessages = 0;
    private int counter = 0;

    @Override
    public void onNewToken(String token) {
        Log.e("FCM TOKEN", token);

        //  SharedPrefsManager.setStringPreferences(this, "FCMTOKEN", token);
        //  Log.e(" refresh FCM TOKEN...",SharedPrefsManager.getStringPreferences(this, "FCMTOKEN"));
        // sending token to server here
        Log.d(TAG, "Refreshed token:.. " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);

    }

    private void sendRegistrationToServer(String token) {
/*
        if (Utility.getBooleanPreferences(this,"login")&&Utility.getStringPreferences(this, "matrixID")!=null){
            sendToken(Utility.getStringPreferences(this, "matrixID"),token);
        }
*/
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);
        Log.e("message", "" + remoteMessage);

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getTitle());
        }

        //sendNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle(),"8795");
        Map<String, String> data = remoteMessage.getData();
        Log.d("FROM", "" + data);
        Log.e("messagesss", "" + data);
        JSONObject obj = new JSONObject(data);
        Log.d("json", "" + obj);
        Log.e("messagesss", "" + obj);


        try {
            //  counter = SharedPrefsManager.getInstance().getInt("count");
            if (counter != 0) {
                counter++;
            } else {
                counter++;
            }
            // SharedPrefsManager.getInstance().setInt("count", counter);
            Log.e("total_msg", "" + counter);
            sendNotification(obj);
            Intent pushNotification = new Intent("counter");
            pushNotification.putExtra("response", counter);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(pushNotification);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void sendNotification(JSONObject data) throws JSONException {

        Intent intent = new Intent(this, ChatActivity.class);
       /* if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            intent.putExtra("uniqueId","0");
        }else{
            intent.putExtra("uniqueId",data.getString("object_id"));
            // If the app is in background, firebase itself handles the notification
        }    */
        // PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.e("title", data.getString("title"));
        Log.e("message", data.getString("body"));

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);   // To open only one activity on launch.
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
                .setContentTitle(data.getString("title"))
                .setContentText(data.getString("body"))
                //.setStyle(new NotificationCompat.BigTextStyle().bigText(data.getString("message"))) // Makes it expandable to show the message
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                .setLights(Color.RED, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setNumber(++numMessages);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.logo);
            notificationBuilder.setColor(getResources().getColor(R.color.design_default_color_primary_dark));
        } else {
            notificationBuilder.setSmallIcon(R.drawable.logo);
        }
        /*try {
            String picture = data.getString(FCM_PARAM);
            if (picture != null && !"".equals(picture)) {
                URL url = new URL(picture);
                Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                notificationBuilder.setStyle(
                        new NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(data.getString("message"))
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    getString(R.string.notification_channel_id), CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESC);
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});

            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        assert notificationManager != null;
        notificationManager.notify(0, notificationBuilder.build());

        Intent pushNotification = new Intent("pushNotification");
        // pushNotification.putExtra("uniqueId", data.getString("object_id"));
        LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
    }

/*
    public void sendToken(String id, String token) {
       */
/* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();*//*

      //  RetrofitService api = retrofit.create(RetrofitService.class);
       // ApiInterface api = retrofit.create(ApiInterface.class);
        ApiInterface api   = ApiClient.getInterface();

        Call<SaveToken> call = api.sendFcmToServer(id, token);
        call.enqueue(new Callback<SaveToken>() {
            @Override
            public void onResponse(Call<SaveToken> call, retrofit2.Response<SaveToken> response) {
                Log.e("response", response.toString());
                if (response.body() != null) {

                }
            }
            @Override
            public void onFailure(Call<SaveToken> call, Throwable t) {
                // swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
*/
}

