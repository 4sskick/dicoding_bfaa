package com.niteroomcreation.basemade.act.main;

import com.niteroomcreation.basemade.base.BasePresenter;

/**
 * Created by Septian Adi Wijaya on 30/09/19
 */
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

    public String getDum(){
        return "from presenter";
    }
}
