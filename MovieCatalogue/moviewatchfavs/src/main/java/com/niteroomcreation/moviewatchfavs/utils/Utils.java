package com.niteroomcreation.moviewatchfavs.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;

import com.niteroomcreation.moviewatchfavs.data.local.entity.MovieEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Septian Adi Wijaya on 05/11/19
 */
public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    public static int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public static List<MovieEntity> mapCursorToList(Cursor moviesCursor) {
        List<MovieEntity> movies = new ArrayList<>();

        if (moviesCursor != null)
            while (moviesCursor.moveToNext()) {
                long id = moviesCursor.getLong(moviesCursor.getColumnIndexOrThrow(BaseColumns._ID));
                String title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieEntity.C_TITLE));
                String posterPath = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieEntity.C_POSTER_PATH));
                String overview = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow("overview"));

                movies.add(new MovieEntity(id, title, posterPath, overview));
            }


        Log.e(TAG, "mapCursorToList: movies size " + movies.size() + "\n" + movies.toString());

        return movies;
    }

}
