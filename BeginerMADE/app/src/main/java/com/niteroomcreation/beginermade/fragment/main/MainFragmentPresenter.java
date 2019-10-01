package com.niteroomcreation.beginermade.fragment.main;

import com.niteroomcreation.beginermade.base.BasePresenter;

/**
 * Created by Septian Adi Wijaya on 01/10/19
 */
public class MainFragmentPresenter extends BasePresenter<MainFragmentContract.View> implements MainFragmentContract.Presenter {

    public MainFragmentPresenter(MainFragmentContract.View view) {
        super.onViewActive(view);
    }

    @Override
    public void onViewActive(MainFragmentContract.View view) {

    }

    @Override
    public void onViewInactive() {

    }
}
