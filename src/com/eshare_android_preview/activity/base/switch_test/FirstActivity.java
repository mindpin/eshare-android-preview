package com.eshare_android_preview.activity.base.switch_test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by fushang318 on 13-11-8.
 */
public class FirstActivity extends EshareBaseActivity {
	boolean state = false;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.st_first);

        Button btn = (Button) findViewById(R.id.st_first_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	AnimatorSet animSet = new AnimatorSet();
            	ObjectAnimator anim_y = ObjectAnimator.ofFloat(v, "y", 200);
            	anim_y.setDuration(3000);
            	TextView textView = (TextView) findViewById(R.id.first_tv_bg);
            	ObjectAnimator anim_scale = ObjectAnimator.ofFloat(textView, "scaleY", 1,50);
            	anim_scale.setDuration(3000);
            	animSet.playTogether(anim_y,anim_scale);
            	animSet.start();
            	state = true;
                open_activity(SecondActivity.class);
            }
        });
    }
    @Override
    protected void onResume() {
    	super.onResume();
    	if (state) {
    		AnimatorSet animSet = new AnimatorSet();
        	
        	Button st_first_btn = (Button)findViewById(R.id.st_first_btn);
        	ObjectAnimator anim_y = ObjectAnimator.ofFloat(st_first_btn, "y", 1);
        	anim_y.setDuration(3000);
        	
        	TextView textView = (TextView) findViewById(R.id.first_tv_bg);
        	ObjectAnimator anim_scale = ObjectAnimator.ofFloat(textView, "scaleY", 50,1);
        	anim_scale.setDuration(3000);
        	
        	animSet.playTogether(anim_scale,anim_y);
        	animSet.start();
		}
    }
}