package com.niteroomcreation.loadermanager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.niteroomcreation.loadermanager.adapter.WeatherAdapter;
import com.niteroomcreation.loadermanager.adapter.WeatherItems;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<WeatherItems>>
        , View.OnClickListener {

    RecyclerView recyclerView;
    WeatherAdapter adapter;
    EditText edtCity;
    Button btnSearch;

    static final String EXTRAS_CITY = "EXTRAS_CITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new WeatherAdapter();
        adapter.notifyDataSetChanged();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        edtCity = findViewById(R.id.editCity);
        btnSearch = findViewById(R.id.btnCity);

        edtCity.setText("1642911,1650357,1627896");

        btnSearch.setOnClickListener(this);

        String city = edtCity.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_CITY, city);

        getSupportLoaderManager().initLoader(0, bundle, this);
    }

    @NonNull
    @Override
    public Loader<ArrayList<WeatherItems>> onCreateLoader(int id, @Nullable Bundle args) {
        String cities = "";
        if (args != null) {
            cities = args.getString(EXTRAS_CITY);
        }
        return new MyAsyncTaskLoader(this, cities);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<WeatherItems>> loader, ArrayList<WeatherItems> data) {
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<WeatherItems>> loader) {
        adapter.setData(null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCity:

                String city = edtCity.getText().toString();

                if (TextUtils.isEmpty(city))
                    return;

                Bundle bundle = new Bundle();
                bundle.putString(EXTRAS_CITY, city);
                getSupportLoaderManager().restartLoader(0, bundle, MainActivity.this);
                break;
        }
    }
}
