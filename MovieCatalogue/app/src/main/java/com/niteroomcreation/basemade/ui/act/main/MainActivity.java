package com.niteroomcreation.basemade.ui.act.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.FrameLayout;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BaseView;
import com.niteroomcreation.basemade.ui.act.detail.DetailActivity;
import com.niteroomcreation.basemade.ui.fragment.main.MainFragment;
import com.niteroomcreation.basemade.models.MoviesModel;
import com.niteroomcreation.basemade.utils.ImageUtils;
import com.niteroomcreation.basemade.utils.Utils;

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
//        Utils.compressImage(this, getResources().getDrawable(item.getPoster()), new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                Log.e(TAG, "onResourceReady: ");
//
//                new ImageUtils(MainActivity.this)
//                        .setFileName(item.getName())
//                        .save(resource, new ImageUtils.ImageUtilsListener() {
//                            @Override
//                            public void success(String fileAbsolutePath) {
//                                Log.e(TAG, String.format("success: path image %s", fileAbsolutePath));
//                            }
//
//                            @Override
//                            public void failed(String errMsg) {
//                                Log.e(TAG, String.format("failed: %s", errMsg));
//                            }
//                        });
//            }
//
//            @Override
//            public void onLoadFailed(@Nullable Drawable errorDrawable) {
//                super.onLoadFailed(errorDrawable);
//                Log.e(TAG, "onLoadFailed: ");
//            }
//        });

//        Utils.compressImage(this, getResources().getDrawable(item.getPoster()), new RequestListener<Bitmap>() {
//            @Override
//            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
//                Log.e(TAG, "onLoadFailed: ");
//
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
//                Log.e(TAG, "onResourceReady: ");
//
//                return true;
//            }
//        });

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


//        DetailActivity.startActivity(this, item);
    }
}
