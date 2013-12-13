package com.eshare_android_preview.base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.model.elog.CurrentState;
import com.eshare_android_preview.model.elog.ExperienceLog;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by menxu on 13-12-10.
 */
public class ExperienceOfView extends View{
	private boolean init_view = true;

	private int druation = 500;
	
	private int rect_color = Color.GREEN; // 背景矩形颜色
	
	private Float left = 40F;  // 矩形的 left
	private Float top = 25F;   // 矩形的 top
	
	private Float rect_width = 220F; // 矩形里面的初始长
	
	private Float rect_width_fill = 220F;
	private Float rect_height = 60F;	
	
	// 圆
	private Float circle_radius = 30F; // 圆半径

	private Float circle_left_cx; // 左圆 坐标
	private Float circle_left_cy;
	
	private Float circle_right_cx;// 右圆 坐标
	private Float circle_right_cy;
	
	private int circle_color = Color.WHITE; // 圆背景颜色
	
	
	//线条 边
	private int line_stroke_width = 5;
	private int line_fine_width = 0;
	
	private int line_color = Color.argb(100, 0, 0, 0);
	
	// current_level
	private int current_level = 3;
	 
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

		
		draw_left_circle(canvas);
		draw_right_circle(canvas);
		draw_text_left(canvas);
		draw_text_right(canvas);
		
		draw_background_rect(canvas);
	}
	private void init() {
		// circle
		this.circle_left_cx =  left;
		this.circle_left_cy =  top + rect_height/2 - line_stroke_width*2;
		
		this.circle_right_cx = rect_width_fill;
		this.circle_right_cy = circle_left_cy;
		
		if(init_view){
			this.init_view = false;
			set_default_rect_width();
		}
	}
	
	private void set_default_rect_width() {
		CurrentState state = ExperienceLog.current_state();
		Float level_up_exp_num = (float) state.level_up_exp_num;
		Float exp_num = (float) state.exp_num;
		Float fen_rect_width = (exp_num/level_up_exp_num);
		this.rect_width  = (rect_width_fill - left) * fen_rect_width + left;
		
		this.current_level = state.level;
	}
	
	private void draw_background_rect(Canvas canvas){
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(line_stroke_width);
		paint.setColor(line_color);
		canvas.drawRect(left, top, rect_width_fill, rect_height, paint);
		
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(rect_color);
		paint.setStrokeWidth(line_fine_width);
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
		paint.setStrokeWidth(line_fine_width);
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
		paint.setStrokeWidth(line_fine_width);
		canvas.drawCircle(circle_right_cx, circle_right_cy, circle_radius, paint);
	}
	
	private void draw_text_left(Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(text_color);
		paint.setTextSize(text_size);
		
		Rect rect = new Rect();
		paint.getTextBounds(current_level+"", 0, 1, rect); 
		
		canvas.drawText(current_level+"", circle_left_cx - rect.width()/2, circle_left_cy, paint);
	}
	
	private void draw_text_right(Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(text_color);
		paint.setTextSize(text_size);
		
		Rect rect = new Rect();
		paint.getTextBounds((current_level + 1)+"", 0, 1, rect); 
		
		canvas.drawText((current_level + 1)+"", circle_right_cx - rect.width()/2, circle_right_cy, paint);
	}

	private void set_rect_width(Float rect_width){
        this.rect_width  = rect_width;
        invalidate();
        requestLayout();
    }
	
	private float get_add_rect(int count){
		CurrentState state = ExperienceLog.current_state();
		Float level_up_exp_num = (float) state.level_up_exp_num;
		Float fen_rect_width = (count/level_up_exp_num);
		
		float rect = (rect_width_fill - left) * fen_rect_width;
		return rect + this.rect_width;
	}
	
    // 根据传入的半径值和动画时间来产生动画效果
    public void add(int count) {
        Float old_rect_width = this.rect_width;

        final float add_to = get_add_rect(count);
        ValueAnimator animation = ValueAnimator.ofFloat(old_rect_width, add_to);
        animation.setDuration(druation);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                value = value >= rect_width_fill ? rect_width_fill:value;
                set_rect_width(value); 
            }
        });
        animation.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator arg0) {}
			@Override
			public void onAnimationRepeat(Animator arg0) {}
			@Override
			public void onAnimationEnd(Animator arg0) {
				if (rect_width_fill <= add_to) {
					float current_count = ((add_to-rect_width_fill)/rect_width_fill) * ExperienceLog.get_level_up_exp_num_by(current_level);
					set_velel(current_level+1);
					set_rect_width(left);
					add((int) current_count);
				}
			}
			@Override
			public void onAnimationCancel(Animator arg0) {}
		});
        animation.start();
    }
    
    private void set_velel(int velel){
        this.current_level = velel;
        invalidate();
        requestLayout();
    }
}
