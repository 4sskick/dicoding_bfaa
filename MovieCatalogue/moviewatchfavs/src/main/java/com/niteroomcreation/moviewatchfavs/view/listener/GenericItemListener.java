package com.niteroomcreation.moviewatchfavs.view.listener;

/**
 * Created by Septian Adi Wijaya on 01/10/19
 */
public interface GenericItemListener<M, V> {
    default void onItemClicked(M item) {
    }

    default void onItemViewClicked(M item, V view) {
    }
}
