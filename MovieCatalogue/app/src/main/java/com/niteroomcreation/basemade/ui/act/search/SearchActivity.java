package com.niteroomcreation.basemade.ui.act.search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BaseView;
import com.niteroomcreation.basemade.utils.Utils;

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
        editSearch.setHintTextColor(getResources().getColor(android.R.color.white));
    }

    private void querySearch(String query) {
        Log.e(TAG, "querySearch: " + query);
        onTextQuery = query;
        btnMovies.performClick();
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

        showLoading();
        Utils.closeKeyboard(this);
        querySearch(s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Log.e(TAG, "onQueryTextChange: ");

        return false;
    }
}
