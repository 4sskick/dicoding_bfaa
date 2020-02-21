package com.niteroomcreation.basemade.ui.act.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BaseView;
import com.niteroomcreation.basemade.utils.PrefsKey;
import com.pixplicity.easyprefs.library.Prefs;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Septian Adi Wijaya on 20/02/2020.
 * please be sure to add credential if you use people's code
 */
public class SettingActivity extends BaseView implements SettingContract.View {

    private static final String TAG = SettingActivity.class.getSimpleName();

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

        swReminderDaily.setChecked(Prefs.getBoolean(PrefsKey.KEY_REMINDER_DAILY, false));
        swReminderRelease.setChecked(Prefs.getBoolean(PrefsKey.KEY_REMINDER_RELEASE, false));

        swReminderRelease.setOnCheckedChangeListener(mSwButtonListener);
        swReminderDaily.setOnCheckedChangeListener(mSwButtonListener);
    }

    private SwitchButton.OnCheckedChangeListener mSwButtonListener =
            new SwitchButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(SwitchButton view, boolean isChecked) {

                    switch (view.getId()) {
                        case R.id.switch_reminder_release:

                            Prefs.putBoolean(PrefsKey.KEY_REMINDER_RELEASE
                                    , swReminderRelease.isChecked());

                            showMessage("reminder release " + (swReminderRelease.isChecked() ?
                                    "diaktifkan" : "dimatikan"));

                            if (swReminderRelease.isChecked()) {
                                FirebaseMessaging.getInstance().subscribeToTopic(getResources().getString(R.string.str_notification_channel_release));

                                Log.e(TAG,
                                        "onCheckedChanged: trial subs to topic" + getResources().getString(R.string.str_notification_channel_release));

                                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                                    @Override
                                    public void onSuccess(InstanceIdResult instanceIdResult) {
                                        String deviceToken = instanceIdResult.getToken();
                                        Prefs.putString(PrefsKey.KEY_FCM_TOKEN, deviceToken);

                                        Log.e(TAG, "onSuccess: token refreshed " + deviceToken);
                                    }
                                });
                            } else
                                FirebaseMessaging.getInstance().unsubscribeFromTopic(getResources().getString(R.string.str_notification_channel_release));

                            break;

                        case R.id.switch_reminder_daily:

                            Prefs.putBoolean(PrefsKey.KEY_REMINDER_DAILY
                                    , swReminderDaily.isChecked());

                            showMessage("reminder daily " + (swReminderDaily.isChecked() ?
                                    "diaktifkan" :
                                    "dimatikan"));

                            if (swReminderDaily.isChecked()) {
                                FirebaseMessaging.getInstance().subscribeToTopic(getResources().getString(R.string.str_notification_channel_daily));

                                Log.e(TAG,
                                        "onCheckedChanged: trial subs to topic" + getResources().getString(R.string.str_notification_channel_daily));

                                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                                    @Override
                                    public void onSuccess(InstanceIdResult instanceIdResult) {
                                        String devieToken = instanceIdResult.getToken();
                                        Prefs.putString(PrefsKey.KEY_FCM_TOKEN, devieToken);

                                        Log.e(TAG, "onSuccess: token refreshed " + devieToken);
                                    }
                                });
                            } else
                                FirebaseMessaging.getInstance().unsubscribeFromTopic(getResources().getString(R.string.str_notification_channel_daily));

                            break;
                    }
                }
            };

    @OnClick({R.id.layout_reminder_daily, R.id.layout_reminder_release})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_reminder_daily:
                swReminderDaily.toggle();
                break;
            case R.id.layout_reminder_release:
                swReminderRelease.toggle();
                break;
        }
    }
}
