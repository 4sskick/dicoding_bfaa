package com.niteroomcreation.moviewatchfavs.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.niteroomcreation.moviewatchfavs.R;
import com.niteroomcreation.moviewatchfavs.utils.NavigationUtils;
import com.niteroomcreation.moviewatchfavs.view.GenericStateView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by Septian Adi Wijaya on 03/09/19
 */
public abstract class BaseView extends AppCompatActivity implements IBaseView,
        BaseFragmentView.BaseFragmentCallback {

    private static final String TAG = BaseView.class.getSimpleName();

    public final int EMPTY_LAYOUT = 0;

    @Nullable
    @BindView(R.id.layout_content)
    RelativeLayout layoutContent;
    @Nullable
    @BindView(R.id.layout_empty)
    GenericStateView layoutGenericPurpose;
    @Nullable
    @BindView(R.id.c_actionbar_title)
    TextView toolbarTextTitle;
    @Nullable
    @BindView(R.id.c_actionbar_ic_search)
    AppCompatImageView iconSearch;
    @Nullable
    @BindView(R.id.c_actionbar_ic_setting)
    AppCompatImageView iconSetting;

    private Activity mActivity;
    private FragmentManager fragmentManager;
    private Toast mToast;

    protected abstract int parentLayout();

    protected abstract int contentLayout();

    abstract protected void initComponents(@Nullable Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (parentLayout() != EMPTY_LAYOUT)
            setContentView(parentLayout());
        else
            setContentView(R.layout.b_activity);

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
        initComponents(savedInstanceState);
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

        if (toolbarTextTitle != null) {
            toolbarTextTitle.setText(show ? title : "");
        }
    }

    public void showIconSearch(boolean show) {
        if (iconSearch != null)
            iconSearch.setVisibility(show ? View.VISIBLE : View.GONE);

        if (iconSetting != null)
            iconSetting.setVisibility(show ? View.VISIBLE : View.GONE);

        if (getSupportActionBar() != null)
            setSupportActionBar(null);
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
        if (layoutGenericPurpose != null)
            return layoutGenericPurpose.isLoadingState();
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
    public void showErrorMessage(String messageRes, int messageResAction) {
        showMessage(messageRes);
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

    @Optional
    @OnClick({R.id.c_actionbar_ic_back, R.id.c_actionbar_ic_setting, R.id.c_actionbar_ic_search})
    void onItemClickedView(View view) {

        Log.e(TAG, "onItemClickedView: ");
        switch (view.getId()) {
            case R.id.c_actionbar_ic_back:
                try {
                    supportFinishAfterTransition();
                } catch (Exception e) {
                    onBackPressed();
                }
                break;

            case R.id.c_actionbar_ic_setting:
                NavigationUtils.directToLocalSetting(this);
                break;

            case R.id.c_actionbar_ic_search:
                //none to go
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                try {
                    supportFinishAfterTransition();
                } catch (Exception e) {
                    onBackPressed();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
