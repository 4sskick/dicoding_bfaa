package com.niteroomcreation.basemade.ui.fragment.tv_show;

import android.content.Context;

import com.niteroomcreation.basemade.base.BasePresenter;

/**
 * Created by Septian Adi Wijaya on 15/11/19
 */
public class TvShowPresenter extends BasePresenter<TvShowContract.View> implements TvShowContract.Presenter {

    public TvShowPresenter(TvShowContract.View view, Context context) {
        super.onViewActive(view, context);
    }
}
