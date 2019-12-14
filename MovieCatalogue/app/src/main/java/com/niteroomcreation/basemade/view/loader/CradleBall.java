package com.niteroomcreation.basemade.view.loader;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.niteroomcreation.basemade.R;

public class CradleBall extends View {

    private int width;
    private int height;

    private Paint paint;

    private int loadingColor = Color.BLACK;

    public CradleBall(Context context) {
        super(context);
        initView(null);
    }

    public CradleBall(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public CradleBall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        if (null != attrs) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                    R.styleable.CradleBall);

            try {
                loadingColor = typedArray.getColor(R.styleable.CradleBall_cradle_ball_color, Color.BLACK);
            } finally {
                typedArray.recycle();
            }
        }
        paint = new Paint();
        paint.setColor(loadingColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle((width / 2), (height / 2), (width / 2), paint);
    }

    public void setLoadingColor(int color) {
        loadingColor = color;
        paint.setColor(color);
        postInvalidate();
    }

    public int getLoadingColor() {
        return loadingColor;
    }
}
