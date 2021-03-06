package com.niteroomcreation.basemade.data.remote;

import com.niteroomcreation.basemade.BuildConfig;
import com.niteroomcreation.basemade.data.local.entity.MovieEntity;
import com.niteroomcreation.basemade.data.local.entity.TvEntity;
import com.niteroomcreation.basemade.data.models.BaseResponse;
import com.niteroomcreation.basemade.models.details.movie.MoviesDetail;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RemoteRepo {

    @GET(BuildConfig.BASE_PATH + "movie")
    Flowable<BaseResponse<MovieEntity>> getMovies(@Query("api_key") String apiKey
            , @Query("language") String lang);

    @GET(BuildConfig.BASE_PATH + "tv")
    Flowable<BaseResponse<TvEntity>> getTvShows(@Query("api_key") String apiKey
            , @Query("language") String lang);

    @GET("3/tv/{tvId}")
    Flowable<MoviesDetail> getTvShowsDetail(@Path("tvId") String tvId
            , @Query("api_key") String apiKey);

    @GET("3/movie/{movieId}")
    Flowable<MoviesDetail> getMoviesDetail(@Path("movieId") String movieId
            , @Query("api_key") String apiKey);

    @GET("3/search/movie")
    Flowable<BaseResponse<MovieEntity>> getOnQueryMovies(@Query("api_key") String apiKey
            , @Query("language") String lang
            , @Query("query") String query);

    @GET("3/search/tv")
    Flowable<BaseResponse<TvEntity>> getOnQueryTvShows(@Query("api_key") String apiKey
            , @Query("language") String lang
            , @Query("query") String query);

    @GET("3/discover/movie")
    Flowable<BaseResponse<MovieEntity>> getMoviesOnDate(@Query("api_key") String apiKey
            , @Query("language") String lang
            , @Query("primary_release_date.gte") String dateGte
            , @Query("primary_release_date.lte") String dateLte);
}
