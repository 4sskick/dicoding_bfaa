package com.niteroomcreation.basemade.data;

import android.content.Context;

import com.niteroomcreation.basemade.BuildConfig;
import com.niteroomcreation.basemade.data.local.LocalRepo;
import com.niteroomcreation.basemade.data.local.entity.MovieEntity;
import com.niteroomcreation.basemade.data.local.entity.TvEntity;
import com.niteroomcreation.basemade.data.models.BaseResponse;
import com.niteroomcreation.basemade.data.remote.APIService;
import com.niteroomcreation.basemade.data.remote.RemoteRepo;
import com.niteroomcreation.basemade.models.details.movie.MoviesDetail;
import com.niteroomcreation.basemade.utils.Utils;

import io.reactivex.Flowable;

/**
 * Created by Septian Adi Wijaya on 14/12/2019.
 * please be sure to add credential if you use people's code
 * <p>
 * this class gonna be central to control which resources (remote or local) gonna used when
 * presenter requesting data
 * see: https://github.com/GulajavaMinistudio/news-reader-clean.git
 */
public class Repository implements EntertainmentDataSource {

    private static Repository ref;

    private final RemoteRepo remoteRepo;
    private final LocalRepo localRepo;

    private Context context;

    public static Repository getInstance(Context context) {
        if (ref == null || context == null) {
            ref = new Repository(context
                    , APIService.createService(context)
                    , LocalRepo.getInstance(context));
        }

        return ref;
    }

    public LocalRepo getLocalRepo() {
        return localRepo;
    }

    private Repository(Context context, RemoteRepo remoteRepo, LocalRepo localRepo) {
        this.context = context;
        this.remoteRepo = remoteRepo;
        this.localRepo = localRepo;
    }

    @Override
    public Flowable<BaseResponse<MovieEntity>> getMovies(String lang) {
        return Utils.isNetworkAvailable(context) ?
                remoteRepo.getMovies(BuildConfig.API_KEY, lang) : localRepo.getMovies(lang);
    }

    @Override
    public Flowable<BaseResponse<TvEntity>> getTvShows(String lang) {
        return Utils.isNetworkAvailable(context) ?
                remoteRepo.getTvShows(BuildConfig.API_KEY, lang) : localRepo.getTvShows(lang);
    }

    @Override
    public Flowable<MoviesDetail> getTvShowsDetail(String tvId) {
        return Utils.isNetworkAvailable(context) ?
                remoteRepo.getTvShowsDetail(tvId, BuildConfig.API_KEY) : localRepo.getTvShowsDetail(tvId);
    }

    @Override
    public Flowable<MoviesDetail> getMoviesDetail(String movieId) {
        return Utils.isNetworkAvailable(context) ?
                remoteRepo.getMoviesDetail(movieId, BuildConfig.API_KEY) : localRepo.getMoviesDetail(movieId);
    }

    @Override
    public Flowable<BaseResponse<MovieEntity>> getOnQueryMovies(String lang, String onQuery) {
        return Utils.isNetworkAvailable(context) ?
                remoteRepo.getOnQueryMovies(BuildConfig.API_KEY, lang, onQuery) :
                localRepo.getOnQueryMovies(lang, onQuery);
    }

    @Override
    public Flowable<BaseResponse<TvEntity>> getOnQueryTvShows(String lang, String onQuery) {

        return Utils.isNetworkAvailable(context) ?
                remoteRepo.getOnQueryTvShows(BuildConfig.API_KEY, lang, onQuery) :
                localRepo.getOnQueryTvShows(lang, onQuery);
    }

    @Override
    public Flowable<BaseResponse<MovieEntity>> getMoviesOnDate(String lang, String date) {

        return remoteRepo.getMoviesOnDate(BuildConfig.API_KEY, lang, date, date);
    }
}
