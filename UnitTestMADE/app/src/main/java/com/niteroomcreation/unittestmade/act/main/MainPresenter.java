package com.niteroomcreation.unittestmade.act.main;

import com.niteroomcreation.unittestmade.base.BasePresenter;
import com.niteroomcreation.unittestmade.models.CuboidModel;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private CuboidModel cuboidModel;

    public MainPresenter(MainContract.View view) {
        cuboidModel = new CuboidModel();

        super.onViewActive(view);
    }

    public MainPresenter(CuboidModel cuboidModel) {
        this.cuboidModel = cuboidModel;
    }

    @Override
    public void onViewActive(MainContract.View view) {

    }

    @Override
    public void onViewInactive() {

    }

    @Override
    public void save(double width, double length, double height) {
        cuboidModel.save(width, length, height);
    }

    @Override
    public double getCircumference() {
//        return cuboidModel.getCircumference();
        return 4 * (cuboidModel.getWidth() * cuboidModel.getLength() * cuboidModel.getHeight());
    }

    @Override
    public double getSurfaceArea() {
//        return cuboidModel.getSurfaceArea();
        double wl = cuboidModel.getWidth() * cuboidModel.getLength();
        double wh = cuboidModel.getWidth() * cuboidModel.getHeight();
        double lh = cuboidModel.getLength() * cuboidModel.getHeight();
        return 2 * (wl + wh + lh);
    }

    @Override
    public double getVolume() {
//        return cuboidModel.getVolume();
        return cuboidModel.getWidth() * cuboidModel.getLength() * cuboidModel.getHeight();
    }
}
