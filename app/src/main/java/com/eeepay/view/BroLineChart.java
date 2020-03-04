package com.eeepay.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.eeepay.activity.R;
import com.eeepay.utils.AllUtils;

/**
 * Created by Ching on 2017/8/14.
 * 折线统计图 带向下渐变效果
 */

public class BroLineChart extends View {

    private Context mContext;
    private Paint asixPaint;
    private Paint linePaint;
    private Paint textPaint;
    private Paint gridPaint;
    private Paint pointPaint;
    private Paint mPointPaint;
    private Paint shaderPaint;

    private int lineColor;
    private int xTextColor;
    private int yTextColor;
    private int asixColor;
    private int pointColor;
    private int pointTextColor;

    private int[] datas = new int[]{};
    private String[] yString = new String[]{};
    private String[] xString = new String[]{};
    private int max = 100;
    private float currPointX;
    private float currPointY;
    private float nextPointX;
    private float nextPointY;
    private float yAsixLength;
    private float xAsixLength;
    private float marginBottom = 100;
    private float marginRight = 0;
    private float marginTop = 0;
    private float marginLeft = 10;
    private float pointMarginLeft = 40;//点距离y轴的距离
    private boolean showGrid = false;
    private int radius = 8;//点半径
    private int textSize = 35;
    private float offsetLeft;
    private String xLabel;
    private String yLabel;
    private Shader mShader;
    private float textWidth;

    public BroLineChart(Context context) {
        super(context);
        mContext = context;
    }

