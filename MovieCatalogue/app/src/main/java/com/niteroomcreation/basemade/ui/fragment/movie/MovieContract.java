package com.niteroomcreation.basemade.ui.fragment.movie;

import com.niteroomcreation.basemade.base.IBasePresenter;
import com.niteroomcreation.basemade.base.IBaseView;
import com.niteroomcreation.basemade.data.models.Movies;
import com.niteroomcreation.basemade.models.MoviesModel;

import java.util.List;

/**
 * Created by Septian Adi Wijaya on 01/10/19
 */
public class MovieContract {
    interface View extends IBaseView {
        void setData(List<Movies> data);
    }

    interface Presenter extends IBasePresenter<MovieContract.View> {
        void getMovies(String lang);
    }
}

