package com.niteroomcreation.basemade.act.main;

import android.content.Context;

import com.niteroomcreation.basemade.base.BasePresenter;

/**
 * Created by Septian Adi Wijaya on 30/09/19
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    public MainPresenter(MainContract.View view, Context context) {
        super.onViewActive(view, context);
    }

    @Override
    public void onViewActive(MainContract.View view, Context context) {

    }

    @Override
    public void onViewInactive() {

    }

    public String getDum(){
        return "from presenter";
    }
}
