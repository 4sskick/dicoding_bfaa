package com.niteroomcreation.basemade.ui.act.detail;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BaseView;
import com.niteroomcreation.basemade.data.local.entity.MovieEntity;
import com.niteroomcreation.basemade.data.local.entity.TvEntity;
import com.niteroomcreation.basemade.utils.ImageUtils;
import com.niteroomcreation.basemade.view.TagPickerView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Septian Adi Wijaya on 04/11/19
 */
public class DetailActivity extends BaseView implements DetailContract.View {

    private static final String TAG = DetailActivity.class.getSimpleName();
    public static final String EXTRA_MODEL = "model.object";

    @BindView(R.id.img_detail_movie)
    AppCompatImageView imgDetailMovie;
    @BindView(R.id.txt_detail_name)
    TextView txtDetailName;
    @BindView(R.id.txt_detail_desc)
    TextView txtDetailDesc;
    @BindView(R.id.txt_detail_save_fav)
    AppCompatTextView txtSaveFav;

    @BindView(R.id.layout_bottom_detail_content)
    FrameLayout layoutBottomContent;
    @BindView(R.id.tag_genre_layout)
    TagPickerView layoutTagGenre;

    private boolean isSaveChosen;
    private boolean isVisible;

    private MovieEntity movies;
    private TvEntity tvShows;
    private DetailPresenter presenter;

    @Override

    protected int parentLayout() {
        return R.layout.b_activity_actionbar;
    }

    @Override
    protected int contentLayout() {
        return R.layout.a_detail;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_MODEL, movies != null ? movies : tvShows);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            if (savedInstanceState.getParcelable(EXTRA_MODEL) instanceof MovieEntity) {
                movies = savedInstanceState.getParcelable(EXTRA_MODEL);
            }

            if (savedInstanceState.getParcelable(EXTRA_MODEL) instanceof TvEntity) {
                tvShows = savedInstanceState.getParcelable(EXTRA_MODEL);
            }
        }
    }

    @Override
    protected void initComponents(@Nullable Bundle savedInstanceState) {
        showLoading();
        supportPostponeEnterTransition();

        showTitleToolbar(false, null);
        presenter = new DetailPresenter(this, this);

        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().getParcelable(EXTRA_MODEL) instanceof MovieEntity) {
                movies = getIntent().getExtras().getParcelable(EXTRA_MODEL);
            } else if (getIntent().getExtras().getParcelable(EXTRA_MODEL) instanceof TvEntity) {
                tvShows = getIntent().getExtras().getParcelable(EXTRA_MODEL);
            } else
                throw new RuntimeException("Model isn't carried by parcelable arguments!");
        } else
            throw new RuntimeException("Model isn't carried by parcelable arguments!");

        if (movies != null) {
            presenter.getMovieDetail(String.valueOf(movies.getId()));
        }

        if (tvShows != null) {
            presenter.getTvShowDetail(String.valueOf(tvShows.getId()));
        }

        setupImage();
        setupContent();

    }

    public void setupSavedFav(boolean isSavedFav) {
        Log.e(TAG, "setupSavedFav: " + isSavedFav + "\n" + isSaveChosen);

        isSaveChosen = isSavedFav;
        txtSaveFav.setTextColor(isSaveChosen ?
                getResources().getColor(R.color.colorPrimary) :
                getResources().getColor(R.color.textColorSecondary));
    }

    public void setupGenre(List<String> genres) {
        Log.e(TAG, "setupGenre: " + genres.toString());
        layoutTagGenre.setItems(genres);
    }

    @Override
    public void setupContent() {
        txtDetailName.setText(movies != null ? movies.getTitle() : tvShows.getName());
        txtDetailDesc.setText(getOverview(movies, tvShows));
    }

    private void setupImage() {
        ImageUtils imageUtils = ImageUtils.init(this);
        imageUtils.setFileName(
                String.format("%s_%s"
                        , movies != null ?
                                movies.getPosterPath().split("/")[1].split(".jpg")[0] :
                                tvShows.getPosterPath().split("/")[1].split(".jpg")[0]
                        , movies != null ?
                                movies.getTitle() :
                                tvShows.getName()));

        Glide.with(this)
                .asBitmap()
                .load(imageUtils.load() != null ?
                        imageUtils.load() : movies != null ?
                        movies.getFullPosterPath(false) :
                        tvShows.getFullPosterPath(false)
                )
                .listener(

                        new RequestListener<Bitmap>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e
                                    , Object model
                                    , Target<Bitmap> target
                                    , boolean isFirstResource) {

                                hideLoading();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource
                                    , Object model
                                    , Target<Bitmap> target
                                    , DataSource dataSource
                                    , boolean isFirstResource) {

                                hideLoading();
                                DetailActivity.this.supportStartPostponedEnterTransition();

                                return false;
                            }
                        })
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.ic_placeholder)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource
                            , @Nullable Transition<? super Bitmap> transition) {

                        imgDetailMovie.setImageBitmap(resource);
                    }
                });
    }

    private String getOverview(MovieEntity m, TvEntity t) {
        if (m != null) {
            if (m.getOverview().isEmpty()) {
                return getResources().getString(R.string.str_trans_not_found);
            } else
                return m.getOverview();
        }

        if (t != null) {
            if (t.getOverview().isEmpty()) {
                return getResources().getString(R.string.str_trans_not_found);
            } else
                return t.getOverview();
        }

        throw new RuntimeException("Model isn't carried by parcelable arguments!");
    }

    @OnClick({R.id.txt_detail_cast_crew
            , R.id.txt_detail_review
            , R.id.txt_detail_video
            , R.id.txt_detail_save_fav})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_detail_cast_crew:
                visibleFrame(view);
                break;

            case R.id.txt_detail_review:
                visibleFrame(view);
                break;

            case R.id.txt_detail_video:
                visibleFrame(view);
                break;

            case R.id.txt_detail_save_fav:
                toggleColor();

                break;
        }
    }

    private void toggleColor() {

        txtSaveFav.setTextColor(isSaveChosen ?
                getResources().getColor(R.color.textColorSecondary) :
                getResources().getColor(R.color.colorPrimary));

        isSaveChosen = !isSaveChosen;
        showMessage(isSaveChosen ? "SAVED" : "UNSAVED");

        if (movies != null) {
            presenter.saveMovieFav(movies.getId(), isSaveChosen);
        }

        if (tvShows != null) {
            presenter.saveTvFav(tvShows.getId(), isSaveChosen);
        }
    }

    private void visibleFrame(View contentView) {

    }
}
