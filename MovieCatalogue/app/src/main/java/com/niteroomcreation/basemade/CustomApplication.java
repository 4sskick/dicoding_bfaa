package com.niteroomcreation.basemade;


import android.app.Application;

import com.facebook.stetho.Stetho;
import com.pixplicity.easyprefs.library.Prefs;

/**
 * Created by Septian Adi Wijaya on 30/09/19
 */
public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build()
        );

        new Prefs.Builder()
                .setContext(this)
                .setMode(MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }
}