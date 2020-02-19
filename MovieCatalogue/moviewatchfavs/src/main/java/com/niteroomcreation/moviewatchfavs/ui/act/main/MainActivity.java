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

import com.niteroomcreation.moviewatchfavs.R;
import com.niteroomcreation.moviewatchfavs.adapter.MainAdapter;
import com.niteroomcreation.moviewatchfavs.base.BaseView;
import com.niteroomcreation.moviewatchfavs.data.local.entity.MovieEntity;
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

            if (a.size() == 0) {
                Log.e(TAG, "onLoadFinished: RESTART LOADER");
                LoaderManager.getInstance(MainActivity.this).restartLoader(1, null, this).forceLoad();
            } else {
                Log.e(TAG, "onLoadFinished: size" + a.size() + "\n" + a.toString());

                adapter.setData(a);
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            Log.e(TAG, "onLoaderReset: ");
        }
    };
}