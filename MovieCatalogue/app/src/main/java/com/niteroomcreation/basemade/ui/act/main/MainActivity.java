package com.niteroomcreation.basemade.ui.act.main;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityOptionsCompat;
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

import butterknife.BindView;

public class MainActivity extends BaseView implements MainContract.View,
        MovieFragment.MoviesListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String EXTRA_INT = "extra.put.state";

    @BindView(R.id.nav_view_main)
    BottomNavigationView navView;
    @BindView(R.id.fl_main_content)
    FrameLayout flMainContent;

    private int lastActiveFragmentId;
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

                            break;

                        case R.id.nav_tv_shows:
                            moveToFragment(flMainContent.getId(), TvShowFragment.newInstance()
                                    , TvShowFragment.class.getSimpleName());

                            lastActiveFragmentId = R.id.nav_tv_shows;
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
        return 0;
    }

    @Override
    protected int contentLayout() {
        return R.layout.a_main;
    }

    @Override
    protected void initComponents() {
        presenter = new MainPresenter(this, this);

        navView.setOnNavigationItemSelectedListener(mOnNavSelectedListener);
        navView.setSelectedItemId(R.id.nav_movies);
        navView.getMenu().findItem(R.id.nav_movies).setChecked(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_INT, lastActiveFragmentId);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        lastActiveFragmentId = savedInstanceState.getInt(EXTRA_INT);
//        navView.setSelectedItemId(lastActiveFragmentId);
//        navView.getMenu().findItem(lastActiveFragmentId).setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.m_main, menu);

        //make it return true to tell system menu you've created
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_setting:
                Intent i = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(i);

                Log.e(TAG, "onOptionsItemSelected: menu setting");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelectedDetail(Object item, View view) {
        Log.e(TAG, String.format("onItemSelectedDetail: %s", item.toString()));

        //asking permission to access external storage
        //don't need permission for accessing local storage
        //accept: run store image
        //reject: do nothing & re-asking permission

        if (item instanceof Movies) {

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this
                    , view.findViewById(R.id.img_item_photo), "img_item_photo");
            NavigationUtils.directToDetailScreen(this, item, options);

        } else if (item instanceof TvShows) {
            Log.e(TAG, "onItemSelectedDetail: here tv show");

            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this
                            , view.findViewById(R.id.img_item_photo), "img_item_photo");
            NavigationUtils.directToDetailScreen(this, item, options);

        } else
            throw new RuntimeException("item object doesn't found");
    }
}
