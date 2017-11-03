package itg8.com.wmcapp.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import itg8.com.wmcapp.R;

/**
 * Created by Android itg 8 on 11/2/2017.
 */

public class BaseActivity  extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void callFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(fragment.getClass().getSimpleName()).commit();
    }
}
