package com.eshare_android_preview.view.ui.auth;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
* Created by Administrator on 14-1-23.
*/
class SemiCircleDrawable extends Drawable {
    View view;
    Paint paint;

    public SemiCircleDrawable(View view, int color) {
        this.view = view;

        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(Canvas canvas) {
        int width = view.getWidth();
        int height = view.getHeight();

        int radius = height / 2;

        Path path = new Path();

        // 左半圆弧
        draw_semi_circle(path, 0, 0, height, 90);
        // 上边框
        path.lineTo(width - radius, 0);
        // 右半圆弧
        draw_semi_circle(path, width - height, 0, height, 270);
        // 下边框
        path.lineTo(radius, height);

        canvas.drawPath(path, paint);
    }

    // 根据传入的左上角和起始角度以半径，绘制半圆路径
    private void draw_semi_circle(Path path, int x, int y, int diameter, int start_angle) {
        RectF rect_f = new RectF(x, y, x + diameter, y + diameter);
        path.arcTo(rect_f, start_angle, 180, false);
    }

    @Override
    public void setAlpha(int i) {}

    @Override
    public void setColorFilter(ColorFilter colorFilter) {}

    @Override
    public int getOpacity() {
        return 0;
    }
}
