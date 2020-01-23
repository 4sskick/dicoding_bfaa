package com.niteroomcreation.basemade.ui.fragment.tv_show;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.niteroomcreation.basemade.BuildConfig;
import com.niteroomcreation.basemade.base.BasePresenter;
import com.niteroomcreation.basemade.data.Repository;
import com.niteroomcreation.basemade.data.models.BaseResponse;
import com.niteroomcreation.basemade.data.local.entity.TvEntity;
import com.niteroomcreation.basemade.data.remote.http.NetworkCallback;
import com.niteroomcreation.basemade.utils.Utils;
import com.niteroomcreation.basemade.utils.thread.ImageHandlerThread;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Septian Adi Wijaya on 15/11/19
 */
public class TvShowPresenter extends BasePresenter<TvShowContract.View> implements TvShowContract.Presenter {

    private static final String TAG = TvShowPresenter.class.getSimpleName();

    private ImageHandlerThread mThread = null;

    public TvShowPresenter(TvShowContract.View view, Context context) {
        super.onViewActive(view, context);
    }

    @Override
    public void getTvShows(String lang) {
        mView.showLoading();

        if (getLocalData().tvDao().getTvsByLang(lang).size() == 0) {
            addSubscribe(Repository.getInstance(mContext).getTvShows(BuildConfig.API_KEY, lang)
                    , new NetworkCallback<BaseResponse<TvEntity>>() {
                        @Override
                        public void onSuccess(BaseResponse<TvEntity> model) {
                            Log.e(TAG, "onSuccess: " + model.toString());

                            onSuccTvs(model, lang, true);
                        }

                        @Override
                        public void onFailure(int code, String message,
                                              @Nullable JSONObject jsonObject) {
                            Log.e(TAG, String.format("onFailure: code %s message %s jsonObj %s", code,
                                    message, jsonObject != null ? jsonObject.toString() : "{}"));

                            mView.showOverrideEmptyState();
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
        } else {
            BaseResponse<TvEntity> a = new BaseResponse<>();
            a.setResults(getLocalData().tvDao().getTvsByLang(lang));

            onSuccTvs(a, lang, false);
        }
    }

    private void onSuccTvs(BaseResponse<TvEntity> model, String lang, boolean isNetwork) {

        if (isNetwork) {

            List<TvEntity> tvs = new ArrayList<>();
            for (TvEntity tv : model.getResults()) {

                tv.setPage(model.getPage());
                tv.setLanguageType(lang);
                tv.setIsFavorite(false);

                tvs.add(tv);
            }

            getLocalData().tvDao().insertTvs(tvs);
            imgIntoLocal(model.getResults());
        }

        mView.setData(model.getResults());
        mView.hideLoading();
    }

    private void imgIntoLocal(List<TvEntity> data) {
        for (int i = 0; i < data.size(); i++) {
            TvEntity model = data.get(i);
            mThread = new ImageHandlerThread(mContext);

            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        mThread.generateByteArrayImage(
                                model.getFullPosterPath(true)
                                , String.format("%s_%s"
                                        , model.getPosterPath().split("/")[1].split(".jpg")[0]
                                        , model.getName()
                                )
                        );
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

    }
}
