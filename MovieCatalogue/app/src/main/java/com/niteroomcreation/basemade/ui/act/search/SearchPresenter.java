package com.niteroomcreation.basemade.ui.act.search;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.niteroomcreation.basemade.BuildConfig;
import com.niteroomcreation.basemade.base.BasePresenter;
import com.niteroomcreation.basemade.data.Repository;
import com.niteroomcreation.basemade.data.local.entity.MovieEntity;
import com.niteroomcreation.basemade.data.local.entity.TvEntity;
import com.niteroomcreation.basemade.data.models.BaseResponse;
import com.niteroomcreation.basemade.data.remote.http.NetworkCallback;

import org.json.JSONObject;

/**
 * Created by Septian Adi Wijaya on 09/02/2020.
 * please be sure to add credential if you use people's code
 */
public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    private static final String TAG = SearchPresenter.class.getSimpleName();

    public SearchPresenter(SearchContract.View view, Context context) {
        super.onViewActive(view, context);
    }

    public void getMovieOnQuery(String onQuery, String lang) {
        if (getLocalData().movieDao().getMoviesOnQuery(onQuery).size() == 0) {
            addSubscribe(Repository.getInstance(mContext).getOnQueryMovies(BuildConfig.API_KEY,
                    lang, onQuery)
                    , new NetworkCallback<BaseResponse<MovieEntity>>() {
                        @Override
                        public void onSuccess(BaseResponse<MovieEntity> model) {
                            Log.e(TAG, "onSuccess: Movie " + model.toString());
                        }

                        @Override
                        public void onFailure(int code
                                , String message
                                , @Nullable JSONObject jsonObject) {
                            Log.e(TAG, String.format("onFailure: Movie %s %s", code, message));
                        }

                        @Override
                        public void onFinish(boolean isFailure) {
                            Log.e(TAG, "onFinish: Movie " + isFailure);
                            mView.hideLoading();
                        }
                    });
        } else {
            BaseResponse<MovieEntity> response = new BaseResponse<>();
            response.setResults(getLocalData().movieDao().getMoviesOnQuery(onQuery));

            Log.e(TAG, "getMovieOnQuery: ELSE " + response.toString());

            mView.hideLoading();
        }
    }

    public void getTvShowOnQuery(String onQuery, String lang) {
        if (getLocalData().tvDao().gettvShowsOnQuery(onQuery).size() == 0) {
            addSubscribe(Repository.getInstance(mContext).getOnQueryTvShows(BuildConfig.API_KEY,
                    lang, onQuery)
                    , new NetworkCallback<BaseResponse<TvEntity>>() {
                        @Override
                        public void onSuccess(BaseResponse<TvEntity> model) {
                            Log.e(TAG, "onSuccess: Tv " + model.toString());
                        }

                        @Override
                        public void onFailure(int code
                                , String message
                                , @Nullable JSONObject jsonObject) {
                            Log.e(TAG, String.format("onFailure: Tv %s %s", code, message));
                        }

                        @Override
                        public void onFinish(boolean isFailure) {
                            Log.e(TAG, "onFinish: Tv " + isFailure);
                            mView.hideLoading();
                        }
                    });
        } else {
            BaseResponse<TvEntity> response = new BaseResponse<>();
            response.setResults(getLocalData().tvDao().gettvShowsOnQuery(onQuery));

            Log.e(TAG, "getTvShowOnQuery: ELSE " + response.toString());

            mView.hideLoading();
        }
    }
}
