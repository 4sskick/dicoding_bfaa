package com.niteroomcreation.workmanager.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.niteroomcreation.workmanager.R;

public class NotificationUtils {

    private Context context;
    private static NotificationUtils ref;

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "channel_01";
    private static final String CHANNEL_NAME = "dicoding_channel";

    private NotificationUtils(Context context) {
        this.context = context;
    }

    public static NotificationUtils getInstance(Context context) {
        if (ref == null) {
            ref = new NotificationUtils(context);
        }

        return ref;
    }

    public void showNotification(String title, String description) {
        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context,
                CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            notification.setChannelId(CHANNEL_ID);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        if (manager != null) {
            manager.notify(NOTIFICATION_ID, notification.build());
        }
    }
}
