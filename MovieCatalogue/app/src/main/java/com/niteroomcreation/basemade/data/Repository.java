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
import com.niteroomcreation.basemade.utils.Utils;

import java.util.List;

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
    public Flowable<BaseResponse<MovieEntity>> getMovies(String apiKey, String lang) {
        return Utils.isNetworkAvailable(context) ?
                remoteRepo.getMovies(apiKey, lang) : localRepo.getMovies(apiKey, lang);
    }

    @Override
    public Flowable<BaseResponse<TvEntity>> getTvShows(String apiKey, String lang) {
        return Utils.isNetworkAvailable(context) ?
                remoteRepo.getTvShows(apiKey, lang) : localRepo.getTvShows(apiKey, lang);
    }

    @Override
    public Flowable<MoviesDetail> getTvShowsDetail(String tvId, String apiKey) {
        return Utils.isNetworkAvailable(context) ?
                remoteRepo.getTvShowsDetail(tvId, apiKey) : localRepo.getTvShowsDetail(tvId, apiKey);
    }

    @Override
    public Flowable<MoviesDetail> getMoviesDetail(String movieId, String apiKey) {
        return Utils.isNetworkAvailable(context) ?
                remoteRepo.getMoviesDetail(movieId, apiKey) : localRepo.getMoviesDetail(movieId, apiKey);
    }
}
