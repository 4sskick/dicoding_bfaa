package com.niteroomcreation.basemade.utils.thread;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import java.lang.ref.WeakReference;

/**
 * Created by Septian Adi Wijaya on 17/12/19
 */
public class ImageHandlerThread<T> extends HandlerThread {
    private static final String TAG = ImageHandlerThread.class.getSimpleName();

    T clazz;
    Handler mHandler = null;
    WeakReference<T> ref = null;

    private ImageUploadHandlerThread<T> mPictureUploadThread;
    private boolean mBurst = false;
    private int mCounter = 1;
    private Context mContext;

    public ImageHandlerThread(T clazz, Context context) {
        super(TAG);

        start();
        this.clazz = clazz;
        mHandler = new Handler(getLooper());
        ref = new WeakReference<>(clazz);
        mContext = context;
    }

    public void startBurst() {
        mBurst = true;
    }

    public void beginQueue(byte[] data) {
        if (mPictureUploadThread == null) {
            mPictureUploadThread = new ImageUploadHandlerThread<>(clazz, mContext);
            mPictureUploadThread.startUpload();
        }

        mPictureUploadThread.queueMakeBitmap(data);
    }
}