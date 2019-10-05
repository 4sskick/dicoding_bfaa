package com.niteroomcreation.beginermade.fragment.detail;

import com.niteroomcreation.beginermade.base.BasePresenter;

public class DetailFragmentPresenter extends BasePresenter<DetailFragmentContract.View> implements DetailFragmentContract.Presenter {

    public DetailFragmentPresenter(DetailFragmentContract.View view) {
        super.onViewActive(view);
    }

    @Override
    public void onViewActive(DetailFragmentContract.View view) {

    }

    @Override
    public void onViewInactive() {

    }
}
