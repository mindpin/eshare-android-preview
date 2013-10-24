package com.eshare_android_preview.activity.base.tab_activity;

import java.util.ArrayList;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.groups.GroupActivity;
import com.eshare_android_preview.activity.base.knowledge_net.KnowledgeNetCategoryActivity;
import com.eshare_android_preview.activity.base.notes.NotesActivity;
import com.eshare_android_preview.activity.base.plans.PlansActivity;
import com.eshare_android_preview.activity.base.questions.KnowledgeNetQuestionActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.ImageTools;
import com.eshare_android_preview.widget.adapter.GridViewAdapter;


public class HomeActivity extends EshareBaseActivity {
	ImageView user_avatar;
	TextView user_name;
	GridView grid_view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.tab_home);

		init_view();
		load_user_data();
		set_buttons_font();

		super.onCreate(savedInstanceState);
	}

	private void set_buttons_font() {
		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");
		int[] icon_ids = new int[]{
				R.id.home_button_plan, R.id.home_button_note, R.id.home_button_fav,
				R.id.home_button_knowledge, R.id.home_button_test, R.id.home_button_group
		};
		
		for(int i = 0; i < icon_ids.length; i++) {
            RelativeLayout button_view = (RelativeLayout) findViewById(icon_ids[i]);
			TextView tv = (TextView) button_view.getChildAt(0);
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
