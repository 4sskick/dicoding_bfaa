package com.niteroomcreation.basemade.ui.fragment.favourite;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.niteroomcreation.basemade.base.BasePresenter;
import com.niteroomcreation.basemade.data.local.entity.MovieEntity;
import com.niteroomcreation.basemade.data.local.entity.TvEntity;
import com.niteroomcreation.basemade.data.models.BaseResponse;
import com.niteroomcreation.basemade.models.FavsObjectItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Septian Adi Wijaya on 25/01/2020.
 * please be sure to add credential if you use people's code
 */
public class FavPresenter extends BasePresenter<FavContract.View> implements FavContract.Presenter {

    private static final String TAG = FavPresenter.class.getSimpleName();

    public FavPresenter(FavContract.View view, Context context) {
        super.onViewActive(view, context);
    }

    @Override
    public void getFavs(String lang) {
        mView.showLoading();

        //convert first the values query into FavsObjectItem.class
        List<MovieEntity> movies = getLocalData().movieDao().getFavMovies();
        List<TvEntity> tvs = getLocalData().tvDao().getFavsTv();
        List<FavsObjectItem> favs = new ArrayList<>();

        for (MovieEntity m : movies) {
            FavsObjectItem result = new FavsObjectItem();
            result.setId(m.getId());
            result.setTypeObject(1);
            result.setTitle(m.getTitle());
            result.setOverview(m.getOverview());
            result.setOriginalTitle(m.getOriginalTitle());
            result.setPosterPath(m.getPosterPath());
            result.setBackdropPath(m.getBackdropPath());
            result.setFavorite(m.getIsFavorite());

            favs.add(result);
        }

        for (TvEntity m : tvs) {
            FavsObjectItem result = new FavsObjectItem();
            result.setId(m.getId());
            result.setTypeObject(2);
            result.setTitle(m.getName());
            result.setOverview(m.getOverview());
            result.setOriginalTitle(m.getOriginalName());
            result.setPosterPath(m.getPosterPath());
            result.setBackdropPath(m.getBackdropPath());
            result.setFavorite(m.getIsFavorite());

            favs.add(result);
        }

        BaseResponse<FavsObjectItem> baseFavs = new BaseResponse<>();
        baseFavs.setResults(favs);

        Log.e(TAG, "getFavs: " + baseFavs.toString());

        onSuccMovies(baseFavs);

    }

    private void onSuccMovies(BaseResponse<FavsObjectItem> models) {
        mView.setData(models.getResults());
        mView.hideLoading();
    }

    public Object convertToEntity(long itemId){
        MovieEntity m = getLocalData().movieDao().getMovieById(itemId);
        TvEntity t = getLocalData().tvDao().getTvById(itemId);

        if(m!= null)
            return m;

        return t;
    }
}
