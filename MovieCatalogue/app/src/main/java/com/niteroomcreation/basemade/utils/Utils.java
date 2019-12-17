package com.niteroomcreation.basemade.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by Septian Adi Wijaya on 05/11/19
 */
public class Utils {

//    public static String storeImageToInternalStorage(Context context, Bitmap b, String fileName) {
//        ContextWrapper cw = new ContextWrapper(context);
//
//        // path to /data/data/yourapp/app_data/image_dir
//        File directory = cw.getDir("image_dir", Context.MODE_PRIVATE);
//        // Create imageDir
//        File path = new File(directory, fileName);
//
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(path);
//
//            // Use the compress method on the BitMap object to write image to the OutputStream
//            b.compress(Bitmap.CompressFormat.PNG, 80, fos);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                fos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return directory.getAbsolutePath();
//    }
//
//    public static Bitmap loadImageFromInternalStorage(String path, String fileName){
//        try{
//            File file = new File(path, fileName);
//            Bitmap b = BitmapFactory.decodeResource(new FileInputStream(file));
//        }catch (FileNotFoundException e){
//            e.printStackTrace();
//        }
//    }

    public static void compressImage(Context context, Drawable resId, SimpleTarget<Bitmap> listener) {
        Glide.with(context)
                .asBitmap()
                .load(resId)
                .encodeFormat(Bitmap.CompressFormat.JPEG)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(listener);
    }

    public static void compressImage(Context context, Drawable resId, RequestListener<Bitmap> listener) {
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
        return Locale.getDefault().getDisplayCountry().equalsIgnoreCase("English") ? "en-EN" : "id-ID";
    }
}
