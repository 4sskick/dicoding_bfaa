package com.niteroomcreation.basemade.ui.widget;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.niteroomcreation.basemade.data.local.LocalDatabase;
import com.niteroomcreation.basemade.data.local.dao.MovieDao;
import com.niteroomcreation.basemade.data.local.entity.MovieEntity;

/**
 * Created by Septian Adi Wijaya on 16/02/2020.
 * please be sure to add credential if you use people's code
 */
public class FavsContentProvider extends ContentProvider {

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

    /**
     * The match code for some items in the Cheese table.
     */
    private static final int CODE_MOVIES_DIR = 1;

    /**
     * The match code for an item in the Cheese table.
     */
    private static final int CODE_MOVIES_ITEM = 2;

    /**
     * The URI matcher.
     */
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, MovieEntity.T_NAME, CODE_MOVIES_DIR);
        MATCHER.addURI(AUTHORITY, MovieEntity.T_NAME + "/*", CODE_MOVIES_ITEM);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri
            , @Nullable String[] projection
            , @Nullable String selection
            , @Nullable String[] selectionArgs
            , @Nullable String sortOrder) {

        final int code = MATCHER.match(uri);
        if (code == CODE_MOVIES_DIR || code == CODE_MOVIES_ITEM) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            MovieDao m = LocalDatabase.getInstance(context).movieDao();
            final Cursor cursor;
            if (code == CODE_MOVIES_DIR) {
                cursor = m.cursorSelectAll();
            } else {
                cursor = m.cursorSelectById(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case CODE_MOVIES_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + MovieEntity.T_NAME;
            case CODE_MOVIES_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + MovieEntity.T_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (MATCHER.match(uri)) {
            case CODE_MOVIES_DIR:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                final long id = LocalDatabase.getInstance(context)
                        .movieDao()
                        .insertMovie(MovieEntity.fromContentValues(values));

                context.getContentResolver().notifyChange(uri, null);

                return ContentUris.withAppendedId(uri, id);
            case CODE_MOVIES_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert Movie with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (MATCHER.match(uri)) {
            case CODE_MOVIES_DIR:

                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_MOVIES_ITEM:

                final Context context = getContext();

                if (context == null) {
                    return 0;
                }

                final MovieEntity m = MovieEntity.fromContentValues(values);
                m.setId(ContentUris.parseId(uri));

                final int count = LocalDatabase.getInstance(context)
                        .movieDao()
                        .updateMovie(m);
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
