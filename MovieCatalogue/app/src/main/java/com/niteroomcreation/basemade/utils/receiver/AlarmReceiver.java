package com.niteroomcreation.basemade.utils.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.niteroomcreation.basemade.utils.NotificationUtils;
import com.niteroomcreation.basemade.utils.Utils;

import java.util.Calendar;

/**
 * Created by Septian Adi Wijaya on 24/02/20
 */
public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = AlarmReceiver.class.getSimpleName();

    private final static String TIME_FORMAT = "HH:mm";
    private final static int ID_REPEATING = 101;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e(TAG, "onReceive: " + intent.toString());

        int notifType = intent.getIntExtra(NotificationUtils.NOTIF_TYPE, 0);
        String notifMsg = notifType == NotificationUtils.CHANNEL_RELEASE ?
                "See latest release Movies on catalogue now . . ." : "Catalogue Movies you might missing, see now . . .";

        NotificationUtils.sendNotification(context
                , notifType
                , "Catalogue Movie"
                , notifMsg);
    }

    public boolean isAlarmSet(Context context, int type) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = type;

        return PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_NO_CREATE) != null;
    }

    public void setRepeatingAlarm(Context context
            , int type) {

//        if (Utils.isDateInvalid(time, TIME_FORMAT)) return;

        if (isAlarmSet(context, type))
            Log.e(TAG, "setRepeatingAlarm: alarm already set");
        else
            Log.e(TAG, "setRepeatingAlarm: alarm not set");

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(NotificationUtils.NOTIF_TYPE, type);

//        String[] timeArray = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, /*Integer.parseInt(timeArray[0])*/type == NotificationUtils.CHANNEL_RELEASE ? 8 : 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context
                , ID_REPEATING
                , intent
                , 0);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP
                    , calendar.getTimeInMillis()
                    , AlarmManager.INTERVAL_DAY
                    , pendingIntent);
        }

        Log.e(TAG, "setRepeatingAlarm: ");
    }

    public void cancelAlarm(Context context, int type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);

        int requestCode = type;

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context
                , requestCode
                , intent
                , 0);

        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }


        Log.e(TAG, "cancelAlarm: ");
    }
}
