package com.eshare_android_preview.activity.base.plans;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.model.Course;
import com.eshare_android_preview.model.database.CourseDBHelper;
import com.eshare_android_preview.model.parse.CourseXMLParse;
import com.eshare_android_preview.widget.adapter.PlansAdapter;
import com.eshare_android_preview.widget.dialog.arc.ArcProgressDialog;

public class AddPlanActivity extends EshareBaseActivity{
	ListView list_view;
	List<Course> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.p_add_plan);

//      如果要调试进度条用connect()这行代码，把 load_data() 注释掉
//		load_data();
      connect();

		hide_head_setting_button();
        set_head_text(getResources().getString(R.string.plans_add_plans_title));

		super.onCreate(savedInstanceState);
	}

	private void load_data() {
		int count = CourseXMLParse.doc_parse_plan_count();
		if (Course.all().size() >= count || count == 0) {
			load_list();
		}else{
		    connect();
        }
	}

	private void connect() {  
		ParsePlanTask task = new ParsePlanTask(this);  
        task.execute();  
    }

	private void load_list() {
		list_view = (ListView)findViewById(R.id.list_view);
        list = Course.all();
        PlansAdapter adapter = new PlansAdapter(this);
        adapter.add_items(list);
        list_view.setDivider(null);
        list_view.setAdapter(adapter);

        list_view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list_view, View list_item, int item_id, long position) {
                Course plan = (Course) list_item.getTag(R.id.adapter_item_tag);

                Intent intent = new Intent(AddPlanActivity.this, PlanShowActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable(PlanShowActivity.ExtraKeys.PLAN, plan);
                intent.putExtras(bundle);

				startActivity(intent);
			}
		});
	}


    class ParsePlanTask extends AsyncTask<Integer, Integer, String>{
        int count = CourseXMLParse.doc_parse_plan_count();
        ArcProgressDialog dialog;
        public ParsePlanTask(Context context){
            dialog = ArcProgressDialog.show(context, count);
        }
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected String doInBackground(Integer... params) {
            int id = 0;
            while (id < count) {
                id = CourseXMLParse.doc_parse_plan_id(id);
                publishProgress(id);
            }
            return "执行完毕";
        }
        protected void onProgressUpdate(Integer... progress) {
            dialog.set_progress(progress[0]);
            super.onProgressUpdate(progress);
        }
        protected void onPostExecute(String result) {
            dialog.dismiss();
            load_list();
            super.onPostExecute(result);
        }
    }
}
