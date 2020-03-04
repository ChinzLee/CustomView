package com.eeepay.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.eeepay.activity.R;

public class RoundRelativeLayout extends RelativeLayout {

    private Path mPath;
    private Paint mPaint;
    private RectF mRectF;
    private float mRadius;
    private boolean isClipBackground;

    public RoundRelativeLayout(Context context) {
        this(context,null);
    }

    public RoundRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundRelativeLayout);
        mRadius = ta.getDimension(R.styleable.RoundRelativeLayout_rlRadius, 0);
        isClipBackground = ta.getBoolean(R.styleable.RoundRelativeLayout_rlClipBackground, true);
        ta.recycle();

        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectF = new RectF();

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    public void setRadius(float radius) {
        mRadius = radius;
        postInvalidate();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF.set(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isClipBackground) {
            if (Build.VERSION.SDK_INT >= 28) {
                canvas.save();
                canvas.clipPath(mPath);
                super.draw(canvas);
                canvas.restore();
            } else {
                canvas.saveLayer(mRectF, null, Canvas.ALL_SAVE_FLAG);
                super.draw(canvas);
                canvas.drawPath(getPath(), mPaint);
                canvas.restore();
            }
        } else
            super.draw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (isClipBackground) {
            if (Build.VERSION.SDK_INT >= 28) {
                canvas.save();
                canvas.clipPath(getPath());
                super.dispatchDraw(canvas);
                canvas.restore();
            } else {
                canvas.saveLayer(mRectF, null, Canvas.ALL_SAVE_FLAG);
                super.dispatchDraw(canvas);
                canvas.drawPath(getPath(), mPaint);
                canvas.restore();
            }
        } else
            super.dispatchDraw(canvas);
    }

    private Path getPath() {
        mPath.reset();
        mPath.addRoundRect(mRectF, mRadius, mRadius, Path.Direction.CW);
        return mPath;
    }
}
