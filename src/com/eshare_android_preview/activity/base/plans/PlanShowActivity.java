package com.eshare_android_preview.activity.base.plans;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.notes.AddNoteActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Favourite;
import com.eshare_android_preview.model.Plan;
import com.eshare_android_preview.model.database.FavouriteDBHelper;

public class PlanShowActivity extends EshareBaseActivity{
	TextView plan_content_tv;
	Button click_plan_add_but;
    Button add_favourate_btn;
    Button cancel_favourate_btn;

	Plan plan;

    public static class ExtraKeys {
        public static final String PLAN = "plan";
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.p_plan_show);

        Bundle bundle = getIntent().getExtras();
        plan = (Plan)bundle.getSerializable(PlanShowActivity.ExtraKeys.PLAN);

		load_ui();
		hide_head_setting_button();
		set_head_text(R.string.plans_show);


        Favourite favourite = HttpApi.find_favourate(plan.id + "", FavouriteDBHelper.Kinds.PLAN);

        if (favourite == null) {
            add_favourate_btn.setVisibility(View.VISIBLE);
            cancel_favourate_btn.setVisibility(View.GONE);
        } else {
            add_favourate_btn.setVisibility(View.GONE);
            cancel_favourate_btn.setVisibility(View.VISIBLE);
        }


		super.onCreate(savedInstanceState);
	}
	private void load_ui() {
		plan_content_tv = (TextView)findViewById(R.id.plan_content_tv);
		click_plan_add_but = (Button)findViewById(R.id.click_plan_add_but);
		System.out.println("111");
        System.out.println(plan_content_tv);
        System.out.println(plan);

		plan_content_tv.setText(plan.content);
		set_but_txt();
		click_plan_add_but.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				plan.checked = plan.checked.equals("true") ? "false":"true";
				HttpApi.update_plan(plan);
				set_but_txt();
			}
		});

        add_favourate_btn = (Button) findViewById(R.id.add_favourate_btn);
        cancel_favourate_btn = (Button) findViewById(R.id.cancel_favourate_btn);
	}
	private void set_but_txt(){
		int text_checked_str_id = plan.checked.equals("false")? R.string.plans_add_plans:R.string.plans_cancel_plans;
		click_plan_add_but.setText(text_checked_str_id);
	}
	
	public void click_notes(View view){
		Bundle bundle = new Bundle();
        bundle.putSerializable("item", plan);

        Intent intent = new Intent(PlanShowActivity.this,AddNoteActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
	}


    @SuppressLint({ "WorldReadableFiles", "WorldWriteableFiles" })
    public void add_favourate(View view) {
        Favourite favourite = new Favourite(plan.id + "", FavouriteDBHelper.Kinds.PLAN);
        HttpApi.create_favourate(favourite);

        add_favourate_btn.setVisibility(View.GONE);
        cancel_favourate_btn.setVisibility(View.VISIBLE);
    }

    @SuppressLint({ "WorldReadableFiles", "WorldWriteableFiles" })
    public void cancel_favourate(View view) {
        Favourite favourite = HttpApi.find_favourate(plan.id + "", FavouriteDBHelper.Kinds.PLAN);
        HttpApi.cancel_favourate(favourite);

        add_favourate_btn.setVisibility(View.VISIBLE);
        cancel_favourate_btn.setVisibility(View.GONE);
    }
}
