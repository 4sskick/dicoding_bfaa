package com.niteroomcreation.basemade.ui.tv_show;

import com.niteroomcreation.basemade.base.IBasePresenter;
import com.niteroomcreation.basemade.base.IBaseView;
import com.niteroomcreation.basemade.data.local.entity.TvEntity;

import java.util.List;

/**
 * Created by Septian Adi Wijaya on 15/11/19
 */
public class TvShowContract {
    interface View extends IBaseView {
        void setData(List<TvEntity> data);
    }

    interface Presenter extends IBasePresenter<TvShowContract.View> {
        void getTvShows(String lang);
    }
}