    public BroLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BroLineChart);
        showGrid = typedArray.getBoolean(R.styleable.BroLineChart_showGrid, false);
        lineColor = typedArray.getColor(R.styleable.BroLineChart_lineColor, AllUtils.getResColor(mContext, R.color.brown_color_D2AC6F));
        xTextColor = typedArray.getColor(R.styleable.BroLineChart_xTextColor, AllUtils.getResColor(mContext, R.color.gray_color_999999));
        yTextColor = typedArray.getColor(R.styleable.BroLineChart_yTextColor, AllUtils.getResColor(mContext, R.color.brown_color_D2AC6F));
        asixColor = typedArray.getColor(R.styleable.BroLineChart_asixColor, AllUtils.getResColor(mContext, R.color.brown_color_D2AC6F));
        pointColor = typedArray.getColor(R.styleable.BroLineChart_pointColor, AllUtils.getResColor(mContext, R.color.brown_color_D2AC6F));
        pointTextColor = typedArray.getColor(R.styleable.BroLineChart_valueTextColor, AllUtils.getResColor(mContext, R.color.gray_color_999999));
        xLabel = typedArray.getString(R.styleable.BroLineChart_xLabel);
        yLabel = typedArray.getString(R.styleable.BroLineChart_yLabel);
    }

    public void setDatas(int[] datas) {
        this.datas = datas;
        invalidate();
    }

    public void setYString(String[] yString) {
        this.yString = yString;
        invalidate();
    }

    public void setXString(String[] xString) {
        this.xString = xString;
        invalidate();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
        invalidate();
    }

    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
    }

    private void initPaint() {
        asixPaint = new Paint();
        asixPaint.setColor(asixColor);
        asixPaint.setStrokeWidth(5);
        asixPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(xTextColor);
        textPaint.setStrokeWidth(3);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);

        gridPaint = new Paint();
        gridPaint.setColor(AllUtils.getResColor(mContext, R.color.brown_color_D2AC6F));
        gridPaint.setStrokeWidth(3);
        gridPaint.setAntiAlias(true);

        pointPaint = new Paint();
        pointPaint.setColor(AllUtils.getResColor(mContext, R.color.brown_color_D2AC6F));
        pointPaint.setStrokeWidth(10);
        pointPaint.setStyle(Paint.Style.STROKE);
        pointPaint.setAntiAlias(true);

        mPointPaint = new Paint();
        mPointPaint.setColor(AllUtils.getResColor(mContext, R.color.brown_color_FAE7C7));
        mPointPaint.setStrokeWidth(3);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setAntiAlias(true);

        linePaint = new Paint();
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(5);
        linePaint.setAntiAlias(true);
        linePaint.setTextSize(textSize);

        shaderPaint = new Paint();
        shaderPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initPaint();
        xAsixLength = getMeasuredWidth() - marginLeft;
        //计算x轴说明文字宽度
        if (!TextUtils.isEmpty(xLabel)) {
            marginRight = textPaint.measureText(xLabel) + 20;
            xAsixLength = getMeasuredWidth() - marginLeft - marginRight;
            canvas.drawText(xLabel, xAsixLength + marginLeft + 10, getMeasuredHeight() - marginBottom, textPaint);
        }
        //计算y轴说明文字高度
        if (!TextUtils.isEmpty(yLabel)) {
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            float yLabelHeight = fontMetrics.bottom - fontMetrics.top;
            marginTop = yLabelHeight + 20;
            canvas.drawText(yLabel, marginLeft, marginTop / 2, textPaint);
        }
        //绘制XY轴
        canvas.drawLine(marginLeft, marginTop, marginLeft, getMeasuredHeight() - marginBottom, asixPaint);//Y轴
        canvas.drawLine(marginLeft, getMeasuredHeight() - marginBottom, xAsixLength + marginLeft, getMeasuredHeight() - marginBottom, asixPaint);//X轴

        yAsixLength = getMeasuredHeight() - marginBottom + marginTop;
        offsetLeft = marginLeft + pointMarginLeft;
        for (int i = 0; i < datas.length; i++) {
            currPointX = i * xAsixLength / datas.length + offsetLeft;
            currPointY = yAsixLength - (datas[i] * yAsixLength / max) + marginTop;
            if (i < datas.length - 1) {
                //绘制折线
                linePaint.setColor(lineColor);
                nextPointX = (i + 1) * xAsixLength / datas.length + offsetLeft;
                nextPointY = yAsixLength - (datas[i + 1] * yAsixLength / max) + marginTop;
                canvas.drawLine(currPointX, currPointY, nextPointX, nextPointY, linePaint);
                //新建一个线性渐变，前两个参数是渐变开始的点坐标，第三四个参数是渐变结束的点的坐标。
                // 下一个参数是渐变颜色的分布，如果为空，每个颜色就是均匀分布的。最后是模式，这里设置的是循环渐变
                Path path = new Path();
                path.moveTo(currPointX, currPointY);
                path.lineTo(nextPointX, nextPointY);
                path.lineTo(nextPointX, getMeasuredHeight() - marginBottom - 5);
                path.lineTo(currPointX, getMeasuredHeight() - marginBottom - 5);
                path.lineTo(currPointX, currPointY);
                path.close();
                mShader = new LinearGradient(currPointX, currPointY, currPointX, getMeasuredHeight() - marginBottom,
                        new int[]{AllUtils.getResColor(mContext, R.color.brown_color_E1C89F), Color.WHITE},
                        null, Shader.TileMode.CLAMP);
                shaderPaint.setShader(mShader);
                canvas.drawPath(path, shaderPaint);
            }
            //绘制点
            canvas.drawCircle(currPointX, currPointY, radius, pointPaint);
            canvas.drawCircle(currPointX, currPointY, radius, mPointPaint);
            // 绘制点的值
            linePaint.setColor(pointTextColor);
            textWidth = linePaint.measureText(String.valueOf(datas[i]));
            canvas.drawText(String.valueOf(datas[i]), currPointX - textWidth / 2, currPointY - 30, linePaint);
            //绘制x轴底部文字
            if (xString != null && xString.length > 0) {
                textWidth = textPaint.measureText(xString[i]);
                canvas.drawText(xString[i], i * xAsixLength / datas.length + offsetLeft - (textWidth / 2), getMeasuredHeight() - marginBottom / 2, textPaint);
            }
//            if (showGrid) {//绘制网格
//                //x轴间隔
//                canvas.drawLine(i * getMeasuredWidth() / datas.length, getMeasuredHeight(),
//                        i * getMeasuredWidth() / datas.length, rest, gridPaint);
//                //y轴间隔
//                canvas.drawLine(0, i * ySpaceLine / 5 + rest,
//                        ((datas.length - 1) * getMeasuredWidth() / datas.length), i * ySpaceLine / 5 + rest, gridPaint);
//            }
        }

    }
}
