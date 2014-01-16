package com.eshare_android_preview.controller.activity.switch_test;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eshare_android_preview.R;
import com.eshare_android_preview.controller.activity.base.EshareBaseActivity;
import com.eshare_android_preview.utils.BaseUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

/**
 * Created by fushang318 on 13-11-8.
 */
public class SecondActivity extends EshareBaseActivity {
    private boolean anim_is_run = false;
    LinearLayout second_ll ;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.st_second);

        Button btn = (Button) findViewById(R.id.st_second_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SecondActivity.this,"click",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus && !anim_is_run){
            run_page_init_anim();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            FirstActivity.instance.run_back_anim();
            run_back_anim();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void run_page_init_anim(){
    	second_ll = (LinearLayout) findViewById(R.id.second_ll);
    	ValueAnimator va = ValueAnimator.ofFloat(570, 300);
    	va.setDuration(500);
    	va.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				Log.i("update", ((Float) animation.getAnimatedValue()).toString());
                float change_value = (Float)animation.getAnimatedValue();
                int change_value_px = (int)BaseUtils.dp_to_px(change_value);
				set_prame(second_ll,0,change_value_px,0,0);
			}
		});
    	va.start();
        anim_is_run = true;
    }
    private void set_prame(ViewGroup group, int left, int top, int right, int bottom){
		FrameLayout.LayoutParams lp = (LayoutParams) group.getLayoutParams();
		lp.setMargins(left, top, right, bottom);
		group.setLayoutParams(lp);
    }

    public void run_back_anim(){
    	second_ll = (LinearLayout) findViewById(R.id.second_ll);
        ObjectAnimator oa = ObjectAnimator.ofFloat(second_ll, "y", 800);
        oa.setDuration(500);
        oa.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {}

            @Override
            public void onAnimationEnd(Animator animator) {
                SecondActivity.this.finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {}

            @Override
            public void onAnimationRepeat(Animator animator) {}
        });
        oa.start();
    }
}