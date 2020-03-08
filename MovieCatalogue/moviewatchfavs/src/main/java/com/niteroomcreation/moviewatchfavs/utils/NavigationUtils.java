package com.niteroomcreation.moviewatchfavs.utils;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;

/**
 * Created by Septian Adi Wijaya on 15/12/2019.
 * please be sure to add credential if you use people's code
 */
public class NavigationUtils {

    public static void directToLocalSetting(Activity act) {
        Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        act.startActivity(intent);
    }
}
