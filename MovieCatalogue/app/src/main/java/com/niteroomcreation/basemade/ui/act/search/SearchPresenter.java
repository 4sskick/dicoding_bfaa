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
import com.niteroomcreation.basemade.models.FavsObjectItem;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
        mView.showLoading();

        if (getLocalData().movieDao().getMoviesOnQuery(onQuery).size() == 0) {
            addSubscribe(Repository.getInstance(mContext).getOnQueryMovies(BuildConfig.API_KEY,
                    lang, onQuery)
                    , new NetworkCallback<BaseResponse<MovieEntity>>() {
                        @Override
                        public void onSuccess(BaseResponse<MovieEntity> model) {
                            Log.e(TAG, "onSuccess: Movie " + model.toString());

                            getLocalData().movieDao().insertMovies(model.getResults());
                            convertDataMovie(model.getResults());
                        }

                        @Override
                        public void onFailure(int code
                                , String message
                                , @Nullable JSONObject jsonObject) {
                            Log.e(TAG, String.format("onFailure: Movie %s %s", code, message));
                            mView.showMessage(message);
                        }

                        @Override
                        public void onFinish(boolean isFailure) {
                            Log.e(TAG, "onFinish: Movie " + isFailure);
                            mView.hideLoading();
                        }
                    });
        } else {
            Log.e(TAG,
                    "getMovieOnQuery: ELSE " + getLocalData().movieDao().getMoviesOnQuery(onQuery));

            convertDataMovie(getLocalData().movieDao().getMoviesOnQuery(onQuery));
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

                            getLocalData().tvDao().insertTvs(model.getResults());
                            convertDataTvs(model.getResults());
                        }

                        @Override
                        public void onFailure(int code
                                , String message
                                , @Nullable JSONObject jsonObject) {
                            Log.e(TAG, String.format("onFailure: Tv %s %s", code, message));
                            mView.showMessage(message);
                        }

                        @Override
                        public void onFinish(boolean isFailure) {
                            Log.e(TAG, "onFinish: Tv " + isFailure);
                            mView.hideLoading();
                        }
                    });
        } else {
            Log.e(TAG,
                    "getTvShowOnQuery: ELSE" + getLocalData().tvDao().gettvShowsOnQuery(onQuery));
            convertDataTvs(getLocalData().tvDao().gettvShowsOnQuery(onQuery));
        }
    }


    private void convertDataMovie(List<MovieEntity> data) {
        List<FavsObjectItem> search = new ArrayList<>();

        for (MovieEntity m : data) {
            FavsObjectItem result = new FavsObjectItem();
            result.setId(m.getId());
            result.setTypeObject(1);
            result.setTitle(m.getTitle());
            result.setOverview(m.getOverview());
            result.setOriginalTitle(m.getOriginalTitle());
            result.setPosterPath(m.getPosterPath());
            result.setBackdropPath(m.getBackdropPath());
            result.setFavorite(m.getIsFavorite());

            search.add(result);
        }

        BaseResponse<FavsObjectItem> baseObj = new BaseResponse<>();
        baseObj.setResults(search);

        Log.e(TAG, "convertDataMovie: " + baseObj.toString());

        onSuccRetrieveData(baseObj);
    }

    private void convertDataTvs(List<TvEntity> data) {
        List<FavsObjectItem> search = new ArrayList<>();

        for (TvEntity m : data) {
            FavsObjectItem result = new FavsObjectItem();
            result.setId(m.getId());
            result.setTypeObject(2);
            result.setTitle(m.getName());
            result.setOverview(m.getOverview());
            result.setOriginalTitle(m.getOriginalName());
            result.setPosterPath(m.getPosterPath());
            result.setBackdropPath(m.getBackdropPath());
            result.setFavorite(m.getIsFavorite());

            search.add(result);
        }

        BaseResponse<FavsObjectItem> baseFavs = new BaseResponse<>();
        baseFavs.setResults(search);

        Log.e(TAG, "convertDataObject: " + baseFavs.toString());

        onSuccRetrieveData(baseFavs);
    }

    private void onSuccRetrieveData(BaseResponse<FavsObjectItem> models) {
        mView.hideLoading();
        mView.setData(models.getResults());
        Log.e(TAG, "onSuccRetrieveData: " + models.toString());
    }
}
