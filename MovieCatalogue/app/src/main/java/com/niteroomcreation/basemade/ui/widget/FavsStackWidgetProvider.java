package com.niteroomcreation.basemade.ui.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.ui.act.detail.DetailActivity;
import com.niteroomcreation.basemade.utils.service.FavsStackWidgetService;

/**
 * Created by Septian Adi Wijaya on 09/02/2020.
 * please be sure to add credential if you use people's code
 */
public class FavsStackWidgetProvider extends AppWidgetProvider {

    public static final String TOAST_ACTION = "com.example.android.stackwidget.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.example.android.stackwidget.EXTRA_ITEM";

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals(TOAST_ACTION)) {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
            Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }

    /**
     * Set the Adapter for out widget
     **/

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static void setRemoteAdapter(@NonNull final RemoteViews views
            , int appWidgetIds
            , Intent serviceIntent) {
        views.setRemoteAdapter(appWidgetIds, R.id.stackWidgetView, serviceIntent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; i++) {

            // set intent for widget service that will create the views
            Intent serviceIntent = new Intent(context, FavsStackWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);

            // embed extras so they don't get ignored
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            // Instruct the widget manager to update the widget
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.w_favs);
//            setRemoteAdapter(remoteViews, appWidgetIds[i], serviceIntent);
            remoteViews.setRemoteAdapter(appWidgetIds[i], R.id.stackWidgetView, serviceIntent);
            remoteViews.setEmptyView(R.id.stackWidgetEmptyView, R.layout.f_empty_transparent);

            //doesn't need pending intent on widget clicked
            //trial to directly into detail act
            Intent detailIntent = new Intent(context, FavsStackWidgetProvider.class);
            detailIntent.setAction(FavsStackWidgetProvider.TOAST_ACTION);
            detailIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            detailIntent.setData(Uri.parse(detailIntent.toUri(Intent.URI_INTENT_SCHEME)));

            //pending intent
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, detailIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.stackWidgetView, pendingIntent);

            // update widget
            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
