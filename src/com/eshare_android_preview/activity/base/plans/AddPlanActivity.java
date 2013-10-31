package com.eshare_android_preview.activity.base.plans;

import java.util.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.application.EshareApplication;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
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
		int count = CourseXMLParse.doc_parse_plan_count();
		if (PlanDBHelper.all().size() >= count || count == 0) {
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
        list = HttpApi.HAPlan.all();
		AddPlanAdapter adapter = new AddPlanAdapter(this);
		adapter.add_items(list);
        list_view.setDivider(null);
		list_view.setAdapter(adapter);
		
		list_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list_view, View list_item,int item_id, long position) {
                Plan plan = (Plan) list_item.getTag(R.id.adapter_item_tag);

				Intent intent = new Intent(AddPlanActivity.this,PlanShowActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable(PlanShowActivity.ExtraKeys.PLAN, plan);
                intent.putExtras(bundle);

				startActivity(intent);
			}
		});
	}


    class ParsePlanTask extends AsyncTask<Integer, Integer, String>{
        int count = CourseXMLParse.doc_parse_plan_count();
        Dialog loadingDialog;
        LinearLayout layout;
        TextView loading_tv;
        public ParsePlanTask(Context context){
            View v = EshareApplication.inflate(R.layout.loading_dialog, null);
            layout = (LinearLayout) v.findViewById(R.id.dialog_view);
            ImageView loading_img = (ImageView) v.findViewById(R.id.loading_img);
            loading_tv = (TextView) v.findViewById(R.id.loading_tv);
            Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
            loading_img.startAnimation(hyperspaceJumpAnimation);
            loading_tv.setText("0/" + count);// 设置加载信息
            loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
            loadingDialog.setCancelable(false);// 不可以用“返回键”取消
            loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
            loadingDialog.show();
        }
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected String doInBackground(Integer... params) {
            int id = 0;
            while (id < count) {
                id = CourseXMLParse.doc_parse_plan_id(id);
                System.out.println("id : --- " + id);
                publishProgress(id);
            }
            return "执行完毕";
        }
        protected void onProgressUpdate(Integer... progress) {
            loading_tv.setText(progress[0] +" / " + count);
            super.onProgressUpdate(progress);
        }
        protected void onPostExecute(String result) {
            loadingDialog.dismiss();
            load_list();
            super.onPostExecute(result);
        }
    }

}
