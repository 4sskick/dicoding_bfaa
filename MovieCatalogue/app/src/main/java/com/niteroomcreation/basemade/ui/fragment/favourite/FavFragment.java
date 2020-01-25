package com.niteroomcreation.basemade.ui.fragment.favourite;

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
import com.niteroomcreation.basemade.adapter.AdapterFavs;
import com.niteroomcreation.basemade.base.BaseFragmentView;
import com.niteroomcreation.basemade.data.local.entity.MovieEntity;
import com.niteroomcreation.basemade.data.local.entity.TvEntity;
import com.niteroomcreation.basemade.ui.fragment.EmptyFragment;
import com.niteroomcreation.basemade.ui.fragment.movie.MovieFragment;
import com.niteroomcreation.basemade.utils.Constants;
import com.niteroomcreation.basemade.view.listener.GenericItemListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import io.reactivex.Flowable;

/**
 * Created by Septian Adi Wijaya on 25/01/2020.
 * please be sure to add credential if you use people's code
 */
public class FavFragment extends BaseFragmentView implements FavContract.View {

    private static final String TAG = FavFragment.class.getSimpleName();

    @BindView(R.id.list_favs)
    RecyclerView listFav;
    @BindView(R.id.fl_favs)
    FrameLayout flFav;

    private AdapterFavs adapter;
    private FavPresenter presenter;
    private MovieFragment.MoviesListener listener;

    private List<MovieEntity> movies;
    private List<TvEntity> tvs;


    public static FavFragment newInstance() {
        return new FavFragment();
    }

    @Override
    protected int contentLayout() {
        return R.layout.f_favs;
    }

    @Override
    protected void initComponents() {
        presenter = new FavPresenter(this, getContext());

        adapter = new AdapterFavs(movies, new GenericItemListener<MovieEntity, List<Pair<View,
                String>>>() {
            @Override
            public void onItemViewClicked(MovieEntity item, List<Pair<View, String>> view) {
                Log.e(TAG, "onItemViewClicked: " + item.toString());
            }
        });

        listFav.setLayoutManager(new LinearLayoutManager(getContext()));
        listFav.setAdapter(adapter);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: ");

        if (!isShownLoading()) {
            outState.putParcelableArrayList(Constants.EXTRA_ARR_MODEL, new ArrayList<>(movies));
            outState.putString(Constants.EXTRA_LANG_MODEL,
                    Locale.getDefault().getDisplayLanguage());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {

            if (!Locale.getDefault().getDisplayLanguage().equalsIgnoreCase(savedInstanceState.getString(Constants.EXTRA_LANG_MODEL))) {
                presenter.getFavs(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase(
                        "english") ? "en-EN" : "id-ID");
            } else {
                movies = savedInstanceState.getParcelableArrayList(Constants.EXTRA_ARR_MODEL);
                setData(movies);
            }
        } else {
            presenter.getFavs(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase(
                    "english") ? "en-EN" : "id-ID");
        }
    }

    @Override
    public void setData(List<MovieEntity> data) {

        Log.e(TAG, "setData: " + data.size());

        movies = data;
        adapter.setData(movies);
        if (adapter.getItemCount() > 0)
            flFav.setVisibility(View.GONE);
        else
            showOverrideEmptyState();
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

    @Override
    public void showOverrideEmptyState() {
        hideLoading();
        mActivity.moveToFragment(flFav.getId()
                , EmptyFragment.newInstance(getString(R.string.str_empty)
                        , new EmptyFragment.EmptyListener() {
                            @Override
                            public void onEmptyClickedView() {
                                Log.e(TAG, "onEmptyClickedView: ");
                                presenter.getFavs(Locale.getDefault().getDisplayLanguage().equalsIgnoreCase(
                                        "english") ? "en-EN" : "id-ID");
                            }
                        })
                , EmptyFragment.class.getSimpleName());
    }
}
