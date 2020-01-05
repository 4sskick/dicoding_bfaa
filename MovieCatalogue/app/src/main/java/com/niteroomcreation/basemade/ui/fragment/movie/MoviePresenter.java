package com.niteroomcreation.basemade.ui.fragment.movie;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.niteroomcreation.basemade.BuildConfig;
import com.niteroomcreation.basemade.base.BasePresenter;
import com.niteroomcreation.basemade.data.Repository;
import com.niteroomcreation.basemade.data.models.BaseResponse;
import com.niteroomcreation.basemade.data.models.Movies;
import com.niteroomcreation.basemade.data.remote.http.NetworkCallback;
import com.niteroomcreation.basemade.utils.thread.ImageHandlerThread;

import org.json.JSONObject;

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
                , new NetworkCallback<BaseResponse<Movies>>() {
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
                        if (isFailure) {
                            mView.showOverrideEmptyState();
                            return;
                        }
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
                                model.getFullPosterPath(true)
                                , String.format("%s_%s"
                                        , model.getPosterPath().split("/")[1].split(".jpg")[0]
                                        , model.getTitle())
                        );
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

        mView.setData(data);
    }
}
