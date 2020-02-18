package com.niteroomcreation.moviewatchfavs.data;

import com.niteroomcreation.moviewatchfavs.data.local.entity.MovieEntity;
import com.niteroomcreation.moviewatchfavs.data.local.entity.TvEntity;
import com.niteroomcreation.moviewatchfavs.data.models.BaseResponse;
import com.niteroomcreation.moviewatchfavs.models.details.movie.MoviesDetail;

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
}
