package com.niteroomcreation.beginermade.act.splash;

import com.niteroomcreation.beginermade.base.BasePresenter;

/**
 * Created by Septian Adi Wijaya on 01/10/19
 */
public class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.Presenter {

    public SplashPresenter(SplashContract.View view) {
        super.onViewActive(view);
    }

    @Override
    public void onViewActive(SplashContract.View view) {

    }

    @Override
    public void onViewInactive() {

    }
}
