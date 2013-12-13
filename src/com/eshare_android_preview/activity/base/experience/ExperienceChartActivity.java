package com.eshare_android_preview.activity.base.experience;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.view.experience_chart_view.ExperienceChartView;


public class ExperienceChartActivity extends EshareBaseActivity {

    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.experience_chart);
        super.onCreate(savedInstanceState);
    }
}
