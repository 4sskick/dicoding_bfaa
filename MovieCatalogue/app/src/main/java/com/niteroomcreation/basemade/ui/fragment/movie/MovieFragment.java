package com.niteroomcreation.basemade.ui.fragment.movie;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.adapter.AdapterMovies;
import com.niteroomcreation.basemade.base.BaseFragmentView;
import com.niteroomcreation.basemade.data.local.entity.MovieEntity;
import com.niteroomcreation.basemade.ui.fragment.EmptyFragment;
import com.niteroomcreation.basemade.utils.Constants;
import com.niteroomcreation.basemade.view.listener.GenericItemListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * Created by Septian Adi Wijaya on 01/10/19
 */
public class MovieFragment extends BaseFragmentView implements MovieContract.View {

    private static final String TAG = MovieFragment.class.getSimpleName();

    @BindView(R.id.list_movie)
    RecyclerView listMovie;
    @BindView(R.id.fl_movie)
    FrameLayout flMovie;

    private AdapterMovies adapter;

    private MoviesListener listener;
    private List<MovieEntity> movies;
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

        adapter = new AdapterMovies(movies, new GenericItemListener<MovieEntity, List<Pair<View, String>>>() {

            @Override
            public void onItemViewClicked(MovieEntity item, List<Pair<View, String>> view) {
                listener.onItemSelectedDetail(item, view);

            }
        });

        listMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        listMovie.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: " + Locale.getDefault().getDisplayLanguage());

        if (!isShownLoading()) {
            outState.putParcelableArrayList(Constants.EXTRA_ARR_MODEL, new ArrayList<>(movies));
            outState.putString(Constants.EXTRA_LANG_MODEL, Locale.getDefault().getDisplayLanguage());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            if (!Locale.getDefault().getDisplayLanguage().equalsIgnoreCase(savedInstanceState.getString(Constants.EXTRA_LANG_MODEL))) {
                presenter.getMovies(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("english") ? "en-EN" : "id-ID");
            } else {
                movies = savedInstanceState.getParcelableArrayList(Constants.EXTRA_ARR_MODEL);
                setData(movies);
            }
        } else {
            presenter.getMovies(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("english") ? "en-EN" : "id-ID");
        }
    }

    @Override
    public void setData(List<MovieEntity> data) {
        movies = data;
        adapter.setData(movies);

        if (adapter.getItemCount() > 0)
            flMovie.setVisibility(View.GONE);
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

    @Override
    public void showOverrideEmptyState() {
        hideLoading();
        mActivity.moveToFragment(flMovie.getId()
                , EmptyFragment.newInstance(getString(R.string.str_empty)
                        , new EmptyFragment.EmptyListener() {
                            @Override
                            public void onEmptyClickedView() {
                                Log.e(TAG, "onEmptyClickedView: ");
                                presenter.getMovies(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("english") ? "en-EN" : "id-ID");
                            }
                        })
                , EmptyFragment.class.getSimpleName());
    }

    public interface MoviesListener {
        void onItemSelectedDetail(Object item, List<Pair<View, String>> view);
    }
}
