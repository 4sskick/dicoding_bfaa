package com.niteroomcreation.beginermade.fragment.about;

import com.niteroomcreation.beginermade.base.BasePresenter;

/**
 * Created by Septian Adi Wijaya on 04/10/19
 */
public class AboutFragmentPresenter extends BasePresenter<AboutFragmentContract.View> implements AboutFragmentContract.Presenter {

    public AboutFragmentPresenter(AboutFragmentContract.View view){
        super.onViewActive(view);
    }

    @Override
    public void onViewActive(AboutFragmentContract.View view) {

    }

    @Override
    public void onViewInactive() {

    }
}
