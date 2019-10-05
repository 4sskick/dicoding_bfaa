package com.niteroomcreation.beginermade.fragment.detail;

import com.niteroomcreation.beginermade.base.BaseFragmentView;

public class DetailFragment extends BaseFragmentView implements DetailFragmentContract.View {

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    protected int contentLayout() {
        return 0;
    }

    @Override
    protected void initComponents() {

    }
}
