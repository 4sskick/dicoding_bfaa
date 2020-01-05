package com.niteroomcreation.basemade.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.niteroomcreation.basemade.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Septian Adi Wijaya on 04/09/19
 */
public abstract class BaseFragmentView extends Fragment implements IBaseView {

    private static final String TAG = BaseFragmentView.class.getSimpleName();

    @Nullable
    @BindView(R.id.layout_content)
    FrameLayout layoutContentFragment;

    public BaseView mActivity;

    private static String fragmentTitle;

    protected abstract int contentLayout();

    protected abstract void initComponents();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = null;
        if (contentLayout() != ((BaseView) getActivity()).EMPTY_LAYOUT)
            view = getActivity().getLayoutInflater().inflate(contentLayout(), container, false);
        else
            throw new IllegalStateException("setDialogView() can't be EMPTY " + this.getClass()
            .getSimpleName());

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (BaseView) getActivity();

        initComponents();

    }

    /**
     * Called when using API23 and above
     *
     * @param context fragment--activity's context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof BaseView) {
            BaseView activity = (BaseView) context;
            this.mActivity = activity;
            activity.onFragmentAttached();
        }
    }

    @Override
    public void onDetach() {
        this.mActivity = null;
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static String getFragmentTitle() {
        return fragmentTitle;
    }

    public static void setFragmentTitle(String fragmentTitle) {
        BaseFragmentView.fragmentTitle = fragmentTitle;
    }

    @Override
    public void showLoading() {
        mActivity.showLoading();
    }

    @Override
    public void hideLoading() {
        mActivity.hideLoading();
    }

    @Override
    public boolean isShownLoading() {
        return mActivity.isShownLoading();
    }

    @Override
    public void showMessage(String message) {
        mActivity.showMessage(message);
    }

    @Override
    public void showErrorMessage(int messageRes, int messageResAction) {
        mActivity.showErrorMessage(messageRes, messageResAction);
    }

    @Override
    public void showEmptyState() {
        mActivity.showEmptyState();
    }

    @Override
    public void hideEmptyState() {
        mActivity.hideEmptyState();
    }

    @Override
    public void showBackButtonToolbar(boolean show) {
        mActivity.showBackButtonToolbar(show);
    }

    @Override
    public void showTitleToolbar(boolean show, @Nullable String title) {
        mActivity.showTitleToolbar(show, title);
    }

    public interface BaseFragmentCallback {
        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }
}
