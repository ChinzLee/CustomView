package com.eeepay.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.eeepay.activity.R;

/**
 * Created by Ching on 2017/8/building.
 */

public class ArcProgressBar extends View {

    private Paint paint;
    private Context context;
    private int used = 75;
    private int max = 100;
    private int progress = 0;

    public ArcProgressBar(Context context) {
        super(context);
        this.context = context;
    }

    public ArcProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.paint = new Paint();
        this.context = context;
    }

    public void setUsed(int used) {
        this.used = used;
        invalidate();
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public int getProgress() {
        return progress;
    }

    public int getUsed(){
        return used;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int centerX = getMeasuredWidth() / 2;
        int centerY = getMeasuredHeight() / 2;
        int radius = getMeasuredWidth() / 3;

        paint.setColor(context.getResources().getColor(R.color.white));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);//矩形弧边
        paint.setStrokeWidth(35);

        RectF rectF = new RectF();
        rectF.left = centerX - radius;
        rectF.right = centerX + radius;
        rectF.top = centerY - radius;
        rectF.bottom = centerY + radius;
        canvas.drawArc(rectF, 135, 270, false, paint);

        paint.setColor(context.getResources().getColor(R.color.unify_btn_normal));
        canvas.drawArc(rectF, 135, (progress * 270) / max, false, paint);

        Paint textPaint = new Paint();
        textPaint.setTextSize(50);
        textPaint.setColor(context.getResources().getColor(R.color.white));
        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        Rect usedRect = new Rect();
        String usedStr = "已使用";
        textPaint.getTextBounds(usedStr, 0, usedStr.length(), usedRect);
        int usedX = centerX - usedRect.width() / 2;
        textPaint.setColor(context.getResources().getColor(R.color.white));
        canvas.drawText(usedStr, usedX, centerY - radius * 0.7f, textPaint);

        Paint ratioPaint = new Paint();
        ratioPaint.setTextSize(180);
        ratioPaint.setColor(context.getResources().getColor(R.color.white));
        ratioPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        Rect ratioRect = new Rect();
        String ratioStr = "85%";
        ratioPaint.getTextBounds(ratioStr, 0, ratioStr.length(), ratioRect);
        int ratioX = centerX - ratioRect.width() / 2;
        int ratioY = centerY + ratioRect.height() / 2;
        canvas.drawText(ratioStr, ratioX, ratioY, ratioPaint);

        Paint totalPaint = new Paint();
        totalPaint.setTextSize(80);
        totalPaint.setColor(context.getResources().getColor(R.color.white));
        totalPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        Rect totalRect = new Rect();
        String totalStr = "1.7G/2.0G";
        totalPaint.getTextBounds(totalStr, 0, totalStr.length(), totalRect);
        int totalX = centerX - totalRect.width() / 2;
        float totalY = centerY + radius * 0.8f;
        canvas.drawText(totalStr, totalX, totalY, totalPaint);

    }

    //  获取字体串宽度
    private int getStringWidth(String str) {
        return (int) paint.measureText(str);
    }
}
