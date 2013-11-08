package com.eshare_android_preview.activity.base.switch_test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.eshare_android_preview.R;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by fushang318 on 13-11-8.
 */
public class SecondActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.st_second);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Button btn = (Button) findViewById(R.id.st_second_btn);
        if(hasFocus){
            ObjectAnimator.ofFloat(btn, "y", 400).setDuration(1000).start();
        }else{
            ObjectAnimator.ofFloat(btn, "y", 590).setDuration(1000).start();
        }
    }

}