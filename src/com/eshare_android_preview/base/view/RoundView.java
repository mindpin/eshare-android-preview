package com.eshare_android_preview.base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fushang318 on 13-10-29.
 */
public class RoundView extends View {
    private int arc_angle;
    private int arc_acolor;

    public RoundView(Context context) {
        super(context);
    }

    public RoundView(Context context, AttributeSet attrs) {
        super(context, attrs);

        arc_acolor = Color.parseColor(attrs.getAttributeValue(null, "arc_color"));
        arc_angle = attrs.getAttributeIntValue(null,"arc_angle",360);
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

//      根据 android:background 绘制背景色
        this.getBackground().draw(canvas);
//      画扇形
        Paint p = new Paint();
        p.setColor(arc_acolor);// 设置圆弧颜色
        p.setStyle(Paint.Style.FILL);//设置填满
//      四个参数分别表示  left, top, right, bottom,
//      也就是坐标系中的两个点 左上和右下
//      RectF 表示一个矩形区域
        RectF oval = new RectF(0, 0, getWidth(), getHeight());
//      画扇形的API，五个参数分别是 矩形区域，起始的角度，弧形的完整角度,是否显示圆弧与圆心的半径连线，画笔
        canvas.drawArc(oval, 30, arc_angle, true, p);
    }
}
