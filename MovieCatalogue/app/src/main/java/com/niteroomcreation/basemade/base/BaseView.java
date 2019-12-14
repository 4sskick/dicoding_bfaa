package com.niteroomcreation.basemade.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.view.GenericStateView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Septian Adi Wijaya on 03/09/19
 */
public abstract class BaseView extends AppCompatActivity implements IBaseView,
        BaseFragmentView.BaseFragmentCallback {

    public final int EMPTY_LAYOUT = 0;

    @Nullable
    @BindView(R.id.layout_content)
    RelativeLayout layoutContent;
    @Nullable
    @BindView(R.id.layout_empty)
    GenericStateView layoutGenericPurpose;

    private Activity mActivity;
    private FragmentManager fragmentManager;
    private Toast mToast;

    protected abstract int parentLayout();

    protected abstract int contentLayout();

    abstract protected void initComponents();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (parentLayout() != EMPTY_LAYOUT)
            setContentView(parentLayout());
        else
            setContentView(R.layout.b_activity);

        if (parentLayout() == EMPTY_LAYOUT)
            if (contentLayout() != EMPTY_LAYOUT)
                try {
                    layoutContent = findViewById(R.id.layout_content);
                    LayoutInflater inflater =
                            (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
                    inflater.inflate(contentLayout(), layoutContent);
                } catch (Exception e) {
                    throw new IllegalStateException("Inflating contentLayout() failed on " + this.getClass().getSimpleName());
                }
            else
                throw new IllegalStateException("contentLayout() can't be EMPTY " + this.getClass().getSimpleName());

        ButterKnife.bind(this);
        mActivity = this;
        fragmentManager = ((BaseView) mActivity).getSupportFragmentManager();
        hideEmptyState();
        initComponents();
    }

    @Override
    public void showBackButtonToolbar(boolean show) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(show);
        }
    }

    @Override
    public void showTitleToolbar(boolean show, @Nullable String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(show ? title : "");
        }
    }

    @Override
    public void showLoading() {
        if (layoutGenericPurpose != null) {
            layoutGenericPurpose.showLoadingState();
        } else
            throw new RuntimeException("layout empty null");
    }

    @Override
    public void hideLoading() {
        hideEmptyState();
    }

    @Override
    public boolean isShownLoading() {
        return false;
    }

    @Override
    public void showMessage(String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }

        if (message != null && !message.isEmpty()) {
            mToast = Toast.makeText(this, message, Toast.LENGTH_LONG);
            mToast.show();
        }
    }

    @Override
    public void showErrorMessage(int messageRes, int messageResAction) {

    }

    @Override
    public void showEmptyState() {
        hideEmptyState();

        if (layoutGenericPurpose != null) {
            layoutGenericPurpose.setListener(new GenericStateView.GenericStateListener() {
                @Override
                public void onActionClicked() {
                    Log.e("tagTes", "onActionClicked: callng me? onActionClicked()");
                }

                @Override
                public void onFooterClicked() {
                    Log.e("tagTes", "onActionClicked: callng me? onFooterClicked()");
                }
            });
            layoutGenericPurpose.showEmptyState();
        }
    }

    @Override
    public void hideEmptyState() {
        if (layoutGenericPurpose != null)
            layoutGenericPurpose.hideAll();
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    public void moveToFragment(int viewIdFrameLayout
            , BaseFragmentView fragment
            , String fragmentTag) {
        try {
            fragmentManager.beginTransaction()
                    .replace(viewIdFrameLayout, fragment, fragmentTag)
                    .commit();
        } catch (Exception e) {
            throw new IllegalStateException(String.format("Seems like fragmentManager isn't " +
                    "initialized %s", e.getMessage()));
        }
    }

    public FragmentManager getBaseFragmentManager() {
        return fragmentManager;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
