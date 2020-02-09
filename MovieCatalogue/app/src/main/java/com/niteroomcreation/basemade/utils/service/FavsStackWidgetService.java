package com.niteroomcreation.basemade.utils.service;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.niteroomcreation.basemade.data.local.LocalDatabase;
import com.niteroomcreation.basemade.models.FavsObjectItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Septian Adi Wijaya on 09/02/2020.
 * please be sure to add credential if you use people's code
 */
public class FavsStackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavsRemoteFactory(this.getApplication(), intent);
    }

    class FavsRemoteFactory implements RemoteViewsService.RemoteViewsFactory {

        private List<FavsObjectItem> favs = new ArrayList<>();

        private Context mContext;
        private int mAppWidgetId;

        public FavsRemoteFactory(Context mContext, Intent intent) {
            this.mContext = mContext;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID
                    , AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            favs = LocalDatabase.getInstance(mContext).movieDao().getFavMovies();
        }

        @Override
        public void onDestroy() {
            favs.clear();
        }

        @Override
        public int getCount() {
            return favs.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            return null;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
