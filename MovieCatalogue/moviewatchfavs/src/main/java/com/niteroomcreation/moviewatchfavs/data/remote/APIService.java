package com.niteroomcreation.moviewatchfavs.data.remote;

import android.content.Context;
import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.niteroomcreation.moviewatchfavs.BuildConfig;
import com.niteroomcreation.moviewatchfavs.data.remote.http.NetworkInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIService {

    private static final String TAG = APIService.class.getSimpleName();

    private static RemoteRepo api;

    public static RemoteRepo createService(Context mContext) {
        HttpLoggingInterceptor httpLogging = new HttpLoggingInterceptor();
        //set logging level to NONE
        //so there is no log information while request
        //see: https://futurestud.io/blog/retrofit-2-log-requests-and-responses
        httpLogging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

        okHttpClient.cache(buildCache(mContext));
        okHttpClient.addInterceptor(new NetworkInterceptor(mContext));
        okHttpClient.addInterceptor(httpLogging);
        okHttpClient.addNetworkInterceptor(new StethoInterceptor());
        okHttpClient.connectTimeout(30, TimeUnit.SECONDS);
        okHttpClient.readTimeout(30, TimeUnit.SECONDS);

        getApi(okHttpClient.build());

        return api;
    }

    private static Cache buildCache(Context mContext) {
        long cacheSize = 10 * 1024 * 1024; //10 MB
        File httpCacheDir = null;
        try {
            httpCacheDir = new File(mContext.getCacheDir(), "http-cache");
        } catch (Exception e) {
            Log.e(TAG, "buildCache: couldn't create directory cache of HTTP");
            e.printStackTrace();
        }
        return new Cache(httpCacheDir, cacheSize);
    }

    private static void getApi(OkHttpClient okHttpClient) {
        if (api == null) {
            api = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()
                    .create(RemoteRepo.class);
        }

    }
}
