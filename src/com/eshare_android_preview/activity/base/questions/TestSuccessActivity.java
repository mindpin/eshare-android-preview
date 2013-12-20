package com.eshare_android_preview.activity.base.questions;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.view.ExperienceView;
import com.eshare_android_preview.base.view.experience_chart_view.ExperienceChartView;
import com.eshare_android_preview.model.TestPaper;
import com.eshare_android_preview.model.elog.ExperienceLog;
import com.eshare_android_preview.model.knowledge.KnowledgeCheckpoint;
import com.eshare_android_preview.model.knowledge.KnowledgeNode;
import com.eshare_android_preview.model.knowledge.base.ILearn;

/**
 * Created by fushang318 on 13-12-18.
 */
public class TestSuccessActivity extends EshareBaseActivity {
    public static class ExtraKeys {
        public static final String TEST_PAPER = "test_paper";
    }

    public TestPaper test_paper;

    private ExperienceView experience_view;
    private ExperienceChartView experience_chart_view;
    private boolean is_learned = false;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            run_animation();
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_success);
        this.test_paper = getIntent().getParcelableExtra(ExtraKeys.TEST_PAPER);

        if(this.test_paper.target.getClass() == KnowledgeNode.class){
            this.is_learned = ((KnowledgeNode)this.test_paper.target).is_learned();
        }else if(this.test_paper.target.getClass() == KnowledgeCheckpoint.class){
            this.is_learned = ((KnowledgeCheckpoint)this.test_paper.target).is_learned();
        }


        this.experience_view = new ExperienceView(this);
        this.experience_chart_view = new ExperienceChartView(this, this.test_paper.target.exp_num());

        LinearLayout.LayoutParams experience_view_params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        experience_view_params.weight = 3;
        this.experience_view.setLayoutParams(experience_view_params);

        LinearLayout.LayoutParams experience_chart_view_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        experience_chart_view_params.weight = 1;
        this.experience_chart_view.setLayoutParams(experience_chart_view_params);


        LinearLayout parent = (LinearLayout)findViewById(R.id.test_success_parent);
        parent.addView(this.experience_view);
        parent.addView(this.experience_chart_view);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        handler.sendEmptyMessageDelayed(0,500);
    }

    private void run_animation(){
        if(!is_learned){
            this.experience_chart_view.run_animation();
            this.experience_view.add(this.test_paper.target.exp_num());
            ExperienceLog.add(this.test_paper.target.exp_num(), this.test_paper.target, "");
            ((ILearn)this.test_paper.target).do_learn();
            is_learned = true;
        }
    }

    public void ok(View view){
        finish();
    }

}