package com.eshare_android_preview.activity.base.questions;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.ImageTools;

public class QuestionListActivity extends EshareBaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.q_question_list);
		
		set_head_text(R.string.questions_title);
        hide_head_setting_button();
        _set_icon_fonts();
        hide_head_bottom_line();
        super.onCreate(savedInstanceState);
	}

    private void _set_icon_fonts() {
        set_fontawesome((TextView) findViewById(R.id.question_icon));

        BitmapDrawable drawable = ImageTools.toRoundCorner(
                (BitmapDrawable) getResources().getDrawable(R.drawable.lan_js), 100);
        ImageView lan_icon_iv = (ImageView) findViewById(R.id.lan_icon);
        lan_icon_iv.setBackgroundDrawable(drawable);
    }
}
