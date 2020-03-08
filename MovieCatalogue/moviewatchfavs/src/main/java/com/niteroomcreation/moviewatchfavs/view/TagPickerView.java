package com.niteroomcreation.moviewatchfavs.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.niteroomcreation.moviewatchfavs.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.niteroomcreation.moviewatchfavs.utils.Utils.dpToPx;

/**
 * Created by Septian Adi Wijaya on 03/01/20
 */
public class TagPickerView extends LinearLayout {

    private static final String TAG = TagPickerView.class.getSimpleName();

    private ViewTreeObserver mViewTreeObserver;
    private LayoutInflater mInflater;
    private LinearLayout mRowItems;
    private Context mContext;
    private AttributeSet mAttrs;

    private Typeface fontType;

    private List<String> mItems = new ArrayList<>();
    private List<String> tagGenreColors = Arrays.asList(
            "#f3c27e"
            , "#66bfdf"
            , "#BE4D01"
            , "#e69700"
    );

    private int mWidth;
    private int mItemMargin = 10;
    private int textPaddingLeft = 5;
    private int textPaddingRight = 5;
    private int textPaddingTop = 5;
    private int texPaddingBottom = 5;
    private int mAddIcon = android.R.drawable.ic_menu_add;
    private int mCancelIcon = android.R.drawable.ic_menu_close_clear_cancel;
    private int mLayoutBackgroundColorNormal = R.color.blue_info;
    private int mLayoutBackgroundColorPressed = R.color.red_error;
    private int mTextColor = android.R.color.white;
    private int mRadius = 5;

    private boolean mRandomColor;
    private boolean mInitialized;

