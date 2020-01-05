package com.niteroomcreation.basemade.ui.fragment;

import android.util.Log;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BaseFragmentView;

/**
 * Created by Septian Adi Wijaya on 06/01/2020.
 * please be sure to add credential if you use people's code
 */
public class EmptyFragment extends BaseFragmentView {

    private static final String TAG = EmptyFragment.class.getSimpleName();

    public static EmptyFragment newInstance() {
        return new EmptyFragment();
    }

    @Override
    protected int contentLayout() {
        return R.layout.f_empty;
    }

    @Override
    protected void initComponents() {
        Log.e(TAG, "initComponents: here me!?");
    }
}
