package com.niteroomcreation.basemade.data;

import android.content.Context;

import com.niteroomcreation.basemade.data.models.BaseResponse;
import com.niteroomcreation.basemade.data.models.Movies;
import com.niteroomcreation.basemade.data.remote.APIService;
import com.niteroomcreation.basemade.data.remote.RemoteRepo;

import io.reactivex.Flowable;
import retrofit2.Response;

/**
 * Created by Septian Adi Wijaya on 14/12/2019.
 * please be sure to add credential if you use people's code
 * <p>
 * this class gonna be central to control which resources (remote or local) gonna used when
 * presenter requesting data
 */
public class Repository {

    private static Repository ref;

    private final RemoteRepo remoteRepo;

    public static Repository getInstance(Context context) {
        if (ref == null) {
            ref = new Repository(APIService.createService());
        }

        return ref;
    }

    private Repository(RemoteRepo remoteRepo) {
        this.remoteRepo = remoteRepo;
    }

    public Flowable<Response<BaseResponse<Movies>>> getMovies(String apiKey, String lang) {
        return remoteRepo.getMovies(apiKey, lang);
    }

}