    public TagPickerView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public TagPickerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mAttrs = attrs;
        init();
    }

    public TagPickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        this.mAttrs = attrs;
        init();
    }

    @SuppressLint("ResourceAsColor")
    private void init() {
        Log.e(TAG, "init: ");

        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TypedArray typeArray = mContext.obtainStyledAttributes(mAttrs, R.styleable.TagPickerView);
        this.mItemMargin = (int) typeArray.getDimension(R.styleable.TagPickerView_tp_itemMargin, dpToPx(this.getContext(), mItemMargin));
        this.textPaddingLeft = (int) typeArray.getDimension(R.styleable.TagPickerView_tp_textPaddingLeft, dpToPx(this.getContext(), textPaddingLeft));
        this.textPaddingRight = (int) typeArray.getDimension(R.styleable.TagPickerView_tp_textPaddingRight, dpToPx(this.getContext(), textPaddingRight));
        this.textPaddingTop = (int) typeArray.getDimension(R.styleable.TagPickerView_tp_textPaddingTop, dpToPx(this.getContext(), textPaddingTop));
        this.texPaddingBottom = (int) typeArray.getDimension(R.styleable.TagPickerView_tp_textPaddingBottom, dpToPx(this.getContext(), texPaddingBottom));
        this.mAddIcon = typeArray.getResourceId(R.styleable.TagPickerView_tp_addIcon, mAddIcon);
        this.mCancelIcon = typeArray.getResourceId(R.styleable.TagPickerView_tp_cancelIcon, mCancelIcon);
        this.mLayoutBackgroundColorNormal = typeArray.getColor(R.styleable.TagPickerView_tp_itemBackgroundNormal, mLayoutBackgroundColorNormal);
        this.mLayoutBackgroundColorPressed = typeArray.getColor(R.styleable.TagPickerView_tp_itemBackgroundPressed, mLayoutBackgroundColorPressed);
        this.mRadius = (int) typeArray.getDimension(R.styleable.TagPickerView_tp_itemRadius, mRadius);
        this.mRandomColor = typeArray.getBoolean(R.styleable.TagPickerView_tp_randomColor, false);
        this.mTextColor = typeArray.getColor(R.styleable.TagPickerView_tp_itemTextColor, mTextColor);
        this.fontType = ResourcesCompat.getFont(getContext(), R.font.nunito_regular);
        typeArray.recycle();

        setOrientation(VERTICAL);
        setGravity(Gravity.START);

        mViewTreeObserver = getViewTreeObserver();
        mViewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!mInitialized) {
                    mInitialized = true;
                    drawItemView();
                }
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
    }

    /**
     * Custom Methods
     * ==============
     */
    public void setItems(List<String> items) {
        mItems = items;
        drawItemView();
    }

    public void drawItemView() {
        if (!mInitialized) {
            Log.e(TAG, "drawItemView: IF");
            return;
        }

        Log.e(TAG, "drawItemView: outer IF");

        clearItemView();

        float paddingLeftRight = getPaddingLeft() + getPaddingRight();
        int indexItemOnFront = 0;

        LayoutParams itemLayoutParams = getItemLayoutParams();

        for (int i = 0; i < mItems.size(); i++) {
            final String itemText = mItems.get(i);
            final View itemView = setupViewText(itemText);

            TextView itemTextView = (AppCompatTextView) itemView.findViewById(R.id.c_tag_item_txt);
            itemTextView.setAllCaps(true);
            itemTextView.setTypeface(fontType);
            itemTextView.setTextSize(10);
            itemTextView.setText(itemText);
            itemTextView.setTypeface(Typeface.DEFAULT_BOLD);
            itemTextView.setPadding(textPaddingLeft
                    , textPaddingTop
                    , textPaddingRight
                    , texPaddingBottom);
            itemTextView.setTextColor(getResources().getColor(mTextColor));

            //adjust width items
            float itemWidth = itemTextView.getPaint().measureText(itemText) + textPaddingLeft + textPaddingRight;
            itemWidth += dpToPx(getContext(), 20) + textPaddingLeft + textPaddingRight;

            if (mWidth <= (itemWidth + paddingLeftRight)) {
                paddingLeftRight = getPaddingLeft() + getPaddingRight();
                indexItemOnFront = i;

                addItemView(itemView, itemLayoutParams, true, i);
            } else {

                if (i != indexItemOnFront) {
                    itemLayoutParams.rightMargin = mItemMargin;
                    paddingLeftRight += mItemMargin;
                }

                addItemView(itemView, itemLayoutParams, false, i);
            }

            paddingLeftRight += itemWidth;
        }
    }

    private void clearItemView() {
        removeAllViews();
        mRowItems = null;
    }

    private LayoutParams getItemLayoutParams() {
        LayoutParams itemParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);

        itemParams.bottomMargin = mItemMargin / 2;
        itemParams.topMargin = 0;
        itemParams.rightMargin = mItemMargin;

        return itemParams;
    }

    private View setupViewText(String s) {
        View view = mInflater.inflate(R.layout.c_tag_genre, this, false);
        if (isJellyBeanAndAbove()) {
            view.setBackground(getSelector(s));
        } else {
            view.setBackgroundDrawable(getSelector(s));
        }

        return view;
    }

    private boolean isJellyBeanAndAbove() {
        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN;
    }

    private StateListDrawable getSelector(String s) {
        return getSelectorNormal();
    }

    @SuppressLint("ResourceAsColor")
    private StateListDrawable getSelectorNormal() {

        int colorChosen = new Random().nextInt(tagGenreColors.size());

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(mRandomColor ? Color.parseColor(tagGenreColors.get(colorChosen)) : mLayoutBackgroundColorPressed);
        gradientDrawable.setCornerRadius(mRadius);

        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{}, gradientDrawable);

        return states;
    }

    private void addItemView(View itemView
            , ViewGroup.LayoutParams chipParams
            , boolean newLine
            , int position) {

        if (mRowItems == null || newLine) {
            mRowItems = new LinearLayout(getContext());
            mRowItems.setGravity(Gravity.LEFT);
            mRowItems.setOrientation(HORIZONTAL);
            mRowItems.setLayoutParams(new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.WRAP_CONTENT));

            addView(mRowItems);
        }

        mRowItems.addView(itemView, chipParams);
        animateItemView(itemView, position);
    }

    private void animateItemView(View view, int position) {
        long delay = 600;

        delay += position * 30;

        view.setScaleY(0);
        view.setScaleX(0);
        view.animate()
                .scaleY(1)
                .scaleX(1)
                .setDuration(200)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(null)
                .setStartDelay(delay)
                .start();
    }

}
