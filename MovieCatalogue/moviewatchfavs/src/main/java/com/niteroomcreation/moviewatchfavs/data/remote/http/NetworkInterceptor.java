package com.niteroomcreation.moviewatchfavs.data.remote.http;

import android.content.Context;

import com.niteroomcreation.moviewatchfavs.utils.Utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkInterceptor implements Interceptor {

    private Context mContext;

    public NetworkInterceptor(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        request = request.newBuilder()
                .addHeader("content-type", "application/json")
                /*.header("Cache-Control", Utils.isNetworkAvailable(mContext) ?
                        "public, max-age=" + 60 :
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)*/
                .method(request.method(), request.body())
                .build();

        return chain.proceed(request);
    }
}
