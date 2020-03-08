package com.niteroomcreation.basemade.ui.act.setting;

import android.content.Context;

import com.niteroomcreation.basemade.base.BasePresenter;

/**
 * Created by Septian Adi Wijaya on 20/02/2020.
 * please be sure to add credential if you use people's code
 */
public class SettingPresenter extends BasePresenter<SettingContract.View> implements SettingContract.Presenter {

    private static final String TAG = SettingPresenter.class.getSimpleName();

    public SettingPresenter(SettingContract.View view, Context context) {
        super.onViewActive(view, context);
    }
}
