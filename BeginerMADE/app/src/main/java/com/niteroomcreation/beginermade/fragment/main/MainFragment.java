package com.niteroomcreation.beginermade.fragment.main;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.niteroomcreation.beginermade.R;
import com.niteroomcreation.beginermade.adapter.AdapterMain;
import com.niteroomcreation.beginermade.base.BaseFragmentView;
import com.niteroomcreation.beginermade.fragment.detail.DetailFragment;
import com.niteroomcreation.beginermade.model.TokohModel;
import com.niteroomcreation.beginermade.view.listener.GenericItemListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Septian Adi Wijaya on 01/10/19
 */
public class MainFragment extends BaseFragmentView implements MainFragmentContract.View {

    @BindView(R.id.list_main)
    RecyclerView listMain;

    private AdapterMain adapter;
    private List<TokohModel> models;
    private MainFragmentPresenter presenter;
    private MainFragmentListener listener;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int contentLayout() {
        return R.layout.f_main;
    }

    @Override
    protected void initComponents() {
        presenter = new MainFragmentPresenter(this);

        models = presenter.constructModels();
        adapter = new AdapterMain(models, new GenericItemListener<TokohModel>() {
            @Override
            public void onItemClicked(TokohModel item) {
//                Log.e("tagCheck", String.format("onItemClicked: %s", item.toString()));
                listener.onItemSelectedDetail(item);
            }
        });

        listMain.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        listMain.setAdapter(adapter);
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
        void onItemSelectedDetail(TokohModel item);
    }
}
