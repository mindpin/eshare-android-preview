package com.eshare_android_preview.activity.base.tab_activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.groups.GroupActivity;
import com.eshare_android_preview.activity.base.knowledge_net.KnowledgeNetCategoryActivity;
import com.eshare_android_preview.activity.base.notes.NotesActivity;
import com.eshare_android_preview.activity.base.plans.PlansActivity;
import com.eshare_android_preview.activity.base.questions.KnowledgeNetQuestionActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.data.GridViewData;
import com.eshare_android_preview.base.utils.ImageTools;
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
		set_head_font();
		set_buttons_font();
	}
	
	private void set_head_font() {
		Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
		Button go_back_button = (Button) findViewById(R.id.go_back);
		go_back_button.setTypeface(font);
		Button setting_button = (Button) findViewById(R.id.setting);
		setting_button.setTypeface(font);
	}
	
	private void set_buttons_font() {
		Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
		int[] icon_ids = new int[]{
				R.id.icon_plan, R.id.icon_note, R.id.icon_fav,
				R.id.icon_knowledge, R.id.icon_test, R.id.icon_group
		};
		
		for(int i = 0; i < icon_ids.length; i++) {
			TextView tv = (TextView) findViewById(icon_ids[i]);
			tv.setTypeface(font);
		}
	}
	
	private void init_view() {
		user_avatar = (ImageView)findViewById(R.id.user_avatar);
		user_name  = (TextView)findViewById(R.id.user_name);
	}
	private void load_user_data() {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pandaabao);
		Bitmap rounded_bitmap = ImageTools.toRoundCorner(bitmap, 500);
		user_avatar.setBackgroundDrawable(new BitmapDrawable(rounded_bitmap));
		
		user_name.setText("熊猫滚滚");
	}

//	private void open_click_item_activity(Map<String, Object> item){
//		if (item.get("text").equals("计划")) {
//			open_activity(PlansActivity.class);
//		}
//		if (item.get("text").equals("笔记")) {
//			open_activity(NotesActivity.class);
//		}
//		if (item.get("text").equals("题目")) {
//			open_activity(KnowledgeNetQuestionActivity.class);
//		}
//		if (item.get("text").equals("学习")) {
//			open_activity(KnowledgeNetCategoryActivity.class);
//		}
//
//		if (item.get("text").equals("收藏")) {
//			open_activity(FavourateActivity.class);
//		}
//
//        if (item.get("text").equals("分组")) {
//            open_activity(GroupActivity.class);
//        }
//
//	}
	
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
