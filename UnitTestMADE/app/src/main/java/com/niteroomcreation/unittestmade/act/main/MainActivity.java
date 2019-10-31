package com.niteroomcreation.unittestmade.act.main;

import com.niteroomcreation.unittestmade.R;
import com.niteroomcreation.unittestmade.base.BaseView;

public class MainActivity extends BaseView implements MainContract.View {


    @Override
    protected int parentLayout() {
        return 0;
    }

    @Override
    protected int contentLayout() {
        return R.layout.a_main;
    }

    @Override
    protected void initComponents() {

    }
}
