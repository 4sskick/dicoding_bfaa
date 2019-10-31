package com.niteroomcreation.basemade.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import com.niteroomcreation.basemade.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by Septian Adi Wijaya on 04/10/19
 */
public abstract class BaseDialogView extends DialogFragment implements IBaseDialogView {

    public static final int MODE_DEFAULT = 0;
    public static final int MODE_CUSTOM = 1;

    DialogInterface baseDialogInterface;

    FrameLayout bDialogContent;

    @Nullable
    @BindView(R.id.b_dialog_wrap_button)
    LinearLayout bDialogWrapButton;

    @Nullable
    @BindView(R.id.b_dialog_ic_back)
    AppCompatImageView bDialogIcBack;

    @Nullable
    @BindView(R.id.b_dialog_ic_close)
    AppCompatImageView bDialogIcClose;

    @Nullable
    @BindView(R.id.b_dialog_title)
    AppCompatTextView bDialogTitle;

    @Nullable
    @BindView(R.id.b_dialog_sub_title)
    AppCompatTextView bDialogSubTitle;

    @Nullable
    @BindView(R.id.b_dialog_parent)
    ConstraintLayout bDialogParent;

    private boolean dismissible;
    private BaseView mActivity;

    protected abstract int setDialogView();

    protected abstract int getDialogMode();

    public interface DialogInterface {
        default int getDialogMode() {
            return MODE_DEFAULT;
        }
    }

    protected abstract String getDialogTitle();

    protected abstract String getDialogSubTitle();

    protected abstract void initComponent(View view);

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppTheme_DialogFullscreen);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        if (baseDialogInterface == null) {
//            Log.e("tagCheck", "onCreateView: baseDialogInterface null");
////            dismiss();
//        } else {
//            Log.e("tagCheck", "onCreateView: baseDialogInterface not null");
//        }

        View view = inflater.inflate(/*baseDialogInterface.*/getDialogMode() == MODE_DEFAULT
                ? R.layout.b_dialog_fragment : setDialogView(), container, false);

        if (/*baseDialogInterface.*/getDialogMode() == MODE_DEFAULT) {
            bDialogContent = view.findViewById(R.id.b_dialog_content);

            if (setDialogView() != 0) {
                View innerView = inflater.inflate(setDialogView(), null);
                bDialogContent.addView(innerView);
            }
        }

        if (bDialogTitle != null) {
            if (getDialogTitle() != null) {
                Log.e("tagCheck", "onCreateView: getDialogTitle() not null");

                bDialogTitle.setText(getDialogTitle());
            } else {
                Log.e("tagCheck", "onCreateView: getDialogTitle() null");

                bDialogTitle.setVisibility(View.GONE);
            }
        } else {
            Log.e("tagCheck", "onCreateView: bDialogTitle  null");
        }

        if (bDialogSubTitle != null) {
            if (getDialogSubTitle() != null) {
                bDialogSubTitle.setText(getDialogSubTitle());
            } else {
                bDialogSubTitle.setVisibility(View.GONE);
            }
        }

        ButterKnife.bind(this, view);

        initComponent(view);
        setCancelable(getDismissible());

        return view;
    }

    public BaseView getParentActivity() {
        return mActivity;
    }

    @Override
    public void showLoading() {
        if (mActivity != null)
            mActivity.showLoading();
    }

    @Override
    public void hideLoading() {
        if (mActivity != null)
            mActivity.hideLoading();
    }

    @Override
    public void showMessage(String message) {
        if (mActivity != null)
            mActivity.showMessage(message);
    }

    @Override
    public void showErrorMessage(int messageRes, int messageResAction) {
        if (mActivity != null)
            mActivity.showErrorMessage(messageRes, messageResAction);
    }

    @Override
    public void showEmptyState() {
        if (mActivity != null)
            mActivity.showEmptyState();
    }

    @Override
    public void hideEmptyState() {
        if (mActivity != null)
            mActivity.hideEmptyState();
    }

    @Override
    public void onStart() {
        super.onStart();

        //stretch it to screen size
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseView) {
            BaseView mActivity = (BaseView) context;
            mActivity.onFragmentAttached();
            this.mActivity = mActivity;

            Log.e("tagCheck", "onAttach: baseview");
        }
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    public void setDismissible(boolean dismissible) {
        this.dismissible = dismissible;
    }

    public boolean getDismissible() {
        return dismissible;
    }

    @Override
    public void dismiss() {
        try {
            dismissAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDismissDialog(String dialogTag) {
        dismiss();
        getParentActivity().onFragmentDetached(dialogTag);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (isAdded())
            return;

        //looking for previous fragment to remove and change to the new one
        try {
            FragmentTransaction fTransaction = manager.beginTransaction();
            Fragment fPrev = manager.findFragmentByTag(tag);

            if (fPrev != null)
                fTransaction.remove(fPrev);

            fTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fTransaction.add(this, tag);
            fTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Optional
    @OnClick({R.id.b_dialog_dismiss_area, R.id.b_dialog_confirm, R.id.b_dialog_ic_back, R.id.b_dialog_ic_close})
    public void onViewClicked(View view) {

        if (getDismissible()) {
            switch (view.getId()) {
                case R.id.b_dialog_dismiss_area:
                case R.id.b_dialog_ic_close:
                    dismiss();
                    break;
            }
        }

        switch (view.getId()) {
            case R.id.b_dialog_confirm:
                break;
            case R.id.b_dialog_ic_back:
                break;
        }
    }
}
