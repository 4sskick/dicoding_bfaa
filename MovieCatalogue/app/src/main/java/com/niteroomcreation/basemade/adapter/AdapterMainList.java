package com.niteroomcreation.basemade.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.models.MoviesModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Septian Adi Wijaya on 05/11/19
 */
public class AdapterMainList extends BaseAdapter {

    private List<MoviesModel> movies;

    public AdapterMainList(List<MoviesModel> movies) {
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public MoviesModel getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.i_movie, parent, false);
        }

        MainListViewHolder vh = new MainListViewHolder(view);
        vh.bind(getItem(position));

        return view;
    }

    class MainListViewHolder {

        @BindView(R.id.txt_item_name)
        TextView txtName;
        @BindView(R.id.txt_item_desc)
        TextView txtDesc;
        @BindView(R.id.img_item_photo)
        AppCompatImageView imgMovie;

        MainListViewHolder(@NonNull View view) {
            ButterKnife.bind(this, view);
        }

        void bind(MoviesModel movie) {

            txtName.setText(movie.getName());
            txtDesc.setText(movie.getDesc());

            Glide.with(imgMovie.getContext())
                    .load(movie.getPoster())
                    .apply(new RequestOptions().override(55, 55))
                    .into(imgMovie);
        }
    }
}
