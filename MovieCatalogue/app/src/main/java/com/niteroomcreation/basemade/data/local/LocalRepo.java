package com.niteroomcreation.basemade.data.local;

import android.content.Context;
import android.support.annotation.Nullable;

import com.niteroomcreation.basemade.data.EntertainmentDataSource;
import com.niteroomcreation.basemade.data.local.entity.MovieEntity;
import com.niteroomcreation.basemade.data.local.entity.TvEntity;
import com.niteroomcreation.basemade.data.models.BaseResponse;
import com.niteroomcreation.basemade.models.details.movie.MoviesDetail;
import com.niteroomcreation.basemade.models.details.tvshow.TvShowsDetail;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.Response;

/**
 * Created by Septian Adi Wijaya on 07/01/20
 */
public class LocalRepo implements EntertainmentDataSource {

    private static final String TAG = LocalRepo.class.getSimpleName();

    private static LocalRepo instance;
    private LocalDatabase mRoomDb;

    public static LocalRepo getInstance(Context context) {
        if (instance == null) {
            instance = new LocalRepo(context);
        }
        return instance;
    }

    private LocalRepo(Context context) {
        mRoomDb = LocalDatabase.getInstance(context);
    }

    public LocalDatabase getmRoomDb() {
        return mRoomDb;
    }

    @Override
    public Flowable<BaseResponse<MovieEntity>> getMovies(String apiKey, String lang) {

        BaseResponse<MovieEntity> a = new BaseResponse<>();
        a.setResults(getmRoomDb().movieDao().getMoviesByLang(lang));

        return Flowable.just(a);
    }

    @Override
    public Flowable<BaseResponse<TvEntity>> getTvShows(String apiKey, String lang) {

        BaseResponse<TvEntity> a = new BaseResponse<>();
        a.setResults(getmRoomDb().tvDao().getTvsByLang(lang));

        return Flowable.just(a);
    }

    @Override
    public Flowable<MoviesDetail> getTvShowsDetail(String tvId, String apiKey) {

        TvEntity tvEntity = getmRoomDb().tvDao().getTvById(Long.parseLong(tvId));
        MoviesDetail result = new MoviesDetail();
        if (tvEntity != null) {
            result.setGenres(tvEntity.getGenres());
            result.setBackdropPath(tvEntity.getBackdropPath());
            result.setId(tvEntity.getId());
            result.setOverview(tvEntity.getOverview());
            result.setOriginalTitle(tvEntity.getOriginalName());
            result.setPosterPath(tvEntity.getPosterPath());
            result.setReleaseDate(tvEntity.getFirstAirDate());
            result.setFavorite(tvEntity.getIsFavorite());
        }

        return Flowable.just(result);
    }

    @Override
    public Flowable<MoviesDetail> getMoviesDetail(String movieId, String apiKey) {

        MovieEntity movieEntity = getmRoomDb().movieDao().getMovieById(Long.parseLong(movieId));
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

        return Flowable.just(result);
    }
}
