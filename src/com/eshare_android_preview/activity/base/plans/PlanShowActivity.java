package com.eshare_android_preview.activity.base.plans;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Plan;

public class PlanShowActivity extends EshareBaseActivity{
	TextView plan_content_tv;
	Button click_plan_add_but;
	Plan plan;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.p_plan_show);
		Intent intent = getIntent();
		plan = (Plan)intent.getExtras().getSerializable("plan");
		
		load_ui();
		hide_head_setting_button();
		set_head_text(R.string.plans_show);
		super.onCreate(savedInstanceState);
	}
	private void load_ui() {
		plan_content_tv = (TextView)findViewById(R.id.plan_content_tv);
		click_plan_add_but = (Button)findViewById(R.id.click_plan_add_but);
		
		plan_content_tv.setText(plan.content);
		
		int text_checked_str_id = plan.checked.equals("true")? R.string.plans_cancel_plans:R.string.plans_add_plans;
		click_plan_add_but.setText(text_checked_str_id);
		click_plan_add_but.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				plan.checked = plan.checked.equals("true") ? "false":"true";
				HttpApi.update_plan(plan);
				int text_checked_str_id = plan.checked.equals("false")? R.string.plans_add_plans:R.string.plans_cancel_plans;
				click_plan_add_but.setText(text_checked_str_id);
			}
		});
	}
}
