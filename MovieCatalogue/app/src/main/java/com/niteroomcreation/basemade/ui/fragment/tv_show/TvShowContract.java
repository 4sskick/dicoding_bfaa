package com.niteroomcreation.basemade.ui.fragment.tv_show;

import com.niteroomcreation.basemade.base.IBasePresenter;
import com.niteroomcreation.basemade.base.IBaseView;
import com.niteroomcreation.basemade.models.TvShowModel;

import java.util.List;

/**
 * Created by Septian Adi Wijaya on 15/11/19
 */
public class TvShowContract {
    interface View extends IBaseView {

    }

    interface Presenter extends IBasePresenter<TvShowContract.View> {
        List<TvShowModel> constructModels();
    }
}