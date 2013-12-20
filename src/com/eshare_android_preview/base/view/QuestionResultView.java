package com.eshare_android_preview.base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.utils.BaseUtils.ScreenSize;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
/**
 * Created by menxu on 13-12-18.
 */
public class QuestionResultView extends RelativeLayout{
	final private static int ANIMATE_DRUATION = 500;
	final private static int TEXT_SIZE  = BaseUtils.dp_to_px(16);
	final private static ScreenSize ss 	= BaseUtils.get_screen_size();
	
	final private static int INIT_MARGIN_LEFT = ss.width_px/2; // left 值
	final private static int INIT_MARGIN_TOP = ss.height_px/2;   // top  值

    private TextView text_true_view, text_false_view;

    private boolean question_result;
    private CloseListener listener;
    
	public QuestionResultView(Context context) {
		super(context);
		add_view(context);
	}

	public QuestionResultView(Context context, AttributeSet attrs){
		super(context, attrs);
		add_view(context);
	}

	private void add_view(Context context) {
		setWillNotDraw(false);
		init_false_text_view(context);
		init_true_text_view(context);
		
		this.setVisibility(View.INVISIBLE);
	}

	private void init_false_text_view(Context context) {
		text_false_view = new TextView(context);
		text_false_view.setText("回答错误");
		text_false_view.setTextSize(TEXT_SIZE);
		text_false_view.setTextColor(Color.parseColor("#ff0000"));
		text_false_view.setGravity(Gravity.CENTER);
        
        LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        text_false_view.setLayoutParams(lp);
        
        addView(text_false_view);
	}

	private void init_true_text_view(Context context) {
		text_true_view = new TextView(context);
		text_true_view.setText("回答正确");
		text_true_view.setTextSize(TEXT_SIZE);
        text_true_view.setTextColor(Color.parseColor("#50ce00"));
        text_true_view.setGravity(Gravity.CENTER);
        
        LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        text_true_view.setLayoutParams(lp);
        
        addView(text_true_view);
	}

	private void set_view_visibility() {
		this.setVisibility(View.VISIBLE);
		text_true_view.setVisibility(View.GONE);
		text_false_view.setVisibility(View.GONE);
		View view = question_result ? text_true_view : text_false_view;
		view.setVisibility(View.VISIBLE);
	}
	private void show_animation(){
		set_view_visibility();

		AnimatorSet animSet = new AnimatorSet();
		ObjectAnimator oa_alpha = ObjectAnimator.ofFloat(this, "alpha", 1f, 1f);
		oa_alpha.setDuration(0);
    	ObjectAnimator oa_x = ObjectAnimator.ofFloat(this, "x", INIT_MARGIN_LEFT,INIT_MARGIN_LEFT);
    	oa_x.setDuration(ANIMATE_DRUATION);
    	ObjectAnimator oa_y = ObjectAnimator.ofFloat(this, "y", ss.height_px,INIT_MARGIN_TOP);
    	oa_y.setDuration(ANIMATE_DRUATION);
    	
    	animSet.play(oa_alpha);
    	animSet.playTogether(oa_x,oa_y);
    	animSet.start();
	}

	public void show_true(){
		question_result = true;
		show_animation();
	}
	public void show_false(){
		question_result = false;
		show_animation();
	}

	public void close(){
    	AnimatorSet animSet = new AnimatorSet();
    	ObjectAnimator oa_x = ObjectAnimator.ofFloat(this, "x", INIT_MARGIN_LEFT, ss.width_px);
    	oa_x.setDuration(ANIMATE_DRUATION);
    	ObjectAnimator oa_alpha = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f);
    	oa_alpha.setDuration(ANIMATE_DRUATION);
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
    	animSet.play(oa_x).with(oa_alpha);
    	animSet.start();
	}

    public void set_close_listener(CloseListener listener){
        this.listener = listener;
    }

    public interface CloseListener{
        public void on_close();
    }
}
