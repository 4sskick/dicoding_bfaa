package com.niteroomcreation.basemade.data;

import android.content.Context;

import com.niteroomcreation.basemade.data.local.LocalRepo;
import com.niteroomcreation.basemade.data.models.BaseResponse;
import com.niteroomcreation.basemade.data.local.entity.MovieEntity;
import com.niteroomcreation.basemade.data.local.entity.TvEntity;
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
 * see: https://github.com/GulajavaMinistudio/news-reader-clean.git
 */
public class Repository implements RemoteRepo {

    private static Repository ref;

    private final RemoteRepo remoteRepo;
    private final LocalRepo localRepo;

    public static Repository getInstance(Context context) {
        if (ref == null) {
            ref = new Repository(APIService.createService(context), LocalRepo.getInstance(context));
        }

        return ref;
    }

    private Repository(RemoteRepo remoteRepo, LocalRepo localRepo) {
        this.remoteRepo = remoteRepo;
        this.localRepo = localRepo;
    }

    @Override
    public Flowable<Response<BaseResponse<MovieEntity>>> getMovies(String apiKey, String lang) {
        return remoteRepo.getMovies(apiKey, lang);
    }

    @Override
    public Flowable<Response<BaseResponse<TvEntity>>> getTvShows(String apiKey, String lang) {
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
