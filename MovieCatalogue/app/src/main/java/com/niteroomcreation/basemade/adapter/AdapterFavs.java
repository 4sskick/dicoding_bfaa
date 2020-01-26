package com.niteroomcreation.basemade.adapter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.models.FavsObjectItem;
import com.niteroomcreation.basemade.utils.ImageUtils;
import com.niteroomcreation.basemade.view.image_utils.BlurTransformation;
import com.niteroomcreation.basemade.view.listener.GenericItemListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Septian Adi Wijaya on 25/01/2020.
 * please be sure to add credential if you use people's code
 * generic recycler view see: https://gist.github.com/Plumillon/f85c6be94e2fdaf339b9
 */
public class AdapterFavs extends RecyclerView.Adapter<AdapterFavs.MainViewHolder> {

    private static final String TAG = AdapterFavs.class.getSimpleName();

    private List<FavsObjectItem> movies;
    private GenericItemListener<FavsObjectItem, List<Pair<View, String>>> listener;

    public AdapterFavs(List<FavsObjectItem> movies
            , GenericItemListener<FavsObjectItem, List<Pair<View, String>>> listener) {
        this.movies = movies;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.i_favs,
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

    private FavsObjectItem getItem(int pos) {
        return movies != null ? movies.get(pos) : null;
    }

    public void setData(List<FavsObjectItem> data) {
        this.movies = data;
        refresh();
    }

    public void refresh(){

    }

    class MainViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_item_name)
        TextView txtName;
        @BindView(R.id.txt_item_desc)
        TextView txtDesc;
        @BindView(R.id.txt_entity_type)
        TextView txtType;
        @BindView(R.id.img_item_photo)
        AppCompatImageView imgMovie;

        private MainViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void binds() {
            final FavsObjectItem model = getItem(getAdapterPosition());

            // TODO: 24/12/19 see https://stackoverflow.com/a/48454860 for loader image

            if (model != null) {
                txtName.setText(model.getTitle());
                txtDesc.setText(model.getOverview());
                txtType.setText(model.getTypeObjectStr(txtType.getContext(), model.getTypeObject()));

                ImageUtils imageUtils = ImageUtils.init(imgMovie.getContext());
                imageUtils.setFileName(String.format("%s_%s"
                        , model.getPosterPath().split("/")[1].split(".jpg")[0]
                        , model.getTitle()));

                Glide.with(imgMovie.getContext())
                        .asBitmap()
                        .load(
                                imageUtils.load() != null ?
                                        imageUtils.load() :
                                        model.getFullPosterPath(true)
                        )
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .transform(BlurTransformation.init(imgMovie.getContext()))
                        .placeholder(R.drawable.ic_placeholder)
                        .into(new SimpleTarget<Bitmap>(200, 100) {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource,
                                                        @Nullable Transition<? super Bitmap> transition) {
                                imgMovie.setImageBitmap(resource);
                            }
                        });

            }
        }

        @OnClick({R.id.layout_item})
        void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.layout_item:

                    Pair<View, String> t1 = Pair.create(imgMovie, "anim_enter_item_img");
                    Pair<View, String> t2 = Pair.create(txtName, "anim_enter_item_name");
                    List<Pair<View, String>> a = new ArrayList<>();
                    a.add(t1);
                    a.add(t2);

                    listener.onItemViewClicked(getItem(getAdapterPosition()), a);
                    break;
            }
        }
    }
}
