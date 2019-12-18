package com.niteroomcreation.basemade.ui.fragment.tv_show;

import com.niteroomcreation.basemade.base.IBasePresenter;
import com.niteroomcreation.basemade.base.IBaseView;
import com.niteroomcreation.basemade.data.models.TvShows;
import com.niteroomcreation.basemade.models.TvShowModel;

import java.util.List;

/**
 * Created by Septian Adi Wijaya on 15/11/19
 */
public class TvShowContract {
    interface View extends IBaseView {
        void setData(List<TvShows> data);
    }

    interface Presenter extends IBasePresenter<TvShowContract.View> {
        void getTvShows(String lang);
    }
}
