package com.eshare_android_preview.base.view;

import com.eshare_android_preview.activity.base.questions.QuestionShowActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.utils.BaseUtils.ScreenSize;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.Animator.AnimatorListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
/**
 * Created by menxu on 13-12-18.
 */
public class QuestionResultView extends View{
	private boolean is_on_init = true;
	
	final private static int ANIMATE_DRUATION = 500;
	
	final private static int TRUE__COLOR 		= Color.parseColor("#50ce00");
	final private static int FALSE__COLOR 		= Color.parseColor("#ff0000");
	
	final private static int TRUE__BG_COLOR 	= Color.parseColor("#ffffff");
	final private static int FALSE__BG_COLOR 	= Color.parseColor("#ffffff");
	
	final private static String TRUE__TEXT = "回答正确";
	final private static String FALSE__TEXT = "回答错误";
	
	private int TEXT_SIZE;
	
	private boolean question_answer;
    private CloseListener listener;
	
	public QuestionResultView(Context context) {
		super(context);
        init_text_size();
	}
	public QuestionResultView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init_text_size();
    }

    private void init_text_size() {
        if (isInEditMode()) {
            TEXT_SIZE = 18;
        } else {
            TEXT_SIZE = BaseUtils.dp_to_px(18);
        }
    }
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		init();
		draw_text(canvas);
	}
	private void init() {
		if (is_on_init) {
			this.is_on_init = false;
			this.setVisibility(View.GONE);
		}else{
			this.setVisibility(View.VISIBLE);
		}
	}
	private void draw_text(Canvas canvas){
		String answer_text = 	question_answer ? TRUE__TEXT : FALSE__TEXT;
		int color = 			question_answer ? TRUE__COLOR : FALSE__COLOR;
		int bg_color = 			question_answer ? TRUE__BG_COLOR : FALSE__BG_COLOR;

		Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(TEXT_SIZE);
        paint.setColor(color);
        this.setBackgroundColor(bg_color);
        
        Rect bounds = new Rect();
        paint.getTextBounds(answer_text, 0, answer_text.length(), bounds);
        
		canvas.drawText(answer_text, 
						(getWidth() -  bounds.width())/2, 
						getHeight()/2, 
						paint);
	}

	private void show_animation(){
		invalidate();
        requestLayout();
		ScreenSize ss = BaseUtils.get_screen_size();
    	ObjectAnimator anim_scale = ObjectAnimator.ofFloat(this, "y", ss.height_dp + getHeight(),ss.height_dp/2);
        anim_scale.setDuration(ANIMATE_DRUATION);
        anim_scale.start();
	}
	
	public void show_true(){
		this.question_answer = true;
		show_animation();
	}
	public void show_false(){
		this.question_answer = false;
		show_animation();
	}
	
	public void close(){
    	ScreenSize ss = BaseUtils.get_screen_size();
    	final int[] location = new int[2];
    	this.getLocationOnScreen(location);
    	
    	AnimatorSet animSet = new AnimatorSet();
    	ObjectAnimator pvh_x = ObjectAnimator.ofFloat(this, "x", location[0],ss.width_dp + getWidth());
    	pvh_x.setDuration(ANIMATE_DRUATION);
    	ObjectAnimator pvh_alpha = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f);
    	pvh_alpha.setDuration(ANIMATE_DRUATION);
    	
    	animSet.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator arg0) {}
			@Override
			public void onAnimationRepeat(Animator arg0) {}
			@Override
			public void onAnimationEnd(Animator arg0) {
                if(listener != null){
                    listener.on_close();
                }
			}
			@Override
			public void onAnimationCancel(Animator arg0) {}
		});
    	animSet.play(pvh_x).with(pvh_alpha);
    	animSet.start();
	}

    public void set_close_listener(CloseListener listener){
        this.listener = listener;
    }

    public interface CloseListener{
        public void on_close();
    }
}
