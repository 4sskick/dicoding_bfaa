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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.niteroomcreation.basemade.BuildConfig;
import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.data.models.TvShows;
import com.niteroomcreation.basemade.utils.ImageUtils;
import com.niteroomcreation.basemade.view.image_utils.BlurTransformation;
import com.niteroomcreation.basemade.view.listener.GenericItemListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdapterTvShow extends RecyclerView.Adapter<AdapterTvShow.MainViewHolder> {

    private List<TvShows> tvShows;
    private GenericItemListener<TvShows, View> listener;

    public AdapterTvShow(List<TvShows> tvShows,
                         GenericItemListener<TvShows, View> listener) {
        this.tvShows = tvShows;
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
        return tvShows != null ? tvShows.size() : 0;
    }

    private TvShows getItem(int pos) {
        return tvShows != null ? tvShows.get(pos) : null;
    }

    public void setData(List<TvShows> data) {
        this.tvShows = data;
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
            TvShows model = getItem(getAdapterPosition());

            if (model != null) {
                txtName.setText(model.getName());
                txtDesc.setText(model.getOverview());

                ImageUtils imageUtils = ImageUtils.init(imgMovie.getContext());
                imageUtils.setFileName(String.format("%s_%s", model.getPosterPath().split("/")[1].split(".jpg")[0], model.getName()));

                Glide.with(imgMovie.getContext())
                        .load(
                                imageUtils.load()!=null?
                                        imageUtils.load():
                                        String.format("%s%sw500%s"
                                                , BuildConfig.BASE_URL_IMG
                                                , BuildConfig.BASE_PATH_IMG
                                                , model.getPosterPath())
                        )
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .transform(BlurTransformation.init(imgMovie.getContext()))
                        .placeholder(R.drawable.ic_placeholder)
                        .transition(DrawableTransitionOptions.withCrossFade())
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
