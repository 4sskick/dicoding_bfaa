package com.niteroomcreation.basemade.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.niteroomcreation.basemade.R;
import com.niteroomcreation.basemade.base.BaseFragmentView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Septian Adi Wijaya on 06/01/2020.
 * please be sure to add credential if you use people's code
 */
public class EmptyFragment extends BaseFragmentView {

    private static final String TAG = EmptyFragment.class.getSimpleName();
    private static final String KEY_EMPTY_TXT = "extra.empty.txt";

    private EmptyListener mListener;

    @BindView(R.id.s_tv_footer)
    TextView txtFooter;

    public static EmptyFragment newInstance(String txtEmptyExplanation, EmptyListener mListener) {

        Bundle b = new Bundle();
        b.putString(KEY_EMPTY_TXT, txtEmptyExplanation);

        EmptyFragment fragment = new EmptyFragment();
        fragment.setArguments(b);
        fragment.setListener(mListener);
        return fragment;
    }

    public void setListener(EmptyListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected int contentLayout() {
        return R.layout.f_empty;
    }

    @Override
    protected void initComponents() {
        Log.e(TAG, "initComponents: here me!?");

        if (getArguments() != null)
            txtFooter.setText(getArguments().getString(KEY_EMPTY_TXT));
    }

    @OnClick({R.id.s_wrap_footer})
    void onClickedView() {
        mListener.onEmptyClickedView();
    }

    public interface EmptyListener {
        void onEmptyClickedView();
    }
}
