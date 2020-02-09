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
 * Created by Septian Adi Wijaya on 09/02/2020.
 * please be sure to add credential if you use people's code
 */
public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.SearchViewHolder> {

    private static final String TAG = AdapterSearch.class.getSimpleName();

    private List<FavsObjectItem> searchObjItems;
    private GenericItemListener<FavsObjectItem, List<Pair<View, String>>> listener;

    public AdapterSearch(List<FavsObjectItem> searchObjItems
            , GenericItemListener<FavsObjectItem, List<Pair<View, String>>> listener) {
        this.searchObjItems = searchObjItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.i_favs,
                viewGroup, false);

        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {
        searchViewHolder.binds();
    }

    @Override
    public int getItemCount() {
        return searchObjItems != null ? searchObjItems.size() : 0;
    }

    private FavsObjectItem getItem(int pos) {
        return searchObjItems != null ? searchObjItems.get(pos) : null;
    }

    public void setData(List<FavsObjectItem> data) {
        this.searchObjItems = data;
        refresh();
    }

    public void refresh() {
        notifyDataSetChanged();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_item_name)
        TextView txtName;
        @BindView(R.id.txt_item_desc)
        TextView txtDesc;
        @BindView(R.id.txt_entity_type)
        TextView txtType;
        @BindView(R.id.img_item_photo)
        AppCompatImageView imgObjItem;

        private SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void binds() {
            final FavsObjectItem model = getItem(getAdapterPosition());

            if (model != null) {
                txtName.setText(model.getTitle());
                txtDesc.setText(model.getOverview());
                txtType.setText(model.getTypeObjectStr(txtType.getContext(),
                        model.getTypeObject()));

                if(model.getPosterPath()!= null) {
                    ImageUtils imageUtils = ImageUtils.init(imgObjItem.getContext());
                    imageUtils.setFileName(String.format("%s_%s"
                            , model.getPosterPath().split("/")[1].split(".jpg")[0]
                            , model.getTitle()));

                    Glide.with(imgObjItem.getContext())
                            .asBitmap()
                            .load(
                                    imageUtils.load() != null ?
                                            imageUtils.load() :
                                            model.getFullPosterPath(true)
                            )
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .transform(BlurTransformation.init(imgObjItem.getContext()))
                            .placeholder(R.drawable.ic_placeholder)
                            .error(R.drawable.ic_placeholder)
                            .into(new SimpleTarget<Bitmap>(200, 100) {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource,
                                                            @Nullable Transition<? super Bitmap> transition) {
                                    imgObjItem.setImageBitmap(resource);
                                }
                            });
                }else
                    Glide.with(imgObjItem.getContext())
                    .load(R.drawable.ic_placeholder)
                    .into(imgObjItem);

            }
        }

        @OnClick({R.id.layout_item})
        void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.layout_item:

                    Pair<View, String> t1 = Pair.create(imgObjItem, "anim_enter_item_img");
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
