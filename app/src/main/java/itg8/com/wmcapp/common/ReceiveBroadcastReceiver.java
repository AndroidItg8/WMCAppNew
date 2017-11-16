package itg8.com.wmcapp.common;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Android itg 8 on 11/15/2017.
 */

public class ReceiveBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        switch (getResultCode() )
        {
            case Activity.RESULT_OK:
                Toast.makeText(context, "SMS delivered", Toast.LENGTH_SHORT).show();
                break;
            case Activity.RESULT_CANCELED:
                Toast.makeText(context, "SMS not delivered", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
