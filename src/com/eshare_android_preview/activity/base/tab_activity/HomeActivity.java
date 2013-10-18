package com.eshare_android_preview.activity.base.tab_activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.knowledge_net.KnowledgeNetCategoryActivity;
import com.eshare_android_preview.activity.base.questions.KnowledgeNetQuestionActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.data.GridViewData;
import com.eshare_android_preview.widget.adapter.GridViewAdapter;

public class HomeActivity extends EshareBaseActivity {
	ImageView user_avatar;
	TextView user_name;
	GridView grid_view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_home);

		init_view();
		load_user_data();
		load_grid_view_data();
	}
	private void init_view() {
		user_avatar = (ImageView)findViewById(R.id.user_avatar);
		user_name  = (TextView)findViewById(R.id.user_name);
		grid_view = (GridView)findViewById(R.id.grid_view);
	}
	private void load_user_data() {
//		user_avatar.setBackgroundDrawable(d);
		user_name.setText("功夫小子");
	}
	private void load_grid_view_data(){
		final List<Map<String, Object>> lists = GridViewData.get_tab_home_grid_view_data();
		GridViewAdapter adapter = new GridViewAdapter(this);
		adapter.add_items(lists);
		grid_view.setAdapter(adapter);
		
		grid_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list_view, View list_item,int item_id, long position) {
				
				TextView info_tv = (TextView)list_item.findViewById(R.id.info_tv);
				@SuppressWarnings("unchecked")
				Map<String, Object> item = (HashMap<String, Object>) info_tv.getTag(R.id.tag_note_uuid);
				open_click_item_activity(item);
			}
		});
	}
	private void open_click_item_activity(Map<String, Object> item){
//		switch (item.get("text")) {
//		case value:
//			
//			break;
//
//		default:
//			break;
//		}
		if (item.get("text").equals("题目")) {
			open_activity(KnowledgeNetQuestionActivity.class);
		}
		if (item.get("text").equals("学习")) {
			open_activity(KnowledgeNetCategoryActivity.class);
		}
	}
	
	public void on_click_hard_right(View view){
		
	}
	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
