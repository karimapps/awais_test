package com.karimapps.mliving.fcm;

/**
 * Created by Shahzad Ahmad on 11/24/2016.
 */

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MyAndroidFirebaseMsgService extends FirebaseMessagingService {
    private static final String TAG = "MyAndroidFCMService";
    String title = "None";
    String Call = "None";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Log data to Log Cat
        String data = remoteMessage.getData().get("message");
        try {
            title = remoteMessage.getData().get("title");
            Call = remoteMessage.getData().get("call");
        } catch (Exception e) {
            e.printStackTrace();
        }

//        String body=remoteMessage.getNotification().getBody();
//        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + data);
        //create notification

        if (data.contains("has invited you to play game")) {


            String[] str = data.split("=");  //now str[0] is "hello" and str[1] is "goodmorning,2,1"

            String game_key = str[0];  //hello
//            Utills.savePreferences(Utills.GAME_KEY, game_key, getApplicationContext());
            String name_player = str[1];


            String[] str2 = name_player.split(":");  //now str[0] is "hello" and str[1] is "goodmorning,2,1"

            String name = str2[0];  //hello


            Intent intent = new Intent("sms");
            intent.putExtra("name", name);
            intent.putExtra("status", "invited");
            getApplicationContext().sendBroadcast(intent);

//            String[] name_player = str[1];

//            createNotification(name_player, "invited");
        } else if (data.contains(" has won the  game")) {

            Intent intent = new Intent("sms");
            intent.putExtra("name", data);
            intent.putExtra("status", "won");
            getApplicationContext().sendBroadcast(intent);


//            createNotification(data, "won");

        } else if (data.contains("has started game")) {
//            createNotification(data, "started");
        } else if (data.contains("has rejected game")) {

            Intent intent = new Intent("sms");
            intent.putExtra("name", data);
            intent.putExtra("status", "rejected");
            getApplicationContext().sendBroadcast(intent);
//            createNotification(data, "rejected");
        } else if (data.contains("has accepted game")) {

            Intent intent = new Intent("sms");
            intent.putExtra("name", data);
            intent.putExtra("status", "accepted");
            getApplicationContext().sendBroadcast(intent);
//            createNotification(data, "accepted");
        } else if (data.contains("has expired game")) {

            Intent intent = new Intent("sms");
            intent.putExtra("name", data);
            intent.putExtra("status", "expired");
            getApplicationContext().sendBroadcast(intent);
//            createNotification(data, "expired");
        } else {
            createNotification(data, Call);

        }


    }

    private void createNotification(String messageBody, String Call) {

        final int min = 20;
        final int max = 9999999;
        Random random1 = new Random();
        int random = random1.nextInt((max - min) + 1) + min;

        Intent intent = null;
      {
//            intent = new Intent(this, DashBoard.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setAction(title);
        PendingIntent resultIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationHelper.sendNotification1(getApplicationContext(), random, title, messageBody, resultIntent);

//        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
//        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher_round)
//                .setLargeIcon(bm)
////                .setContentTitle(getResources().getString(R.string.app_name)+" New item added")
//                .setContentTitle(title)
//                .setContentText(messageBody)
//                .setAutoCancel(true)
//                .setSound(notificationSoundURI)
//                .setContentIntent(resultIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0, mNotificationBuilder.build());
    }
}
