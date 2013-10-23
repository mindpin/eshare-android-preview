package com.eshare_android_preview.activity.base.tab_activity;

import java.util.ArrayList;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TextView;
import com.eshare_android_preview.R;

public class MainTabActivity extends TabActivity  implements OnClickListener {
	
	private TabHost tab_host;
	private ArrayList<Tab> tabs = new ArrayList<Tab>();
	Tab current_tab;
	
	FrameLayout tab_home_btn, tab_qa_btn, tab_message_btn;
	
	private Animation left_in, left_out;
	private Animation right_in, right_out;
	
	public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.tab_main);  
        prepare_view();
        init_tab_switch_anim();
        
        init_tab_host();
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
	}
	
	private void init_tab_switch_anim() {
		left_in = AnimationUtils.loadAnimation(this, R.anim.left_in);
		left_out = AnimationUtils.loadAnimation(this, R.anim.left_out);

		right_in = AnimationUtils.loadAnimation(this, R.anim.right_in);
		right_out = AnimationUtils.loadAnimation(this, R.anim.right_out);
	}
	
	private void init_tab_host() {
		tab_host = getTabHost();
		tab_host.addTab(build_tab_spec(
				R.string.category_home,
				tab_home_btn,
				new Intent(this, HomeActivity.class)));
		
		tab_host.addTab(build_tab_spec(
				R.string.category_qa,
				tab_qa_btn,
				new Intent(this, QAActivity.class)));
		
		tab_host.addTab(build_tab_spec(
				R.string.category_message,
				tab_message_btn,
				new Intent(this, MessageActivity.class)));
	
		Tab first_tab = tabs.get(0);
		first_tab.btn.setSelected(true);
		current_tab = first_tab;

	}
	
	private TabHost.TabSpec build_tab_spec(int res_label, FrameLayout btn, Intent content) {
		String tag_name = getString(res_label);
		int size = tabs.size();
		Tab tab = new Tab(tag_name, btn, size);
		tabs.add(tab);
		btn.setTag(tab);
		// 给底部切换页签的按钮注册点击事件
		btn.setOnClickListener(this);
		return tab_host.newTabSpec(tag_name).setIndicator(tag_name).setContent(content);
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && current_tab.index != 0) {
			tab_home_btn.performClick();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onClick(View v) {
		Tab click_tab = (Tab)v.getTag();
		if (current_tab.index == click_tab.index) {
			return;
		}
		current_tab.btn.setSelected(false);
		
		boolean swtich_to_right = current_tab.index < click_tab.index ? true : false;
		Animation out_anim;
		Animation in_anim;
		if (swtich_to_right){
			out_anim = left_out;
			in_anim = right_in;
		}else{
			out_anim = right_out;
			in_anim = left_in;
		}
		
		tab_host.getCurrentView().startAnimation(out_anim);
		tab_host.setCurrentTabByTag(click_tab.tag_name);
		click_tab.btn.setSelected(true);
		tab_host.getCurrentView().startAnimation(in_anim);
		current_tab = click_tab;
	}
	
	class Tab{
		public String tag_name;
		public FrameLayout btn;
		public int index;

		public Tab(String tag_name, FrameLayout btn, int index) {
			this.tag_name = tag_name;
			this.btn = btn;
			this.index = index;
		}
	}
}
