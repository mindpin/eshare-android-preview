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
    public static FirstActivity instance;
	boolean btn_is_open = false;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.st_first);

        Button btn = (Button) findViewById(R.id.st_first_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                run_to_next_page_anim();
                open_activity(SecondActivity.class);
            }
        });
    }

    public void run_to_next_page_anim(){
        Button btn = (Button) findViewById(R.id.st_first_btn);
        AnimatorSet animSet = new AnimatorSet();
        ObjectAnimator anim_y = ObjectAnimator.ofFloat(btn, "y", 200);
        anim_y.setDuration(500);
        TextView textView = (TextView) findViewById(R.id.first_tv_bg);
        ObjectAnimator anim_scale = ObjectAnimator.ofFloat(textView, "scaleY", 1,50);
        anim_scale.setDuration(500);
        animSet.playTogether(anim_y,anim_scale);
        animSet.start();
        btn_is_open = true;
    }

    public void run_back_anim(){
        AnimatorSet animSet = new AnimatorSet();

        Button st_first_btn = (Button)findViewById(R.id.st_first_btn);
        ObjectAnimator anim_y = ObjectAnimator.ofFloat(st_first_btn, "y", 1);
        anim_y.setDuration(500);

        TextView textView = (TextView) findViewById(R.id.first_tv_bg);
        ObjectAnimator anim_scale = ObjectAnimator.ofFloat(textView, "scaleY", 50,1);
        anim_scale.setDuration(500);

        animSet.playTogether(anim_scale,anim_y);
        animSet.start();
    }
}