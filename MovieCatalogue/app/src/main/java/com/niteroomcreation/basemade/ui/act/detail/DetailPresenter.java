package com.niteroomcreation.basemade.ui.act.detail;

import android.content.Context;

import com.niteroomcreation.basemade.base.BasePresenter;

/**
 * Created by Septian Adi Wijaya on 04/11/19
 */
public class DetailPresenter extends BasePresenter<DetailContract.View> implements DetailContract.Presenter {

    public DetailPresenter(DetailContract.View view, Context context) {
        super.onViewActive(view, context);
    }
}
