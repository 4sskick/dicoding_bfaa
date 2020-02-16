package com.niteroomcreation.basemade.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.utils.service.FavsStackWidgetService;

/**
 * Created by Septian Adi Wijaya on 09/02/2020.
 * please be sure to add credential if you use people's code
 */
public class FavsStackWidgetProvider extends AppWidgetProvider {

    public static final String TOAST_ACTION = "com.example.android.stackwidget.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.example.android.stackwidget.EXTRA_ITEM";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (intent.getAction() != null) {
            if (intent.getAction().equals(TOAST_ACTION)) {
                AppWidgetManager manager = AppWidgetManager.getInstance(context);

                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.w_favs);

                int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
                int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
                Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();

                manager.updateAppWidget(appWidgetId, remoteViews);
            }
        }
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
            remoteViews.setRemoteAdapter(R.id.stackWidgetView, serviceIntent);
            remoteViews.setEmptyView(R.id.stackWidgetView, R.id.stackWidgetEmptyView);

            //doesn't need pending intent on widget clicked
            //trial to directly into detail act
            Intent detailIntent = new Intent(context, FavsStackWidgetProvider.class);
            detailIntent.setAction(FavsStackWidgetProvider.TOAST_ACTION);
            detailIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
//            detailIntent.setData(Uri.parse(detailIntent.toUri(Intent.URI_INTENT_SCHEME)));
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            //pending intent
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context
                    , 0
                    , detailIntent
                    , PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.stackWidgetView, pendingIntent);

            // update widget
            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


}
