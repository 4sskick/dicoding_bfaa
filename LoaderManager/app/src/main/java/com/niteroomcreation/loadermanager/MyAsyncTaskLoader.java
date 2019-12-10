package com.niteroomcreation.loadermanager;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.niteroomcreation.loadermanager.adapter.WeatherItems;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Septian Adi Wijaya on 10/12/19
 */
public class MyAsyncTaskLoader extends AsyncTaskLoader<ArrayList<WeatherItems>> {

    private ArrayList<WeatherItems> mData;
    private boolean mHasResult = false;
    private String cities;

    private static final String API_KEY = "72d89f872ece775826e1965684fe382c";

    MyAsyncTaskLoader(final Context context, String cities) {
        super(context);
        onContentChanged();
        this.cities = cities;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<WeatherItems> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            mData = null;
            mHasResult = false;
        }
    }

    // Format search kota url JAKARTA = 1642911 ,BANDUNG = 1650357, SEMARANG = 1627896
    // http://api.openweathermap.org/data/2.5/group?id=1642911,1650357,1627896&units=metric&appid=API_KEY

    @Override
    public ArrayList<WeatherItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<WeatherItems> weatherItemses = new ArrayList<>();
        String url = "http://api.openweathermap.org/data/2.5/group?id=" + cities + "&units=metric&appid=" + API_KEY;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject weather = list.getJSONObject(i);
                        WeatherItems weatherItems = new WeatherItems(weather);
                        weatherItemses.add(weatherItems);
                    }
                } catch (Exception e) {
                    //Jika terjadi error pada saat parsing maka akan masuk ke catch()
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Jika response gagal maka , do nothing
            }
        });
        return weatherItemses;
    }
}
