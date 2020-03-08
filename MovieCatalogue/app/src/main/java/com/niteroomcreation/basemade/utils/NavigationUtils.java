package com.niteroomcreation.basemade.utils;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import com.niteroomcreation.basemade.data.local.entity.MovieEntity;
import com.niteroomcreation.basemade.data.local.entity.TvEntity;
import com.niteroomcreation.basemade.models.FavsObjectItem;
import com.niteroomcreation.basemade.ui.act.detail.DetailActivity;
import com.niteroomcreation.basemade.ui.act.search.SearchActivity;
import com.niteroomcreation.basemade.ui.act.setting.SettingActivity;

/**
 * Created by Septian Adi Wijaya on 15/12/2019.
 * please be sure to add credential if you use people's code
 */
public class NavigationUtils {

    public static void directToDetailScreen(Activity act
            , Object obj
            , ActivityOptionsCompat options) {

        Intent intent = new Intent(act, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MODEL, obj instanceof MovieEntity ?
                (MovieEntity) obj : obj instanceof TvEntity ?
                (TvEntity) obj : obj instanceof FavsObjectItem ? (FavsObjectItem) obj : null);
        ActivityCompat.startActivity(act, intent, options.toBundle());
    }

    public static void directToSearchScreen(Activity act) {
        Intent intent = new Intent(act, SearchActivity.class);
        act.startActivity(intent);
    }

    public static void directToLocalSetting(Activity act) {
        Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        act.startActivity(intent);
    }

    public static void directToSettingScreen(Activity act) {
        Intent intent = new Intent(act, SettingActivity.class);
        act.startActivity(intent);
    }
}
