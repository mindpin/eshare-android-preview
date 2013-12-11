package com.eshare_android_preview.base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by menxu on 13-12-10.
 */
public class ExperienceOfView extends View{
	private int rect_color = Color.GREEN; // 背景矩形颜色
	
	private Float left = 20F;
	private Float top = 20F;
	private Float rect_width_fill;
	private Float rect_width = 100F;
	private Float rect_height;
	
	
	private Float circle_radius = 35F; // 圆半径
	
	private Float circle_left_cx; // 左圆 坐标
	private Float circle_left_cy;
	
	private Float circle_right_cx;// 右圆 坐标
	private Float circle_right_cy;
	
	private int circle_color = Color.WHITE; // 圆背景颜色
	
	private int line_stroke_width = 5;
	private int line_color = Color.argb(100, 0, 0, 0);
	
	// text
	private String left_text = "3";
	private String right_text = "4";
	
	private int text_color = Color.BLACK;
	private float text_size = 16.0F;
	
	
	
	public ExperienceOfView(Context context) {
		super(context);
	}
	public ExperienceOfView(Context context, AttributeSet attrs){
		super(context, attrs);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		init();

		draw_background_rect(canvas);
		draw_left_circle(canvas);
		draw_right_circle(canvas);
		draw_text_left(canvas);
		draw_text_right(canvas);
	}
	private void init() {
		// rect
		this.rect_width_fill = getWidth() - left;
		this.rect_height = getHeight() - top;
		
		// circle
		this.circle_left_cx =  circle_radius + left/2;
		this.circle_left_cy =  getHeight()/2F;
		
		this.circle_right_cx = getWidth() - circle_left_cx;
		this.circle_right_cy = circle_left_cy;
	}
	
	private void draw_background_rect(Canvas canvas){
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(line_stroke_width);
		paint.setColor(line_color);
		RectF rectF = new RectF(left, top-line_stroke_width, rect_width_fill, rect_height+line_stroke_width);
		canvas.drawRoundRect(rectF,30,30, paint);
		
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(rect_color);
		canvas.drawRect(left, top, rect_width, rect_height, paint);
	}

	private void draw_left_circle(Canvas canvas){
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		
		paint.setStrokeWidth(line_stroke_width);
		paint.setColor(line_color);
		canvas.drawCircle(circle_left_cx, circle_left_cy, circle_radius+line_stroke_width, paint);
		
		paint.setColor(circle_color);
		canvas.drawCircle(circle_left_cx, circle_left_cy, circle_radius, paint);	
	}
	
	private void draw_right_circle(Canvas canvas){
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		
		paint.setStrokeWidth(line_stroke_width);
		paint.setColor(line_color);
		canvas.drawCircle(circle_right_cx, circle_right_cy, circle_radius+line_stroke_width, paint);
		
		paint.setColor(circle_color);
		canvas.drawCircle(circle_right_cx, circle_right_cy, circle_radius, paint);
	}
	
	private void draw_text_left(Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(text_color);
		paint.setTextSize(text_size);
		
		Rect rect = new Rect();
		paint.getTextBounds(left_text, 0, 1, rect); 
		
		canvas.drawText(left_text, circle_left_cx - rect.width()/2, circle_left_cy, paint);
	}
	
	private void draw_text_right(Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(text_color);
		paint.setTextSize(text_size);
		
		Rect rect = new Rect();
		paint.getTextBounds(right_text, 0, 1, rect); 
		
		canvas.drawText(right_text, circle_right_cx - rect.width()/2, circle_right_cy, paint);
	}
	
	
	
	public void set_rect_width(Float value){
        this.rect_width = value;
        invalidate();
        requestLayout();
    }

    // 根据传入的半径值和动画时间来产生动画效果
    public void set_rect_width_animate(Float rect_width, int druation) {
        Float old_rect_width = this.rect_width;
        ValueAnimator animation = ValueAnimator.ofFloat(old_rect_width, rect_width);
        animation.setDuration(druation);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                set_rect_width(value);
            }
        });
        animation.start();
    }
}
