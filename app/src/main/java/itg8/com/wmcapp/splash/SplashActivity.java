package itg8.com.wmcapp.splash;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.cilty.CityFragment;
import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.cilty.mvp.CityMVP;
import itg8.com.wmcapp.cilty.mvp.CityPresenterImp;
import itg8.com.wmcapp.common.BaseActivity;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.database.BaseDatabaseHelper;
import itg8.com.wmcapp.home.HomeActivity;
import itg8.com.wmcapp.home.HomeFragment;

public class SplashActivity extends BaseActivity implements CityMVP.CityView {

    private static final String TAG = SplashActivity.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private Fragment fragment;
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    private FragmentTransaction ft;
    private CityMVP.CityPresenter presenter;
    private Snackbar snackbar;
    private Dao<CityModel, Integer> mDAOCity = null;
    private List<CityModel> cityList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        presenter = new CityPresenterImp(this);
      //  presenter.onGetCity(getString(R.string.url_city));
//        presenter.onGetCity(getString(R.string.url_city));
        setSupportActionBar(toolbar);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.splash_animation);
        imgLogo.setAnimation(animation);

//        imgLogo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//                imgLogo.clearAnimation();
//                finish();
//            }
//        });

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }






    @Override
    public void onGetCityList(List<CityModel> list) {
        try {
            saveBrandToDatabase(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onFail(String message) {
        showTextSnackbar(message);


    }

    @Override
    public void onError(Object t) {
        showTextSnackbar(t.toString());


    }

    @Override
    public void showProgress() {
        //progressView.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        // progressView.setVisibility(View.GONE);


    }

    @Override
    public void onNoInternetConnect(boolean b) {
        showSnackbar(b);


    }




    private void showSnackbar(boolean isConnected) {

        int color;
        String message;
        if (!isConnected) {

            message = "Connected to Internet";
            color = Color.WHITE;
            hideSnackbar();

        } else {
            message = " Not connected to internet...Please try again";
            color = Color.RED;
        }
        snackbar = Snackbar.make(findViewById(R.id.fab), message, Snackbar.LENGTH_INDEFINITE);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        textView.setMaxLines(2);
        snackbar.show();


        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSnackbarOkClicked(view);

            }
        });
        snackbar.show();
    }

    private void onSnackbarOkClicked(View view) {
        presenter.onGetCity(getString(R.string.url_city));
    }

    public void hideSnackbar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }

    private void showTextSnackbar(String s) {
        snackbar = Snackbar
                .make(findViewById(R.id.fab), s, Snackbar.LENGTH_INDEFINITE);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setText(s);

        textView.setMaxLines(2);

        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();

            }
        });
        snackbar.show();
    }

    private void saveBrandToDatabase(List<CityModel> list) throws SQLException {
        try {

            mDAOCity = BaseDatabaseHelper.getBaseInstance().getHelper(SplashActivity.this).getmDAOCity();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (mDAOCity != null) {
            BaseDatabaseHelper.getBaseInstance().clearCityTable();


            for (CityModel model : list) {
                try {
                    int id = mDAOCity.create(model);

                    cityList = mDAOCity
                            .queryBuilder()
                            .where()
                            .eq(CityModel.FIELD_ID, model.getID())
                            .query();
                    Log.d(TAG, "CityList:" + new Gson().toJson(cityList));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }




        }


    }
}
