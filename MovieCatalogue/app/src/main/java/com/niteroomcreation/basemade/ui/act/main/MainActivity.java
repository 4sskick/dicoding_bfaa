package com.niteroomcreation.basemade.ui.act.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BaseView;
import com.niteroomcreation.basemade.models.MoviesModel;
import com.niteroomcreation.basemade.models.TvShowModel;
import com.niteroomcreation.basemade.ui.act.detail.DetailActivity;
import com.niteroomcreation.basemade.ui.fragment.movie.MovieFragment;
import com.niteroomcreation.basemade.ui.fragment.tv_show.TvShowFragment;
import com.niteroomcreation.basemade.utils.ImageUtils;

import butterknife.BindView;

public class MainActivity extends BaseView implements MainContract.View,
        MovieFragment.MoviesListener, TvShowFragment.TvShowListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.nav_view_main)
    BottomNavigationView navView;
    @BindView(R.id.fl_main_content)
    FrameLayout flMainContent;

    private MainPresenter presenter;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.nav_movies:
                    moveToFragment(flMainContent.getId(), MovieFragment.newInstance()
                            , MovieFragment.class.getSimpleName());

                    Log.e(TAG, "onNavigationItemSelected: nav_movies");
                    break;

                case R.id.nav_tv_shows:
                    moveToFragment(flMainContent.getId(), TvShowFragment.newInstance()
                            , TvShowFragment.class.getSimpleName());

                    Log.e(TAG, "onNavigationItemSelected: nav_tv_shows");
                    break;
            }

            return false;
        }
    };

    public static void startActivity(BaseView act) {
        Intent i = new Intent(act, MainActivity.class);
        act.finish();
        act.startActivity(i);
    }

    @Override
    protected int parentLayout() {
        return 0;
    }

    @Override
    protected int contentLayout() {
        return R.layout.a_main;
    }

    @Override
    protected void initComponents() {
        presenter = new MainPresenter(this, this);
        moveToFragment(flMainContent.getId(), MovieFragment.newInstance(),
                MovieFragment.class.getSimpleName());

        navView.setOnNavigationItemSelectedListener(mOnNavSelectedListener);
    }

    @Override
    public void onItemSelectedDetail(Object item) {
        Log.e(TAG, String.format("onItemSelectedDetail: %s", item.toString()));

        //asking permission to access external storage
        //don't need permission for accessing local storage
        //accept: run store image
        //reject: do nothing & re-asking permission

        if (item instanceof MoviesModel) {
            Bitmap b =
                    ((BitmapDrawable) getResources().getDrawable(((MoviesModel) item).getPoster())).getBitmap();

            new ImageUtils(MainActivity.this)
                    .setFileName(((MoviesModel) item).getName())
                    .save(b, new ImageUtils.ImageUtilsListener() {
                        @Override
                        public void success(String fileAbsolutePath) {
                            Log.e(TAG, String.format("success: path image %s", fileAbsolutePath));

                            MoviesModel m = ((MoviesModel) item);
                            m.setPosterPath(fileAbsolutePath);

                            DetailActivity.startActivity(MainActivity.this, m);
                        }

                        @Override
                        public void failed(String errMsg) {
                            Log.e(TAG, String.format("failed: %s", errMsg));

                        }
                    });
        } else if (item instanceof TvShowModel) {
            Log.e(TAG, "onItemSelectedDetail: here tv show");
        }
    }
}
