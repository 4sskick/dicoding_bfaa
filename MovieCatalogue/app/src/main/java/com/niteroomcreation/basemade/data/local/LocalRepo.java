package com.niteroomcreation.basemade.data.local;

import android.content.Context;

/**
 * Created by Septian Adi Wijaya on 07/01/20
 */
public class LocalRepo {

    private static LocalRepo instance;
    private LocalDatabase mRoomDb;

    public static LocalRepo getInstance(Context context) {
        if (instance == null) {
            instance = new LocalRepo(context);
        }
        return instance;
    }

    private LocalRepo(Context context) {
        mRoomDb = LocalDatabase.getInstance(context);
    }


}
