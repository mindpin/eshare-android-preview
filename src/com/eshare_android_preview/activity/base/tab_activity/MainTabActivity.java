package com.eshare_android_preview.activity.base.tab_activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import com.eshare_android_preview.R;

public class MainTabActivity extends TabActivity  implements OnClickListener {
	
	private TabHost tab_Host;
	int current_tab_id;
	
	FrameLayout tab_home_btn, tab_qa_btn, tab_message_btn;
	ImageView tab_home_btn_image,tab_qa_btn_image,tab_message_btn_image;
	TextView tab_home_btn_text, tab_qa_btn_text, tab_message_btn_text;
	
	private Animation left_in, left_out;
	private Animation right_in, right_out;
	
	public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.tab_main);  
        
        prepare_view();
        init_tab_switch_anim();
        init_tab_host();
		
		tab_home_btn_image.setSelected(true);
		tab_home_btn_text.setSelected(true);
		current_tab_id = R.id.tab_home_btn;
		
		test_msg_show_view();
    }
	
	private void test_msg_show_view(){
		TextView tab_qa_btn_unread_text = (TextView)findViewById(R.id.tab_qa_btn_unread_text);
		tab_qa_btn_unread_text.setText("3");
		tab_qa_btn_unread_text.setVisibility(View.VISIBLE);
	}
	
	private void prepare_view() {
		// 底部三个切换页签的按钮
		tab_home_btn = (FrameLayout)findViewById(R.id.tab_home_btn);
		tab_qa_btn = (FrameLayout)findViewById(R.id.tab_qa_btn);
		tab_message_btn = (FrameLayout)findViewById(R.id.tab_message_btn);
		// 底部三个切换页签按钮的图片
		tab_home_btn_image = (ImageView) findViewById(R.id.tab_home_btn_image);
		tab_qa_btn_image = (ImageView) findViewById(R.id.tab_qa_btn_image);
		tab_message_btn_image = (ImageView) findViewById(R.id.tab_message_btn_image);
		// 底部三个切换页签按钮的标题
		tab_home_btn_text = (TextView) findViewById(R.id.tab_home_btn_text);
		tab_qa_btn_text = (TextView) findViewById(R.id.tab_qa_btn_text);
		tab_message_btn_text = (TextView) findViewById(R.id.tab_message_btn_text);
		// 给底部三个切换页签的按钮注册点击事件
		findViewById(R.id.tab_home_btn).setOnClickListener(this);
		findViewById(R.id.tab_qa_btn).setOnClickListener(this);
		findViewById(R.id.tab_message_btn).setOnClickListener(this);
	}
	
	private void init_tab_switch_anim() {
		left_in = AnimationUtils.loadAnimation(this, R.anim.left_in);
		left_out = AnimationUtils.loadAnimation(this, R.anim.left_out);

		right_in = AnimationUtils.loadAnimation(this, R.anim.right_in);
		right_out = AnimationUtils.loadAnimation(this, R.anim.right_out);
	}
	
	private void init_tab_host() {
		tab_Host = getTabHost();
		tab_Host.addTab(build_tab_spec(
				getResources().getString(R.string.category_home),
				R.string.category_home,
				R.drawable.tab_home_btn_image_bg_normal, 
				new Intent(this, HomeActivity.class)));
		
		tab_Host.addTab(build_tab_spec(
				getResources().getString(R.string.category_qa),
				R.string.category_qa, 
				R.drawable.tab_qa_btn_image_bg_normal, 
				new Intent(this, QAActivity.class)));
		
		tab_Host.addTab(build_tab_spec(
				getResources().getString(R.string.category_message),
				R.string.category_message, 
				R.drawable.tab_message_btn_image_bg_normal, 
				new Intent(this, MessageActivity.class)));
	}
	
	private TabHost.TabSpec build_tab_spec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return tab_Host
				.newTabSpec(tag)
				.setIndicator(getString(resLabel),
						getResources().getDrawable(resIcon))
				.setContent(content);
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			tab_home_btn.performClick();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onClick(View v) {
		if (current_tab_id == v.getId()) {
			return;
		}
		
		tab_home_btn_image.setSelected(false);
		tab_home_btn_text.setSelected(false);
		
		tab_qa_btn_text.setSelected(false);
		tab_qa_btn_image.setSelected(false);
		
		tab_message_btn_text.setSelected(false);
		tab_message_btn_image.setSelected(false);
		
		int checked_id = v.getId();
		boolean swtich_to_right = current_tab_id < checked_id ? true : false;
		
		if (swtich_to_right){
			tab_Host.getCurrentView().startAnimation(left_out);
		}else{
			tab_Host.getCurrentView().startAnimation(right_out);
		}
		
		switch (checked_id) {
		case R.id.tab_home_btn:
			tab_Host.setCurrentTabByTag(getResources().getString(R.string.category_home));
			tab_home_btn_image.setSelected(true);
			tab_home_btn_text.setSelected(true);
			break;
		case R.id.tab_qa_btn:
			tab_Host.setCurrentTabByTag(getResources().getString(R.string.category_qa));
			tab_qa_btn_text.setSelected(true);
			tab_qa_btn_image.setSelected(true);
			break;
		case R.id.tab_message_btn:
			tab_Host.setCurrentTabByTag(getResources().getString(R.string.category_message));
			tab_message_btn_text.setSelected(true);
			tab_message_btn_image.setSelected(true);
			break;
		default:
			break;
		}

		if (swtich_to_right){
			tab_Host.getCurrentView().startAnimation(right_in);
		}else{
			tab_Host.getCurrentView().startAnimation(left_in);
		}
		current_tab_id = checked_id;
	} 
}
