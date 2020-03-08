package com.niteroomcreation.moviewatchfavs.ui.widget;

import android.net.Uri;

import com.niteroomcreation.moviewatchfavs.data.local.entity.MovieEntity;

/**
 * Created by Septian Adi Wijaya on 18/02/20
 */
public class DbContract {
    /**
     * The authority of this content provider.
     */
    public static final String AUTHORITY = "com.niteroomcreation.basemade.provider";

    /**
     * The URI for the Cheese table.
     */
    public static final Uri URI_FAVS =
//            Uri.parse("content://" + AUTHORITY + "/" + MovieEntity.T_NAME);
            new Uri.Builder().scheme("content").authority(AUTHORITY).appendPath(MovieEntity.T_NAME).build();
}
