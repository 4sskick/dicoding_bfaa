package com.niteroomcreation.basemade.ui.fragment.tv_show;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.niteroomcreation.basemade.BuildConfig;
import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BasePresenter;
import com.niteroomcreation.basemade.data.Repository;
import com.niteroomcreation.basemade.data.models.BaseResponse;
import com.niteroomcreation.basemade.data.models.Movies;
import com.niteroomcreation.basemade.data.models.TvShows;
import com.niteroomcreation.basemade.data.remote.http.NetwokCallback;
import com.niteroomcreation.basemade.models.TvShowModel;
import com.niteroomcreation.basemade.utils.ImageUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Septian Adi Wijaya on 15/11/19
 */
public class TvShowPresenter extends BasePresenter<TvShowContract.View> implements TvShowContract.Presenter {

    private static final String TAG = TvShowPresenter.class.getSimpleName();

    public TvShowPresenter(TvShowContract.View view, Context context) {
        super.onViewActive(view, context);
    }

    @Override
    public void getTvShows(String lang) {
        mView.showLoading();

        addSubscribe(Repository.getInstance(mContext).getTvShows(BuildConfig.API_KEY, lang)
                , new NetwokCallback<BaseResponse<TvShows>>() {
                    @Override
                    public void onSuccess(BaseResponse<TvShows> model) {
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

    private void imgIntoLocal(List<TvShows> data) {
        for (int i = 0; i < data.size(); i++) {
            TvShows model = data.get(i);

            Glide.with(mContext)
                    .asBitmap()
                    .load(String.format("%s%sw500%s"
                            , BuildConfig.BASE_URL_IMG
                            , BuildConfig.BASE_PATH_IMG
                            , model.getPosterPath()))
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object m, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            new ImageUtils(mContext)
                                    .setFileName(String.format("%s_%s", model.getPosterPath().split("/")[1].split(".jpg")[0], model.getName()))
                                    .save(resource, new ImageUtils.ImageUtilsListener() {
                                        @Override
                                        public void success(String fileAbsolutePath) {
                                            Log.e(TAG, "success: " + fileAbsolutePath);

                                        }

                                        @Override
                                        public void failed(String errMsg) {
                                            Log.e(TAG, String.format("failed: %s", errMsg));
                                        }
                                    });
                            return false;
                        }
                    })
                    .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

        }

        mView.setData(data);
    }
}
