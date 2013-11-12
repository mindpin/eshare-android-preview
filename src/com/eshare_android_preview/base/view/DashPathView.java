package com.eshare_android_preview.base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fushang318 on 13-11-12.
 */
public class DashPathView extends View {
    boolean is_init = false;
    // 这个变量是为了产生虚线是向一个方向移动的效果
    float phase = 0;
    // 线的画笔
    Paint paint;
    // 线的路径
    Path line_path;
    // 虚线的最小构成图片（这里是一个实心小圆）
    Path dash_icon;
    // 实心小圆的半径
    int dash_icon_radius;
    // 实心小圆两个圆心之间的距离
    int hash_icon_advance;

    public DashPathView(Context context) {
        super(context);
    }

    public DashPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DashPathView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init(){
        // Paint.ANTI_ALIAS_FLAG 抗锯齿
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 画笔设置为黑色
        paint.setColor(Color.BLACK);
        // 画笔设置为填充
        paint.setStyle(Paint.Style.FILL);

        // 线的路径
        line_path = new Path();
        line_path.moveTo(0, 0);
        line_path.lineTo(getWidth(),getHeight());

        dash_icon_radius = 10;
        hash_icon_advance = 30;
        dash_icon = new Path();
        // Path.Direction.CW 表示顺时针画圆，这个参数是卖萌用的么，完全没用啊 -_-!
        dash_icon.addCircle(0,0, dash_icon_radius,Path.Direction.CW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!is_init){
            init();
            is_init = true;
        }

        PathEffect effect = build_effect(phase);
        paint.setPathEffect(effect);
        canvas.drawPath(line_path, paint);

        phase-=1;
        invalidate();
    }

    private PathEffect build_effect(float phase){
        // 线条的转弯处，用曲线平滑处理
        CornerPathEffect cpe = new CornerPathEffect(10);
        // 用自定义的小图标做虚线
        // PathDashPathEffect.Style.TRANSLATE,表示小图标的角度不受线的走向影响
        PathDashPathEffect pdpe = new PathDashPathEffect(dash_icon, hash_icon_advance, phase,
                PathDashPathEffect.Style.TRANSLATE);
        // 混合使用上面定义的两种效果
        return new ComposePathEffect(cpe, pdpe);
    }

}
