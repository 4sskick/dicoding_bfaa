package com.niteroomcreation.basemade.ui.fragment.favourite;

import android.content.Context;
import android.support.annotation.MainThread;
import android.util.Log;

import com.niteroomcreation.basemade.base.BasePresenter;
import com.niteroomcreation.basemade.data.local.entity.MovieEntity;
import com.niteroomcreation.basemade.data.local.entity.TvEntity;
import com.niteroomcreation.basemade.data.models.BaseResponse;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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

        //zip query
        Flowable flowMovie = Flowable.just(getLocalData().movieDao().getFavMovies());
        Flowable flowTvs = Flowable.just(getLocalData().tvDao().getFavsTv());

        flowMovie.concatMap(new Function() {
            @Override
            public Object apply(Object o) throws Exception {
                Log.e(TAG, "apply: " + o.toString());

                return flowTvs;
            }
        }).subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Log.e(TAG, "accept: " + o.toString());
            }
        });


        Flowable.merge(flowMovie, flowTvs)
                .subscribe(new FlowableSubscriber() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.e(TAG, "onSubscribe: " + s.toString());
                    }

                    @Override
                    public void onNext(Object o) {
                        Log.e(TAG, "onNext: " + o.toString());
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError: " + t.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: merge");
                    }
                });

        BaseResponse<MovieEntity> a = new BaseResponse<>();
        a.setResults(getLocalData().movieDao().getFavMovies());


        Observable obMovies = Observable.just(a);

        BaseResponse<TvEntity> b = new BaseResponse<>();
        b.setResults(getLocalData().tvDao().getFavsTv());

        Observable obTvs = Observable.just(b);

        Observable.zip(obMovies, obTvs, new BiFunction<List<MovieEntity>, List<TvEntity>,
                List<String>>() {
            @Override
            public List<String> apply(List<MovieEntity> movieEntities, List<TvEntity> tvEntities) throws Exception {

                List<String> a = new ArrayList<>();

                for (int i = 0; i < movieEntities.size(); i++) {
                    a.add("MOVIE - " + movieEntities.get(i).getTitle());
                }

                for (int i = 0; i < tvEntities.size(); i++) {
                    a.add("TV SHOW - " + tvEntities.get(i).getName());
                }

                return a;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(Object o) {
                        Log.e(TAG, "onNext: " + o.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: ");
                    }
                });


        onSuccMovies(a, lang, false);
    }

    private void onSuccMovies(BaseResponse<MovieEntity> models, String lang, boolean isNetwork) {
        mView.setData(models.getResults());
        mView.hideLoading();
    }
}
