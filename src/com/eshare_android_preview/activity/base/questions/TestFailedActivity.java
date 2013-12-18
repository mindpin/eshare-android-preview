package com.eshare_android_preview.activity.base.questions;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;

/**
 * Created by fushang318 on 13-12-18.
 */
public class TestFailedActivity extends EshareBaseActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_failed);
    }

    public void ok(View view){
        finish();
    }
}