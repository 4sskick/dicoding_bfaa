package com.niteroomcreation.basemade.ui.movie;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.niteroomcreation.basemade.BuildConfig;
import com.niteroomcreation.basemade.base.BasePresenter;
import com.niteroomcreation.basemade.data.Repository;
import com.niteroomcreation.basemade.data.local.entity.MovieEntity;
import com.niteroomcreation.basemade.data.models.BaseResponse;
import com.niteroomcreation.basemade.data.remote.http.NetworkCallback;
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

    public void getMoviesOnDate(String lang, String date) {
        mView.showLoading();

        addSubscribe(Repository.getInstance(mContext).getMoviesOnDate(BuildConfig.API_KEY,
                lang, date)
                , new NetworkCallback<BaseResponse<MovieEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<MovieEntity> model) {
                        Log.e(TAG, "onSuccess: " + model.toString());

                        onSuccMovies(model, lang, true);
                    }

                    @Override
                    public void onFailure(int code
                            , String message
                            , @Nullable JSONObject jsonObject) {
                        Log.e(TAG, "onFailure: " + message + " code " + code);

                        mView.showOverrideEmptyState();
                    }

                    @Override
                    public void onFinish(boolean isFailure) {
                        Log.e(TAG, "onFinish: ");

                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getMovies(String lang) {
        mView.showLoading();

        if (getLocalData().movieDao().getMoviesByLang(lang).size() == 0) {

            addSubscribe(Repository.getInstance(mContext).getMovies(BuildConfig.API_KEY, lang)
                    , new NetworkCallback<BaseResponse<MovieEntity>>() {
                        @Override
                        public void onSuccess(BaseResponse<MovieEntity> model) {
                            Log.e(TAG, "onSuccess: " + model.toString());

                            onSuccMovies(model, lang, true);

                        }

                        @Override
                        public void onFailure(int code
                                , String message
                                , @Nullable JSONObject jsonObject) {
                            Log.e(TAG, "onFailure: " + message + " code " + code);

                            mView.showOverrideEmptyState();
                        }

                        @Override
                        public void onFinish(boolean isFailure) {
                            Log.e(TAG, "onComplete: ");

                            mView.hideLoading();

                        }
                    });
        } else {
            BaseResponse<MovieEntity> a = new BaseResponse<>();
            a.setResults(getLocalData().movieDao().getMoviesByLang(lang));

            onSuccMovies(a, lang, false);
        }
    }

    private void onSuccMovies(BaseResponse<MovieEntity> model, String lang, boolean isNetwork) {

        if (isNetwork) {

            List<MovieEntity> movies = new ArrayList<>();
            for (MovieEntity movie : model.getResults()) {

                movie.setPage(model.getPage());
                movie.setLanguageType(lang);
                movie.setIsFavorite(false);

                movies.add(movie);
            }

            getLocalData().movieDao().insertMovies(movies);
            imgIntoLocal(model.getResults());
        }

        mView.setData(model.getResults());
        mView.hideLoading();
    }

    private void imgIntoLocal(List<MovieEntity> data) {
        for (int i = 0; i < data.size(); i++) {
            MovieEntity model = data.get(i);

            if (model.getPosterPath() != null) {
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
        }

    }
}
