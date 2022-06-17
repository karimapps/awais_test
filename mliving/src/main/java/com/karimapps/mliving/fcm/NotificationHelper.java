package com.karimapps.mliving.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.karimapps.mliving.R;




/**
 * Created by Shahzad Ahmad on 11/24/2017.
 */
//
public class NotificationHelper {
//    private static int NOTIFICATION_ID = 10;

    public static void sendNotification1(Context context, int NOTIFICATION_ID, String title, String body, PendingIntent resultIntent) {

        try {
            NotificationManager mNotificationManager =

                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            String id = "my_channel_01";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                /* Create or update. */

// The user-visible name of the channel.
                CharSequence name = context.getString(R.string.app_name);
// The user-visible description of the channel.
                String description = context.getString(R.string.app_name);
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(id, name, importance);
// Configure the notification channel.
                mChannel.setDescription(description);
                mChannel.enableLights(true);
// Sets the notification light color for notifications posted to this
// channel, if the device supports this feature.
                mChannel.setLightColor(Color.RED);
                mChannel.enableVibration(true);

//                AudioAttributes audioAttrib = new AudioAttributes.Builder()
//                        .setUsage(AudioAttributes.USAGE_ALARM)
//                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                        .build();
//                mChannel.setSound(Uri.parse("android.resource://"
//                        + context.getPackageName() + "/" + R.raw.adhan_trimmed), audioAttrib);

//                if (direct.exists()) {
//                    mChannel.setSound(Uri.parse(String.valueOf(direct)),audioAttrib);
//                } else {
//                    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                    mChannel.setSound(alarmSound,audioAttrib);
//                }
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                mNotificationManager.createNotificationChannel(mChannel);

            }


            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context);

//Create the intent that’ll fire when the user taps the notification//
            String CHANNEL_ID = "my_channel_01";


            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
            mBuilder.setContentIntent(resultIntent);
            mBuilder.setAutoCancel(true);
            mBuilder.setLargeIcon(bm);
            mBuilder.setSmallIcon(R.drawable.logo);
            mBuilder.setContentTitle(title);
            mBuilder.setContentText(body);
            mBuilder.setAutoCancel(true);
            mBuilder.setSound(notificationSoundURI);

            mBuilder.setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mBuilder.setChannelId(id);


            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
