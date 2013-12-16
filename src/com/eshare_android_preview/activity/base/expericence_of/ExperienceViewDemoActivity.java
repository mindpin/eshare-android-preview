package com.eshare_android_preview.activity.base.expericence_of;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.view.ExperienceView;
/**
 * Created by menxu on 13-12-10.
 */
public class ExperienceViewDemoActivity extends EshareBaseActivity{
	ExperienceView experience_of_view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.demo_expericence_view);
		
		init_view();
	}
	private void init_view() {
		experience_of_view = (ExperienceView)findViewById(R.id.experience_view);
	}
	public void click_experience_of_animator(View view){
		EditText exp_tv = (EditText)findViewById(R.id.exp_tv);
		String e_s = exp_tv.getText().toString();
		int num = 0;
		if (!BaseUtils.is_str_blank(e_s)) {
			num = Integer.parseInt(e_s);
		}
		experience_of_view.add(num);
	}
}
