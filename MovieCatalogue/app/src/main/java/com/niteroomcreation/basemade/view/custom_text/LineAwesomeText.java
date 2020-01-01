package com.niteroomcreation.basemade.view.custom_text;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by Septian Adi Wijaya on 01/01/2020.
 * please be sure to add credential if you use people's code
 */
public class LineAwesomeText extends AppCompatTextView {
    public LineAwesomeText(Context context) {
        super(context);
    }

    public LineAwesomeText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(CustomTypefaceText.getInstance(context, "fonts/line-awesome.ttf"));
    }

    public LineAwesomeText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
