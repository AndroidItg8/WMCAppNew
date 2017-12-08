package itg8.com.wmcapp.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import itg8.com.wmcapp.R;
import itg8.com.wmcapp.profile.ProfileModel;

/**
 * Created by Android itg 8 on 11/1/2017.
 */

public final class CommonMethod {

//    public static final String BASE_URL = "http://192.168.1.54:8080";
    public static final String BASE_URL = "http://wmc.itechgalaxyprojects.com";
    public static final String RECEIVER = "myReceiver";
    public static final String LOCATION_DATA_EXTRA = "LocationDataExtras";
    public static final int FAILURE_RESULT = 111;
    public static final int SUCCESS_RESULT = 112;
    public static final String RESULT_DATA_KEY = "resultJet";
    public static final String HEADER = "HEADER";
    public static final String IS_LOGIN = "IS_LOGIN";
    public static final String CITY = "CITY";
    public static final String LANGUAGE = "LANGUAGE";
    public static final String SELECTED_CITY = "SELECTED_CITY";
    public static final int COMPLAINT = 1;
    public static final int NOTICE = 2;
    public static final int TOURISM = 3;
    public static final int FROM_COMPLAINT = 0;
    public static final int FROM_COMPLAINT_USER = 1;
    public static final String SENT = "SENT";
    public static final String DELIVERED = "DELIVERED";
    public static final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.getDefault());
    public static final String IS_LOGIN_FIRST_TIME = "IS_LOGIN_FIRST_TIME";
    public static final String FROM_FIRST_TIME_LOGIN = "FROM_FIRST_TIME_LOGIN";
    public static final int PRABHAG = 1;
    public static final int WARD = 2;
    public static final int WARD_MEMEBER = 3;
    // these two are for snackbar.
    public static final int FROM_INTERNET = 1;
    public static final int FROM_ERROR = 2;
    //COMPLAINT STATUS
    public static final int PENDING = 0;
    public static final int PROCESS = 1;
    public static final int CLOSED = 2;
    public static final int SOLVED = 3;


    public static final String USER_NAME = "USER_NAME";
    public static final String USER_MOBILE = "USER_MOBILE";
    // Successfully deleted item from server
    public static final String TYPE_NOTICE_SYNC = "NOTICE_SYNC";
    public static final String TYPE_NOTICE_UNSYNC = "NOTICE_SYNC";
    public static final String SYNC_NB_JOB = "SYNC_NB_JOB";
    // FOR NOTIFICATION OF NEWS
    public static final String SETTING_NEWS_NOTIFICATION = "SETTING_NEWS_NOTIFICATION";
    public static final String SETTING_NOTICE_NOTIFICATION = "SETTING_NOTICE_NOTIFICATION";
    public static final String SELECT_LIST_BROADCAST = "SELECT_LIST_BROADCAST";
    public static final String SELECT_LIST = "SELECT_LIST";
    public static final String FROM_TOURISM = "FROM_TOURISM";


//    1) Complaint
//2)Notice
//3)tourism
//
//1)0 or null :- open complaint;
//2)1 :- process
//3)2:- closed


    private static Typeface typeface;


    public static Typeface setFontRobotoLight(Context context) {
        typeface = Typeface.createFromAsset(context.getAssets(), "font/Roboto-Light.ttf");
        return typeface;
    }

    public static Typeface setFontRobotoMedium(Context context) {
        typeface = Typeface.createFromAsset(context.getAssets(), "font/Roboto-Medium.ttf");
        return typeface;
    }

    public static Typeface setFontRobotoRegular(Context context) {
        typeface = Typeface.createFromAsset(context.getAssets(), "font/Roboto-Regular.ttf");
        return typeface;
    }

    public static Calendar convertStringToDate(String assignDate) {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SS", Locale.getDefault());


        Calendar date = Calendar.getInstance();
        try {
            date.setTime(formatter.parse(assignDate));

        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }


//        String finalString = formatter.format(date);
        return date;
    }

    public static Calendar convertStringToComplaintDate(String assignDate) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar date = Calendar.getInstance();
        try {
            date.setTime(formatter.parse(assignDate));

        } catch (ParseException e) {
            e.printStackTrace();
        }

