package com.eshare_android_preview.activity.base.experience;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.view.experience_chart_view.ExperienceChartView;


public class ExperienceChartActivity extends EshareBaseActivity {

    ExperienceChartView graph;

    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.experience_chart);

        LinearLayout root_view = (LinearLayout)findViewById(R.id.experience_chart);

        // 增加的经验值
        graph = new ExperienceChartView(this);

        root_view.addView(graph);

        super.onCreate(savedInstanceState);
    }

    public void run_animation(View view) {
//        graph.run_animation(10);
    }
}
