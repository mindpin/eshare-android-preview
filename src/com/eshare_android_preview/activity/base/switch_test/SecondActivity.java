package com.eshare_android_preview.activity.base.switch_test;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by fushang318 on 13-11-8.
 */
public class SecondActivity extends EshareBaseActivity {
    private boolean anim_is_run = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.st_second);

        Button btn = (Button) findViewById(R.id.st_second_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SecondActivity.this,"click",2000).show();
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
        Button btn = (Button) findViewById(R.id.st_second_btn);
        ObjectAnimator.ofFloat(btn, "y", 400).setDuration(500).start();
        anim_is_run = true;
    }

    public void run_back_anim(){
        Button btn = (Button) findViewById(R.id.st_second_btn);
        ObjectAnimator oa = ObjectAnimator.ofFloat(btn, "y", 800);
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