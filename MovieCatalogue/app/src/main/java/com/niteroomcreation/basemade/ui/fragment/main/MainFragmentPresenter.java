package com.niteroomcreation.basemade.ui.fragment.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BasePresenter;
import com.niteroomcreation.basemade.models.MoviesModel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Septian Adi Wijaya on 01/10/19
 */
public class MainFragmentPresenter extends BasePresenter<MainFragmentContract.View> implements MainFragmentContract.Presenter {

    public MainFragmentPresenter(MainFragmentContract.View view, Context context) {
        super.onViewActive(view, context);
    }

    @Override
    public void onViewActive(MainFragmentContract.View view, Context context) {

    }

    @Override
    public void onViewInactive() {

    }

    @Override
    public List<MoviesModel> constructModels() {
        List<MoviesModel> a = new ArrayList<>();

        a.add(new MoviesModel("A Star Is Born", mContext.getString(R.string.desc_a_star),
                decodeDrawable(R.drawable.poster_a_star), 75, null));
        a.add(new MoviesModel("Aquaman", mContext.getString(R.string.desc_aquaman),
                decodeDrawable(R.drawable.poster_aquaman), 68, null));
        a.add(new MoviesModel("Avengers: Infinity War", mContext.getString(R.string.desc_avenger)
                , decodeDrawable(R.drawable.poster_avengerinfinity), 83, null));
        a.add(new MoviesModel("Bird Box", mContext.getString(R.string.desc_birdbox),
                decodeDrawable(R.drawable.poster_birdbox), 70, null));
        a.add(new MoviesModel("Bohemian Rhapsody", mContext.getString(R.string.desc_bohemian),
                decodeDrawable(R.drawable.poster_bohemian), 80, null));
        a.add(new MoviesModel("Bumblebee", mContext.getString(R.string.desc_bumblebee),
                decodeDrawable(R.drawable.poster_bumblebee), 65, null));
        a.add(new MoviesModel("Creed", mContext.getString(R.string.desc_creed),
                decodeDrawable(R.drawable.poster_creed), 73, null));
        a.add(new MoviesModel("Once Upon a Deadpool", mContext.getString(R.string.desc_deadpool),
                decodeDrawable(R.drawable.poster_deadpool), 69, null));
        a.add(new MoviesModel("How to Train Your Dragon: The Hidden World",
                mContext.getString(R.string.desc_dragon), decodeDrawable(R.drawable.poster_dragon), 77, null));
        a.add(new MoviesModel("Dragon Ball Super: Broly",
                mContext.getString(R.string.desc_dragonball), decodeDrawable(R.drawable.poster_dragonball), 75, null));
        return a;
    }

    private Bitmap decodeDrawable(int resId) {
        Drawable is = mContext.getResources().getDrawable(resId);

//        Bitmap b = BitmapFactory.decodeResource(mContext.getResources(), resId);
        Bitmap b = ((BitmapDrawable) is).getBitmap();

        return b;
    }
}
