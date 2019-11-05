package com.niteroomcreation.basemade.ui.act.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.FrameLayout;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BaseView;
import com.niteroomcreation.basemade.models.MoviesModel;
import com.niteroomcreation.basemade.ui.act.detail.DetailActivity;
import com.niteroomcreation.basemade.ui.fragment.main.MainFragment;
import com.niteroomcreation.basemade.utils.ImageUtils;

import butterknife.BindView;

public class MainActivity extends BaseView implements MainContract.View,
        MainFragment.MainFragmentListener {

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
        presenter = new MainPresenter(this, this);
        moveToFragment(flMainContent.getId(), MainFragment.newInstance(),
                MainFragment.class.getSimpleName());
    }

    @Override
    public void onItemSelectedDetail(MoviesModel item) {
        Log.e(TAG, String.format("onItemSelectedDetail: %s", item.toString()));

        //asking permission to access external storage
        //don't need permission for accessing local storage
        //accept: run store image
        //reject: do nothing & re-asking permission

        Bitmap b = ((BitmapDrawable) getResources().getDrawable(item.getPoster())).getBitmap();

        new ImageUtils(MainActivity.this)
                .setFileName(item.getName())
                .save(b, new ImageUtils.ImageUtilsListener() {
                    @Override
                    public void success(String fileAbsolutePath) {
                        Log.e(TAG, String.format("success: path image %s", fileAbsolutePath));

                        MoviesModel m = item;
                        m.setPosterPath(fileAbsolutePath);

                        DetailActivity.startActivity(MainActivity.this, m);
                    }

                    @Override
                    public void failed(String errMsg) {
                        Log.e(TAG, String.format("failed: %s", errMsg));
                    }
                });
    }
}
