package com.niteroomcreation.moviewatchfavs.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.niteroomcreation.moviewatchfavs.R;
import com.niteroomcreation.moviewatchfavs.data.local.entity.MovieEntity;

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


    public void setData(List<MovieEntity> data) {
        this.movies = data;
        notifyDataSetChanged();
    }

    class MainHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.stack_txt_item_name)
        TextView txtName;

        public MainHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void binds() {
        }
    }
}
