package com.karimapps.mliving.reminders;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.legacy.content.WakefulBroadcastReceiver;

import com.karimapps.mliving.Common.Constants;
import com.karimapps.mliving.Common.Utills;
import com.karimapps.mliving.MainActivity;
import com.karimapps.mliving.R;

import java.util.Calendar;

/**
 * Created by kyle on 07/09/16.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {

    private static final int HOURLY = 1, DAILY = 2, WEEKLY = 3, MONTHLY = 4, YEARLY = 5;

    private Context context2;

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra(ReminderParams.ID, -1);
        String title = intent.getStringExtra(ReminderParams.TITLE);
        String msg = intent.getStringExtra(ReminderParams.CONTENT);

        if (context == null) {
            return;
        } else {
            context2 = context;
        }

        ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver == null) {
            return;
        }

        Uri uri = ContentUris.withAppendedId(ReminderContract.All.CONTENT_URI, id);
        Cursor cursor = contentResolver.query(uri,
                null, null, null, null);

        if (cursor == null || !cursor.moveToFirst()) {
            return;
        }

        int frequency = cursor.getInt(cursor.getColumnIndex(ReminderParams.FREQUENCY));
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(ReminderParams.TIME)));
        cursor.close();

        if (frequency > 0) {
            if (frequency == HOURLY) {
                time.add(Calendar.MILLISECOND, 9000);

            } else if (frequency == 1) {
                time.add(Calendar.DATE, 1);
                /*when 1 selected*/

            } else if (frequency == 2) {
                time.add(Calendar.HOUR, 12);
                /*when 2 selected*/

            } else if (frequency == 3) {
                time.add(Calendar.HOUR, 8);
                /*when 2 selected*/

            } else if (frequency == 4) {
                time.add(Calendar.HOUR, 6);
                /*when 2 selected*/

            } else if (frequency == 5) {
                time.add(Calendar.HOUR, 5);
                /*when 2 selected*/

            } else if (frequency == 6) {
                time.add(Calendar.HOUR, 4);
                /*when 2 selected*/

            } else if (frequency == 600) {   /*these three are for health 6 month , 2 years , 03 years*/
                time.add(Calendar.MONTH, 6);
                /*when 2 selected*/

            } else if (frequency == 200) {
                time.add(Calendar.YEAR, 2);
                /*when 2 selected*/

            } else if (frequency == 300) {
                time.add(Calendar.YEAR, 3);
                /*when 2 selected*/
            } else if (frequency == 7000) {

                SleepDays(time);
            } else if (frequency == 8000) {

                FastDays(time);
            }


            ContentValues values = new ContentValues();
            values.put(ReminderContract.Alerts.TIME, time.getTimeInMillis());
            uri = ContentUris.withAppendedId(ReminderContract.Alerts.CONTENT_URI, id);
            context.getContentResolver().update(uri, values, null, null);

            Intent setAlarm = new Intent(context, AlarmService.class);
            setAlarm.putExtra(ReminderParams.ID, id);
            setAlarm.setAction(AlarmService.CREATE);
            context.startService(setAlarm);
        }

        Intent result = new Intent(context, MainActivity.class);
        result.putExtra(ReminderParams.ID, id);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(result);
        PendingIntent clicked = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        Configuration config = context2.getResources().getConfiguration();
        boolean isWatch = (config.uiMode & Configuration.UI_MODE_TYPE_MASK) == Configuration.UI_MODE_TYPE_WATCH;

//        if(isWatch){
        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
        bigStyle.setBigContentTitle(title);
        bigStyle.bigText(msg);
        Notification n = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(msg)
                .setPriority(Notification.PRIORITY_MAX)
                .setWhen(0)
                .setStyle(bigStyle)
                .setContentIntent(clicked)
                .setAutoCancel(true)
                .build();


//            n.defaults |= Notification.DEFAULT_VIBRATE;
        n.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        n.defaults |= Notification.DEFAULT_SOUND;

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, n);

        notifyToWear(title, msg);
//        }


//        TelephonyManager manager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
//        if(manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE){
//
//        }else{
//
//        }


    }


    private void notifyToWear(String title, String msg) {
        NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender()
                .setHintShowBackgroundOnly(true);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context2)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(msg)
                .extend(wearableExtender);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context2);
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void SleepDays(Calendar time) {
        String mon = Utills.getPreferences(Constants.KEY_MON, context2);
        String tues = Utills.getPreferences(Constants.KEY_TUES, context2);
        String wed = Utills.getPreferences(Constants.KEY_WED, context2);
        String thu = Utills.getPreferences(Constants.KEY_THU, context2);
        String fri = Utills.getPreferences(Constants.KEY_FRI, context2);
        String sat = Utills.getPreferences(Constants.KEY_SAT, context2);
        String sun = Utills.getPreferences(Constants.KEY_SUN, context2);

        /*tvs*/


        if (mon.equalsIgnoreCase("1")) {

            time.add(Calendar.DAY_OF_WEEK, Calendar.MONDAY);


        }


        if (tues.equalsIgnoreCase("1")) {

            time.add(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);

        }

        if (wed.equalsIgnoreCase("1")) {

            time.add(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);

        }


        if (thu.equalsIgnoreCase("1")) {

            time.add(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);

        }


        if (fri.equalsIgnoreCase("1")) {

            time.add(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

        }


        if (sat.equalsIgnoreCase("1")) {

            time.add(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);

        }

        if (sun.equalsIgnoreCase("1")) {

            time.add(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        }
    }

    private void FastDays(Calendar time) {
        String mon = Utills.getPreferences(Constants.KEY_MON2, context2);
        String tues = Utills.getPreferences(Constants.KEY_TUES2, context2);
        String wed = Utills.getPreferences(Constants.KEY_WED2, context2);
        String thu = Utills.getPreferences(Constants.KEY_THU2, context2);
        String fri = Utills.getPreferences(Constants.KEY_FRI2, context2);
        String sat = Utills.getPreferences(Constants.KEY_SAT2, context2);
        String sun = Utills.getPreferences(Constants.KEY_SUN2, context2);

        /*tvs*/


        if (mon.equalsIgnoreCase("1")) {

            time.add(Calendar.DAY_OF_WEEK, Calendar.MONDAY);


        }


        if (tues.equalsIgnoreCase("1")) {

            time.add(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);

        }

        if (wed.equalsIgnoreCase("1")) {

            time.add(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);

        }


        if (thu.equalsIgnoreCase("1")) {

            time.add(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);

        }


        if (fri.equalsIgnoreCase("1")) {

            time.add(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

        }


        if (sat.equalsIgnoreCase("1")) {

            time.add(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);

        }

        if (sun.equalsIgnoreCase("1")) {

            time.add(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        }
    }

}
