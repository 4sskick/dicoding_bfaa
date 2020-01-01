package com.niteroomcreation.basemade.view.custom_text;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


/**
 * Created by Septian Adi Wijaya on 01/10/19
 */
public class BillabongText extends AppCompatTextView {

    public BillabongText(Context context) {
        super(context);
    }

    public BillabongText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BillabongText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(CustomTypefaceText.getInstance(context));
    }
}
