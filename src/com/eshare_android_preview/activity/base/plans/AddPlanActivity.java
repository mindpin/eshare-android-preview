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
import com.eshare_android_preview.widget.EshareProgressDialog;
import com.eshare_android_preview.widget.adapter.AddPlanAdapter;

public class AddPlanActivity extends EshareBaseActivity{
//	ProgressDialog m_pDialog;
//	EshareProgressDialog dialog;
	
	ListView list_view;
	List<Plan> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		 setContentView(R.layout.p_add_plan);
		 
//		 m_pDialog = new ProgressDialog(AddPlanActivity.this);
//		 m_pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//		 m_pDialog.setTitle("提示");
//		 m_pDialog.setMessage("这是一个长形对话框进度条");
//		 m_pDialog.setProgress(100);
//		 m_pDialog.setIndeterminate(false);
//		 m_pDialog.setCancelable(true);
		 
//		 dialog = new EshareProgressDialog(this);
		 
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
//		dialog.show();
//		ParsePlanTask dTask = new ParsePlanTask();  
//        dTask.execute(100);
        
		new BaseAsyncTask<Void, Void, List<Plan>>() {
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
//	class ParsePlanTask extends AsyncTask<Integer, Integer, String>{
//		 protected void onPreExecute() {
//			 super.onPreExecute();
//		 }
//		 protected String doInBackground(Integer... params) {
//			 for(int i=0;i<=100;i++){  
////				CourseXMLParse.parse_xml(HttpApi.course_xml_path);
//	            publishProgress(i);  
//	            try {
//	               Thread.sleep(params[0]);  
//	            } catch (InterruptedException e) {  
//	               e.printStackTrace();  
//	            }  
//	          }  
//	          return "执行完毕"; 
//		 }
//		 protected void onProgressUpdate(Integer... progress) { 
////			 dialog.setProgress(progress[0]);
//			 dialog.set
//			 super.onProgressUpdate(progress);
//		}
//		protected void onPostExecute(String result) {
//			dialog.dismiss();
//			load_list();
//	        super.onPostExecute(result); 
//		}
//	}
	
	
	

	
	
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
				intent.putExtra("item_id", plan.id+"");
				startActivity(intent);
			}
		});
	}
}
