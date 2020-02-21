package com.niteroomcreation.basemade.utils.service;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.niteroomcreation.basemade.utils.NotificationUtils;
import com.niteroomcreation.basemade.utils.PrefsKey;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Map;

/**
 * Created by Septian Adi Wijaya on 21/02/2020.
 * please be sure to add credential if you use people's code
 */
public class FCMService extends FirebaseMessagingService {

    private static final String TAG = FCMService.class.getSimpleName();

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        Log.e(TAG, "onNewToken: ");
        Prefs.putString(PrefsKey.KEY_FCM_TOKEN, s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.e(TAG, "onMessageReceived: " + remoteMessage.toString());

        if (remoteMessage.getNotification() != null) {
            Log.e(TAG,
                    "onMessageReceived: get notif body " + remoteMessage.getNotification().getBody());

            NotificationUtils.sendNotification(this
                    , remoteMessage.getNotification().getBody().toLowerCase().contains("release") ?
                            NotificationUtils.CHANNEL_RELEASE : NotificationUtils.CHANNEL_DAILY
                    , remoteMessage.getNotification().getTitle()
                    , remoteMessage.getNotification().getBody());
        } else
            Log.e(TAG, "onMessageReceived: get notif body null");

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "onMessageReceived: payload data " + remoteMessage.getData().toString());

            handleMessage(remoteMessage.getData());
        } else
            Log.e(TAG, "onMessageReceived: payload data empty");
    }

    private void handleMessage(Map<String, String> data) {
        Log.e(TAG, "handleMessage: " + data.toString());

        NotificationUtils.sendNotification(this
                , NotificationUtils.CHANNEL_DAILY
                , "TITLE"
                , "CONTENT - RELEASE");
    }
}
