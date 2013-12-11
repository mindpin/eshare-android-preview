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
        LinearLayout root_view = (LinearLayout)findViewById(R.id.experience_chart);

        float[] xvalues = new float[] { 0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f };
        float[] yvalues = new float[] { 0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f };


        ExperienceChartView graph = new ExperienceChartView(this, xvalues, yvalues, 1);

        root_view.addView(graph);

        super.onCreate(savedInstanceState);

    }
}
