package com.niteroomcreation.moviewatchfavs.adapter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.niteroomcreation.moviewatchfavs.BuildConfig;
import com.niteroomcreation.moviewatchfavs.R;
import com.niteroomcreation.moviewatchfavs.data.local.entity.MovieEntity;
import com.niteroomcreation.moviewatchfavs.view.image_utils.BlurTransformation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Septian Adi Wijaya on 18/02/20
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {

    private static final String TAG = MainAdapter.class.getSimpleName();

    private List<MovieEntity> movies;

    public MainAdapter(List<MovieEntity> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.i_movies, viewGroup, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder mainHolder, int i) {
        mainHolder.binds();
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    private MovieEntity getItem(int pos) {
        return movies.get(pos);
    }

    public void setData(List<MovieEntity> data) {
        this.movies = data;
        notifyDataSetChanged();
    }

    class MainHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.stack_txt_item_name)
        TextView txtName;
        @BindView(R.id.stack_txt_item_desc)
        TextView txtDesc;
        @BindView(R.id.stack_img_item_photo)
        AppCompatImageView imgItem;

        public MainHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void binds() {
            txtName.setText(getItem(getAdapterPosition()).getTitle());
            txtDesc.setText(getItem(getAdapterPosition()).getOverview());
            Glide.with(imgItem.getContext())
                    .asBitmap()
                    .load(BuildConfig.BASE_URL_IMG + "" + BuildConfig.BASE_PATH_IMG + "w342/" + getItem(getAdapterPosition()).getPosterPath())
                    .transform(BlurTransformation.init(imgItem.getContext()))
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            imgItem.setImageBitmap(resource);
                        }
                    });
        }
    }
}