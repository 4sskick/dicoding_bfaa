package com.niteroomcreation.basemade.data.remote.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("content-type", "application/json")
                .method(request.method(), request.body())
                .build();

        Response response = chain.proceed(request);

        return response;
    }
}
