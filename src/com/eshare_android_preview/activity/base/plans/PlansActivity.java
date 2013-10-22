package com.eshare_android_preview.activity.base.plans;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Plan;
import com.eshare_android_preview.model.parse.CourseXMLParse;
import com.eshare_android_preview.widget.adapter.PlanAdapter;

public class PlansActivity extends EshareBaseActivity{
	ListView list_view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.p_plans);
		CourseXMLParse.parse_xml(HttpApi.course_xml_path);
		load_list();
	}

	private void load_list() {
		list_view = (ListView)findViewById(R.id.list_view);
		List<Plan> list = HttpApi.get_plan_checked("true");
		PlanAdapter adapter = new PlanAdapter(this);
		adapter.add_items(list);
		list_view.setAdapter(adapter);
		
		list_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list_view, View list_item,int item_id, long position) {
				TextView info_tv = (TextView) list_item.findViewById(R.id.info_tv);
				Plan item = (Plan) info_tv.getTag(R.id.tag_note_uuid);
				BaseUtils.toast(item.content);
			}
		});
	}
	
	public void click_add_plans(View view){
		open_activity(AddPlanActivity.class);
	}
}
