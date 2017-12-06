package itg8.com.wmcapp.common;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import java.util.Locale;

import itg8.com.wmcapp.R;
import itg8.com.wmcapp.home.HomeActivity;

/**
 * Created by Android itg 8 on 11/2/2017.
 */

public class BaseActivity  extends AppCompatActivity{

    private FragmentTransaction ft;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void callFragment(Fragment fragment) {


        Logs.d("FragmentTAG:"+fragment.getClass().getSimpleName());

        ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        ft.replace(R.id.frame_container,fragment);
        ft.addToBackStack(fragment.getClass().getSimpleName());
        ft.commit();
    }


    public void callFragmentWithoutStack(Fragment fragment) {
        ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        ft.replace(R.id.frame_container,fragment,fragment.getClass().getSimpleName());
        ft.commit();
    }

    public void setLocale(String lang,String countryCode) {
        Locale myLocale = new Locale(lang,countryCode);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, HomeActivity.class);
        startActivity(refresh);
        finish();
    }
}
