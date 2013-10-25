package com.eshare_android_preview.activity.base.plans;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.task.BaseAsyncTask;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Plan;
import com.eshare_android_preview.model.database.PlanDBHelper;
import com.eshare_android_preview.model.parse.CourseXMLParse;
import com.eshare_android_preview.widget.adapter.AddPlanAdapter;

public class AddPlanActivity extends EshareBaseActivity{
	
	ListView list_view;
	List<Plan> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setContentView(R.layout.p_add_plan);
		load_data();
		
		hide_head_setting_button();
        set_head_text(getResources().getString(R.string.plans_add_plans_title));
		super.onCreate(savedInstanceState);
	}
	private void load_data() {
		if (PlanDBHelper.all().size()!=0) {
			load_list();
			return;
		}
		
		new BaseAsyncTask<Void, Void, List<Plan>>(this,"获取数据") {
			@Override
			public List<Plan> do_in_background(Void... params) throws Exception {
				CourseXMLParse.parse_xml(HttpApi.course_xml_path);
				return null;
			}
			@Override
			public void on_success(List<Plan> result) {
				load_list();
			}
		}.execute();
	}
	
	private void load_list() {
		list_view = (ListView)findViewById(R.id.list_view);
		list = HttpApi.get_plan_all();
		AddPlanAdapter adapter = new AddPlanAdapter(this);
		adapter.add_items(list);
        list_view.setDivider(null);
		list_view.setAdapter(adapter);
		
		list_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list_view, View list_item,int item_id, long position) {
				TextView tv = (TextView) list_item.findViewById(R.id.item_tv);
				Plan plan = (Plan) tv.getTag(R.id.tag_plan_uuid);
				
				Intent intent = new Intent(AddPlanActivity.this,PlanShowActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("plan", plan);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
}
