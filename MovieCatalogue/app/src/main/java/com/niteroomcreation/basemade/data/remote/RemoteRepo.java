package com.niteroomcreation.basemade.data.remote;

import com.niteroomcreation.basemade.BuildConfig;
import com.niteroomcreation.basemade.data.models.BaseResponse;
import com.niteroomcreation.basemade.data.models.Movies;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RemoteRepo {

    @GET(BuildConfig.BASE_PATH + "movie")
    Flowable<Response<BaseResponse<Movies>>> getMovies(@Query("api_key") String apiKey
            , @Query("language") String lang);
}
