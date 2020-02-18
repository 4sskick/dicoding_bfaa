package com.niteroomcreation.moviewatchfavs.ui.act.main;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.niteroomcreation.moviewatchfavs.R;
import com.niteroomcreation.moviewatchfavs.adapter.MainAdapter;
import com.niteroomcreation.moviewatchfavs.base.BaseView;
import com.niteroomcreation.moviewatchfavs.data.local.entity.MovieEntity;
import com.niteroomcreation.moviewatchfavs.ui.widget.DbContract;
import com.niteroomcreation.moviewatchfavs.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseView implements MainContract.View
        , LoadFavsCallback {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String EXTRA_STATE = "extra.put.state";

    @BindView(R.id.list_favs)
    RecyclerView list;

    private MainPresenter presenter;
    private MainAdapter adapter;

    @Override
    protected int parentLayout() {
        return R.layout.b_activity_actionbar_base;
    }

    @Override
    protected int contentLayout() {
        return R.layout.a_main;
    }

    @Override
    protected void initComponents(@Nullable Bundle savedInstanceState) {
        showTitleToolbar(false, null);
        presenter = new MainPresenter(this, this);
        adapter = new MainAdapter(new ArrayList<>());

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        //data observer
        handlerObserver();

        LoaderManager.getInstance(this).initLoader(1, null, mLoaderCallback);
    }

    private final LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {
        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
            return new CursorLoader(MainActivity.this
                    , DbContract.URI_FAVS
                    , new String[]{MovieEntity.C_TITLE}
                    , null
                    , null
                    , null);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
            Log.e(TAG, "onLoadFinished: EARLY");

            List<MovieEntity> a = Utils.mapCursorToList(cursor);

            if (a.size() == 0) {
                Log.e(TAG, "onLoadFinished: RESTART LOADER");
                LoaderManager.getInstance(MainActivity.this).restartLoader(1, null, this).forceLoad();
            } else
                Log.e(TAG, "onLoadFinished: size" + a.size() + "\n" + a.toString());
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            Log.e(TAG, "onLoaderReset: ");
        }
    };

    private void handlerObserver() {
        HandlerThread ht = new HandlerThread("DataObserver");
        ht.start();

        Handler handler = new Handler(ht.getLooper());

        DataObserverDb dOb = new DataObserverDb(handler, this);
        getContentResolver().registerContentObserver(DbContract.URI_FAVS
                , true
                , dOb);
    }


    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showLoading();
            }
        });
    }

    @Override
    public void postExecute(List<MovieEntity> movies) {
        hideLoading();

        Log.e(TAG, "postExecute: size " + movies.size() + "\n" + movies.toString());

        if (movies.size() > 0) {
            //change on adapter by notifying
        } else {
            showMessage("Tidak ada daftar film favorit");
        }
    }

    /**
     * custom class
     */
    private static class DataObserverDb extends ContentObserver {

        final Context mContext;

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        private DataObserverDb(Handler handler, Context mContext) {
            super(handler);
            this.mContext = mContext;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

            Log.e(TAG, "onChange: calling!");

            new LoadFavsAsync(mContext, (LoadFavsCallback) mContext).execute();
        }
    }

    private static class LoadFavsAsync extends AsyncTask<Void, Void, List<MovieEntity>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadFavsCallback> weakCallback;

        private LoadFavsAsync(Context mContext
                , LoadFavsCallback callback) {

            this.weakContext = new WeakReference<>(mContext);
            this.weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected List<MovieEntity> doInBackground(Void... voids) {
            Context mContext = weakContext.get();
            Cursor dataCursor = mContext.getContentResolver().query(DbContract.URI_FAVS
                    , null
                    , null
                    , null
                    , null);

            return Utils.mapCursorToList(dataCursor);
        }

        @Override
        protected void onPostExecute(List<MovieEntity> movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }
    }

}

/**
 * interface
 */
interface LoadFavsCallback {

    void preExecute();

    void postExecute(List<MovieEntity> notes);
}
