package com.niteroomcreation.basemade.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.ui.act.main.MainActivity;

/**
 * Created by Septian Adi Wijaya on 20/02/20
 */
public class NotificationUtils {

    private static final String TAG = NotificationUtils.class.getSimpleName();

    /**
     * CHANNEL id:
     * - 1 only for make sure to bring back user to the application
     * - 2 bring back user to the application by querying list movies by current date released
     */
    public static final int CHANNEL_DAILY = 1;
    public static final int CHANNEL_RELEASE = 2;
    public static final String EXTRA_DATE_RELEASE = "extra.date.release";
    public static final String NOTIF_TYPE = "extra.notif.type";

    private static final int CHANNEL_ID = 99;
    private static final int NOTIFICATION_REQUEST_CODE = 100;
    private static final String CHANNEL_NAME = "MADe";

    private static NotificationManager manager;

    public static void sendNotification(Context context
            , int notifId
            , String title
            , String message) {

        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(context, MainActivity.class);
        if (notifId == CHANNEL_RELEASE)
            intent.putExtra(EXTRA_DATE_RELEASE, Utils.getCurrentDateFormatted());
        PendingIntent pendingIntent = PendingIntent.getActivity(context
                , NOTIFICATION_REQUEST_CODE
                , intent
                , PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,
                context.getResources().getString(R.string.app_name))
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(String.valueOf(CHANNEL_ID)
                    , CHANNEL_NAME
                    , NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(String.valueOf(CHANNEL_ID));

            if (manager != null)
                manager.createNotificationChannel(channel);
        }

        Notification notification = builder.build();

        if (manager != null)
            manager.notify(notifId, notification);

    }

}
