package com.niteroomcreation.basemade.ui.act.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BaseView;

import butterknife.BindView;

/**
 * Created by Septian Adi Wijaya on 20/02/2020.
 * please be sure to add credential if you use people's code
 */
public class SettingActivity extends BaseView implements SettingContract.View {

    @BindView(R.id.c_actionbar_ic_back)
    AppCompatImageView backButton;
    @BindView(R.id.c_actionbar_title)
    TextView txtTitle;
    @BindView(R.id.list_settings)
    RecyclerView listSetting;

    @Override
    protected int parentLayout() {
        return R.layout.b_activity_actionbar;
    }

    @Override
    protected int contentLayout() {
        return R.layout.a_setting;
    }

    @Override
    protected void initComponents(@Nullable Bundle savedInstanceState) {
        showTitleToolbar(true, "SETTING");
        showBackButtonToolbar(true);

        backButton.setColorFilter(getResources().getColor(R.color.colorPrimary));
        txtTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
    }
}
