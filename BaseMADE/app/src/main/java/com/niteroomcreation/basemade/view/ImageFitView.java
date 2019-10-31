package com.niteroomcreation.basemade.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;


/**
 * Created by Septian Adi Wijaya on 09/10/19
 */
public class ImageFitView extends AppCompatImageView {
    public ImageFitView(Context context) {
        super(context);
    }

    public ImageFitView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageFitView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable drawable = this.getDrawable();

        if (drawable != null) {
            int width = MeasureSpec.getSize(widthMeasureSpec);

            this.setMeasuredDimension(width
                    , (int) Math.ceil((double) ((((float) width) * ((float) drawable.getIntrinsicHeight())) / ((float) drawable.getIntrinsicWidth()))));

            return;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
