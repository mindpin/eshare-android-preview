package com.eshare_android_preview.activity.base.experience;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.view.experience_chart_view.ExperienceChartView;


public class ExperienceChartActivity extends EshareBaseActivity {

    public void onCreate(Bundle savedInstanceState) {
        LinearLayout buttonGraph = new LinearLayout(this);
        buttonGraph.setOrientation(LinearLayout.VERTICAL);


        LinearLayout buttons = new LinearLayout(this);
        buttons.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT); // Verbose!
        buttonGraph.addView(buttons, lp);

        float[] xvalues = new float[] { 0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f };
        float[] yvalues = new float[] { 0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f };


        ExperienceChartView graph = new ExperienceChartView(this, xvalues, yvalues, 1);

        buttonGraph.addView(graph, lp);

        setContentView(buttonGraph);

        super.onCreate(savedInstanceState);

    }
}
