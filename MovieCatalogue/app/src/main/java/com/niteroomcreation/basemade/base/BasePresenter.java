package com.niteroomcreation.basemade.base;

/**
 * Created by Septian Adi Wijaya on 03/09/19
 */
public class BasePresenter<ViewT> implements IBasePresenter<ViewT> {

    protected ViewT mView;

    @Override
    public void onViewActive(ViewT view) {
        this.mView = view;
    }

    @Override
    public void onViewInactive() {
        this.mView = null;
    }
}
