package com.niteroomcreation.moviewatchfavs.view.image_utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

public class BlurTransformation extends BitmapTransformation {

    private RenderScript rs;
    private static BlurTransformation reff;

    private BlurTransformation(Context context) {
        super();
        rs = RenderScript.create(context);
    }

    public static synchronized BlurTransformation init(Context context){
        if(reff == null){
            reff = new BlurTransformation(context);
        }

        return reff;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform,
                               int outWidth, int outHeight) {

        Bitmap blurredBitmap = toTransform.copy(Bitmap.Config.ARGB_8888, true);

        //allocate memory for renderscript to work with
        Allocation inp = Allocation.createFromBitmap(rs, blurredBitmap,
                Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SHARED);
        Allocation out = Allocation.createTyped(rs, inp.getType());

        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setInput(inp);

        //set blur radius
        script.setRadius(5);

        // Start the ScriptIntrinisicBlur
        script.forEach(out);

        // Copy the output to the blurred bitmap
        out.copyTo(blurredBitmap);

        // don't work in glide version 4, only work in version 3
        // see: https://stackoverflow.com/a/57195802
//        toTransform.recycle();

        return blurredBitmap;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
