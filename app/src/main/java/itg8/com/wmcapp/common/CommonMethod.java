package itg8.com.wmcapp.common;

import android.content.Context;
import android.graphics.Typeface;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Android itg 8 on 11/1/2017.
 */

public class CommonMethod {

    public static final String BASE_URL = "http://192.168.1.54:8080";
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


    private static Typeface typeface;



    public static Typeface setFontRobotoLight(Context context) {
        typeface = Typeface.createFromAsset(context.getAssets(), "font/Roboto-Light.ttf");
        return typeface;
    }
    public static Typeface setFontRobotoMedium(Context context) {
        typeface = Typeface.createFromAsset(context.getAssets(), "font/Roboto-Medium.ttf");
        return typeface;
    }public static Typeface setFontRobotoRegular(Context context) {
        typeface = Typeface.createFromAsset(context.getAssets(), "font/Roboto-Regular.ttf");
        return typeface;
    }

    public static Calendar convertStringToDate(String assignDate) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.getDefault());
        Calendar date = Calendar.getInstance();
        try{
            date.setTime(formatter.parse(assignDate));

        } catch (ParseException e) {
            e.printStackTrace();
        }

//        String finalString = formatter.format(date);
        return date;
    }

    public static String getFormattedDateTime(String assigndate){
        Calendar calendar=convertStringToDate(assigndate);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm a",Locale.getDefault());
        String convertedDate = "";

        try {
            convertedDate=sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

}
