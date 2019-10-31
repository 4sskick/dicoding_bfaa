package com.niteroomcreation.unittestmade.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by Septian Adi Wijaya on 01/10/19
 */
public class CustomTextView extends AppCompatTextView {

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(CustomTypefaceText.getInstance(context));
    }
}
