package com.niteroomcreation.unittestmade.base;

/**
 * Created by Septian Adi Wijaya on 03/09/19
 */
public interface IBaseView {

    void showLoading();

    void hideLoading();

    boolean isShownLoading();

    void showMessage(String message);

    void showErrorMessage(int messageRes, int messageResAction);

    void showEmptyState();

    void hideEmptyState();

    default void showBackButtonToolbar(boolean show) {
    }

    default void showTitleToolbar(boolean show, String title) {
    }
}
