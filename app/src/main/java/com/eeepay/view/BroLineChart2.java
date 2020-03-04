package com.eeepay.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.eeepay.activity.R;
import com.eeepay.utils.AllUtils;
import com.eeepay.utils.MathUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 描述：自定义金额转万元显示、转2位小数点
 * 作者：LiangAn
 * 时间: 2019年1月7日 15:28:56
 * 备注:
 */

public class BroLineChart2 extends View {

    private Context mContext;
    //轴线画笔
    private Paint asixPaint;
    //趋势图线画笔
    private Paint linePaint;
    //轴坐标值画笔
    private Paint textPaint;
    //点坐标值画笔
    private Paint mTextPaint;
    //格线画笔
    private Paint gridPaint;
    //外圆画笔
    private Paint pointPaint;
    //内圆画笔
    private Paint mPointPaint;
    private Paint shaderPaint;

    private int lineColor;
    private int xTextColor;
    private int yTextColor;
    private int girdColor;
    private int asixColor;
    private int pointColor;
    private int pointTextColor;
    //曲线下方的填充色
    private int shaderColor;

    private int[] datas = new int[]{};
    private List<String> yString = new ArrayList<>();
    private List<String> xString = new ArrayList<>();
    private int max = 100;
    //y轴长度
    private int yAsixLength;
    //x轴长度
    private int xAsixLength;
    //x轴到底部距离
    private int marginBottom = 100;
    private int marginTop = 0;
    private int marginLeft = 10;
    //点距离y轴的距离
    private boolean showGrid = false;
    //点半径
    private int radius = 8;
    private int textSize = 35;
    private String xLabel = "";
    private String yLabel = "";
    private Shader mShader;
    //字体宽度
    private float textWidth;
    private Point[] mPoints;

    private boolean yLabelToDouble = false;//y轴数据显示身份转double

    public BroLineChart2(Context context) {
        super(context);
        mContext = context;
    }