//        String finalString = formatter.format(date);
        return date;
    }

    public static String getFormattedDateTime(String assigndate) {
        Calendar calendar = convertStringToDate(assigndate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault());
        String convertedDate = "";

        try {
            convertedDate = sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    public static void directionShow(Context context, String generateDirection) {
        if(!TextUtils.isEmpty(generateDirection)) {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse(generateDirection));
            context.startActivity(intent);
        }
    }

    public static void sendSMS(String phoneNo, String message, Context mContext) {
        SmsManager smsManager = SmsManager.getDefault();

        Logs.d("SendSMS B4");
        //   smsManager.sendTextMessage(phoneNo, null, message, null, null);
        Logs.d("SendSMS After");


    }

    public static void showHideItem(View show, View hide) {
        show.setVisibility(View.VISIBLE);
        hide.setVisibility(View.GONE);

    }

    public static void clearText(EditText title) {
        title.setText(" ");
    }

    public static String checkEmptyProfile(String customername) {
        if (customername != null)
            return customername;
        else
            return "UNKNOWN PERSON";
    }

    public static String calculateDays(String date) {
if(TextUtils.isEmpty(date))
{
    return "NOT AVAILABLE";
}
        Calendar calOld = convertStringToDate(date);

        Calendar cal = Calendar.getInstance();
        Date firstDate = cal.getTime();
        Date secondDate = calOld.getTime();


        long diff = firstDate.getTime() - secondDate.getTime();


        System.out.println("Days: " + diff);

        long seconds = diff / 1000;
        String daysAgo;
        if (diff < 1000) {
            daysAgo = "NOT AVAILABLE";
            return daysAgo;
        }
        if (seconds < 60) {
            daysAgo = "Few Seconds Ago";
            return daysAgo;
        }
        long minute = seconds / 60;
        if (minute < 60) {

            daysAgo = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS) + " Minute Ago";
            return daysAgo;
        }
        long hr = minute / 60;
        if (hr < 24) {

            daysAgo = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS) + " Hours Ago";
            return daysAgo;
        }
        long days = hr / 24;
        if (days < 30) {

            daysAgo = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + " Days Ago";
        } else {
            days = days / 30;
            daysAgo = days + " Month Ago";
        }
        return daysAgo;

    }

    public static String checkEmpty(String name) {
        if (!TextUtils.isEmpty(name)) {
            return name;
        } else
            return "NOT AVAILABLE";
    }

    public static double dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public static void shareItem(Context context, String title,String body, Uri uri) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        if (uri != null) {
            sharingIntent.setType("image/*");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

        }else
        {
            sharingIntent.setType("text/plain");
        }
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(Intent.createChooser(sharingIntent, "Share"));


    }

    public static int calculateTerm() {

        int number = 12;
        int nextNumber;
        int finalNumber = 0;


        int previousNumber = 0;
        while (number < 1000) {
            if (number % 2 == 0) {
                previousNumber = number / 2;
                number = previousNumber;
                Logs.d("number:" + number);


            } else {
                nextNumber = 3 * number + 1;

                number = nextNumber;
                Logs.d("NextNumber:" + nextNumber);
            }
            finalNumber = number;
            Logs.d("finalNumber:" + finalNumber);
            number++;


        }

        return finalNumber;
    }

    public static void setUserPicaso(final Context mContext, final String subUrl, final ImageView imgGarbage) {
        Picasso.with(mContext)
                .load(CommonMethod.BASE_URL + subUrl)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imgGarbage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        // Try again online if cache failed
                        Picasso.with(mContext)
                                .load(CommonMethod.BASE_URL + subUrl)
                                .error(R.drawable.ic_face_black_24dp)
                                .into(imgGarbage, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        //   imgGarbage.setVisibility(View.VISIBLE);

                                    }

                                    @Override
                                    public void onError() {
                                        // imgGarbage.setVisibility(View.GONE);
                                    }
                                });
                    }
                });


    }


    public static interface ResultListener {
        void onResultAddress(String result, LatLng mLocation, String city);
    }

    public interface OnImageFileListner {
        void onGetImageFileSucces(String file);

        void onGetImageFileFailed(String s);
    }

    public interface OnBackPressListener {
        void onBackPress();
    }

    public interface onSetToolbarTitle {
        void onSetTitle(String name);
    }

    public interface ProfileSetListener {
        void onSetProfile(ProfileModel model);

        void onFailed(String s);
    }


    public interface OnMoveComplaintListener {
        void moveComplaint();
    }
}
