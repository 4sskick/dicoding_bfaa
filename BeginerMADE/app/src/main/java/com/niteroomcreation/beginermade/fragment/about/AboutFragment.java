package com.niteroomcreation.beginermade.fragment.about;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.niteroomcreation.beginermade.R;
import com.niteroomcreation.beginermade.base.BaseDialogView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Septian Adi Wijaya on 04/10/19
 */
public class AboutFragment extends BaseDialogView implements AboutFragmentContract.View, BaseDialogView.DialogInterface {

    @BindView(R.id.c_actionbar_title)
    TextView txtToolbarTitle;

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    protected int setDialogView() {
        return R.layout.f_about;
    }

    @Override
    public int getDialogMode() {
        return MODE_CUSTOM;
    }

    @Override
    protected String getDialogTitle() {
        return null;
    }

    @Override
    protected String getDialogSubTitle() {
        return null;
    }

    @Override
    protected void initComponent(View view) {
        txtToolbarTitle.setText("Tentang");
    }

    @Override
    public boolean isShownLoading() {
        return false;
    }

    @OnClick({R.id.c_actionbar_ic_back, R.id.img_linkedin, R.id.img_github, R.id.img_wordpress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.c_actionbar_ic_back:
                dismiss();
                break;

            case R.id.img_github:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/4sskick")));
                break;

            case R.id.img_linkedin:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/septian-wijaya/")));
                break;

            case R.id.img_wordpress:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://ceritakodok.wordpress.com/")));
                break;
        }
    }
}
