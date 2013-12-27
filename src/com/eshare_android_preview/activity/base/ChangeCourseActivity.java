package com.eshare_android_preview.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.model.knowledge.KnowledgeNet;

/**
 * Created by Administrator on 13-12-26.
 */
public class ChangeCourseActivity extends EshareBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.change_course);


        set_head_text("选择课程");
        hide_head_setting_button();
        super.onCreate(savedInstanceState);
    }

    public void change_to_javascript(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        KnowledgeNet.switch_to("javascript");
        startActivity(intent);
    }

    public void change_to_english(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        KnowledgeNet.switch_to("english");
        startActivity(intent);
    }
}
