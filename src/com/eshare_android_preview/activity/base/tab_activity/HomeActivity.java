package com.eshare_android_preview.activity.base.tab_activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.knowledge_net.KnowledgeNetCategoryActivity;
import com.eshare_android_preview.activity.base.notes.NotesActivity;
import com.eshare_android_preview.activity.base.plans.PlansActivity;
import com.eshare_android_preview.activity.base.questions.KnowledgeNetQuestionActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.ImageTools;


public class HomeActivity extends EshareBaseActivity {
	ImageView user_avatar;
	TextView user_name;
	RelativeLayout home_button_plan,home_button_note,home_button_fav,home_button_knowledge,home_button_test,home_button_group;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.tab_home);

		init_view();
		load_user_data();
		set_buttons_font();
		set_layout_click();

        hide_head_go_back_button();
        set_head_text("哈哈哈");

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

		home_button_plan = (RelativeLayout)findViewById(R.id.home_button_plan);
		home_button_note = (RelativeLayout)findViewById(R.id.home_button_note);
		home_button_fav = (RelativeLayout)findViewById(R.id.home_button_fav);
		home_button_knowledge = (RelativeLayout)findViewById(R.id.home_button_knowledge);
		home_button_test = (RelativeLayout)findViewById(R.id.home_button_test);
		home_button_group = (RelativeLayout)findViewById(R.id.home_button_group);
		
	}

    private void load_user_data() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pandaabao);
        Bitmap rounded_bitmap = ImageTools.toRoundCorner(bitmap, 500);
        user_avatar.setBackgroundDrawable(new BitmapDrawable(rounded_bitmap));

        user_name.setText("熊猫滚滚");
    }

	private void set_layout_click(){
		home_button_plan.setOnTouchListener(new RelativeLayoutClice(home_button_plan,PlansActivity.class));
		home_button_note.setOnTouchListener(new RelativeLayoutClice(home_button_note,NotesActivity.class));
		home_button_fav.setOnTouchListener(new RelativeLayoutClice(home_button_fav,FavourateActivity.class));
		home_button_knowledge.setOnTouchListener(new RelativeLayoutClice(home_button_knowledge,KnowledgeNetCategoryActivity.class));
		home_button_test.setOnTouchListener(new RelativeLayoutClice(home_button_test,KnowledgeNetQuestionActivity.class));
		home_button_group.setOnTouchListener(new RelativeLayoutClice(home_button_group,PlansActivity.class));
	}
	class RelativeLayoutClice implements OnTouchListener{
		RelativeLayout layout;
		Class activity;
		public RelativeLayoutClice(RelativeLayout layout,Class activity){
			this.layout = layout;
			this.activity = activity;
		}
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			TextView icon_view = (TextView) layout.getChildAt(0);
			TextView text_view = (TextView) layout.getChildAt(1);
			
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				layout.setBackgroundColor(getResources().getColor(R.color.tab_home_item_bg_selected));
				icon_view.setTextColor(getResources().getColor(R.color.tab_home_item_icon_selected));
				text_view.setTextColor(getResources().getColor(R.color.tab_home_item_text_selected));
				break;
			case MotionEvent.ACTION_UP:
				open_activity(activity);
				layout.setBackgroundColor(getResources().getColor(R.color.tab_home_item_bg_normal));
				icon_view.setTextColor(getResources().getColor(R.color.tab_home_item_icon_normal));
				text_view.setTextColor(getResources().getColor(R.color.tab_home_item_text_normal));
			default:
				break;
			}
			return true;
		}
		
	}

	
	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
