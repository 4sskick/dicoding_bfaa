package com.niteroomcreation.basemade.ui.act.detail;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.niteroomcreation.basemade.BuildConfig;
import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BaseView;
import com.niteroomcreation.basemade.data.models.Movies;
import com.niteroomcreation.basemade.data.models.TvShows;
import com.niteroomcreation.basemade.models.TvShowModel;

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
    protected void initComponents() {
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

        Glide.with(this)
                .load(String.format("%s%sw500%s"
                        , BuildConfig.BASE_URL_IMG
                        , BuildConfig.BASE_PATH_IMG
                        , movies != null ? movies.getPosterPath() : tvShows.getPosterPath()))
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
        txtDetailDesc.setText(movies != null ? movies.getOverview() : tvShows.getOverview());
        txtDetailPercentage.setText(String.format("%s"
                , String.valueOf(movies != null ? movies.getVoteAverage() :
                        tvShows.getVoteAverage())));
        txtDetailYear.setText(String.format("( %s )"
                , String.valueOf(movies != null ?
                        movies.getReleaseDate().split("-")[0] :
                        tvShows.getFirstAirDate().split("-")[0])));
    }
}
