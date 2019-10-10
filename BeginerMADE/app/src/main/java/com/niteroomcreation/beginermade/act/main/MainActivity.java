package com.niteroomcreation.beginermade.act.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.niteroomcreation.beginermade.R;
import com.niteroomcreation.beginermade.base.BaseView;
import com.niteroomcreation.beginermade.fragment.about.AboutFragment;
import com.niteroomcreation.beginermade.fragment.detail.DetailFragment;
import com.niteroomcreation.beginermade.fragment.main.MainFragment;
import com.niteroomcreation.beginermade.model.TokohModel;

import butterknife.BindView;

public class MainActivity extends BaseView implements MainContract.View, MainFragment.MainFragmentListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.fl_main_content)
    FrameLayout flMainContent;

    private MainPresenter presenter;

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
        presenter = new MainPresenter(this);

        moveToFragment(flMainContent.getId(), MainFragment.newInstance(), MainFragment.class.getSimpleName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                AboutFragment fragment = AboutFragment.newInstance();
                fragment.setDismissible(true);
                fragment.show(getBaseFragmentManager(), AboutFragment.class.getSimpleName());
                break;

            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemSelectedDetail(TokohModel item) {
        moveToFragment(flMainContent.getId(), DetailFragment.newInstance(item), DetailFragment.class.getSimpleName());
    }

//    public void showBackButton() {
//        Fragment fragment = getSupportFragmentManager().findFragmentById(flMainContent.getId());
//        if (fragment instanceof DetailFragment) {
//
//            Log.e(TAG, "showBackButton: ");
//
//            if (getSupportActionBar() != null)
//                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            else
//                Log.e(TAG, "showBackButton: else detailFragment");
//
//        } else {
//
//            Log.e(TAG, "showBackButton: else");
//
//            if (getSupportActionBar() != null)
//                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//            else
//                Log.e(TAG, "showBackButton: else mainFragment");
//        }
//    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(flMainContent.getId());
        if (!(fragment instanceof MainFragment))
            moveToFragment(flMainContent.getId(), MainFragment.newInstance(), MainFragment.class.getSimpleName());
        else
            super.onBackPressed();
    }
}
