package com.niteroomcreation.workmanager.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONObject;

import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;

public class WorkerUtils extends Worker {

    public static final String TAG = WorkerUtils.class.getSimpleName();
    public static final String APP_ID = "72d89f872ece775826e1965684fe382c";
    public static final String EXTRA_CITY = "city";

    private Result resultWorker;

    public WorkerUtils(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String dataCity = getInputData().getString(EXTRA_CITY);
        Result status = getCurrentWeather(dataCity);

        return status;
    }

    private Result getCurrentWeather(final String city) {
        Log.e(TAG, "getCurrentWeather: city " + city);

        SyncHttpClient client = new SyncHttpClient();
        String url =
                "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + APP_ID;

        Log.e(TAG, "getCurrentWeather: url " + url);

        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String respond = new String(responseBody);

                Log.e(TAG, "onSuccess: respond " + respond);
                try {
                    JSONObject respObj = new JSONObject(respond);

                    String weatherCurrent =
                            respObj.getJSONArray("weather").getJSONObject(0).getString("main");
                    String weatherDesc =
                            respObj.getJSONArray("weather").getJSONObject(0).getString(
                                    "description");
                    double weatherKelvin = respObj.getJSONObject("main").getDouble("temp");
                    double weatherCelcius = weatherKelvin - 273;

                    String temperature = new DecimalFormat("##.##").format(weatherCelcius);

                    NotificationUtils notif =
                            NotificationUtils.getInstance(getApplicationContext());
                    notif.showNotification(String.format("current weather in %s", city),
                            String.format("%s, %s with %s celcius", weatherCurrent, weatherDesc,
                                    temperature));

                    resultWorker = Result.success();

                } catch (Exception e) {
                    NotificationUtils notif =
                            NotificationUtils.getInstance(getApplicationContext());
                    notif.showNotification("Get Current Weather Not Success", e.getMessage());

                    e.printStackTrace();
                    resultWorker = Result.failure();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                  Throwable error) {

                NotificationUtils notif =
                        NotificationUtils.getInstance(getApplicationContext());
                notif.showNotification("Get Current Weather Failed", error.getMessage());
            }
        });

        return resultWorker;
    }
}
