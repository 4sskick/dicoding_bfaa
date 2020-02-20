package com.niteroomcreation.basemade.ui.act.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BaseView;
import com.niteroomcreation.basemade.utils.NotificationUtils;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Septian Adi Wijaya on 20/02/2020.
 * please be sure to add credential if you use people's code
 */
public class SettingActivity extends BaseView implements SettingContract.View {

    @BindView(R.id.c_actionbar_ic_back)
    AppCompatImageView backButton;
    @BindView(R.id.c_actionbar_title)
    TextView txtTitle;
    @BindView(R.id.switch_reminder_release)
    SwitchButton swReminderRelease;
    @BindView(R.id.switch_reminder_daily)
    SwitchButton swReminderDaily;

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

        swReminderRelease.setOnCheckedChangeListener(mSwButtonListener);
        swReminderDaily.setOnCheckedChangeListener(mSwButtonListener);
    }

    private SwitchButton.OnCheckedChangeListener mSwButtonListener = new SwitchButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(SwitchButton view, boolean isChecked) {

            switch (view.getId()) {
                case R.id.switch_reminder_release:

                    showMessage("reminder release " + (swReminderRelease.isChecked() ? "diaktifkan" : "dimatikan"));

                    NotificationUtils.sendNotification(SettingActivity.this
                            , NotificationUtils.CHANNEL_ID
                            , "Reminder Release"
                            , "NOTIFICATION CONTENT Release");

                    break;

                case R.id.switch_reminder_daily:

                    showMessage("reminder daily " + (swReminderDaily.isChecked() ? "diaktifkan" : "dimatikan"));

                    NotificationUtils.sendNotification(SettingActivity.this
                            , NotificationUtils.CHANNEL_ID
                            , "Reminder Daily"
                            , "NOTIFICATION CONTENT Daily");

                    break;
            }
        }
    };

    @OnClick({R.id.layout_reminder_daily, R.id.layout_reminder_release})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_reminder_daily:
//                swReminderDaily.toggle();
                NotificationUtils.sendNotification(SettingActivity.this
                        , NotificationUtils.CHANNEL_ID
                        , "Reminder Daily"
                        , "NOTIFICATION CONTENT Daily");
                break;
            case R.id.layout_reminder_release:
                swReminderRelease.toggle();
                break;
        }
    }
}
