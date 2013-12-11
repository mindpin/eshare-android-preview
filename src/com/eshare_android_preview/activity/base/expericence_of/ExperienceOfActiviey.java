package com.eshare_android_preview.activity.base.expericence_of;

import android.os.Bundle;
import android.view.View;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.view.ExperienceOfView;
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
		experience_of_view.set_rect_width_animate(280F, 500);
	}
}
