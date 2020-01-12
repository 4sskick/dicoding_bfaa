package com.niteroomcreation.basemade.data.remote;

import com.niteroomcreation.basemade.BuildConfig;
import com.niteroomcreation.basemade.data.models.BaseResponse;
import com.niteroomcreation.basemade.data.local.entity.MovieEntity;
import com.niteroomcreation.basemade.data.local.entity.TvEntity;
import com.niteroomcreation.basemade.models.details.movie.MoviesDetail;
import com.niteroomcreation.basemade.models.details.tvshow.TvShowsDetail;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RemoteRepo {

    @GET(BuildConfig.BASE_PATH + "movie")
//    Flowable<Response<BaseResponse<MovieEntity>>> getMovies(@Query("api_key") String apiKey
//            , @Query("language") String e")

    Flowable<BaseResponse<MovieEntity>> getMovies(@Query("api_key") String apiKey
//    Flowable</*Response<*/List<MovieEntity>>/*>*/ getMovies(@Query("api_key") String apiKey
            , @Query("language") String lang);

    @GET(BuildConfig.BASE_PATH + "tv")
//    Flowable<Response<BaseResponse<TvEntity>>> getTvShows(@Query("api_key") String apiKey
//            , @Query("language") String lang);

    Flowable</*Response<*/List<TvEntity>>/*>*/ getTvShows(@Query("api_key") String apiKey
            , @Query("language") String lang);

    @GET("3/tv/{tvId}")
    Flowable</*Response<*/TvShowsDetail>/*>*/ getTvShowsDetail(@Path("tvId") String tvId
            , @Query("api_key") String apiKey);

    @GET("3/movie/{movieId}")
    Flowable</*Response<*/MoviesDetail>/*>*/ getMoviesDetail(@Path("movieId") String movieId
            , @Query("api_key") String apiKey);
}
