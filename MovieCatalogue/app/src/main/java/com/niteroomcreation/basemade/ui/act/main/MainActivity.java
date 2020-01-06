package com.niteroomcreation.basemade.ui.act.main;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BaseView;
import com.niteroomcreation.basemade.data.models.Movies;
import com.niteroomcreation.basemade.data.models.TvShows;
import com.niteroomcreation.basemade.ui.fragment.movie.MovieFragment;
import com.niteroomcreation.basemade.ui.fragment.tv_show.TvShowFragment;
import com.niteroomcreation.basemade.utils.NavigationUtils;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseView implements MainContract.View,
        MovieFragment.MoviesListener {

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
    }

    @Override
    public void onItemSelectedDetail(Object item, List<Pair<View, String>> view) {
        Log.e(TAG, String.format("onItemSelectedDetail: %s", item.toString()));

        //asking permission to access external storage
        //don't need permission for accessing local storage
        //accept: run store image
        //reject: do nothing & re-asking permission

        if (item instanceof Movies) {

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this
                    , view.get(0)
                    , view.get(1));
            NavigationUtils.directToDetailScreen(this, item, options);

        } else if (item instanceof TvShows) {
            Log.e(TAG, "onItemSelectedDetail: here tv show");

            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this
                            , view.get(0)
                            , view.get(1));
            NavigationUtils.directToDetailScreen(this, item, options);

        } else
            throw new RuntimeException("item object doesn't found");
    }
}
