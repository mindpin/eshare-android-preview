package com.eshare_android_preview.activity.base.tab_activity;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.parse.CourseXMLParse;

public class MainTabActivity extends TabActivity  implements OnClickListener {
	public static String TAB_TAG_HOME = "home";
	public static String TAB_TAG_QA = "qa";
	public static String TAB_TAG_MESSAGE = "message";
	
	private TabHost mTabHost;
	
	static final int COLOR1 = Color.parseColor("#787878");
	static final int COLOR2 = Color.parseColor("#ffffff");
	
	public static final String TAB_HOME="tabHome";
    public static final String TAB_MSG = "tabMSG";
    public static final String TAB_HAIR_LOW="tabHairLow";
    public static final String TAB_USERINFO = "tabUserInFo";
    
    ImageView mBut1, mBut2, mBut4;
	TextView mCateText1, mCateText2, mCateText4;
	
	Intent mHomeItent, mRiceIntent, mMessionIntent, mWeatherIntent;
	
	int mCurTabId = R.id.channel1;
	
	private Animation left_in, left_out;
	private Animation right_in, right_out;
	
	
	public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.tab_main);  
        
        prepareAnim();
        prepareIntent();
		setupIntent();
		prepareView();
		
		test_msg_show_view();
    }
	private void test_msg_show_view(){
		TextView main_tab_fmessage_unread_tv = (TextView)findViewById(R.id.main_tab_fmessage_unread_tv);
		main_tab_fmessage_unread_tv.setText("3");
		main_tab_fmessage_unread_tv.setVisibility(View.VISIBLE);
	}
	
	private void prepareView() {
		mBut1 = (ImageView) findViewById(R.id.imageView1);
		mBut2 = (ImageView) findViewById(R.id.imageView2);
		mBut4 = (ImageView) findViewById(R.id.imageView4);
		
		findViewById(R.id.channel1).setOnClickListener(this);
		findViewById(R.id.channel2).setOnClickListener(this);
		findViewById(R.id.channel4).setOnClickListener(this);
		
		mCateText1 = (TextView) findViewById(R.id.textView1);
		mCateText2 = (TextView) findViewById(R.id.textView2);
		mCateText4 = (TextView) findViewById(R.id.textView4);
	}
	
	private void prepareAnim() {
		left_in = AnimationUtils.loadAnimation(this, R.anim.left_in);
		left_out = AnimationUtils.loadAnimation(this, R.anim.left_out);

		right_in = AnimationUtils.loadAnimation(this, R.anim.right_in);
		right_out = AnimationUtils.loadAnimation(this, R.anim.right_out);
	}
	
	private void prepareIntent() {
		mHomeItent = new Intent(this, HomeActivity.class);
		mRiceIntent = new Intent(this, QAActivity.class);
		mWeatherIntent = new Intent(this, MessageActivity.class);
	}
	
	private void setupIntent() {
		mTabHost = getTabHost();
		mTabHost.addTab(buildTabSpec(
				TAB_TAG_HOME, 
				R.string.category_home,
				R.drawable.tab_weixin_normal, 
				mHomeItent));
		
		mTabHost.addTab(buildTabSpec(
				TAB_TAG_QA,
				R.string.category_qa, 
				R.drawable.tab_address_normal, 
				mRiceIntent));
		
		mTabHost.addTab(buildTabSpec(
				TAB_TAG_MESSAGE,
				R.string.category_message, 
				R.drawable.tab_settings_normal, 
				mWeatherIntent));
	}
	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return mTabHost
				.newTabSpec(tag)
				.setIndicator(getString(resLabel),
						getResources().getDrawable(resIcon))
				.setContent(content);
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			mBut1.performClick();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onClick(View v) {
		if (mCurTabId == v.getId()) {
			return;
		}
		mBut1.setImageResource(R.drawable.tab_weixin_normal);
		mBut2.setImageResource(R.drawable.tab_address_normal);
		mBut4.setImageResource(R.drawable.tab_settings_normal);
		
		mCateText1.setTextColor(COLOR1);
		mCateText2.setTextColor(COLOR1);
		mCateText4.setTextColor(COLOR1);
		
		int checkedId = v.getId();
		final boolean o;
		if (mCurTabId < checkedId)
			o = true;
		else
			o = false;
		if (o)
			mTabHost.getCurrentView().startAnimation(left_out);
		else
			mTabHost.getCurrentView().startAnimation(right_out);
		switch (checkedId) {
		case R.id.channel1:
			mTabHost.setCurrentTabByTag(TAB_TAG_HOME);
			mBut1.setImageResource(R.drawable.tab_weixin_pressed);
			mCateText1.setTextColor(COLOR2);
			break;
		case R.id.channel2:
			mTabHost.setCurrentTabByTag(TAB_TAG_QA);
			mBut2.setImageResource(R.drawable.tab_address_pressed);
			mCateText2.setTextColor(COLOR2);
			break;
		case R.id.channel4:
			mTabHost.setCurrentTabByTag(TAB_TAG_MESSAGE);
			mBut4.setImageResource(R.drawable.tab_settings_pressed);
			mCateText4.setTextColor(COLOR2);
			break;
		default:
			break;
		}

		if (o)
			mTabHost.getCurrentView().startAnimation(left_in);
		else
			mTabHost.getCurrentView().startAnimation(right_in);
		mCurTabId = checkedId;
	} 
}
