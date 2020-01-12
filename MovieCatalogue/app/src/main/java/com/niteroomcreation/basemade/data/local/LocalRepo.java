package com.niteroomcreation.basemade.data.local;

import android.content.Context;
import android.support.annotation.Nullable;

import com.niteroomcreation.basemade.data.EntertainmentDataSource;
import com.niteroomcreation.basemade.data.local.entity.MovieEntity;
import com.niteroomcreation.basemade.data.local.entity.TvEntity;
import com.niteroomcreation.basemade.data.models.BaseResponse;
import com.niteroomcreation.basemade.models.details.movie.MoviesDetail;
import com.niteroomcreation.basemade.models.details.tvshow.TvShowsDetail;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import retrofit2.Response;

/**
 * Created by Septian Adi Wijaya on 07/01/20
 */
public class LocalRepo implements EntertainmentDataSource {

    private static LocalRepo instance;
    private LocalDatabase mRoomDb;

    public static LocalRepo getInstance(Context context) {
        if (instance == null) {
            instance = new LocalRepo(context);
        }
        return instance;
    }

    private LocalRepo(Context context) {
        mRoomDb = LocalDatabase.getInstance(context);
    }

    public LocalDatabase getmRoomDb() {
        return mRoomDb;
    }

    @Override
    public Flowable<BaseResponse<MovieEntity>> getMovies(@Nullable String apiKey, @Nullable String lang) {

        BaseResponse<MovieEntity> a = new BaseResponse<>();

        List<MovieEntity> ar = new ArrayList<>();
        ar.add(mRoomDb.movieDao().getMoviesByPage(1L));

        a.setResults(ar);

        return Flowable.just(a);
    }

    @Override
    public Flowable</*Response<*//*BaseResponse*/List<TvEntity>>/*>*/ getTvShows(@Nullable String apiKey, @Nullable String lang) {
        return null;
    }

    @Override
    public Flowable</*Response<*/TvShowsDetail>/*>*/ getTvShowsDetail(@Nullable String apiKey, @Nullable String lang) {
        return null;
    }

    @Override
    public Flowable</*Response<*/MoviesDetail>/*>*/ getMoviesDetail(@Nullable String apiKey, @Nullable String lang) {
        return null;
    }
}
