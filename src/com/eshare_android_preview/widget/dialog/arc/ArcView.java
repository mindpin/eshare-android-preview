package com.eshare_android_preview.widget.dialog.arc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 13-10-31.
 */
public class ArcView extends View {
    private float arc_angle;
    private int arc_acolor;

    public ArcView(Context context) {
        super(context);
    }

    public ArcView(Context context, AttributeSet attrs) {
        super(context, attrs);

        arc_acolor = Color.parseColor(attrs.getAttributeValue(null, "arc_color"));
        arc_angle = attrs.getAttributeIntValue(null,"arc_angle",20);
    }

    public void set_arc_angle(float arc_angle){
        this.arc_angle = arc_angle;
        invalidate();
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

//      根据 android:background 绘制背景色
        this.getBackground().draw(canvas);
//      画扇形
        Paint p = new Paint();
        p.setColor(arc_acolor);// 设置圆弧颜色
        p.setStyle(Paint.Style.STROKE);//设置用笔刷
        int p_width = getWidth() / 8;
        p.setStrokeWidth(p_width);//设置用笔刷宽度
//      四个参数分别表示  left, top, right, bottom,
//      也就是坐标系中的两个点 左上和右下
//      RectF 表示一个矩形区域
        RectF oval = new RectF(p_width, p_width, getWidth()-p_width, getHeight()-p_width);
//      画扇形的API，五个参数分别是 矩形区域，起始的角度，弧形的完整角度,是否显示圆弧与圆心的半径连线，画笔
        canvas.drawArc(oval, -90, arc_angle, false, p);
    }
}
