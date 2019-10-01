package com.niteroomcreation.beginermade.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.niteroomcreation.beginermade.R;
import com.niteroomcreation.beginermade.fragment.main.MainFragment;
import com.niteroomcreation.beginermade.model.TokohModel;
import com.niteroomcreation.beginermade.view.listener.GenericItemListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Septian Adi Wijaya on 01/10/19
 */
public class AdapterMain extends RecyclerView.Adapter<AdapterMain.ViewHolder> {

    private List<TokohModel> models;
    private GenericItemListener<TokohModel> listener;

    public AdapterMain(List<TokohModel> models, GenericItemListener<TokohModel> listener) {
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.i_main, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.binds();
    }

    public TokohModel getItem(int pos) {
        return models.get(pos);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_item_name)
        TextView txtItemName;
        @BindView(R.id.txt_item_desc)
        TextView txtItemDesc;
        @BindView(R.id.img_item_photo)
        CircleImageView imgItemPhoto;
        @BindView(R.id.layout_item)
        RelativeLayout layoutItem;

        private View itemView;

        private ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        void binds() {
            TokohModel model = getItem(getAdapterPosition());

            txtItemName.setText(model.getName());
            txtItemDesc.setText(model.getDesc());

            Glide.with(itemView.getContext())
                    .load(model.getImage())
                    .apply(new RequestOptions().override(55, 55))
                    .into(imgItemPhoto);
        }

        @OnClick({R.id.layout_item})
        void onViewItemClicked(View view) {
            switch (view.getId()) {
                case R.id.layout_item:
                    Log.e("tagCheck", "onViewItemClicked: clicking me?");
                    break;
            }
        }
    }
}
