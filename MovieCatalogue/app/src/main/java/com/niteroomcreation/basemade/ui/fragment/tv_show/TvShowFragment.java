package com.niteroomcreation.basemade.ui.fragment.tv_show;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BaseFragmentView;

/**
 * Created by Septian Adi Wijaya on 15/11/19
 */
public class TvShowFragment extends BaseFragmentView implements TvShowContract.View {

    private TvShowPresenter presenter;

    public static TvShowFragment newInstance() {
        return new TvShowFragment();
    }

    @Override
    protected int contentLayout() {
        return R.layout.f_tv_shows;
    }

    @Override
    protected void initComponents() {
        presenter = new TvShowPresenter(this, getContext());
    }
}
