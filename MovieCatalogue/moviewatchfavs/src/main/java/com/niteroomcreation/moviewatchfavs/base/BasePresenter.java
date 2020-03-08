package com.niteroomcreation.moviewatchfavs.base;

import android.content.Context;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by Septian Adi Wijaya on 03/09/19
 */
public class BasePresenter<ViewT> implements IBasePresenter<ViewT> {

    protected ViewT mView;
    protected Context mContext;

    private CompositeDisposable compositeDisposable;
    private DisposableSubscriber disposableSubscriber;

    @Override
    public void onViewActive(ViewT view, Context context) {
        this.mView = view;
        this.mContext = context;
    }

    @Override
    public void onViewInactive() {
        onUnsubscribe();
        this.mView = null;
        this.mContext = null;
    }

    public void onUnsubscribe() {
        if (compositeDisposable != null && compositeDisposable.size() > 0) {
            if (disposableSubscriber != null)
                disposableSubscriber.dispose();
            compositeDisposable.dispose();
        }
    }

    protected <T> void addSubscribe(Flowable<T> flowable
            , DisposableSubscriber<T> disposableSubscriber) {
        this.disposableSubscriber = disposableSubscriber;
        if (compositeDisposable == null)
            compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(disposableSubscriber));
    }
}
