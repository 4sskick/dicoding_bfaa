package com.niteroomcreation.basemade.ui.splash;

import android.content.Context;

import com.niteroomcreation.basemade.base.BasePresenter;

/**
 * Created by Septian Adi Wijaya on 01/10/19
 */
public class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.Presenter {

    public SplashPresenter(SplashContract.View view, Context context) {
        super.onViewActive(view, context);
    }

    @Override
    public void onViewActive(SplashContract.View view, Context context) {

    }

    @Override
    public void onViewInactive() {

    }
}
