package com.niteroomcreation.basemade.ui.fragment.movie;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.niteroomcreation.basemade.BuildConfig;
import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BasePresenter;
import com.niteroomcreation.basemade.data.Repository;
import com.niteroomcreation.basemade.data.models.BaseResponse;
import com.niteroomcreation.basemade.data.models.Movies;
import com.niteroomcreation.basemade.data.remote.http.NetwokCallback;
import com.niteroomcreation.basemade.models.MoviesModel;
import com.niteroomcreation.basemade.utils.ImageUtils;
import com.niteroomcreation.basemade.utils.thread.ImageHandlerThread;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Septian Adi Wijaya on 01/10/19
 */
public class MoviePresenter extends BasePresenter<MovieContract.View> implements MovieContract.Presenter {

    private static final String TAG = MoviePresenter.class.getSimpleName();

    private ImageHandlerThread mThread = null;

    public MoviePresenter(MovieContract.View view, Context context) {
        super.onViewActive(view, context);
    }

    @Override
    public void getMovies(String lang) {
        mView.showLoading();

        addSubscribe(Repository.getInstance(mContext).getMovies(BuildConfig.API_KEY, lang)
                , new NetwokCallback<BaseResponse<Movies>>() {
                    @Override
                    public void onSuccess(BaseResponse<Movies> model) {
                        Log.e(TAG, "onSuccess: " + model.toString());
                        imgIntoLocal(model.getResults());
                    }

                    @Override
                    public void onFailure(int code, String message,
                                          @Nullable JSONObject jsonObject) {
                        Log.e(TAG, String.format("onFailure: code %s message %s jsonObj %s", code,
                                message, jsonObject != null ? jsonObject.toString() : "{}"));

                        mView.showMessage(String.format("code %s, %s", code, message));
                    }

                    @Override
                    public void onFinish(boolean isFailure) {
                        if (isFailure)
                            onFailure(0, "Something not right", null);
                        mView.hideLoading();
                    }
                });
    }

    private void imgIntoLocal(List<Movies> data) {

        for (int i = 0; i < data.size(); i++) {
            Movies model = data.get(i);
            mThread = new ImageHandlerThread(mContext);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mThread.generateByteArrayImage(
                                String.format("%s%sw500%s"
                                        , BuildConfig.BASE_URL_IMG
                                        , BuildConfig.BASE_PATH_IMG
                                        , model.getPosterPath())
                        );
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

//        for (int i = 0; i < data.size(); i++) {
//            Movies model = data.get(i);
//
//            Glide.with(mContext)
//                    .asBitmap()
//                    .load(String.format("%s%sw500%s"
//                            , BuildConfig.BASE_URL_IMG
//                            , BuildConfig.BASE_PATH_IMG
//                            , model.getPosterPath()))
//                    .listener(new RequestListener<Bitmap>() {
//                        @Override
//                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(Bitmap resource, Object m, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
//                            new ImageUtils(mContext)
//                                    .setFileName(String.format("%s_%s", model.getPosterPath().split("/")[1].split(".jpg")[0], model.getTitle()))
//                                    .save(resource, new ImageUtils.ImageUtilsListener() {
//                                        @Override
//                                        public void success(String fileAbsolutePath) {
//                                            Log.e(TAG, "success: " + fileAbsolutePath);
//
//                                        }
//
//                                        @Override
//                                        public void failed(String errMsg) {
//                                            Log.e(TAG, String.format("failed: %s", errMsg));
//                                        }
//                                    });
//                            return false;
//                        }
//                    })
//                    .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
//
//        }

        mView.setData(data);
    }
}
