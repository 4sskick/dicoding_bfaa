package com.niteroomcreation.basemade.ui.fragment.tv_show;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.niteroomcreation.basemade.BuildConfig;
import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BasePresenter;
import com.niteroomcreation.basemade.data.Repository;
import com.niteroomcreation.basemade.data.models.BaseResponse;
import com.niteroomcreation.basemade.data.models.TvShows;
import com.niteroomcreation.basemade.data.remote.http.NetwokCallback;
import com.niteroomcreation.basemade.models.TvShowModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Septian Adi Wijaya on 15/11/19
 */
public class TvShowPresenter extends BasePresenter<TvShowContract.View> implements TvShowContract.Presenter {

    private static final String TAG = TvShowPresenter.class.getSimpleName();

    public TvShowPresenter(TvShowContract.View view, Context context) {
        super.onViewActive(view, context);
    }

    @Override
    public void getTvShows(String lang) {
        mView.showLoading();

        addSubscribe(Repository.getInstance(mContext).getTvShows(BuildConfig.API_KEY, lang)
                , new NetwokCallback<BaseResponse<TvShows>>() {
                    @Override
                    public void onSuccess(BaseResponse<TvShows> model) {
                        Log.e(TAG, "onSuccess: " + model.toString());

                        mView.setData(model.getResults());
                    }

                    @Override
                    public void onFailure(int code, String message,
                                          @Nullable JSONObject jsonObject) {
                        Log.e(TAG, String.format("onFailure: code %s message %s jsonObj %s", code,
                                message, jsonObject != null ? jsonObject.toString() : "{}"));

                        mView.showMessage(String.format("code %s, %s", code, message));
                    }

                    @Override
                    public void onFinish(boolean isFailure) {
                        if (isFailure)
                            onFailure(0, "Something not right", null);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public List<TvShowModel> constructModels() {
        List<TvShowModel> a = new ArrayList<>();

        a.add(new TvShowModel("Titans", mContext.getString(R.string.desc_titans),
                R.drawable.poster_tv_titan, 2018, 76, null));
        a.add(new TvShowModel("Legacies", mContext.getString(R.string.desc_legacies),
                R.drawable.poster_tv_legacies, 2018, 78, null));
        a.add(new TvShowModel("9-1-1", mContext.getString(R.string.desc_911)
                , R.drawable.poster_tv_911, 2018, 72, null));
        a.add(new TvShowModel("Tom Clancy's Jack Ryan",
                mContext.getString(R.string.desc_tom_clancy),
                R.drawable.poster_tv_tom_clancy, 2018, 75, null));
        a.add(new TvShowModel("The Resident", mContext.getString(R.string.desc_the_resident),
                R.drawable.poster_tv_the_resident, 2018, 78, null));
        a.add(new TvShowModel("Castle Rock", mContext.getString(R.string.desc_castle_rock),
                R.drawable.poster_tv_castle_rock, 2018, 69, null));
        a.add(new TvShowModel("The Rookie", mContext.getString(R.string.desc_the_rookie),
                R.drawable.poster_tv_the_rookie, 2018, 71, null));
        a.add(new TvShowModel("FBI", mContext.getString(R.string.desc_fbi),
                R.drawable.poster_tv_fbi, 2018, 68, null));
        a.add(new TvShowModel("Krypton",
                mContext.getString(R.string.desc_krypton), R.drawable.poster_tv_krypton, 2018, 68
                , null));
        a.add(new TvShowModel("Britannia",
                mContext.getString(R.string.desc_britannia), R.drawable.poster_tv_britannia, 2018
                , 71, null));
        return a;
    }
}
