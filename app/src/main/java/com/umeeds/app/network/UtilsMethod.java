package com.umeeds.app.network;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.umeeds.app.R;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class UtilsMethod {
    private static Dialog dialog;
    private final Context context;

    public UtilsMethod(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.prograss_bar_dialog);
        ImageView loder = (ImageView) dialog.findViewById(R.id.loder);
        Glide.with(context).load(R.drawable.loder).into(loder);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public static Point getDisplaySize(WindowManager windowManager) {
        try {
            if (Build.VERSION.SDK_INT > 16) {
                Display display = windowManager.getDefaultDisplay();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                display.getMetrics(displayMetrics);
                return new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
            } else {
                return new Point(0, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Point(0, 0);
        }
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

/*
    public static void switchFragmentWithAnimation(int id, Fragment fragment,
                                                   FragmentActivity activity, boolean addToBackStack, AnimationType transitionStyle) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        if (transitionStyle != null) {
            switch (transitionStyle) {
                case SLIDE_DOWN:

                    // Exit from down
                    fragmentTransaction.setCustomAnimations(R.anim.slide_up,
                            R.anim.slide_down);

                    break;

                case SLIDE_UP:

                    // Enter from Up
                    fragmentTransaction.setCustomAnimations(R.anim.slide_in_up,
                            R.anim.slide_out_up);

                    break;

                case SLIDE_LEFT:

                    // Enter from left
                    fragmentTransaction.setCustomAnimations(R.anim.slide_left,
                            R.anim.slide_out_left);

                    break;

                // Enter from right
                case SLIDE_RIGHT:
                    fragmentTransaction.setCustomAnimations(R.anim.slide_right,
                            R.anim.slide_out_right);

                    break;

                case FADE_IN:
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in,
                            R.anim.fade_out);

                case FADE_OUT:
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in,
                            R.anim.donot_move);

                    break;

                case SLIDE_IN_SLIDE_OUT:

                    fragmentTransaction.setCustomAnimations(R.anim.slide_left,
                            R.anim.slide_out_left);

                    break;

                default:
                    break;
            }
        }

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(id, fragment).commit();


    }
*/

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String decripter(String str) throws UnsupportedEncodingException {
        byte[] data = Base64.decode(str, Base64.DEFAULT);
        String text = new String(data, StandardCharsets.UTF_8);
        return text;
    }


    @SuppressLint("NewApi")
    public static void showSnack(View view, String text, int duration) {
        Snackbar customBar = Snackbar.make(view, text, duration);


        View sbView = customBar.getView();
//Changing background to White
        sbView.setBackgroundColor(Color.parseColor("#c70e33"));

        TextView snackText = (TextView) sbView.findViewById(R.id.snackbar_text);
        if (snackText != null) {
            //Changing text color to Black
            snackText.setTextColor(Color.WHITE);
        }

        TextView actionText = (TextView) sbView.findViewById(R.id.snackbar_action);
//        if (actionText != null) {
//            // Setting custom Undo icon
//            actionText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.user, 0, 0, 0);
//        }
        customBar.show();

    }

    public static String convertTime(String time) {
        //Date/time pattern of input date
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Date/time pattern of desired output date
        DateFormat outputformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
        Date date = null;
        String output = null;
        try {
            //Conversion of input String to date
            date = df.parse(time);
            //old date format to new date format
            output = outputformat.format(date);
            System.out.println(output);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return output;
    }

    public static String getTimeString(Date fromdate) {

        long then;
        then = fromdate.getTime();
        Date date = new Date(then);

        StringBuffer dateStr = new StringBuffer();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar now = Calendar.getInstance();

        int days = daysBetween(calendar.getTime(), now.getTime());
        int minutes = hoursBetween(calendar.getTime(), now.getTime());
        int hours = minutes / 60;
        if (days == 0) {

            int second = minuteBetween(calendar.getTime(), now.getTime());
            if (minutes > 60) {

                if (hours >= 1 && hours <= 24) {
                    dateStr.append(hours).append(" Hour ago");
                }

            } else {

                if (second <= 10) {
                    dateStr.append("Now");
                } else if (second > 10 && second <= 30) {
                    dateStr.append("Few seconds ago");
                } else if (second > 30 && second <= 60) {
                    dateStr.append(second).append(" Sec ago");
                } else if (second >= 60 && minutes <= 60) {
                    dateStr.append(minutes).append(" Min ago");
                }
            }
        } else if (hours > 24 && days <= 7) {
            dateStr.append(days).append(" Day ago");
        } else {

            SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy,HH:mm");
            dateStr.append(simpleDate.format(date));
        }

        return dateStr.toString();
    }

    public static int minuteBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / DateUtils.SECOND_IN_MILLIS);
    }

    public static int hoursBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / DateUtils.MINUTE_IN_MILLIS);
    }

    public static int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / DateUtils.DAY_IN_MILLIS);
    }

    //Show Progress Dialog...
    public void showDialog() {
        dialog.show();
    }

    //Cancle Progress Dialog...
    public void cancleDialog() {
        dialog.dismiss();
    }

    public enum AnimationType {
        SLIDE_LEFT, SLIDE_RIGHT, SLIDE_UP, SLIDE_DOWN, FADE_IN, SLIDE_IN_SLIDE_OUT, FADE_OUT
    }
}
