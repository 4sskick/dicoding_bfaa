package com.niteroomcreation.basemade.data.remote.http;

import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Created by Septian Adi Wijaya on 14/12/2019.
 * please be sure to add credential if you use people's code
 */
public abstract class NetworkCallback<T> extends DisposableSubscriber<Response<T>> {

    private static final String TAG = NetworkCallback.class.getSimpleName();

    public abstract void onSuccess(T model);

    public abstract void onFailure(int code, String message, @Nullable JSONObject jsonObject);

    public abstract void onFinish(boolean isFailure);

    private boolean isFailure = false;

    public String extractMessageError(String message) {
        try {
            StringBuilder extracted = new StringBuilder();
            JSONObject jsonObject = new JSONObject(message);
            Iterator<?> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = String.valueOf(keys.next());
                if (extracted.length() > 0)
                    extracted.append("\n");

                if (jsonObject.get(key) instanceof JSONObject)
                    extracted.append(jsonObject.getString(key));
                else if (jsonObject.get(key) instanceof JSONArray)
                    for (int i = 0; i < jsonObject.getJSONArray(key).length(); i++) {
                        extracted.append(jsonObject.getJSONArray(key).getString(i));
                    }
            }
            return extracted.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return message;
        }
    }

    @Override
    public void onNext(Response<T> modelRespond) {
        if (modelRespond.isSuccessful()) {//for respond code 2xx - 3xx
            onSuccess(modelRespond.body());
        } else {//for respond code 4xx -5xx
            isFailure = true;
            try {
                //todo 10/07/19: consider to add crashlytics report for 5xx code http error

                String result = modelRespond.errorBody() != null ?
                        modelRespond.errorBody().string() : null;
                onFailure(modelRespond.code(), extractMessageError(result), new JSONObject(result));
            } catch (Exception e) {
                e.printStackTrace();
                onFailure(modelRespond.code(), "ERROR SERVER", null);
            }
        }
    }

    @Override
    public void onError(Throwable t) {
        isFailure = true;
        t.printStackTrace();
        if (t instanceof HttpException) {
            HttpException e = (HttpException) t;
            int code = e.code();
            String errorMessage = e.getMessage();

            Log.e(TAG, "onError: code " + code);

            onFailure(code, errorMessage, null);
        } else {
            onFailure(-1, t.getMessage(), null);
        }
    }

    @Override
    public void onComplete() {
        onFinish(isFailure);
    }
}
