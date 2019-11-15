package com.niteroomcreation.basemade.ui.fragment.main;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.adapter.AdapterMain;
import com.niteroomcreation.basemade.adapter.AdapterMainList;
import com.niteroomcreation.basemade.base.BaseFragmentView;
import com.niteroomcreation.basemade.models.MoviesModel;
import com.niteroomcreation.basemade.view.listener.GenericItemListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Septian Adi Wijaya on 01/10/19
 */
public class MainFragment extends BaseFragmentView implements MainFragmentContract.View {

    private static final String TAG = MainFragment.class.getSimpleName();

    @BindView(R.id.list_main)
    ListView listMain;

    //    private AdapterMain adapter;
    private AdapterMainList adapterList;

    private MainFragmentListener listener;
    private List<MoviesModel> movies;
    private MainFragmentPresenter presenter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int contentLayout() {
        return R.layout.f_main;
    }

    @Override
    protected void initComponents() {
        presenter = new MainFragmentPresenter(this, getContext());

        movies = presenter.constructModels();
//        adapter = new AdapterMain(movies, new GenericItemListener<MoviesModel>() {
//            @Override
//            public void onItemClicked(MoviesModel item) {
//                listener.onItemSelectedDetail(item);
//            }
//        });
//
//        listMain.setLayoutManager(new LinearLayoutManager(getContext()));
//        listMain.setAdapter(adapter);

        adapterList = new AdapterMainList(movies);
        listMain.setAdapter(adapterList);
        listMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, String.format("onItemClick: %s", movies.get(position).toString()));

                listener.onItemSelectedDetail(movies.get(position));
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MainFragmentListener)
            listener = (MainFragmentListener) context;
        else
            throw new RuntimeException("Listener must implemented");
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    public interface MainFragmentListener {
        void onItemSelectedDetail(MoviesModel item);
    }
}