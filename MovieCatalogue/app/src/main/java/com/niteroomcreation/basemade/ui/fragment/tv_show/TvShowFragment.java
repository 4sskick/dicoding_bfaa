package com.niteroomcreation.basemade.ui.fragment.tv_show;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.adapter.AdapterTvShow;
import com.niteroomcreation.basemade.base.BaseFragmentView;
import com.niteroomcreation.basemade.data.models.TvShows;
import com.niteroomcreation.basemade.models.TvShowModel;
import com.niteroomcreation.basemade.ui.fragment.movie.MovieFragment;
import com.niteroomcreation.basemade.utils.Constants;
import com.niteroomcreation.basemade.utils.Utils;
import com.niteroomcreation.basemade.view.listener.GenericItemListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * Created by Septian Adi Wijaya on 15/11/19
 */
public class TvShowFragment extends BaseFragmentView implements TvShowContract.View {

    private static final String TAG = TvShowFragment.class.getSimpleName();

    @BindView(R.id.list_tv_show)
    RecyclerView listTvShow;

    private TvShowPresenter presenter;
    private AdapterTvShow adapter;
    private List<TvShows> tvShows;
    private MovieFragment.MoviesListener listener;

    public static TvShowFragment newInstance() {
        return new TvShowFragment();
    }

    @Override
    protected int contentLayout() {
        return R.layout.f_tv_shows;
    }

    @Override
    protected void initComponents() {
        presenter = new TvShowPresenter(this, getContext());

        adapter = new AdapterTvShow(tvShows, new GenericItemListener<TvShows, View>() {

            @Override
            public void onItemViewClicked(TvShows item, View view) {
                listener.onItemSelectedDetail(item, view);
            }
        });

        listTvShow.setLayoutManager(new LinearLayoutManager(getContext()));
        listTvShow.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: ");

        outState.putParcelableArrayList(Constants.EXTRA_ARR_MODEL, new ArrayList<>(tvShows));
        outState.putString(Constants.EXTRA_LANG_MODEL, Locale.getDefault().getDisplayLanguage());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {

            if (!Locale.getDefault().getDisplayLanguage().equalsIgnoreCase(savedInstanceState.getString(Constants.EXTRA_LANG_MODEL))) {
                presenter.getTvShows(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("english") ? "en-EN" : "id-ID");
            } else {
                tvShows = savedInstanceState.getParcelableArrayList(Constants.EXTRA_ARR_MODEL);
                setData(tvShows);
            }
        } else {
            presenter.getTvShows(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("english") ? "en-EN" : "id-ID");
        }
    }

    @Override
    public void setData(List<TvShows> data) {
        tvShows = data;
        adapter.setData(tvShows);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MovieFragment.MoviesListener)
            listener = (MovieFragment.MoviesListener) context;
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
}
