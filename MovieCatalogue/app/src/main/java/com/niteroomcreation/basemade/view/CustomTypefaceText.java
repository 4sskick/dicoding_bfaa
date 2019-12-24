package com.niteroomcreation.basemade.view;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Septian Adi Wijaya on 01/10/19
 */
public class CustomTypefaceText {
    public static Typeface instance;

    public static Typeface getInstance(Context context) {

        if (instance != null)
            return instance;

        instance = Typeface.createFromAsset(context.getAssets(), "fonts/billabong.ttf");
        return instance;

    }

    public static Typeface getInstance(Context context, String pathFont) {
        if (instance != null) {
            return instance;
        }
        instance = Typeface.createFromAsset(context.getAssets(), pathFont);
        return instance;
    }
}
