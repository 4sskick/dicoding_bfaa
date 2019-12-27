package com.niteroomcreation.basemade;


import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Septian Adi Wijaya on 30/09/19
 */
public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // TODO: 27/12/19 not work for changing fonts
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/billabong.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}