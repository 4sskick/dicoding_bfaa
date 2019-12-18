package com.niteroomcreation.basemade.ui.act.detail;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BaseView;
import com.niteroomcreation.basemade.data.models.Movies;
import com.niteroomcreation.basemade.data.models.TvShows;
import com.niteroomcreation.basemade.utils.ImageUtils;

import butterknife.BindView;

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
    @BindView(R.id.txt_detail_percentage)
    TextView txtDetailPercentage;
    @BindView(R.id.txt_detail_year)
    TextView txtDetailYear;

    private Movies movies;
    private TvShows tvShows;

    @Override

    protected int parentLayout() {
        return 0;
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
            if (savedInstanceState.getParcelable(EXTRA_MODEL) instanceof Movies) {
                movies = savedInstanceState.getParcelable(EXTRA_MODEL);
            }

            if (savedInstanceState.getParcelable(EXTRA_MODEL) instanceof TvShows) {
                tvShows = savedInstanceState.getParcelable(EXTRA_MODEL);
            }
        }
    }

    @Override
    protected void initComponents(@Nullable Bundle savedInstanceState) {
        supportPostponeEnterTransition();

        showBackButtonToolbar(true);

        if (getIntent() != null && getIntent().getExtras() != null) {

            if (getIntent().getExtras().getParcelable(EXTRA_MODEL) instanceof Movies) {
                movies = getIntent().getExtras().getParcelable(EXTRA_MODEL);
            } else if (getIntent().getExtras().getParcelable(EXTRA_MODEL) instanceof TvShows) {
                tvShows = getIntent().getExtras().getParcelable(EXTRA_MODEL);
            } else
                throw new RuntimeException("Model isn't carried by parcelable arguments!");
        } else
            throw new RuntimeException("Model isn't carried by parcelable arguments!");

        ImageUtils imageUtils = ImageUtils.init(this);
        imageUtils.setFileName(
                movies != null ?
                        String.format("%s_%s", movies.getPosterPath().split("/")[1].split(".jpg")[0], movies.getTitle()) :
                        String.format("%s_%s", tvShows.getPosterPath().split("/")[1].split(".jpg")[0], tvShows.getName()));

        Glide.with(this)
                .load(imageUtils.load())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource
                            , Object model
                            , Target<Drawable> target, DataSource dataSource
                            , boolean isFirstResource) {
                        DetailActivity.this.supportStartPostponedEnterTransition();
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.ic_placeholder)
                .into(imgDetailMovie);

        txtDetailName.setText(movies != null ? movies.getTitle() : tvShows.getName());
        txtDetailDesc.setText(getOverview(movies, tvShows));
        txtDetailPercentage.setText(String.format("%s"
                , String.valueOf(movies != null ? movies.getVoteAverage() :
                        tvShows.getVoteAverage())));
        txtDetailYear.setText(String.format("( %s )"
                , String.valueOf(movies != null ?
                        movies.getReleaseDate().split("-")[0] :
                        tvShows.getFirstAirDate().split("-")[0])));
    }

    private String getOverview(Movies m, TvShows t) {
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
}
