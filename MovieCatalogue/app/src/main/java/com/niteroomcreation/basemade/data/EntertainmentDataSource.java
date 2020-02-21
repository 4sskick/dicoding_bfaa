package com.niteroomcreation.basemade.data;

import com.niteroomcreation.basemade.data.local.entity.MovieEntity;
import com.niteroomcreation.basemade.data.local.entity.TvEntity;
import com.niteroomcreation.basemade.data.models.BaseResponse;
import com.niteroomcreation.basemade.models.details.movie.MoviesDetail;

import io.reactivex.Flowable;

/**
 * Created by Septian Adi Wijaya on 08/01/20
 */
public interface EntertainmentDataSource {

    Flowable<BaseResponse<MovieEntity>> getMovies(String apiKey, String lang);

    Flowable<BaseResponse<TvEntity>> getTvShows(String apiKey
            , String lang);

    Flowable<MoviesDetail> getTvShowsDetail(String tvId
            , String apiKey);

    Flowable<MoviesDetail> getMoviesDetail(String movieId
            , String apiKey);

    Flowable<BaseResponse<MovieEntity>> getOnQueryMovies(String apiKey
            , String lang
            , String onQuery);

    Flowable<BaseResponse<TvEntity>> getOnQueryTvShows(String apiKey
            , String lang
            , String onQuery);

    Flowable<BaseResponse<MovieEntity>> getMoviesOnDate(String apiKey
            , String lang
            , String date);
}
