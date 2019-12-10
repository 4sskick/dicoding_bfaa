package com.niteroomcreation.loadermanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.niteroomcreation.loadermanager.R;

import java.util.ArrayList;

/**
 * Created by Septian Adi Wijaya on 10/12/19
 */
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private ArrayList<WeatherItems> mData = new ArrayList<>();

    public WeatherAdapter() {
    }

    public void setData(ArrayList<WeatherItems> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(final WeatherItems item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weather_items, viewGroup, false);
        return new WeatherViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder weatherViewHolder, int position) {
        weatherViewHolder.textViewNamaKota.setText(mData.get(position).getName());
        weatherViewHolder.textViewTemperature.setText(mData.get(position).getTemperature());
        weatherViewHolder.textViewDescription.setText(mData.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNamaKota;
        TextView textViewTemperature;
        TextView textViewDescription;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNamaKota = itemView.findViewById(R.id.textKota);
            textViewTemperature = itemView.findViewById(R.id.textTemp);
            textViewDescription = itemView.findViewById(R.id.textDesc);
        }
    }
}
