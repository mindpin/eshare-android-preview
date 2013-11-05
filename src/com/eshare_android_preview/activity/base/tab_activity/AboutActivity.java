package com.eshare_android_preview.activity.base.tab_activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;

public class AboutActivity extends EshareBaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.tab_about);
        this.hide_head_go_back_button();
        this.set_head_text(R.string.category_about);
		super.onCreate(savedInstanceState);

        set_fontawesome((TextView) findViewById(R.id.icon_t1));
        set_fontawesome((TextView) findViewById(R.id.icon_t2));
	}



	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
