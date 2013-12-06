package com.eshare_android_preview.base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.utils.BaseUtils;

/**
 * Created by fushang318 on 13-11-22.
 */
public class BorderRadiusRelativeLayout extends RelativeLayout{
    int border_top_left_radius = 0;
    int border_top_right_radius = 0;
    int border_bottom_right_radius = 0;
    int border_bottom_left_radius = 0;

    public BorderRadiusRelativeLayout(Context context) {
        super(context);
    }

    public BorderRadiusRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        set_radius_border_background_color(attrs);
        if(!isInEditMode()){
            set_border_radius(attrs);
        }
    }

    public BorderRadiusRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void set_border_radius(AttributeSet attrs) {
        int border_radius = attrs.getAttributeIntValue(null,"border_radius",0);
        border_radius = BaseUtils.dp_to_int_px(border_radius);
        border_top_left_radius = border_radius;
        border_top_right_radius = border_radius;
        border_bottom_right_radius = border_radius;
        border_bottom_left_radius = border_radius;

        int radius1 = attrs.getAttributeIntValue(null,"border_top_left_radius",-1);
        if(radius1 != -1){
            radius1 = BaseUtils.dp_to_int_px(radius1);
            border_top_left_radius = radius1;
        }

        int radius2 = attrs.getAttributeIntValue(null,"border_top_right_radius",-1);
        if(radius2 != -1){
            radius2 = BaseUtils.dp_to_int_px(radius2);
            border_top_right_radius = radius2;
        }

        int radius3 = attrs.getAttributeIntValue(null,"border_bottom_right_radius",-1);
        if(radius3 != -1){
            radius3 = BaseUtils.dp_to_int_px(radius3);
            border_bottom_right_radius = radius3;
        }

        int radius4 = attrs.getAttributeIntValue(null,"border_bottom_left_radius",-1);
        if(radius4 != -1){
            radius4 = BaseUtils.dp_to_int_px(radius4);
            border_bottom_left_radius = radius4;
        }
    }

    private void set_radius_border_background_color(AttributeSet attrs){
        String color_str = attrs.getAttributeValue(null,"border_radius_background_color");
        if(color_str != null){
            int color = Color.parseColor(color_str);
            setBackgroundColor(color);
        }
    }

    @Override
    public void setBackgroundColor(int color) {
        setBackgroundDrawable(new RadiusBorderColorDrawable(color, this));
    }

    public class RadiusBorderColorDrawable extends Drawable {
        private BorderRadiusRelativeLayout layout;
        private int color;

        public RadiusBorderColorDrawable(int color, BorderRadiusRelativeLayout layout){
            this.color = color;
            this.layout = layout;
        }

        @Override
        public void draw(Canvas canvas) {
            int width = layout.getWidth();
            int height = layout.getHeight();

            Paint paint = new Paint();
            paint.setColor(color);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);

            Path path = new Path();

            int tl = layout.border_top_left_radius;
            int tr = layout.border_top_right_radius;
            int bl = layout.border_bottom_left_radius;
            int br = layout.border_bottom_right_radius;

            int xr1 = 0;
            int yr1 = 0;

            int xr2 = width  - tr * 2;
            int yr2 = 0;

            int xr3 = width  - br * 2;
            int yr3 = height - br * 2;

            int xr4 = 0;
            int yr4 = height - bl * 2;

            int xs1 = width - tr;
            int ys1 = 0;

            int xs2 = width;
            int ys2 = height - br;

            int xs3 = bl;
            int ys3 = height;

            int xs4 = 0;
            int ys4 = tl;


            // 左上圆角
            draw_radius(path, xr1, yr1, tl, 180);
            // 上边框
            path.lineTo(xs1, ys1);

            // 右上圆角
            draw_radius(path, xr2, yr2, tr, 270);
            // 右边框
            path.lineTo(xs2, ys2);

            // 右下圆角
            draw_radius(path, xr3, yr3, br, 0);
            // 下边框
            path.lineTo(xs3, ys3);

            // 左下圆角
            draw_radius(path, xr4, yr4, bl, 90);
            // 左边框
            path.lineTo(xs4, ys4);

            canvas.drawPath(path, paint);
        }

        private RectF build_rectf(int radius, int sx, int sy){
            return new RectF(sx, sy, sx + radius*2, sy + radius*2);
        }

        // 根据传入的左上角和起始角度以半径，画出圆弧路径
        private void draw_radius(Path path, int x, int y, int radius, int start_angle) {
            RectF rect_f = new RectF(x, y, x + radius * 2, y + radius * 2);
            path.arcTo(rect_f, start_angle, 90, false);
        }

        @Override
        public void setAlpha(int alpha) {
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
        }

        @Override
        public int getOpacity() {
            return 0;
        }
    }
}
