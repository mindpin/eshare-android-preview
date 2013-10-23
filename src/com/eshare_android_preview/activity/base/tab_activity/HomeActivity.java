package com.eshare_android_preview.activity.base.tab_activity;

import java.util.ArrayList;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
		ArrayList<GridViewData> lists = new ArrayList<GridViewData>();
        lists.add(
            new GridViewData("计划", R.drawable.tab_qa_btn_image_bg_normal, PlansActivity.class)
        );
        lists.add(
            new GridViewData("笔记", R.drawable.tab_find_frd_normal, NotesActivity.class)
        );
        lists.add(
            new GridViewData("收藏", R.drawable.tab_message_btn_image_bg_selected, FavourateActivity.class)
        );
        lists.add(
            new GridViewData("学习", R.drawable.tab_find_frd_normal, KnowledgeNetCategoryActivity.class)
        );
        lists.add(
            new GridViewData("题目", R.drawable.tab_message_btn_image_bg_selected, KnowledgeNetQuestionActivity.class)
        );
        lists.add(
            new GridViewData("分组", R.drawable.tab_home_btn_image_bg_normal, GroupActivity.class)
        );

		GridViewAdapter adapter = new GridViewAdapter(this);
		adapter.add_items(lists);
		grid_view.setAdapter(adapter);
		
		grid_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list_view, View list_item,int item_id, long position) {
				
				TextView info_tv = (TextView)list_item.findViewById(R.id.info_tv);
				@SuppressWarnings("unchecked")
                GridViewData item = (GridViewData) info_tv.getTag(R.id.tag_home_grid_view_data);
                open_activity(item.activity);
			}
		});
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

    public class GridViewData {
        public Class activity;
        public int img;
        public String text;

        public GridViewData(String text, int img, Class activity){
            this.text = text;
            this.img = img;
            this.activity= activity;
        }
    }
}
