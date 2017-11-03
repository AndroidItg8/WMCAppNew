package itg8.com.wmcapp.common;

import android.app.Application;

/**
 * Created by swapnilmeshram on 31/10/17.
 */

public class MyApplication extends Application{

    private static final String SHARED = "MyWardhPref";
    private static MyApplication mInstance;

    public static synchronized MyApplication getInstance(){
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        mInstance.initPref();

    }

    private void initPref() {
        new Prefs.Builder()
                .setContext(this)
                .setMode(MODE_PRIVATE)
                .setPrefsName(SHARED)
                .setUseDefaultSharedPreference(false)
                .build();
    }


    public Retro getRetroController() {
        return null;
    }
}
