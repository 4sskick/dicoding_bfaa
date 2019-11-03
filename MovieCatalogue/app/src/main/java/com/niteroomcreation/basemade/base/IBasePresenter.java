package com.niteroomcreation.basemade.base;

import android.content.Context;

/**
 * Created by Septian Adi Wijaya on 03/09/19
 */
public interface IBasePresenter<ViewT> {

    void onViewActive(ViewT view, Context context);

    void onViewInactive();
}
