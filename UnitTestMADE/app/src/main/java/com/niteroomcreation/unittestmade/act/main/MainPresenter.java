package com.niteroomcreation.unittestmade.act.main;

import com.niteroomcreation.unittestmade.base.BasePresenter;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    public MainPresenter(MainContract.View view) {
        super.onViewActive(view);
    }

    @Override
    public void onViewActive(MainContract.View view) {

    }

    @Override
    public void onViewInactive() {

    }
}
