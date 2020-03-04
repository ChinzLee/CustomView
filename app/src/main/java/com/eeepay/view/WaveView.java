package com.eeepay.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.eeepay.activity.R;

public class WaveView extends View {

    private Context context;
    private int width = 0;
    private int height = 0;
    private int baseLine = 0;//基线，用于控制水位上涨的
    private Paint mPaint;
    private int waveHeight = 100;//波浪高度
    private int waveWidth;//波长
    private float offset = 0f;//偏移量

    public WaveView(Context context) {
        super(context);
        this.context = context;
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(context.getResources().getColor(R.color.unify_blue));
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(getPath(), mPaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        waveWidth = width;
        baseLine = height / 2;
        updateXControl();
    }

    /**
     * 不断的更新偏移量，并且循环。
     */
    private void updateXControl() {
        final ValueAnimator mAnimator = ValueAnimator.ofFloat(0, waveWidth);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatorValue = (float) mAnimator.getAnimatedValue();
                offset = animatorValue;
                postInvalidate();
            }
        });
        mAnimator.setDuration(1000);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.start();
    }

    /**
     * 核心代码，计算path
     *
     * @return
     */
    private Path getPath() {
        int itemWidth = waveWidth / 2;//半个波长
        Path mPath = new Path();
        mPath.moveTo(-itemWidth * 3, baseLine);//起始坐标

        for (int i = -3; i < 2; i++) {
            int startX = i * itemWidth;
            mPath.quadTo(startX + itemWidth / 2 + offset,//控制点的X,（起始点X + itemWidth/2 + offset)
                    getWaveHeight(i),//控制点的Y
                    startX + itemWidth + offset,//结束点的X
                    baseLine
            );//只需要处理完半个波长，剩下的有for循环自已就添加了。
        }
        //下面这三句话很重要，它是形成了一封闭区间，让曲线以下的面积填充一种颜色，大家可以把这3句话注释了看看效果。
        mPath.lineTo(width, height);
        mPath.lineTo(0, height);
        mPath.close();
        return mPath;
    }

    //奇数峰值是正的，偶数峰值是负数
    private int getWaveHeight(int num) {
        if (num % 2 == 0) {
            return baseLine + waveWidth / 10;
        }
        return baseLine - waveWidth / 10;
    }

}
