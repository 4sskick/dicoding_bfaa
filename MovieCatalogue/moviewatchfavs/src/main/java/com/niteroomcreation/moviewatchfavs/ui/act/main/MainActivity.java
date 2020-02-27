package com.niteroomcreation.moviewatchfavs.ui.act.main;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.niteroomcreation.moviewatchfavs.R;
import com.niteroomcreation.moviewatchfavs.adapter.MainAdapter;
import com.niteroomcreation.moviewatchfavs.base.BaseView;
import com.niteroomcreation.moviewatchfavs.data.local.entity.MovieEntity;
import com.niteroomcreation.moviewatchfavs.ui.fragment.EmptyFragment;
import com.niteroomcreation.moviewatchfavs.ui.widget.DbContract;
import com.niteroomcreation.moviewatchfavs.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseView implements MainContract.View {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String EXTRA_STATE = "extra.put.state";

    @BindView(R.id.list_favs)
    RecyclerView list;
    @BindView(R.id.fl_empty)
    FrameLayout flEmpty;

    private MainPresenter presenter;
    private MainAdapter adapter;

    private List<MovieEntity> movies;
    private int trialLoaded = 0;

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

        showLoading();

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        LoaderManager.getInstance(this).initLoader(1, null, mLoaderCallback);
    }

    private final LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {
        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
            return new CursorLoader(MainActivity.this
                    , DbContract.URI_FAVS
                    , new String[]{MovieEntity.C_TITLE, MovieEntity.C_POSTER_PATH}
                    , null
                    , null
                    , null);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
            Log.e(TAG, "onLoadFinished: EARLY");

            List<MovieEntity> a = Utils.mapCursorToList(cursor);

//            if (a.size() == 0) {
//
//                if (trialLoaded < 8) {
//                    Log.e(TAG, "onLoadFinished: RESTART LOADER");
//                    LoaderManager.getInstance(MainActivity.this).restartLoader(1, null, this).forceLoad();
//                    trialLoaded++;
//                } else
//                    showOverrideEmptyState();
//
//            } else {
//                Log.e(TAG, "onLoadFinished: size" + a.size() + "\n" + a.toString());
//
//                adapter.setData(a);
//            }

            setData(a);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            Log.e(TAG, "onLoaderReset: ");
        }
    };

    private void setData(List<MovieEntity> data) {

        movies = data;

        if (data.size() == 0) {
            if (trialLoaded < 10) {
                Log.e(TAG, "setData: restart loader");
                LoaderManager.getInstance(MainActivity.this).restartLoader(1, null, mLoaderCallback).forceLoad();
                trialLoaded++;
            } else
                showOverrideEmptyState();
        } else {
            Log.e(TAG, "setData: load finished, size " + data.size() + "\n" + data.toString());

            adapter.setData(movies);
            flEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void showOverrideEmptyState() {

        trialLoaded = 0;
        hideLoading();

        moveToFragment(flEmpty.getId(), EmptyFragment.newInstance("Daftar film favorit kosong"
                , new EmptyFragment.EmptyListener() {
                    @Override
                    public void onEmptyClickedView() {
                        LoaderManager.getInstance(MainActivity.this).restartLoader(1, null, mLoaderCallback).forceLoad();
                    }
                }), EmptyFragment.class.getSimpleName());
    }
}