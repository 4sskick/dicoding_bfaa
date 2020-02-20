package com.niteroomcreation.basemade.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
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

    public static final int CHANNEL_ID = 1;
    public static final String CHANNEL_NAME = "MADe";

    private static NotificationManager manager;

    public static void sendNotification(Context context, int notifId, String title, String message) {

        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(context, MainActivity.class);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getResources().getString(R.string.app_name))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
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
