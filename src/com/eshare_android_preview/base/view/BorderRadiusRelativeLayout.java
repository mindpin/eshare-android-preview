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

        int radius1 = attrs.getAttributeIntValue(null,"border_top_left_radius",0);
        if(radius1 != 0){
            radius1 = BaseUtils.dp_to_int_px(radius1);
            border_top_left_radius = radius1;
        }

        int radius2 = attrs.getAttributeIntValue(null,"border_top_right_radius",0);
        if(radius2 != 0){
            radius2 = BaseUtils.dp_to_int_px(radius2);
            border_top_right_radius = radius2;
        }

        int radius3 = attrs.getAttributeIntValue(null,"border_bottom_right_radius",0);
        if(radius3 != 0){
            radius3 = BaseUtils.dp_to_int_px(radius3);
            border_bottom_right_radius = radius3;
        }

        int radius4 = attrs.getAttributeIntValue(null,"border_bottom_left_radius",0);
        if(radius4 != 0){
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

            // 左上圆角
            RectF top_left_rect_f = build_rectf(layout.border_top_left_radius, 0, 0);
            path.arcTo(top_left_rect_f, 180, 90, false);
            // 上边框
            path.lineTo(width - layout.border_top_right_radius, 0);
            // 右上圆角
            RectF top_right_rect_f = build_rectf(layout.border_top_right_radius, width - layout.border_top_right_radius*2, 0);
            path.arcTo(top_right_rect_f, 270, 90, false);
            // 右边框
            path.lineTo(width, height - layout.border_bottom_right_radius);
            // 右下圆角
            RectF bottom_right_rect_f = build_rectf(layout.border_bottom_right_radius, width - layout.border_bottom_right_radius*2, height - layout.border_bottom_right_radius*2);
            path.arcTo(bottom_right_rect_f, 0, 90, false);
            // 下边框
            path.lineTo(layout.border_bottom_left_radius, width);
            // 左下圆角
            RectF bottom_left_rect_f = build_rectf(layout.border_bottom_left_radius, 0, height - layout.border_bottom_left_radius*2);
            path.arcTo(bottom_left_rect_f, 90, 90, false);
            // 左边框
            path.lineTo(0, layout.border_top_left_radius);

            canvas.drawPath(path, paint);
        }

        private RectF build_rectf(int radius, int sx, int sy){
            return new RectF(sx, sy, sx + radius*2, sy + radius*2);
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
