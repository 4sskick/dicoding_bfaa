package com.niteroomcreation.basemade.ui.fragment.movie;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.adapter.AdapterMovies;
import com.niteroomcreation.basemade.base.BaseFragmentView;
import com.niteroomcreation.basemade.data.models.Movies;
import com.niteroomcreation.basemade.models.MoviesModel;
import com.niteroomcreation.basemade.view.listener.GenericItemListener;
import com.niteroomcreation.basemade.view.loader.NewtonCradleLoading;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * Created by Septian Adi Wijaya on 01/10/19
 */
public class MovieFragment extends BaseFragmentView implements MovieContract.View {

    private static final String TAG = MovieFragment.class.getSimpleName();
    private static final String EXTRA_ARR_MODEL = "extra.put.arr.model.state";


    @BindView(R.id.list_movie)
    RecyclerView listMovie;

    private AdapterMovies adapter;

    private MoviesListener listener;
    private List<Movies> movies;
    private MoviePresenter presenter;

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    protected int contentLayout() {
        return R.layout.f_movies;
    }

    @Override
    protected void initComponents() {
        presenter = new MoviePresenter(this, getContext());

        adapter = new AdapterMovies(movies, new GenericItemListener<Movies, View>() {

            @Override
            public void onItemViewClicked(Movies item, View view) {
                listener.onItemSelectedDetail(item, view);
            }
        });

        listMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        listMovie.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: ");

        outState.putParcelableArrayList(EXTRA_ARR_MODEL, new ArrayList<>(movies));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.e(TAG, "onActivityCreated: ");

        if (savedInstanceState != null)
            Log.e(TAG, "onActivityCreated: inside IF");
        else {
            Log.e(TAG, "onActivityCreated: inside ELSE ");

            String lang = Locale.getDefault().getDisplayLanguage();
            if (lang.equalsIgnoreCase("English")) {
                presenter.getMovies("en-EN");
            } else if (lang.equalsIgnoreCase("indonesia")) {
                presenter.getMovies("id-ID");
            }
            Log.e(TAG, "initComponents: language used " + lang);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.e(TAG, "onViewStateRestored: ");

        if (savedInstanceState != null) {
            Log.e(TAG, "onViewStateRestored: inside IF");

            movies = savedInstanceState.getParcelableArrayList(EXTRA_ARR_MODEL);
            setData(movies);

        } else
            Log.e(TAG, "onViewStateRestored: inside ELSE");

    }

    @Override
    public void setData(List<Movies> data) {
        movies = data;
        adapter.setData(movies);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MoviesListener)
            listener = (MoviesListener) context;
        else
            throw new RuntimeException("Listener must implemented");
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        presenter.onUnsubscribe();
        presenter = null;
        super.onDestroyView();
    }

    public interface MoviesListener {
        void onItemSelectedDetail(Object item, View view);
    }
}
