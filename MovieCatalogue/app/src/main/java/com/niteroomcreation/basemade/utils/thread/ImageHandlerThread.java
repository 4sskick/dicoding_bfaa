package com.niteroomcreation.basemade.utils.thread;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.HandlerThread;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;

/**
 * Created by Septian Adi Wijaya on 17/12/19
 */
public class ImageHandlerThread extends HandlerThread {
    private static final String TAG = ImageHandlerThread.class.getSimpleName();

    private ImageUploadHandlerThread mPictureUploadThread;
    private Context mContext;

    public ImageHandlerThread(Context context) {
        super(TAG);

        start();
        this.mContext = context;
    }

    public void beginQueue(byte[] data, String fileName) {
        if (mPictureUploadThread == null) {
            mPictureUploadThread = new ImageUploadHandlerThread(mContext);
            mPictureUploadThread.startUpload();
        }

        mPictureUploadThread.queueMakeBitmap(data, fileName);
    }

    public Bitmap loadQueue(String fileName) {
        return null;
    }

    public void generateByteArrayImage(String url, String fileName) throws ExecutionException, InterruptedException {
        Glide.with(mContext)
                .asBitmap()
                .load(url)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        resource.compress(Bitmap.CompressFormat.PNG, 80, stream);

                        beginQueue(stream.toByteArray(), fileName);

                        return false;
                    }
                })
                .submit()
                .get();
    }

}