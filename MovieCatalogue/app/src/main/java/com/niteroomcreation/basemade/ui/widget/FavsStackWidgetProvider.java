package com.niteroomcreation.basemade.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.utils.service.FavsStackWidgetService;

/**
 * Created by Septian Adi Wijaya on 09/02/2020.
 * please be sure to add credential if you use people's code
 */
public class FavsStackWidgetProvider extends AppWidgetProvider {

    private static final String TAG = FavsStackWidgetProvider.class.getSimpleName();

    public static final String TOAST_ACTION = "com.example.android.stackwidget.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.example.android.stackwidget.EXTRA_ITEM";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        final String action = intent.getAction();

        if (intent.getAction() != null && intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName()
                    , R.layout.w_favs);

            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);

            ComponentName cn = new ComponentName(context, FavsStackWidgetProvider.class);

            manager.updateAppWidget(appWidgetId, remoteViews);
            manager.notifyAppWidgetViewDataChanged(manager.getAppWidgetIds(cn),
                    R.id.stackWidgetView);
        }
    }

    public static void sendRefreshBroadcast(Context context) {

        Log.e(TAG, "sendRefreshBroadcast: called");

        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, FavsStackWidgetProvider.class));
        context.sendBroadcast(intent);
    }

    static void updateAppWidget(Context context
            , AppWidgetManager appWidgetManager
            , int appWidgetIds) {

        // set intent for widget service that will create the views
        Intent serviceIntent = new Intent(context, FavsStackWidgetService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds);

        // embed extras so they don't get ignored
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

        // Instruct the widget manager to update the widget
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.w_favs);
        remoteViews.setRemoteAdapter(R.id.stackWidgetView, serviceIntent);
        remoteViews.setEmptyView(R.id.stackWidgetView, R.id.stackWidgetEmptyView);

        // update widget
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
