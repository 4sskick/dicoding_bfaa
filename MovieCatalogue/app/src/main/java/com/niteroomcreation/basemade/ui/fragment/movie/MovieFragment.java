package com.niteroomcreation.basemade.ui.fragment.movie;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.adapter.AdapterMovies;
import com.niteroomcreation.basemade.base.BaseFragmentView;
import com.niteroomcreation.basemade.models.MoviesModel;
import com.niteroomcreation.basemade.view.listener.GenericItemListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Septian Adi Wijaya on 01/10/19
 */
public class MovieFragment extends BaseFragmentView implements MovieContract.View {

    private static final String TAG = MovieFragment.class.getSimpleName();

    @BindView(R.id.list_movie)
    RecyclerView listMovie;

    private AdapterMovies adapter;

    private MoviesListener listener;
    private List<MoviesModel> movies;
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

        movies = presenter.constructModels();
        adapter = new AdapterMovies(movies, new GenericItemListener<MoviesModel>() {
            @Override
            public void onItemClicked(MoviesModel item) {
                listener.onItemSelectedDetail(item);
            }
        });

        listMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        listMovie.setAdapter(adapter);
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

    public interface MoviesListener {
        void onItemSelectedDetail(Object item);
    }
}
