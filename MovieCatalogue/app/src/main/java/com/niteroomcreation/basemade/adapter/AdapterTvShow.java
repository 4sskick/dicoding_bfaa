package com.niteroomcreation.basemade.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.models.TvShowModel;
import com.niteroomcreation.basemade.view.image_utils.BlurTransformation;
import com.niteroomcreation.basemade.view.listener.GenericItemListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdapterTvShow extends RecyclerView.Adapter<AdapterTvShow.MainViewHolder> {

    private List<TvShowModel> movies;
    private GenericItemListener<TvShowModel> listener;

    public AdapterTvShow(List<TvShowModel> movies, GenericItemListener<TvShowModel> listener) {
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
        return movies.size();
    }

    private TvShowModel getItem(int pos) {
        return movies.get(pos);
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
            TvShowModel tvShow = getItem(getAdapterPosition());

            txtName.setText(tvShow.getName());
            txtDesc.setText(tvShow.getDesc());

            Glide.with(imgMovie.getContext())
                    .load(tvShow.getPoster())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .transform(BlurTransformation.init(imgMovie.getContext()))
                    .into(imgMovie);
        }

        @OnClick({R.id.layout_item})
        void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.layout_item:
                    listener.onItemClicked(getItem(getAdapterPosition()));
                    break;
            }
        }
    }
}