    public BroLineChart2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BroLineChart);
        showGrid = typedArray.getBoolean(R.styleable.BroLineChart_showGrid, false);
        lineColor = typedArray.getColor(R.styleable.BroLineChart_broLineColor, AllUtils.getResColor(mContext, R.color.color_0D83FF));
        xTextColor = typedArray.getColor(R.styleable.BroLineChart_xTextColor, AllUtils.getResColor(mContext, R.color.color_9197A6));
        yTextColor = typedArray.getColor(R.styleable.BroLineChart_yTextColor, AllUtils.getResColor(mContext, R.color.color_9197A6));
        asixColor = typedArray.getColor(R.styleable.BroLineChart_asixColor, AllUtils.getResColor(mContext, R.color.color_DCDFE8));
        pointColor = typedArray.getColor(R.styleable.BroLineChart_pointColor, AllUtils.getResColor(mContext, R.color.color_499FF9));
        pointTextColor = typedArray.getColor(R.styleable.BroLineChart_valueTextColor, AllUtils.getResColor(mContext, R.color.color_333333));
        girdColor = typedArray.getColor(R.styleable.BroLineChart_girdColor, AllUtils.getResColor(mContext, R.color.color_E9EDF5));
        shaderColor = typedArray.getColor(R.styleable.BroLineChart_shaderColor, AllUtils.getResColor(mContext, R.color.color_529BFF));
        xLabel = typedArray.getString(R.styleable.BroLineChart_xLabel);
        yLabel = typedArray.getString(R.styleable.BroLineChart_yLabel);
    }

    public void setDatas(int[] datas) {
        this.datas = new int[datas.length + 2];
        this.datas[0] = datas[0] / 2;
        for (int i = 0; i < datas.length; i++) {
            this.datas[i + 1] = datas[i];
        }
        this.datas[datas.length + 1] = datas[datas.length - 1] / 2;
        invalidate();
    }

    public void setYString(List<String> yString) {
        this.yString.clear();

        this.yString.addAll(yString);
        invalidate();
    }

    public void setXString(List<String> xString) {
        this.xString.clear();
        this.xString.add("");
        for (String str : xString) {
            this.xString.add(str);
        }
        this.xString.add("");
        this.xString.addAll(xString);
        invalidate();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
        invalidate();
    }

    public String getxLabel() {
        return xLabel;
    }

    public void setxLabel(String xLabel) {
        this.xLabel = xLabel;
    }

    public String getyLabel() {
        return yLabel;
    }

    public void setyLabel(String yLabel) {
        this.yLabel = yLabel;
        invalidate();
    }

    public boolean isyLabelToDouble() {
        return yLabelToDouble;
    }

    public void setyLabelToDouble(boolean yLabelToDouble) {
        this.yLabelToDouble = yLabelToDouble;
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

        mTextPaint = new Paint();
        mTextPaint.setColor(pointTextColor);
        mTextPaint.setStrokeWidth(3);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setAntiAlias(true);

        gridPaint = new Paint();
        gridPaint.setColor(girdColor);
        gridPaint.setStrokeWidth(3);
        gridPaint.setAntiAlias(true);

        pointPaint = new Paint();
        pointPaint.setColor(pointColor);
        pointPaint.setStrokeWidth(10);
        pointPaint.setStyle(Paint.Style.STROKE);
        pointPaint.setAntiAlias(true);

        mPointPaint = new Paint();
        mPointPaint.setColor(AllUtils.getResColor(mContext, R.color.white));
        mPointPaint.setStrokeWidth(3);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setAntiAlias(true);

        linePaint = new Paint();
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(10);
        linePaint.setAntiAlias(true);
        linePaint.setTextSize(textSize);
        linePaint.setStyle(Paint.Style.STROKE);

        shaderPaint = new Paint();
        shaderPaint.setAntiAlias(true);
        shaderPaint.setAlpha(18);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        initPaint();
        xAsixLength = getMeasuredWidth();
        //绘制x轴说明文字
        if (!TextUtils.isEmpty(xLabel)) {
            //x轴单位宽度
            float xLabelWidth = textPaint.measureText(xLabel);
//            marginRight = (int) (textPaint.measureText(xLabel) + 20);
            canvas.drawText(xLabel, xAsixLength - xLabelWidth, getMeasuredHeight() - marginBottom - 20, textPaint);
        }
        //绘制y轴说明文字
        if (!TextUtils.isEmpty(yLabel)) {
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            float yLabelHeight = fontMetrics.bottom - fontMetrics.top;
            marginTop = (int) (yLabelHeight + 60);
            canvas.drawText(yLabel, marginLeft, marginTop / 3, textPaint);
        }
        //绘制XY轴
        canvas.drawLine(0, getMeasuredHeight() - marginBottom, xAsixLength, getMeasuredHeight() - marginBottom, asixPaint);//X轴
//        canvas.drawLine(marginLeft, marginTop, marginLeft, getMeasuredHeight() - marginBottom, asixPaint);//Y轴

        yAsixLength = getMeasuredHeight() - marginBottom - marginTop;

        int currPointX;
        int currPointY;
        mPoints = new Point[datas.length];
        mPoints[0] = new Point(0, (yAsixLength - marginTop) / 2);
        for (int i = 0; i < datas.length; i++) {
            currPointX = i * xAsixLength / (datas.length - 1);
            currPointY = (yAsixLength - (datas[i] * yAsixLength / max) + marginTop);
            mPoints[i] = new Point(currPointX, currPointY);
            if (showGrid) {//绘制网格
                //x轴间隔
                if (i != 0 && i != datas.length - 1)
                    canvas.drawLine(i * xAsixLength / (datas.length - 1), getMeasuredHeight() - marginBottom,
                            i * xAsixLength / (datas.length - 1), marginTop / 2, gridPaint);
//                //y轴间隔
//                canvas.drawLine(0, i * ySpaceLine / 5 + rest,
//                        ((datas.length - 1) * getMeasuredWidth() / datas.length), i * ySpaceLine / 5 + rest, gridPaint);
            }
        }
        drawScrollLine(canvas);

        for (int i = 0; i < datas.length; i++) {

            if (i != 0 && i != datas.length - 1) {
                //绘制点
                canvas.drawCircle(mPoints[i].x, mPoints[i].y - 2, radius, pointPaint);
                canvas.drawCircle(mPoints[i].x, mPoints[i].y - 2, radius, mPointPaint);
                // 绘制点的值
                String transDatas = "";
                double merData = datas[i];
                if (yLabelToDouble) {//转万元显示、保留两位小数点
                    merData = (double) merData / 10000;
                    transDatas = MathUtil.twoNumber(String.valueOf(merData).toString());
                } else {
                    transDatas = String.valueOf(merData);
                }
                textWidth = mTextPaint.measureText(transDatas);
                canvas.drawText(transDatas, mPoints[i].x - textWidth / 2, mPoints[i].y - 30, mTextPaint);
            }
            //绘制x轴底部文字
            if (xString != null && xString.size() > 0) {
                textWidth = textPaint.measureText(xString.get(i));
                canvas.drawText(xString.get(i), i * xAsixLength / (datas.length - 1) - (textWidth / 2), getMeasuredHeight() - marginBottom / 2, textPaint);
            }
        }
        //            if (datas != null && datas.length > 0)
//                if (i < datas.length - 1) {
//                    //绘制折线
//                    linePaint.setColor(lineColor);
//                    nextPointX = (i + 1) * xAsixLength / datas.length + offsetLeft;
//                    nextPointY = (yAsixLength - (datas[i + 1] * yAsixLength / max) + marginTop);
//                    //新建一个线性渐变，前两个参数是渐变开始的点坐标，第三四个参数是渐变结束的点的坐标。
//                    // 下一个参数是渐变颜色的分布，如果为空，每个颜色就是均匀分布的。最后是模式，这里设置的是循环渐变
////                    Path path = new Path();
////                    path.moveTo(mPoints[i].x, mPoints[i].y);
////                    path.lineTo(nextPointX, nextPointY);
////                    path.lineTo(nextPointX, getMeasuredHeight() - marginBottom - 5);
////                    path.lineTo(mPoints[i].x, getMeasuredHeight() - marginBottom - 5);
////                    path.lineTo(mPoints[i].x, mPoints[i].y);
////                    path.close();
////                    mShader = new LinearGradient(mPoints[i].x, mPoints[i].y, mPoints[i].x, getMeasuredHeight() - marginBottom,
////                            new int[]{AllUtils.getResColor(mContext, R.color.color_F1F8FF), AllUtils.getResColor(mContext, R.color.color_F1F8FF)},
////                            null, Shader.TileMode.CLAMP);
////                    shaderPaint.setShader(mShader);
////                    canvas.drawPath(path, shaderPaint);
//                }
    }

    /**
     * 绘制曲线-曲线图
     *
     * @param canvas
     */
    private void drawScrollLine(Canvas canvas) {
        Point startp;
        Point endp;
        for (int i = 0; i < mPoints.length - 1; i++) {
            startp = mPoints[i];
            endp = mPoints[i + 1];
            int wt = (startp.x + endp.x) / 2;
            Point p3 = new Point();
            Point p4 = new Point();
            p3.y = startp.y;
            p3.x = wt;
            p4.y = endp.y;
            p4.x = wt;
            Path path = new Path();
            path.moveTo(startp.x, startp.y);
            path.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
            canvas.drawPath(path, linePaint);
            float nextPointX = (i + 1) * xAsixLength / (datas.length - 1);
//          float nextPointY = (yAsixLength - (datas[i + 1] * yAsixLength / max) + marginTop);
            //新建一个线性渐变，前两个参数是渐变开始的点坐标，第三四个参数是渐变结束的点的坐标。
            // 下一个参数是渐变颜色的分布，如果为空，每个颜色就是均匀分布的。最后是模式，这里设置的是循环渐变
            path.lineTo(nextPointX, getMeasuredHeight() - marginBottom - 1);
            path.lineTo(mPoints[i].x, getMeasuredHeight() - marginBottom - 1);
            path.lineTo(mPoints[i].x, mPoints[i].y);
            path.close();
            mShader = new LinearGradient(mPoints[i].x, mPoints[i].y, mPoints[i].x, getMeasuredHeight() - marginBottom,
                    new int[]{AllUtils.getResColor(mContext, R.color.color_529BFF), AllUtils.getResColor(mContext, R.color.color_529BFF)},
                    null, Shader.TileMode.CLAMP);
            shaderPaint.setShader(mShader);
            canvas.drawPath(path, shaderPaint);
        }
    }

}
