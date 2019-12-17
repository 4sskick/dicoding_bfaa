package com.niteroomcreation.basemade.utils.thread;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.niteroomcreation.basemade.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Created by Septian Adi Wijaya on 17/12/19
 * see https://github.com/alphamu/ThreadPoolWithCameraPreview.git
 */
public class PictureUploadHandlerThread<T extends Class> extends HandlerThread {
    private static final String TAG = PictureUploadHandlerThread.class.getSimpleName();

    private static final int WHAT_UPLOAD = 0;
    private static final int WHAT_WRITE_TO_DISK = 1;
    private static final int WHAT_CREATE_BITMAP = 2;

    private Handler mHandler = null;
    private Context mContext;

    WeakReference<T> reffClass = null;

    private final File UPLOAD_DIR;
    private SimpleDateFormat FILE_NAME = new SimpleDateFormat("yyyymmddHHMMdssS", Locale.ENGLISH);
    private Set<File> mQueue = new HashSet<>();
    private Map<File, Bitmap> mWritePictureRequest = new HashMap<>();

    private Long mCounter = 1l;
    private boolean mUseThreads = true;

    public PictureUploadHandlerThread(T clazz, Context context) {
        super(TAG);
        start();

        mHandler = new Handler(getLooper());
        mContext = context;
        reffClass = new WeakReference<>(clazz);
//        UPLOAD_DIR = CameraConstants.getUploadDir(mContext);
    }

    public void startUpload() {
        mHandler = new Handler(getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case WHAT_UPLOAD:
                        UploadThreadPool.post(new Runnable() {
                            File data;

                            @Override
                            public void run() {
                                File file = data;
                                mQueue.remove(file);
                                Log.i(TAG, String.format("Processing %s", file.getName()));
                                handleRequest(file);
                            }

                            private Runnable init(File data) {
                                this.data = data;
                                return this;
                            }
                        }.init((File) msg.obj));
                        break;

                    case WHAT_WRITE_TO_DISK:
                        BitmapThreadPool.post(new Runnable() {
                            File data;

                            @Override
                            public void run() {
                                File f = data;
                                Bitmap bmp = mWritePictureRequest.get(f);
                                writeToFile(f, bmp, true);
                                mWritePictureRequest.remove(f);
                                queueFileToUpload(f);
                            }

                            private Runnable init(File data) {
                                this.data = data;
                                return this;

                            }
                        }.init((File) msg.obj));
                        break;

                    case WHAT_CREATE_BITMAP:
                        makeBitmap((MakeBitmapData) msg.obj);
                        break;
                }

                return false;
            }
        })
    }

    private void handleRequest(final File file) {
        //Do upload
        try {
            Log.i(TAG, "Uploading picture " + file.getName());
            sleep(1000); //simulate upload
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized void writeToFile(File file, Bitmap bitmap, boolean recycle) {
        if (bitmap == null) {
            Log.e(TAG, "writeToFile: Bitmap was null");
            return;
        }
        OutputStream fout = null;
        try {
            fout = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, fout);
            fout.flush();
            fout.close();

        } catch (FileNotFoundException e) {
            Log.e(TAG, e.getMessage(), e);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            if (recycle) {
                bitmap.recycle();
            }
        }
    }

    public void queueFileToUpload(File file) {
        queueFileToUpload(file, 0);
    }

    public void queueFileToUpload(File file, long delay) {
        synchronized (mQueue) {
            mQueue.add(file);
            Log.i(TAG, file.getName() + " added to the upload queue");
            Message msg = mHandler.obtainMessage(WHAT_UPLOAD, file);
            mHandler.sendMessageDelayed(msg, delay);
        }
    }

    private void makeBitmap(MakeBitmapData bitmapData) {
        Log.i(TAG, "Called make bitmap");

        T c = reffClass.get();
        if (c != null) {
            Resources res = mContext.getResources();

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bitmapData.data, 0, bitmapData.data.length, options);

            //Resize to 1MP 1280 x 960
            int reqWidth = 1280;
            int reqHeight = 960;
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapData.data, 0, bitmapData.data.length, options);
            Bitmap rotated = rotateImage(bitmap, bitmapData.orientation);
            if (bitmap != rotated) {
                //Recycle if they aren't referencing the same Bitmap object.
                //This means the rotation was successful.
                bitmap.recycle();
            }

            //Size for display
            String fileName = "bitmap-" + mCounter + "-" + FILE_NAME.format(new Date()) + ".jpg";
            mCounter += 1;
            reqWidth = res.getDimensionPixelSize(R.dimen.thumb_height); //this is actually the height, since we display in portrait
            reqHeight = res.getDimensionPixelSize(R.dimen.thumb_width);
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;

            //Write to file/upload
            if (!UPLOAD_DIR.exists()) {
                UPLOAD_DIR.mkdir();
            }

            queueWriteToFile(rotated, fileName);
        }
    }

    public void queueWriteToFile(Bitmap bitmap, String fileName) {
        File f = new File(UPLOAD_DIR, fileName);

        Log.i(TAG, f.getName() + " added to write to disk queue");

        mWritePictureRequest.put(f, bitmap);
        mHandler.obtainMessage(WHAT_WRITE_TO_DISK, f).sendToTarget();
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public Bitmap rotateImage(Bitmap src, float degree) {
        // create new matrix object
        Matrix matrix = new Matrix();
        // setup rotation degree
        matrix.postRotate(degree);
        // return new bitmap rotated using matrix
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    /**
     * custom class
     */
    private static class MakeBitmapData {
        byte[] data;
        float orientation;
    }
}
