package com.niteroomcreation.basemade.ui.act.search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.adapter.AdapterSearch;
import com.niteroomcreation.basemade.base.BaseView;
import com.niteroomcreation.basemade.models.FavsObjectItem;
import com.niteroomcreation.basemade.ui.fragment.EmptyFragment;
import com.niteroomcreation.basemade.utils.Utils;
import com.niteroomcreation.basemade.view.listener.GenericItemListener;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Septian Adi Wijaya on 09/02/2020.
 * please be sure to add credential if you use people's code
 */
public class SearchActivity extends BaseView implements SearchContract.View
        , SearchView.OnQueryTextListener {

    private static final String TAG = SearchActivity.class.getSimpleName();

    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.btn_search_movie)
    AppCompatRadioButton btnMovies;
    @BindView(R.id.btn_search_tv)
    AppCompatRadioButton btnTvShows;
    @BindView(R.id.layout_search_list)
    RecyclerView listSearch;
    @BindView(R.id.fl_search)
    FrameLayout flSearch;

    private List<FavsObjectItem> searchObjItems;
    private AdapterSearch adapter;
    private SearchPresenter presenter;

    private String onTextQuery;

    @Override
    protected int parentLayout() {
        return EMPTY_LAYOUT;
    }

    @Override
    protected int contentLayout() {
        return R.layout.a_search;
    }

    @Override
    protected void initComponents(@Nullable Bundle savedInstanceState) {

        presenter = new SearchPresenter(this, this);
        initSearchView();
        initSwitch();
        initListSearch();
    }

    private void initListSearch() {
        adapter = new AdapterSearch(searchObjItems
                , new GenericItemListener<FavsObjectItem, List<Pair<View, String>>>() {
            @Override
            public void onItemViewClicked(FavsObjectItem item, List<Pair<View, String>> view) {
                Log.e(TAG, "onItemViewClicked: " + item.toString());
            }
        });

        listSearch.setLayoutManager(new LinearLayoutManager(this));
        listSearch.setAdapter(adapter);
    }

    private void initSwitch() {
        onTextQuery = "";
        btnMovies.performClick();
    }

    private void initSearchView() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);

        //inflate view inside search view native component
        EditText editSearch = searchView.findViewById(android.support.design.R.id.search_src_text);
        editSearch.setTextColor(getResources().getColor(android.R.color.white));
        editSearch.setHintTextColor(getResources().getColor(R.color.text_disabled));
    }

    private void querySearch(String query) {
        Log.e(TAG, "querySearch: " + query);
        onTextQuery = query;
        btnMovies.performClick();
    }

    public void setData(List<FavsObjectItem> data) {
        Log.e(TAG, "setData: " + data.size());

        searchObjItems = data;
        adapter.setData(searchObjItems);
        if (adapter.getItemCount() > 0)
            flSearch.setVisibility(View.GONE);
        else
            showOverrideEmptyState();
    }

    @OnClick({R.id.btn_search_movie, R.id.btn_search_tv})
    void onClickedView(View view) {

        if (onTextQuery != null && onTextQuery.length() > 0)

            switch (view.getId()) {
                case R.id.btn_search_movie:
                    Log.e(TAG, "onClickedView: movie");

                    presenter.getMovieOnQuery(onTextQuery
                            , Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("english") ?
                                    "en-EN" : "id-ID");
                    break;

                case R.id.btn_search_tv:
                    Log.e(TAG, "onClickedView: tv show");

                    presenter.getTvShowOnQuery(onTextQuery
                            , Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("english") ?
                                    "en-EN" : "id-ID");
                    break;
            }
        else
            showMessage(getResources().getString(R.string.str_warning_empty));
    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        Log.e(TAG, "onQueryTextSubmit: ");

        Utils.closeKeyboard(this);
        querySearch(s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Log.e(TAG, "onQueryTextChange: ");

        return false;
    }

    @Override
    public void showOverrideEmptyState() {
        hideLoading();
        moveToFragment(flSearch.getId()
                , EmptyFragment.newInstance(getString(R.string.str_empty_fav)
                        , new EmptyFragment.EmptyListener() {
                            @Override
                            public void onEmptyClickedView() {
                                Log.e(TAG, "onEmptyClickedView: ");
                                presenter.getMovieOnQuery(onTextQuery
                                        , Locale.getDefault().getDisplayLanguage().equalsIgnoreCase(
                                                "english") ? "en-EN" : "id-ID");
                            }
                        })
                , EmptyFragment.class.getSimpleName());
    }
}
