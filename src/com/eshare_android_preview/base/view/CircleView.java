package com.eshare_android_preview.base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.eshare_android_preview.base.utils.BaseUtils;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by fushang318 on 13-11-19.
 */
public class CircleView extends View {
    private int color;
    private float cx;
    private float cy;
    private float radius;
    private int width;
    private int height;

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void set_color(int color){
        this.color = color;
        invalidate();
    }

    public void set_circle_center_position(float x_dp, float y_dp){
        this.cx = BaseUtils.dp_to_px(x_dp);
        this.cy = BaseUtils.dp_to_px(y_dp);
        invalidate();
        requestLayout();
    }

    public void set_radius(float radius_dp){
        set_radius_px(BaseUtils.dp_to_px(radius_dp));
    }

    public void set_radius_px(float radius_px) {
        this.radius = radius_px;
        invalidate();
        requestLayout();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.width = (int)(this.radius + this.cx);
        this.height = (int)(this.radius + this.cy);
        setMeasuredDimension(width, height);
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

//      画圆
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(color);// 设置圆颜色
        p.setStyle(Paint.Style.FILL);//设置填满
        canvas.drawCircle(cx,cy,radius, p);
    }
}
