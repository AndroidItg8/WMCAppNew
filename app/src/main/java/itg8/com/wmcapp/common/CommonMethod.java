package itg8.com.wmcapp.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import itg8.com.wmcapp.torisum.model.TorisumModel;

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
    public static final int COMPLAINT = 1;
    public static final int NOTICE = 2;
    public static final int TOURISM = 3;


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

    public static void directionShow(Context context, String generateDirection) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(generateDirection));

        context.startActivity(intent);
    }

    public static interface ResultListener{
        void onResultAddress(String result, LatLng mLocation, String city);
    }

    public static String checkEmpty(String name) {
        if(!TextUtils.isEmpty(name))
        {
            return name;
        }else
            return "NOT AVAILABLE";
    }

     public  static void  shareItem(Context context, String  body, String title)
     {
         Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//         sharingIntent.setType("text/plain");
         sharingIntent.setType("image/*");
         sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
         sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "http://winnipeg.ca/waterandwaste/images/garbage/garbage_cc.jpg");
         context.startActivity(Intent.createChooser(sharingIntent, "Share"));
     }
       public static int calculateTerm()
       {

           int number=12;
       int nextNumber;
          int finalNumber = 0;


           int previousNumber=0;
           while (number<1000)
           {
               if(number%2==0)
               {
                   previousNumber= number /2;
                   number= previousNumber;
                   Logs.d("number:"+number);


               }else
               {
                   nextNumber= 3*number+1;

                   number = nextNumber;
                   Logs.d("NextNumber:"+nextNumber);
               }
               finalNumber = number;
               Logs.d("finalNumber:"+finalNumber);
               number++;


           }

           return finalNumber;
       }
}
