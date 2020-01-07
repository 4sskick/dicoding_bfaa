package com.niteroomcreation.basemade;


import android.app.Application;

import com.niteroomcreation.basemade.data.local.LocalDatabase;

/**
 * Created by Septian Adi Wijaya on 30/09/19
 */
public class CustomApplication extends Application {

    public static LocalDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();

        db = LocalDatabase.getInstance(this);

    }
}