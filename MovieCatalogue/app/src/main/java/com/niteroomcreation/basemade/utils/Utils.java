package com.niteroomcreation.basemade.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Septian Adi Wijaya on 05/11/19
 */
public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    public static void compressImage(Context context, Drawable resId,
                                     SimpleTarget<Bitmap> listener) {
        Glide.with(context)
                .asBitmap()
                .load(resId)
                .encodeFormat(Bitmap.CompressFormat.JPEG)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(listener);
    }

    public static void compressImage(Context context, Drawable resId,
                                     RequestListener<Bitmap> listener) {
        Glide.with(context)
                .asBitmap()
                .load(resId)
                .encodeFormat(Bitmap.CompressFormat.JPEG)
                .apply(new RequestOptions().override(500, 750))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .listener(listener)
                .submit();
    }

    public static String getUniCodeLanguageDisplay() {
        return Locale.getDefault().getDisplayCountry().equalsIgnoreCase("English") ? "en-EN" :
                "id-ID";
    }

    public static int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo connection = manager.getActiveNetworkInfo();
        return connection != null;
    }

    public static void closeKeyboard(Activity activity) {
        final InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            if (activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    public static String getCurrentDateFormatted() {
        DateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dt.format(date);
    }

    public static boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);

            Log.e(TAG, "isDateInvalid: try");

            return false;
        } catch (ParseException e) {

            Log.e(TAG, "isDateInvalid: catch");

            return true;
        }
    }
}
