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
/**
 * Created by menxu on 13-12-18.
 */
public class QuestionResultView extends RelativeLayout{
	private boolean is_on_init = true;
    private int height, width;
    
	final private static int ANIMATE_DRUATION = 500;
	final private static int TEXT_SIZE  = BaseUtils.dp_to_px(16);
	final private static ScreenSize ss 	= BaseUtils.get_screen_size();

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
		init_false_text_view(context);
		init_true_text_view(context);
		setWillNotDraw(false);
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
	
	private void init() {
        if (is_on_init) {
            is_on_init = false;

            height = getHeight();
            width  = getWidth();
            
            this.setVisibility(View.GONE);
        }
    }
	@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
	}
	
	private void set_view_visibility() {
		setVisibility(View.VISIBLE);
		text_true_view.setVisibility(View.GONE);
		text_false_view.setVisibility(View.GONE);
		View view = question_result ? text_true_view : text_false_view;
		view.setVisibility(View.VISIBLE);
	}
	private void show_animation(){
		set_view_visibility();

		ObjectAnimator pvh_alpha = ObjectAnimator.ofFloat(this, "alpha", 1f, 1f);
		pvh_alpha.setDuration(0);
		pvh_alpha.start();

		AnimatorSet animSet = new AnimatorSet();
    	ObjectAnimator pvh_scale = ObjectAnimator.ofFloat(this, "y", ss.height_dp + height,ss.height_dp/2);
    	pvh_scale.setDuration(ANIMATE_DRUATION);
    	ObjectAnimator pvh_x = ObjectAnimator.ofFloat(this, "x", ss.width_dp - width,ss.width_dp - width);
    	pvh_x.setDuration(ANIMATE_DRUATION);
    	animSet.play(pvh_x).with(pvh_scale);
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
    	ObjectAnimator pvh_x = ObjectAnimator.ofFloat(this, "x", ss.width_dp - width,ss.width_dp + width);
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