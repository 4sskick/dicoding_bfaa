package com.niteroomcreation.basemade.ui.act.splash;

import android.os.Handler;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.ui.act.main.MainActivity;
import com.niteroomcreation.basemade.base.BaseView;

/**
 * Created by Septian Adi Wijaya on 01/10/19
 */
public class SplashActivity extends BaseView implements SplashContract.View {
    @Override
    protected int parentLayout() {
        return 0;
    }

    @Override
    protected int contentLayout() {
        return R.layout.a_splash;
    }

    @Override
    protected void initComponents() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.startActivity(SplashActivity.this);
            }
        }, 2500);
    }
}
