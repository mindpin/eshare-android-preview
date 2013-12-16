package com.eshare_android_preview.activity.base.expericence_of;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.view.ExperienceOfView;
import com.eshare_android_preview.model.elog.CurrentState;
import com.eshare_android_preview.model.elog.ExperienceLog;
/**
 * Created by menxu on 13-12-10.
 */
public class ExperienceOfActiviey extends EshareBaseActivity{
	ExperienceOfView experience_of_view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.ef_expericence_of);
		
		init_view();
	}
	private void init_view() {
		experience_of_view = (ExperienceOfView)findViewById(R.id.experience_of_view);
	}
	public void click_experience_of_animator(View view){
		EditText exp_tv = (EditText)findViewById(R.id.exp_tv);
		String e_s = exp_tv.getText().toString();
		int num = 0;
		if (!BaseUtils.is_str_blank(e_s)) {
			num = Integer.parseInt(e_s);
//			BaseUtils.toast(num + " : ");
		}
		CurrentState state = ExperienceLog.current_state();
		Float level_up_exp_num = (float) state.level_up_exp_num;
		Float f = (num/level_up_exp_num);
		
		experience_of_view.add(num);
		
//		experience_of_view.set_rect_width_animate(2/3F, 500);
//		experience_of_view.set_left_text(left_text);
//		experience_of_view.set_right_text(right_text);
	}
}
