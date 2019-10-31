package com.niteroomcreation.basemade.act.main;

import android.content.Intent;
import android.widget.FrameLayout;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BaseView;
import com.niteroomcreation.basemade.fragment.main.MainFragment;

import butterknife.BindView;

public class MainActivity extends BaseView implements MainContract.View {

    @BindView(R.id.fl_main_content)
    FrameLayout flMainContent;

    private MainPresenter presenter;

    public static void startActivity(BaseView act) {
        Intent i = new Intent(act, MainActivity.class);
        act.finish();
        act.startActivity(i);
    }

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
        moveToFragment(flMainContent.getId(), MainFragment.newInstance(), MainFragment.class.getSimpleName());
    }
}
