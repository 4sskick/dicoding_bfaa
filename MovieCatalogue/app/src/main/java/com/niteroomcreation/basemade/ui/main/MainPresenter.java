package com.niteroomcreation.basemade.ui.main;

import android.content.Context;

import com.niteroomcreation.basemade.base.BasePresenter;

/**
 * Created by Septian Adi Wijaya on 30/09/19
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    public MainPresenter(MainContract.View view, Context context) {
        super.onViewActive(view, context);
    }
}
