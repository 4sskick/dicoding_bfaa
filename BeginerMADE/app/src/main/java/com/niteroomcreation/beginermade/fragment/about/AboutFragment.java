package com.niteroomcreation.beginermade.fragment.about;

import android.view.View;

import com.niteroomcreation.beginermade.base.BaseDialogView;

/**
 * Created by Septian Adi Wijaya on 04/10/19
 */
public class AboutFragment extends BaseDialogView implements AboutFragmentContract.View, BaseDialogView.MODEINTERFACE {


    @Override
    protected int setDialogView() {
        return 0;
    }

    @Override
    public int getDialogMode() {
        return 0;
    }

    @Override
    protected String getDialogTitle() {
        return null;
    }

    @Override
    protected String getDialogSubTitle() {
        return null;
    }

    @Override
    protected void initComponent(View view) {

    }

    @Override
    public boolean isShownLoading() {
        return false;
    }
}
