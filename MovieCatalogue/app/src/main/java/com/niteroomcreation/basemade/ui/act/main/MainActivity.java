package com.niteroomcreation.basemade.ui.act.main;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BaseView;
import com.niteroomcreation.basemade.data.local.entity.MovieEntity;
import com.niteroomcreation.basemade.data.local.entity.TvEntity;
import com.niteroomcreation.basemade.ui.fragment.favourite.FavFragment;
import com.niteroomcreation.basemade.ui.fragment.movie.MovieFragment;
import com.niteroomcreation.basemade.ui.fragment.tv_show.TvShowFragment;
import com.niteroomcreation.basemade.ui.widget.FavsContentProvider;
import com.niteroomcreation.basemade.utils.NavigationUtils;
import com.niteroomcreation.basemade.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseView implements MainContract.View,
        MovieFragment.MoviesListener
        , LoadFavsCallback {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String EXTRA_INT = "extra.put.state";
    private static final String EXTRA_STR = "extra.put.state.tag";

    @BindView(R.id.nav_view_main)
    BottomNavigationView navView;
    @BindView(R.id.fl_main_content)
    FrameLayout flMainContent;

    private int lastActiveFragmentId;
    private String lastActiveFragmentTag;

    private MainPresenter presenter;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    for (int i = 0; i < navView.getMenu().size(); i++) {
                        MenuItem item = navView.getMenu().getItem(i);
                        boolean isChecked = item.getItemId() == menuItem.getItemId();
                        menuItem.setChecked(isChecked);
                    }

                    switch (menuItem.getItemId()) {
                        case R.id.nav_movies:
                            moveToFragment(flMainContent.getId(), MovieFragment.newInstance()
                                    , MovieFragment.class.getSimpleName());

                            lastActiveFragmentId = R.id.nav_movies;
                            lastActiveFragmentTag = MovieFragment.class.getSimpleName();

                            break;

                        case R.id.nav_tv_shows:
                            moveToFragment(flMainContent.getId(), TvShowFragment.newInstance()
                                    , TvShowFragment.class.getSimpleName());

                            lastActiveFragmentId = R.id.nav_tv_shows;
                            lastActiveFragmentTag = TvShowFragment.class.getSimpleName();

                            break;

                        case R.id.nav_saved_fav:
                            moveToFragment(flMainContent.getId(), FavFragment.newInstance(),
                                    FavFragment.class.getSimpleName());

                            lastActiveFragmentId = R.id.nav_saved_fav;
                            lastActiveFragmentTag = FavFragment.class.getSimpleName();

                            Fragment f =
                                    getSupportFragmentManager().findFragmentByTag(FavFragment.class.getSimpleName());
                            if (f instanceof FavFragment) {
                                Log.e(TAG, "onNavigationItemSelected: favfragment");

                                ((FavFragment) f).refresh();
                            } else if (f instanceof MovieFragment)
                                Log.e(TAG, "onNavigationItemSelected: MovieFragment");
                            else if (f instanceof TvShowFragment)
                                Log.e(TAG, "onNavigationItemSelected: TvShowFragment");
                            else
                                Log.e(TAG, "onNavigationItemSelected: nothing!");

                            break;
                    }

                    return true;
                }
            };

    public static void startActivity(BaseView act) {
        Intent i = new Intent(act, MainActivity.class);
        act.finish();
        act.startActivity(i);
    }

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

        navView.setOnNavigationItemSelectedListener(mOnNavSelectedListener);

        if (savedInstanceState == null) {
            navView.setSelectedItemId(R.id.nav_movies);
            navView.getMenu().findItem(R.id.nav_movies).setChecked(true);
        }

        //data observer
        handlerObserver();

        LoaderManager.getInstance(this).initLoader(1, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @NonNull
            @Override
            public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
                return new CursorLoader(getApplicationContext()
                        , FavsContentProvider.URI_FAVS
                        , new String[]{MovieEntity.C_TITLE}
                        , null
                        , null
                        , null);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
                Log.e(TAG, "onLoadFinished: " + Utils.mapCursorToList(cursor));
            }

            @Override
            public void onLoaderReset(@NonNull Loader<Cursor> loader) {
                Log.e(TAG, "onLoaderReset: ");
            }
        });
    }

    @Override
    public void onItemSelectedDetail(Object item, List<Pair<View, String>> view) {
        Log.e(TAG, String.format("onItemSelectedDetail: %s", item.toString()));

        //asking permission to access external storage
        //don't need permission for accessing local storage
        //accept: run store image
        //reject: do nothing & re-asking permission

        if (item instanceof MovieEntity) {

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this
                    , view.get(0)
                    , view.get(1));
            NavigationUtils.directToDetailScreen(this, item, options);

        } else if (item instanceof TvEntity) {
            Log.e(TAG, "onItemSelectedDetail: here tv show");

            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this
                            , view.get(0)
                            , view.get(1));
            NavigationUtils.directToDetailScreen(this, item, options);

        } else
            throw new RuntimeException("item object doesn't found");
    }

    /**
     * custom
     */
    private void handlerObserver() {
        HandlerThread ht = new HandlerThread("DataObserver");
        ht.start();

        Handler handler = new Handler(ht.getLooper());

        DataObserverDb dOb = new DataObserverDb(handler, this);
        getContentResolver().registerContentObserver(FavsContentProvider.URI_FAVS
                , true
                , dOb);
    }

    @Override
    public void preExecute() {
        Log.e(TAG, "preExecute: calling me");
    }

    @Override
    public void postExecute(List<MovieEntity> movies) {
        Log.e(TAG, "postExecute: size " + movies.size() + "\n" + movies.toString());
    }

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
            Cursor dataCursor = mContext.getContentResolver().query(FavsContentProvider.URI_FAVS
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
