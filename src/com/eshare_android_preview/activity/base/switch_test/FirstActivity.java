package com.eshare_android_preview.activity.base.switch_test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;

/**
 * Created by fushang318 on 13-11-8.
 */
public class FirstActivity extends EshareBaseActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.st_first);

        Button btn = (Button) findViewById(R.id.st_first_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_activity(SecondActivity.class);
            }
        });
    }
}