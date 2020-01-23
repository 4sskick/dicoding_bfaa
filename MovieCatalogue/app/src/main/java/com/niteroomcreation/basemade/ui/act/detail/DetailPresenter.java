package com.niteroomcreation.basemade.ui.act.detail;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.niteroomcreation.basemade.BuildConfig;
import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BasePresenter;
import com.niteroomcreation.basemade.data.Repository;
import com.niteroomcreation.basemade.data.local.entity.MovieEntity;
import com.niteroomcreation.basemade.data.local.entity.TvEntity;
import com.niteroomcreation.basemade.data.remote.http.NetworkCallback;
import com.niteroomcreation.basemade.models.details.Genre;
import com.niteroomcreation.basemade.models.details.movie.MoviesDetail;
import com.niteroomcreation.basemade.models.details.tvshow.TvShowsDetail;
import com.niteroomcreation.basemade.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.subscribers.DisposableSubscriber;

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

        MovieEntity entity = getLocalData().movieDao().getMovieById(Long.parseLong(movieId));

        if (entity.getGenres() == null) {
            addSubscribe(Repository.getInstance(mContext).getMoviesDetail(movieId, BuildConfig
                            .API_KEY)
                    , new NetworkCallback<MoviesDetail>() {
                        @Override
                        public void onSuccess(MoviesDetail model) {
                            Log.e(TAG, "onSuccess: " + model.toString());

                            onSuccMovieDetail(model, movieId, true);
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
        } else {

            MovieEntity movieEntity = getLocalData().movieDao().getMovieById(Long.parseLong(movieId));
            MoviesDetail result = new MoviesDetail();
            if (movieEntity != null) {
                result.setGenres(movieEntity.getGenres());
                result.setAdult(movieEntity.isAdult());
                result.setBackdropPath(movieEntity.getBackdropPath());
                result.setId(movieEntity.getId());
                result.setOverview(movieEntity.getOverview());
                result.setOriginalTitle(movieEntity.getOriginalTitle());
                result.setPosterPath(movieEntity.getPosterPath());
                result.setReleaseDate(movieEntity.getReleaseDate());
                result.setFavorite(movieEntity.getIsFavorite());
            }

            onSuccMovieDetail(result, movieId, false);
        }
    }

    private void onSuccMovieDetail(MoviesDetail model, String movieId, boolean isNetwork) {

        if (isNetwork) {

            //get entity by ID
            MovieEntity entity = getLocalData().movieDao().getMovieById(Long.parseLong(movieId));
            entity.setGenres(model.getGenres());

            getLocalData().movieDao().updateMovie(entity);
        }

        List<String> genres = new ArrayList<>();
        if (model.getGenres() != null)
            for (Object obj : model.getGenres()) {
                if (obj instanceof String) {
                    genres.add(Objects.toString(obj, null));
                } else {
                    genres.add(String.valueOf(((Genre) obj).getName()));
                }
            }

        mView.setupSavedFav(model.isFavorite());
        mView.setupGenre(genres);
    }

    @Override
    public void saveMovieFav(long movieId, boolean isSaved) {
        MovieEntity movieEntity = getLocalData().movieDao().getMovieById(movieId);

        if (movieEntity != null) {
            Log.e(TAG, "saveMovieFav: not null loh " + movieEntity.toString());

            movieEntity.setIsFavorite(isSaved);
            Log.e(TAG,
                    "saveMovieFav: UPDATE " + getLocalData().movieDao().updateMovie(movieEntity));
        } else {
            Log.e(TAG, "saveMovieFav: null loh");

            mView.showErrorMessage("Entity not found", R.string.app_name);
        }
    }

    @Override
    public void saveTvFav(long tvId, boolean isSaved) {
        TvEntity tvEntity = getLocalData().tvDao().getTvById(tvId);

        if (tvEntity != null) {
            Log.e(TAG, "saveTvFav: not null loh " + tvEntity.toString());

            tvEntity.setIsFavorite(isSaved);
            Log.e(TAG, "saveMovieFav: UPDATE " + getLocalData().tvDao().updateTv(tvEntity));
        } else {
            Log.e(TAG, "saveTvFav: null loh");

            mView.showErrorMessage("Entity not found", R.string.app_name);
        }
    }

    @Override
    public void getTvShowDetail(String tvId) {
        Log.e(TAG, "getTvShowDetail: " + tvId);
        addSubscribe(Repository.getInstance(mContext).getTvShowsDetail(tvId, BuildConfig.API_KEY)
                , new NetworkCallback<MoviesDetail>() {
                    @Override
                    public void onSuccess(MoviesDetail model) {
                        Log.e(TAG, "onSuccess: " + model.toString());

                        List<String> genres = new ArrayList<>();
                        for (Object obj : model.getGenres()) {
                            if (obj instanceof String) {
                                genres.add(Objects.toString(obj, null));
                            } else
                                genres.add(String.valueOf(((Genre) obj).getName()));
                        }

                        mView.setupGenre(genres);
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
