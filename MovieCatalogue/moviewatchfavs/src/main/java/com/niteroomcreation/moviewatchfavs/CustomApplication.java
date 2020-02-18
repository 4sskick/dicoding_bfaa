package com.niteroomcreation.moviewatchfavs;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Septian Adi Wijaya on 18/02/20
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
    }
}