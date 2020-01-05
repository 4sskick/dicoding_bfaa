package com.niteroomcreation.basemade.data;

import android.content.Context;

import com.niteroomcreation.basemade.data.models.BaseResponse;
import com.niteroomcreation.basemade.data.models.Movies;
import com.niteroomcreation.basemade.data.models.TvShows;
import com.niteroomcreation.basemade.data.remote.APIService;
import com.niteroomcreation.basemade.data.remote.RemoteRepo;
import com.niteroomcreation.basemade.models.details.movie.MoviesDetail;
import com.niteroomcreation.basemade.models.details.tvshow.TvShowsDetail;

import io.reactivex.Flowable;
import retrofit2.Response;

/**
 * Created by Septian Adi Wijaya on 14/12/2019.
 * please be sure to add credential if you use people's code
 * <p>
 * this class gonna be central to control which resources (remote or local) gonna used when
 * presenter requesting data
 */
public class Repository implements RemoteRepo {

    private static Repository ref;

    private final RemoteRepo remoteRepo;

    public static Repository getInstance(Context context) {
        if (ref == null) {
            ref = new Repository(APIService.createService(context));
        }

        return ref;
    }

    private Repository(RemoteRepo remoteRepo) {
        this.remoteRepo = remoteRepo;
    }

    public Flowable<Response<BaseResponse<Movies>>> getMovies(String apiKey, String lang) {
        return remoteRepo.getMovies(apiKey, lang);
    }

    @Override
    public Flowable<Response<BaseResponse<TvShows>>> getTvShows(String apiKey, String lang) {
        return remoteRepo.getTvShows(apiKey, lang);
    }

    @Override
    public Flowable<Response<TvShowsDetail>> getTvShowsDetail(String tvId, String apiKey) {
        return remoteRepo.getTvShowsDetail(tvId, apiKey);
    }

    @Override
    public Flowable<Response<MoviesDetail>> getMoviesDetail(String movieId, String apiKey) {
        return remoteRepo.getMoviesDetail(movieId, apiKey);
    }
}
