package itg8.com.wmcapp.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.google.gson.Gson;

import itg8.com.wmcapp.complaint.model.TempComplaintModel;

/**
 * Created by Android itg 8 on 11/14/2017.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {


    private TempComplaintModel model;

    @Override
    public void onReceive(Context context, Intent intent) {
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        Log.d("Network Available ", "Flag No 1");
        final android.net.NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isAvailable() || mobile.isAvailable()) {

            MyApplication.getInstance().uploadAllRemaining();

            Log.d("Network Available ", "Flag No 1");
            Log.d("Network Available ", "model "+new Gson().toJson(model));
        }
    }
}
