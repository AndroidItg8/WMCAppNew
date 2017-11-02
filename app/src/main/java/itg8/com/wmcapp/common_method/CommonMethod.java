package itg8.com.wmcapp.common_method;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import itg8.com.wmcapp.R;

/**
 * Created by Android itg 8 on 11/1/2017.
 */

public class CommonMethod {

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

}
