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
import com.niteroomcreation.basemade.BuildConfig;
import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.data.models.Movies;
import com.niteroomcreation.basemade.view.image_utils.BlurTransformation;
import com.niteroomcreation.basemade.view.listener.GenericItemListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdapterMovies extends RecyclerView.Adapter<AdapterMovies.MainViewHolder> {

    private List<Movies> movies;
    private GenericItemListener<Movies, View> listener;

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
        return movies.size();
    }

    private Movies getItem(int pos) {
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
            Movies model = getItem(getAdapterPosition());

            txtName.setText(model.getTitle());
            txtDesc.setText(model.getOverview());

            Glide.with(imgMovie.getContext())
                    .load(String.format("%s%sw342%s", BuildConfig.BASE_URL_IMG,
                            BuildConfig.BASE_PATH_IMG, model.getPosterPath()))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .transform(BlurTransformation.init(imgMovie.getContext()))
                    .into(imgMovie);
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
