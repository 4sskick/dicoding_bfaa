package com.niteroomcreation.basemade.ui.act.detail;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.niteroomcreation.basemade.BuildConfig;
import com.niteroomcreation.basemade.base.BasePresenter;
import com.niteroomcreation.basemade.data.Repository;
import com.niteroomcreation.basemade.data.remote.http.NetworkCallback;
import com.niteroomcreation.basemade.models.details.movie.MoviesDetail;
import com.niteroomcreation.basemade.models.details.tvshow.TvShowsDetail;

import org.json.JSONObject;

/**
 * Created by Septian Adi Wijaya on 04/11/19
 */
public class DetailPresenter extends BasePresenter<DetailContract.View> implements DetailContract.Presenter {

    private static final String TAG = DetailPresenter.class.getSimpleName();

    public DetailPresenter(DetailContract.View view, Context context) {
        super.onViewActive(view, context);
    }

    @Override
    public void getMovieDetail(String movieId) {
        Log.e(TAG, "getMovieDetail: " + movieId);
        addSubscribe(Repository.getInstance(mContext).getMoviesDetail(movieId, BuildConfig.API_KEY)
                , new NetworkCallback<MoviesDetail>() {
                    @Override
                    public void onSuccess(MoviesDetail model) {
                        Log.e(TAG, "onSuccess: " + model.toString());
                    }

                    @Override
                    public void onFailure(int code, String message
                            , @Nullable JSONObject jsonObject) {
                        Log.e(TAG, String.format("onFailure: %s\n%s", code, message));
                    }

                    @Override
                    public void onFinish(boolean isFailure) {
                        Log.e(TAG, "onFinish: " + isFailure);
                    }
                });
    }

    @Override
    public void getTvShowDetail(String tvId) {
        Log.e(TAG, "getTvShowDetail: " + tvId);
        addSubscribe(Repository.getInstance(mContext).getTvShowsDetail(tvId, BuildConfig.API_KEY)
                , new NetworkCallback<TvShowsDetail>() {
                    @Override
                    public void onSuccess(TvShowsDetail model) {
                        Log.e(TAG, "onSuccess: " + model.toString());
                    }

                    @Override
                    public void onFailure(int code, String message
                            , @Nullable JSONObject jsonObject) {
                        Log.e(TAG, String.format("onFailure: %s\n%s", code, message));
                    }

                    @Override
                    public void onFinish(boolean isFailure) {
                        Log.e(TAG, "onFinish: " + isFailure);
                    }
                });
    }
}
