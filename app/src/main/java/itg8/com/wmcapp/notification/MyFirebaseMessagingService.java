package itg8.com.wmcapp.notification;


import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;




/**
 * Created by itg_Android on 12/3/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static final int NOTIFICATION_ID = 101;
    private static final String TITLE = "title";
    private String message = "";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        //Displaying data in log
        //It is optional
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        Log.d(TAG, "Notification Message DATA: " + remoteMessage.getNotification().getBody());
        Log.d(TAG, "Notification Message: " + remoteMessage.getNotification().getTitle());


//        List<TicketModel> list = new ArrayList<>();
//        sendNotification(list, remoteMessage.getNotification().getTitle());


        //Calling method to generate notification
        //  sendNotification(remoteMessage.getData().get("image"), remoteMessage.getData().get("message"));
    }
}

//    private void sendNotification(List<TicketModel> body, String title) {
//
//        Intent intent = new Intent(this, TicketActivity.class);
//        intent.putParcelableArrayListExtra(CommonMethod.FROM_NOTIFICATION, (ArrayList<? extends Parcelable>) body);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        final Notification.Builder notificationBuilder = new Notification.Builder(this);
//        final int id = 0;
//        Bitmap placeHolder = BitmapFactory.decodeResource(getResources(), R.drawable.itech_logo);
//        notificationBuilder
//                .setSmallIcon(R.drawable.itech_logo)
//                .setContentTitle(title)
//                .setContentText("ss")
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent)
//                .setStyle(new Notification.BigPictureStyle());
//
////    }
//}

