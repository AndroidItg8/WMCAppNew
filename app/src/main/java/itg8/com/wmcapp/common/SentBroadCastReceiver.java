package itg8.com.wmcapp.common;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

/**
 * Created by Android itg 8 on 11/15/2017.
 */

public class SentBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (getResultCode())
        {
            case Activity.RESULT_OK:
                Logs.d("OnResult OK");

                break;
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                Logs.d("RESULT_ERROR_GENERIC_FAILURE");

                break;
            case SmsManager.RESULT_ERROR_NO_SERVICE:
                Logs.d("RESULT_ERROR_NO_SERVICE");

                break;
            case SmsManager.RESULT_ERROR_NULL_PDU:
                Logs.d("RESULT_ERROR_NULL_PDU");

                break;
            case SmsManager.RESULT_ERROR_RADIO_OFF:
                Logs.d("RESULT_ERROR_RADIO_OFF");

                break;
            default:
                break;
        }
    }
}
