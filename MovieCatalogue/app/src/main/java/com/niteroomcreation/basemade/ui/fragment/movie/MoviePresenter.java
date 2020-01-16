package com.niteroomcreation.basemade.ui.fragment.movie;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.niteroomcreation.basemade.BuildConfig;
import com.niteroomcreation.basemade.base.BasePresenter;
import com.niteroomcreation.basemade.data.Repository;
import com.niteroomcreation.basemade.data.local.LocalRepo;
import com.niteroomcreation.basemade.data.models.BaseResponse;
import com.niteroomcreation.basemade.data.local.entity.MovieEntity;
import com.niteroomcreation.basemade.data.remote.http.NetworkCallback;
import com.niteroomcreation.basemade.utils.Utils;
import com.niteroomcreation.basemade.utils.thread.ImageHandlerThread;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.Flowable;
import io.reactivex.subscribers.DisposableSubscriber;

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

//        addSubscribe(Repository.getInstance(mContext).getMovies(BuildConfig.API_KEY, lang)
//                , new NetworkCallback</*BaseResponse*/List<MovieEntity>>() {
//                    @Override
//                    public void onSuccess(/*BaseResponse*/List<MovieEntity> model) {
//                        Log.e(TAG, "onSuccess: " + model.toString());
//                        imgIntoLocal(model/*.getResults()*/);
//                    }
//
//                    @Override
//                    public void onFailure(int code, String message,
//                                          @Nullable JSONObject jsonObject) {
//                        Log.e(TAG, String.format("onFailure: code %s message %s jsonObj %s", code,
//                                message, jsonObject != null ? jsonObject.toString() : "{}"));
//
//                        mView.showMessage(String.format("code %s, %s", code, message));
//                        mView.hideLoading();
//                    }
//
//                    @Override
//                    public void onFinish(boolean isFailure) {
//                        if (isFailure) {
//                            mView.showOverrideEmptyState();
//                            return;
//                        }
//                        mView.hideLoading();
//                    }
//                });


        addSubscribe(Repository.getInstance(mContext).getMovies(BuildConfig.API_KEY, lang)
                , new NetworkCallback<BaseResponse<MovieEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<MovieEntity> model) {
                        Log.e(TAG, "onSuccess: " + model.toString());

                        if (Utils.isNetworkAvailable(mContext)) {
                            List<MovieEntity> movies = new ArrayList<>();
                            for (MovieEntity movie : model.getResults()) {
                                MovieEntity storedMovies =
                                        getLocalData().movieDao().getMovieById(movie.getId());
                                if (storedMovies == null) movie.setLanguageType(lang);

                                movie.setPage(model.getPage());
                                movie.setLanguageType(lang);
                                movie.setIsFavorite(false);

                                movies.add(movie);
                            }

                            getLocalData().movieDao().insertMovies(movies);
                        }

                    }

                    @Override
                    public void onFailure(int code
                            , String message
                            , @Nullable JSONObject jsonObject) {
                        Log.e(TAG, "onFailure: " + message + " code " + code);
                    }

                    @Override
                    public void onFinish(boolean isFailure) {
                        Log.e(TAG, "onComplete: ");

                        mView.hideLoading();

                    }
                });


//        addSubscribe(Repository.getInstance(mContext).getMovies(BuildConfig.API_KEY, lang),
//                new DisposableSubscriber<BaseResponse<MovieEntity>>() {
//                    @Override
//                    public void onNext(BaseResponse<MovieEntity> movieEntities) {
//                        Log.e(TAG, "onNext: " + movieEntities.toString());
//
//                        if (Utils.isNetworkAvailable(mContext)) {
//                            List<MovieEntity> movies = new ArrayList<>();
//                            for (MovieEntity movie : movieEntities.getResults()) {
//                                MovieEntity storedMovies =
//                                        getLocalData().movieDao().getMovieById(movie.getId());
//                                if (storedMovies == null) movie.setLanguageType(lang);
//
//                                movie.setPage(movieEntities.getPage());
//                                movie.setLanguageType(lang);
//                                movie.setIsFavorite(false);
//
//                                movies.add(movie);
//                            }
//
//                            getLocalData().movieDao().insertMovies(movies);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        Log.e(TAG, "onError: " + t);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e(TAG, "onComplete: ");
//
//                        mView.hideLoading();
//                    }
//                });
    }

    private void imgIntoLocal(List<MovieEntity> data) {

        LocalRepo.getInstance(mContext).getMovies(null, null);

        for (int i = 0; i < data.size(); i++) {
            MovieEntity model = data.get(i);
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
