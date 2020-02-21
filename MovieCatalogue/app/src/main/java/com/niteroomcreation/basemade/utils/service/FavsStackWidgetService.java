package com.niteroomcreation.basemade.utils.service;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.niteroomcreation.basemade.BuildConfig;
import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.data.local.LocalDatabase;
import com.niteroomcreation.basemade.data.local.entity.MovieEntity;
import com.niteroomcreation.basemade.data.local.entity.TvEntity;
import com.niteroomcreation.basemade.models.FavsObjectItem;
import com.niteroomcreation.basemade.ui.widget.FavsStackWidgetProvider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Septian Adi Wijaya on 09/02/2020.
 * please be sure to add credential if you use people's code
 */
public class FavsStackWidgetService extends RemoteViewsService {

    private static final String TAG = FavsStackWidgetService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavsRemoteFactory(this.getApplicationContext(), intent);
    }

    class FavsRemoteFactory implements RemoteViewsService.RemoteViewsFactory {

        private List<FavsObjectItem> favs = new ArrayList<>();
        private Context mContext;
        private LocalDatabase db;

        private int mAppWidgetId;

        private FavsRemoteFactory(Context mContext, Intent intent) {
            this.mContext = mContext;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID
                    , AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
//            onDataSetChanged();

            Log.e(TAG, "onCreate: called");

            db = LocalDatabase.getInstance(mContext);
        }

        @Override
        public void onDataSetChanged() {

            Log.e(TAG, "onDataSetChanged: " + favs.size() + "\n" + favs.toString());

            favs = convertObjToFavs();

        }

        private List<FavsObjectItem> convertObjToFavs() {
            List<MovieEntity> mFavs = db.movieDao().getFavMovies();
            List<TvEntity> tFavs = db.tvDao().getFavsTv();
            List<FavsObjectItem> favs = new ArrayList<>();

            for (MovieEntity m : mFavs) {
                FavsObjectItem result = new FavsObjectItem();
                result.setId(m.getId());
                result.setTypeObject(1);
                result.setTitle(m.getTitle());
                result.setOverview(m.getOverview());
                result.setOriginalTitle(m.getOriginalTitle());
                result.setPosterPath(m.getPosterPath());
                result.setBackdropPath(m.getBackdropPath());
                result.setFavorite(m.getIsFavorite());

                favs.add(result);
            }

            for (TvEntity t : tFavs) {
                FavsObjectItem result = new FavsObjectItem();
                result.setId(t.getId());
                result.setTypeObject(3);
                result.setTitle(t.getName());
                result.setOverview(t.getOverview());
                result.setOriginalTitle(t.getOriginalName());
                result.setPosterPath(t.getPosterPath());
                result.setBackdropPath(t.getBackdropPath());
                result.setFavorite(t.getIsFavorite());

                favs.add(result);
            }

            Log.e(TAG, "convertObjToFavs: size " + favs.size() + "\n" + favs.toString());

            return favs;
        }

        @Override
        public void onDestroy() {
            favs.clear();
            db = null;
        }

        @Override
        public int getCount() {
            return favs.size();
        }

        @BindView(R.id.stack_img_item_photo)
        ImageView img;

        //place to set items
        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews rvs = new RemoteViews(mContext.getPackageName(), R.layout.w_i_favs);

            if (position <= getCount()) {
                Log.e(TAG,
                        "getViewAt: position <= getCount() " + position + " count " + getCount());

                FavsObjectItem fav = favs.get(position);

                Log.e(TAG, "getViewAt: processing " + fav.toString());

                if (fav.getPosterPath() != null) {
//                    try {
//                        AppWidgetTarget glideAwt = new AppWidgetTarget(mContext
//                                , R.id.stack_img_item_photo
//                                , rvs
//                                , mAppWidgetId) {
//                            @Override
//                            public void onResourceReady(@NonNull Bitmap resource
//                                    , @Nullable Transition<? super Bitmap> transition) {
//                                super.onResourceReady(resource, transition);
//
//                            }
//                        };
//
//                        RequestOptions glideOptions = new RequestOptions()
//                                .placeholder(R.drawable.ic_placeholder)
//                                .error(R.drawable.ic_placeholder);
//
//                        Glide.with(mContext.getApplicationContext())
//                                .asBitmap()
//                                .load(BuildConfig.BASE_URL_IMG + "" + BuildConfig.BASE_PATH_IMG
//                                        + "w500/" + fav.getPosterPath())
//                                .apply(glideOptions)
//                                .into(glideAwt);
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Log.e(TAG, "getViewAt: " + e);
//                    }

                    try {
                        Bitmap b = Glide.with(mContext.getApplicationContext())
                                .asBitmap()
                                .load(BuildConfig.BASE_URL_IMG + "" + BuildConfig.BASE_PATH_IMG
                                        + "w500/" + fav.getPosterPath())
                                .submit(300, 300)
                                .get();

                        rvs.setImageViewBitmap(R.id.stack_img_item_photo, b);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "getViewAt: " + e);
                    }

                }

                if (!fav.getTitle().isEmpty()) {
                    rvs.setTextViewText(R.id.stack_txt_item_name, fav.getTitle());
                }

                //store the ID's into bundle, so the activity could use it
                Bundle b = new Bundle();
                b.putLong(FavsStackWidgetProvider.EXTRA_ITEM, fav.getId());

                Intent fillIntent = new Intent();
                fillIntent.putExtras(b);

                rvs.setOnClickFillInIntent(R.id.stack_layout_item, fillIntent);
            } else
                Log.e(TAG, "getViewAt: position > getCount() " + position + " count " + getCount());

            return rvs;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
