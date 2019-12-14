package com.niteroomcreation.basemade.data.remote;

import com.niteroomcreation.basemade.BuildConfig;
import com.niteroomcreation.basemade.data.remote.http.NetworkInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIService {

    private static APIInterface api;

    public static APIInterface createService() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        //set logging level to NONE
        //so there is no log information while request
        //see: https://futurestud.io/blog/retrofit-2-log-requests-and-responses
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new NetworkInterceptor())
                .addInterceptor(httpLoggingInterceptor)
                .build();

        getApi(okHttpClient);

        return api;
    }

    private static void getApi(OkHttpClient okHttpClient) {
        if (api == null) {
            api = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()
                    .create(APIInterface.class);
        }

    }
}
