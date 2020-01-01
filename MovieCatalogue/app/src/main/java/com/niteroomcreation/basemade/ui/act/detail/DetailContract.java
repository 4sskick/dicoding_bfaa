package com.niteroomcreation.basemade.ui.act.detail;

import com.niteroomcreation.basemade.base.IBasePresenter;
import com.niteroomcreation.basemade.base.IBaseView;

/**
 * Created by Septian Adi Wijaya on 04/11/19
 */
public class DetailContract {

    interface View extends IBaseView {

    }

    interface Presenter extends IBasePresenter<View> {
        void getMovieDetail(String movieId);

        void getTvShowDetail(String tvId);
    }
}
