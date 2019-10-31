package com.niteroomcreation.basemade.fragment.main;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BaseFragmentView;

/**
 * Created by Septian Adi Wijaya on 01/10/19
 */
public class MainFragment extends BaseFragmentView implements MainFragmentContract.View {

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int contentLayout() {
        return R.layout.f_main;
    }

    @Override
    protected void initComponents() {
        
    }
}
