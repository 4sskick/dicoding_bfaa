package com.niteroomcreation.beginermade.act.main;

import android.content.Intent;
import android.widget.TextView;

import com.niteroomcreation.beginermade.R;
import com.niteroomcreation.beginermade.act.splash.SplashActivity;
import com.niteroomcreation.beginermade.base.BaseView;

import butterknife.BindView;

public class MainActivity extends BaseView implements MainContract.View {

    @BindView(R.id.txt_dum)
    TextView txtDum;

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

        txtDum.setText(presenter.getDum());
    }
}
