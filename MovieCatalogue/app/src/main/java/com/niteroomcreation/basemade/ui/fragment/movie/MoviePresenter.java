package com.niteroomcreation.basemade.ui.fragment.movie;

import android.content.Context;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BasePresenter;
import com.niteroomcreation.basemade.models.MoviesModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Septian Adi Wijaya on 01/10/19
 */
public class MoviePresenter extends BasePresenter<MovieContract.View> implements MovieContract.Presenter {

    public MoviePresenter(MovieContract.View view, Context context) {
        super.onViewActive(view, context);
    }

    @Override
    public void onViewActive(MovieContract.View view, Context context) {

    }

    @Override
    public void onViewInactive() {

    }

    @Override
    public List<MoviesModel> constructModels() {
        List<MoviesModel> a = new ArrayList<>();

        a.add(new MoviesModel("A Star Is Born", mContext.getString(R.string.desc_a_star),
                R.drawable.poster_a_star, 2018, 75, null));
        a.add(new MoviesModel("Aquaman", mContext.getString(R.string.desc_aquaman),
                R.drawable.poster_aquaman, 2018, 68, null));
        a.add(new MoviesModel("Avengers: Infinity War", mContext.getString(R.string.desc_avenger)
                , R.drawable.poster_avengerinfinity, 2018, 83, null));
        a.add(new MoviesModel("Bird Box", mContext.getString(R.string.desc_birdbox),
                R.drawable.poster_birdbox, 2018, 70, null));
        a.add(new MoviesModel("Bohemian Rhapsody", mContext.getString(R.string.desc_bohemian),
                R.drawable.poster_bohemian, 2018, 80, null));
        a.add(new MoviesModel("Bumblebee", mContext.getString(R.string.desc_bumblebee),
                R.drawable.poster_bumblebee, 2018, 65, null));
        a.add(new MoviesModel("Creed", mContext.getString(R.string.desc_creed),
                R.drawable.poster_creed, 2015, 73, null));
        a.add(new MoviesModel("Once Upon a Deadpool", mContext.getString(R.string.desc_deadpool),
                R.drawable.poster_deadpool, 2018, 69, null));
        a.add(new MoviesModel("How to Train Your Dragon: The Hidden World",
                mContext.getString(R.string.desc_dragon), R.drawable.poster_dragon, 2019, 77, null));
        a.add(new MoviesModel("Dragon Ball Super: Broly",
                mContext.getString(R.string.desc_dragonball), R.drawable.poster_dragonball, 2018, 75, null));
        return a;
    }
}
