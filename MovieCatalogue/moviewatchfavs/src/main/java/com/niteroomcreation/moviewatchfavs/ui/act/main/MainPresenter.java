package com.niteroomcreation.moviewatchfavs.ui.act.main;

import android.content.Context;

import com.niteroomcreation.moviewatchfavs.base.BasePresenter;

/**
 * Created by Septian Adi Wijaya on 18/02/20
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    public MainPresenter(MainContract.View view, Context context) {
        super.onViewActive(view, context);
    }
}
