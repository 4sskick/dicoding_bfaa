package com.niteroomcreation.basemade.ui.act.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BaseView;
import com.niteroomcreation.basemade.models.MoviesModel;
import com.niteroomcreation.basemade.models.TvShowModel;
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

    private Object model;
    private MoviesModel modelMovie;
    private TvShowModel modelTV;

    public static void startActivity(BaseView act, Object objModel) {
        Intent i = new Intent(act, DetailActivity.class);

        if (objModel instanceof MoviesModel)
            i.putExtra(EXTRA_MODEL, (MoviesModel) objModel);
        else if (objModel instanceof TvShowModel)
            i.putExtra(EXTRA_MODEL, (TvShowModel) objModel);

        act.startActivity(i);
    }

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
        showBackButtonToolbar(true);

        if (getIntent() != null && getIntent().getExtras() != null) {

            if (getIntent().getExtras().getParcelable(EXTRA_MODEL) instanceof MoviesModel) {

                model = (MoviesModel) getIntent().getExtras().getParcelable(EXTRA_MODEL);
                modelMovie = getIntent().getExtras().getParcelable(EXTRA_MODEL);

            } else if (getIntent().getExtras().getParcelable(EXTRA_MODEL) instanceof TvShowModel) {

                model = (TvShowModel) getIntent().getExtras().getParcelable(EXTRA_MODEL);
                modelTV = getIntent().getExtras().getParcelable(EXTRA_MODEL);
            }
        } else
            throw new RuntimeException("Model isn't carried by parcelable arguments!");

//        if (getIntent().getExtras().getParcelable(EXTRA_MODEL) instanceof MoviesModel) {
//            Glide.with(this)
//                    .load(new ImageUtils(this)
//                            .setFileName(((MoviesModel) model).getName())
//                            .load())
//                    .placeholder(R.drawable.poster_glass)
//                    .into(imgDetailMovie);
//
//            txtDetailName.setText(((MoviesModel) model).getName());
//            txtDetailDesc.setText(((MoviesModel) model).getDesc());
//            txtDetailPercentage.setText(((MoviesModel) model).getPercentage());
//            txtDetailYear.setText(String.format("( %s )",
//                    String.valueOf(((MoviesModel) model).getYear())));
//
//            Log.e(TAG, String.format("initComponents: %s", ((MoviesModel) model).toString()));
//
//        } else if (getIntent().getExtras().getParcelable(EXTRA_MODEL) instanceof TvShowModel) {
//
//            Glide.with(this)
//                    .asBitmap()
//                    .load(new ImageUtils(this)
//                            .setFileName(((TvShowModel) model).getName())
//                            .load())
//                    .placeholder(R.drawable.poster_glass)
//                    .into(imgDetailMovie);
//
//            txtDetailName.setText(((TvShowModel) model).getName());
//            txtDetailDesc.setText(((TvShowModel) model).getDesc());
//            txtDetailPercentage.setText(((TvShowModel) model).getPercentage());
//            txtDetailYear.setText(String.format("( %s )",
//                    String.valueOf(((TvShowModel) model).getYear())));
//
//            Log.e(TAG, String.format("initComponents: %s", ((TvShowModel) model).toString()));
//        }

        Glide.with(this)
                .load(new ImageUtils(this)
                        .setFileName(modelMovie != null ? modelMovie.getName() : modelTV.getName())
                        .load())
                .placeholder(R.drawable.poster_glass)
                .into(imgDetailMovie);

        txtDetailName.setText(modelMovie != null ? modelMovie.getName() : modelTV.getName());
        txtDetailDesc.setText(modelMovie != null ? modelMovie.getDesc() : modelTV.getDesc());
        txtDetailPercentage.setText(modelMovie != null ? modelMovie.getPercentage() : modelTV.getPercentage());
        txtDetailYear.setText(String.format("( %s )",
                String.valueOf(modelMovie != null ? modelMovie.getYear() : modelTV.getYear())));
    }
}
