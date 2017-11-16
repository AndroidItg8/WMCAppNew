package itg8.com.wmcapp.common;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.Uri;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Android itg 8 on 11/1/2017.
 */

public final class CommonMethod {

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
    public static final String FROM_COMPLAINT = "FROM_COMPLAINT";
    public static final String SENT = "SENT";
    public static final String DELIVERED = "DELIVERED";
    public static final  DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.getDefault());



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
    public static Calendar convertStringToComplaintDate(String assignDate) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
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

    public static void sendSMS(String phoneNo, String message, Context mContext) {
        SmsManager smsManager = SmsManager.getDefault();

        Logs.d("SendSMS B4");
     //   smsManager.sendTextMessage(phoneNo, null, message, null, null);
        Logs.d("SendSMS After");




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

     public  static void  shareItem(Context context, String body, String title, Uri uri)
     {
         Intent sharingIntent = new Intent(Intent.ACTION_SEND);
         sharingIntent.setType("image/*");
          if(uri != null)
            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
         sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
         sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
         sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
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
    public static void  SendSMS(final String phoneNumber, final String message, final Activity mContext)
    {
        SmsManager sms = SmsManager.getDefault();
        PendingIntent sentPI = PendingIntent.getBroadcast(mContext, 0, new Intent(CommonMethod.SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(mContext, 0, new Intent(CommonMethod.DELIVERED), 0);
        mContext.registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1)
            {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Logs.d("OnResult OK");
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Logs.d("RESULT_ERROR_GENERIC_FAILURE");

                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Logs.d("RESULT_ERROR_NO_SERVICE");

                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Logs.d("RESULT_ERROR_NULL_PDU");

                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Logs.d("RESULT_ERROR_RADIO_OFF");

                        break;
                    default:
                        break;
                }
            }
        }, new IntentFilter(CommonMethod.SENT));
        mContext.registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1)
            {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Logs.d("OnResult OK");

                        break;
                    case Activity.RESULT_CANCELED:
                        Logs.d("RESULT_CANCELED ");

                        break;
                }
            }
        }, new IntentFilter(CommonMethod.DELIVERED));
        try{
            sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(mContext, "exception", Toast.LENGTH_LONG).show();
        }
    }



}
