package com.niteroomcreation.basemade.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import com.niteroomcreation.basemade.data.models.Movies;
import com.niteroomcreation.basemade.data.models.TvShows;
import com.niteroomcreation.basemade.ui.act.detail.DetailActivity;

/**
 * Created by Septian Adi Wijaya on 15/12/2019.
 * please be sure to add credential if you use people's code
 */
public class NavigationUtils {

    public static void directToDetailScreen(Activity act
            , Object obj
            , ActivityOptionsCompat options) {

        Intent intent = new Intent(act, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MODEL, obj instanceof Movies ?
                (Movies) obj : obj instanceof TvShows ? (TvShows) obj : null);
        ActivityCompat.startActivity(act, intent, options.toBundle());
    }
}
