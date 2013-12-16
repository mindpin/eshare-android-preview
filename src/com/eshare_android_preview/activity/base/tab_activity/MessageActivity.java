package com.eshare_android_preview.activity.base.tab_activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.dash_path_demo.DashPathDemoActivity;

import com.eshare_android_preview.activity.base.expericence_of.ExperienceViewDemoActivity;

import com.eshare_android_preview.activity.base.experience.ExperienceChartActivity;
import com.eshare_android_preview.activity.base.webview_demo.WebViewDemoActivity;

import com.eshare_android_preview.activity.base.switch_test.FirstActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;

public class MessageActivity extends EshareBaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.tab_message);

        this.hide_head_go_back_button();
        this.set_head_text(R.string.category_message);
        this.render_markdown_button();
        this.render_dash_path_button();
        this.render_to_first_button();
        this.render_experience_chart();
		super.onCreate(savedInstanceState);
	}

    private void render_dash_path_button() {
        Button button = (Button) findViewById(R.id.dash_path_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_activity(DashPathDemoActivity.class);
            }
        });
	}

    private void render_to_first_button() {
        Button button = (Button) findViewById(R.id.to_first_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_activity(FirstActivity.class);
            }
        });
    }

    private void render_markdown_button() {
        Button button = (Button) findViewById(R.id.markdown_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_activity(WebViewDemoActivity.class);
            }
        });
    }

    public void click_experience_of_button(View view){
    	open_activity(ExperienceViewDemoActivity.class);
    }
    
    private void render_experience_chart() {
        Button button = (Button) findViewById(R.id.experience_chart_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_activity(ExperienceChartActivity.class);
            }
        });
    }

	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
