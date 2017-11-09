package itg8.com.wmcapp.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.lang.ref.WeakReference;

import itg8.com.wmcapp.utility.Connectivity;


public class BaseWeakPresenter<T> {
    private WeakReference<T> weakReference;
    Context context;
    public String url;

    public BaseWeakPresenter(T t) {
        this.weakReference=new WeakReference<T>(t);
        if(t instanceof Context){
            this.context= (Context) t;
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean hasView(){
        return weakReference!=null && weakReference.get() != null;
    }

    public T getView(){
        return weakReference.get();
    }


    protected void detachView()
    {
        weakReference.clear();
    }

    // Check if there is any connectivity for a Wifi network
    public boolean isConnectedWifi(){
        if(context==null) {
            throw new NullPointerException("in BaseWeekPresenter context is null");
        }
        NetworkInfo info = Connectivity.getNetworkInfo(context);

        if (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI){
            return true;
        }
        return false;
    }

    // Check if there is any connectivity for a mobile network
    public boolean isConnectedMobile(){
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        if(info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE){
            return true;
        }
        return false;
    }

    // Check all connectivities whether available or not
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }

}
