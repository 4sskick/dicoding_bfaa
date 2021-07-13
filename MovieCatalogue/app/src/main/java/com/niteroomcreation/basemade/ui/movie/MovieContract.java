package com.niteroomcreation.basemade.ui.movie;

import com.niteroomcreation.basemade.base.IBasePresenter;
import com.niteroomcreation.basemade.base.IBaseView;
import com.niteroomcreation.basemade.data.local.entity.MovieEntity;

import java.util.List;

/**
 * Created by Septian Adi Wijaya on 01/10/19
 */
public class MovieContract {
    interface View extends IBaseView {
        void setData(List<MovieEntity> data);
    }

    interface Presenter extends IBasePresenter<MovieContract.View> {
        void getMovies(String lang);
    }
}

