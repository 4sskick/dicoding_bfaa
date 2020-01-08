package com.niteroomcreation.basemade.data;

import com.niteroomcreation.basemade.data.local.entity.MovieEntity;
import com.niteroomcreation.basemade.data.local.entity.TvEntity;
import com.niteroomcreation.basemade.data.models.BaseResponse;
import com.niteroomcreation.basemade.models.details.movie.MoviesDetail;
import com.niteroomcreation.basemade.models.details.tvshow.TvShowsDetail;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.Response;

/**
 * Created by Septian Adi Wijaya on 08/01/20
 */
public interface EntertainmentDataSource {

    //    Flowable<Response<BaseResponse<MovieEntity>>> getMovies(String apiKey, String lang);
    Flowable</*Response<*/List<MovieEntity>>/*>*/ getMovies(String apiKey, String lang);

    //    Flowable<Response<BaseResponse<TvEntity>>> getTvShows(String apiKey
    Flowable</*Response<*/List<TvEntity>>/*>*/ getTvShows(String apiKey
            , String lang);

    Flowable</*Response<*/TvShowsDetail>/*>*/ getTvShowsDetail(String tvId
            , String apiKey);

    Flowable</*Response<*/MoviesDetail>/*>*/ getMoviesDetail(String movieId
            , String apiKey);
}
