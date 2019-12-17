package com.niteroomcreation.basemade.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.niteroomcreation.basemade.BuildConfig;
import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.data.models.Movies;
import com.niteroomcreation.basemade.utils.ImageUtils;
import com.niteroomcreation.basemade.view.image_utils.BlurTransformation;
import com.niteroomcreation.basemade.view.listener.GenericItemListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdapterMovies extends RecyclerView.Adapter<AdapterMovies.MainViewHolder> {

    private static final String TAG = AdapterMovies.class.getSimpleName();

    private List<Movies> movies;
    private GenericItemListener<Movies, View> listener;
    private Bitmap localDataBitmap;

    public AdapterMovies(List<Movies> movies
            , GenericItemListener<Movies, View> listener) {
        this.movies = movies;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.i_movie,
                viewGroup, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int i) {
        mainViewHolder.binds();
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    private Movies getItem(int pos) {
        return movies != null ? movies.get(pos) : null;
    }

    public void setData(List<Movies> data) {
        this.movies = data;
        notifyDataSetChanged();
    }

    class MainViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_item_name)
        TextView txtName;
        @BindView(R.id.txt_item_desc)
        TextView txtDesc;
        @BindView(R.id.img_item_photo)
        AppCompatImageView imgMovie;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void binds() {
            final Movies model = getItem(getAdapterPosition());

            if (model != null) {
                txtName.setText(model.getTitle());
                txtDesc.setText(model.getOverview());


                ImageUtils imageUtils = ImageUtils.init(imgMovie.getContext());
                imageUtils.setFileName(String.format("%s_%s", model.getPosterPath().split("/")[1].split(".jpg")[0], model.getTitle()));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        localDataBitmap = imageUtils.load();
                    }
                }).start();

                Log.e(TAG, "binds: localDataBitmap " + (localDataBitmap != null ? "not null" : "null"));

                Glide.with(imgMovie.getContext())
                        .load(localDataBitmap != null ?
                                localDataBitmap :
                                String.format("%s%sw500%s"
                                        , BuildConfig.BASE_URL_IMG
                                        , BuildConfig.BASE_PATH_IMG
                                        , model.getPosterPath()))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object m, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                if (localDataBitmap == null)
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new ImageUtils(imgMovie.getContext())
                                                    .setFileName(String.format("%s_%s", model.getPosterPath().split("/")[1].split(".jpg")[0], model.getTitle()))
                                                    .save(((BitmapDrawable) resource).getBitmap(), new ImageUtils.ImageUtilsListener() {
                                                        @Override
                                                        public void success(String fileAbsolutePath) {
                                                            Log.e(TAG, "success: " + fileAbsolutePath);
                                                        }

                                                        @Override
                                                        public void failed(String errMsg) {
                                                            Log.e(TAG, String.format("failed: %s", errMsg));
                                                        }
                                                    });
                                        }
                                    }).start();

                                return false;
                            }
                        })
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .transform(BlurTransformation.init(imgMovie.getContext()))
                        .into(imgMovie);
            }
        }

        @OnClick({R.id.layout_item})
        void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.layout_item:
                    listener.onItemViewClicked(getItem(getAdapterPosition()), imgMovie);
                    break;
            }
        }
    }
}
