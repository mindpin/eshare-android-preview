package com.eshare_android_preview.activity.base.plans;

import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.task.BaseAsyncTask;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Plan;
import com.eshare_android_preview.model.database.PlanDBHelper;
import com.eshare_android_preview.model.parse.CourseXMLParse;
import com.eshare_android_preview.widget.adapter.AddPlanAdapter;
import com.eshare_android_preview.widget.model.ViewHolder;

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
		AddPlanAdapter adapter = new AddPlanAdapter(list);
		list_view.setAdapter(adapter);
		
		list_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list_view, View list_item,int item_id, long position) {
				ViewHolder viewHolder=(ViewHolder)list_item.getTag(); 
				viewHolder.cb.toggle();
				AddPlanAdapter.getIsSelected().put(item_id, viewHolder.cb.isChecked());//将CheckBox的选中状况记录下来
			}
		});
	}
	
	public void click_add_plans(View view){
		HashMap<Integer,Boolean> hashMap = AddPlanAdapter.getIsSelected();
		for (int i = 0; i < list.size(); i++) {
			if (hashMap.get(i)) {
				Plan plan = list.get(i);
				plan.checked = true+"";
				HttpApi.update_plan(plan);
			}
//			System.out.println(hashMap.get(i));
		}
		
		open_activity(PlansActivity.class);
		this.finish();
	}
	
}
