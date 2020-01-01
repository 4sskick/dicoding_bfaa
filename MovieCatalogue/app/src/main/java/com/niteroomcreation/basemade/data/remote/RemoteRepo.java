package com.niteroomcreation.basemade.data.remote;

import com.niteroomcreation.basemade.BuildConfig;
import com.niteroomcreation.basemade.data.models.BaseResponse;
import com.niteroomcreation.basemade.data.models.Movies;
import com.niteroomcreation.basemade.data.models.TvShows;
import com.niteroomcreation.basemade.models.details.movie.MoviesDetail;
import com.niteroomcreation.basemade.models.details.tvshow.TvShowsDetail;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RemoteRepo {

    @GET(BuildConfig.BASE_PATH + "movie")
    Flowable<Response<BaseResponse<Movies>>> getMovies(@Query("api_key") String apiKey
            , @Query("language") String lang);

    @GET(BuildConfig.BASE_PATH + "tv")
    Flowable<Response<BaseResponse<TvShows>>> getTvShows(@Query("api_key") String apiKey
            , @Query("language") String lang);

    @GET("3/tv/{tvId}")
    Flowable<Response<TvShowsDetail>> getTvShowsDetail(@Path("tvId") String tvId
            , @Query("api_key") String apiKey);

    @GET("3/movie/{movieId}")
    Flowable<Response<MoviesDetail>> getMoviesDetail(@Path("movieId") String movieId
            , @Query("api_key") String apiKey);
}
