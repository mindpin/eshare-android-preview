package com.eshare_android_preview.activity.base.plans;

import java.util.List;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Plan;
import com.eshare_android_preview.widget.adapter.PlanAdapter;

public class PlansActivity extends EshareBaseActivity{
	ListView list_view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setContentView(R.layout.p_plans);
		load_list();
		
		hide_head_setting_button();
        hide_head_bottom_line();
		set_head_text(getResources().getString(R.string.plans_my_plans));
        _set_btn_fonts();
		super.onCreate(savedInstanceState);
	}

    private void _set_btn_fonts() {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");
        Button button = (Button) findViewById(R.id.add_plan);
        button.setTypeface(font);
    }

	private void load_list() {
		list_view = (ListView)findViewById(R.id.list_view);
        list_view.setDivider(null);
		List<Plan> list = HttpApi.get_plan_checked("true");
		PlanAdapter adapter = new PlanAdapter(this);
		adapter.add_items(list);
		list_view.setAdapter(adapter);
		
		list_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list_view, View list_item,int item_id, long position) {
				TextView tv = (TextView) list_item.findViewById(R.id.item_tv);
				Plan item = (Plan) tv.getTag(R.id.tag_plan);
				BaseUtils.toast(item.content);
			}
		});
	}
	
	public void click_add_plans(View view){
		open_activity(AddPlanActivity.class);
	}
}
