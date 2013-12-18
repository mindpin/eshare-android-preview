package com.eshare_android_preview.activity.base.questions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.view.ExperienceView;
import com.eshare_android_preview.base.view.experience_chart_view.ExperienceChartView;
import com.eshare_android_preview.model.TestPaper;
import com.eshare_android_preview.model.elog.CurrentState;
import com.eshare_android_preview.model.elog.ExperienceLog;

/**
 * Created by fushang318 on 13-12-18.
 */
public class TestSuccessActivity extends EshareBaseActivity {
    public static TestPaper test_paper;

    private ExperienceView experience_view;
    private ExperienceChartView experience_chart_view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_success);

        this.experience_view = (ExperienceView)findViewById(R.id.experience_view);
        this.experience_chart_view = (ExperienceChartView)findViewById(R.id.experience_chart_view);
    }


//    public void add_exp(){
//        ExperienceLog.add(10, this.test_paper.target, "");
//    }
}