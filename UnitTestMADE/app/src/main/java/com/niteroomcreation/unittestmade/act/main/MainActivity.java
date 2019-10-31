package com.niteroomcreation.unittestmade.act.main;

import com.niteroomcreation.unittestmade.R;
import com.niteroomcreation.unittestmade.base.BaseView;

public class MainActivity extends BaseView implements MainContract.View {
    private static final String TAG = MainActivity.class.getSimpleName();

    private MainPresenter presenter;


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
        presenter = new MainPresenter(this);

    }
}
