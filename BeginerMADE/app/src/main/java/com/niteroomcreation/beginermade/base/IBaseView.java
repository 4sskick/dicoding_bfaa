package com.niteroomcreation.beginermade.base;

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
}
