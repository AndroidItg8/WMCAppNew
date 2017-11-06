package itg8.com.wmcapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import itg8.com.wmcapp.R;
import itg8.com.wmcapp.home.HomeActivity;

/**
 * Created by Android itg 8 on 11/1/2017.
 */

public class CustomFontTextView extends TextView {

//    public void setFontType(int fontType) {
//        this.fontType = fontType;
//    }

    private static final int ROBOTO_LIGHT = 0;
    private static final int ROBOTO_REGULAR = 1;
    private static final int ROBOTO_MEDIUM = 2;

    int fontType = 0;

    public CustomFontTextView(Context context) {
        super(context);
        setFont(context);
    }

    public CustomFontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initeDef(context, attrs);
    }

    public CustomFontTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initeDef(context, attrs);

    }


    private void initeDef(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);
        fontType = a.getInt(R.styleable.CustomFontTextView_font_value, 0);
        setFont(context);
        a.recycle();


    }

    public void setFont(Context context) {


        Typeface customFont = null;
        switch (fontType) {
            case ROBOTO_LIGHT:
                customFont = Typeface.createFromAsset(context.getAssets(), "font/Roboto-Light.ttf");

                break;
            case ROBOTO_REGULAR:
                customFont = Typeface.createFromAsset(context.getAssets(), "font/Roboto-Regular.ttf");
                break;
            case ROBOTO_MEDIUM:
                customFont = Typeface.createFromAsset(context.getAssets(), "font/Roboto-Medium.ttf");
                break;
            default:
                customFont = Typeface.createFromAsset(context.getAssets(), "font/Roboto-Regular.ttf");
                break;
        }

        setTypeface(customFont);
    }



}