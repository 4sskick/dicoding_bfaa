package com.niteroomcreation.basemade.ui.act.detail;

import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BaseView;
import com.niteroomcreation.basemade.models.MoviesModel;
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

    private MoviesModel model;

    public static void startActivity(BaseView act, MoviesModel movies) {
        Intent i = new Intent(act, DetailActivity.class);
        i.putExtra(EXTRA_MODEL, movies);
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
            model = (MoviesModel) getIntent().getExtras().getParcelable(EXTRA_MODEL);
        } else
            throw new RuntimeException("Model isn't carried by parcelable arguments!");

        Glide.with(this)
                .load(new ImageUtils(this)
                        .setFileName(model.getName())
                        .load())
                .placeholder(R.drawable.poster_glass)
                .into(imgDetailMovie);

        txtDetailName.setText(model.getName());
        txtDetailDesc.setText(model.getDesc());
        txtDetailPercentage.setText(model.getPercentage());
        txtDetailYear.setText(String.format("( %s )", String.valueOf(model.getYear())));

        Log.e(TAG, String.format("initComponents: %s", model.toString()));
    }
}
