package itg8.com.wmcapp.notification;

import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.Prefs;


/**
 * Created by itg_Android on 12/3/2016.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    public static final String REGISTRATION_SUCCESS = "RegistrationSuccess";
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed :" + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        if(!TextUtils.isEmpty(Prefs.getString(CommonMethod.HEADER))) {
         //   MyApplication.getInstance().getRetroController().sendFirebaseTokenToServer(getString(R.string.url_firebase_token), refreshedToken);

        }
    }
}
