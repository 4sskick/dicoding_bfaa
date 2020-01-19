package com.niteroomcreation.basemade.base;

/**
 * Created by Septian Adi Wijaya on 03/09/19
 */
public interface IBaseView {

    void showLoading();

    void hideLoading();

    boolean isShownLoading();

    void showMessage(String message);

    void showErrorMessage(String messageRes, int messageResAction);

    void showEmptyState();

    void hideEmptyState();

    default void showBackButtonToolbar(boolean show) {
    }

    default void showTitleToolbar(boolean show, String title) {
    }

    //used in case you wanna show the empty state inside class fragment
    //by replacing the current fragment with another layout class
    default void showOverrideEmptyState() {

    }
}
