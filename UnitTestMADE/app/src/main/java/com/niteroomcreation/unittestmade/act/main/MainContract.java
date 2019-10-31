package com.niteroomcreation.unittestmade.act.main;

import com.niteroomcreation.unittestmade.base.IBasePresenter;
import com.niteroomcreation.unittestmade.base.IBaseView;

public class MainContract {

    interface View extends IBaseView {

    }

    interface Presenter extends IBasePresenter<View> {

        void save(double width, double length, double height);

        double getCircumference();

        double getSurfaceArea();

        double getVolume();

    }
}
