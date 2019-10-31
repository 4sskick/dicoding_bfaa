package com.niteroomcreation.unittestmade.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.niteroomcreation.unittestmade.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by Septian Adi Wijaya on 03/09/19
 */
public class GenericStateView extends ConstraintLayout {

    View vContent;
    @BindView(R.id.s_tv_footer)
    TextView sTvFooter;
    @BindView(R.id.wrap_view)
    ConstraintLayout wrapView;
    @BindView(R.id.s_wrap_state)
    ConstraintLayout sWrapState;
    @BindView(R.id.s_progress)
    ContentLoadingProgressBar sProgress;
    @BindView(R.id.s_wrap_parent)
    ConstraintLayout sWrapParent;

    private int icon, idContent;
    private String title, subTitle, footer, button;
    private boolean buttonCaps = false;
    private boolean templateContent = false;

    private GenericStateListener mListener;

    public GenericStateView(Context context) {
        super(context);
        init();
    }

    public GenericStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeAttrs(context, attrs);
    }

    public GenericStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeAttrs(context, attrs);
    }

    private void init() {
        View view = inflate(getContext(), R.layout.v_generic_state, this);
        ButterKnife.bind(this, view);

        if (!templateContent) {
            //should set the data based on user set
        } else {
            setTemplateData();
        }
    }

    private void initializeAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.GenericStateView,
                0, 0);

        try {
            title = a.getString(R.styleable.GenericStateView_state_title);
            subTitle = a.getString(R.styleable.GenericStateView_state_sub_title);
            footer = a.getString(R.styleable.GenericStateView_state_footer);
            button = a.getString(R.styleable.GenericStateView_state_button);
            icon = a.getResourceId(R.styleable.GenericStateView_state_icon, -1);
            idContent = a.getResourceId(R.styleable.GenericStateView_state_content_view, -1);
            buttonCaps = a.getBoolean(R.styleable.GenericStateView_state_button_caps, false);
            templateContent = a.getBoolean(R.styleable.GenericStateView_state_template_empty_view, false);
        } finally {
            a.recycle();
        }
        init();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (idContent > 0) {
            ViewGroup row = (ViewGroup) getParent();
            for (int itemPos = 0; itemPos < row.getChildCount(); itemPos++) {
                View view = row.getChildAt(itemPos);
                if (view.getId() == idContent)
                    vContent = view;
            }
        }
    }

    private View getContent() {
        return vContent;
    }

    public void setListener(GenericStateListener mListener) {
        this.mListener = mListener;
    }

    public void showState() {
        showState(icon, title, subTitle, footer, button, mListener);
    }

    public void showState(int icon, String title, String subTitle, String footer, String button, GenericStateListener mListener) {
        this.icon = icon;
        this.title = title;
        this.subTitle = subTitle;
        this.footer = footer;
        this.button = button;
        this.mListener = mListener;

        if (!templateContent) {
            //setData();
            //should set the data based on user set
        } else {
            setTemplateData();
        }

        sWrapParent.setVisibility(VISIBLE);
        if (getContent() != null) getContent().setVisibility(GONE);
        sWrapState.setVisibility(VISIBLE);
        sProgress.setVisibility(VISIBLE);
    }

    private void setTemplateData() {
        sTvFooter.setVisibility(VISIBLE);
    }


    @Optional
    public void hideAll() {
        sWrapParent.setVisibility(GONE);
        if (getContent() != null) getContent().setVisibility(VISIBLE);
    }

    @OnClick({R.id.s_wrap_parent})
    void onClickedView(View view) {
        if (mListener == null) return;
        switch (view.getId()) {
            case R.id.s_wrap_parent:
                mListener.onActionClicked();
                break;
        }
    }

    public interface GenericStateListener {
        void onActionClicked();

        void onFooterClicked();
    }
}
