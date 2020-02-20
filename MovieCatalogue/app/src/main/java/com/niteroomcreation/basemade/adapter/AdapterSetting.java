package com.niteroomcreation.basemade.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Septian Adi Wijaya on 20/02/2020.
 * please be sure to add credential if you use people's code
 */
public class AdapterSetting extends RecyclerView.Adapter<AdapterSetting.SettingHolder> {

    @NonNull
    @Override
    public SettingHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SettingHolder settingHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class SettingHolder extends RecyclerView.ViewHolder {

        public SettingHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
