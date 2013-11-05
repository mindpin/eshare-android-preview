package com.eshare_android_preview.activity.base.questions;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.ImageTools;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.TestResult;

public class QuestionListActivity extends EshareBaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.q_question_list);
		
		set_head_text(R.string.questions_title);
        hide_head_setting_button();
        _set_icon_fonts();
        hide_head_bottom_line();

        _set_start_answer_button();
        super.onCreate(savedInstanceState);
	}

    private void _set_icon_fonts() {
        set_fontawesome((TextView) findViewById(R.id.question_icon));

        BitmapDrawable drawable = ImageTools.toRoundCorner(
                (BitmapDrawable) getResources().getDrawable(R.drawable.lan_js), 100);
        ImageView lan_icon_iv = (ImageView) findViewById(R.id.lan_icon);
        lan_icon_iv.setBackgroundDrawable(drawable);
    }

    private void _set_start_answer_button() {
        TextView start_answer_tv = (TextView) findViewById(R.id.start_answer_tv);
        start_answer_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionListActivity.this, QuestionShowActivity.class);
                Bundle bundle = new Bundle();
                TestResult test_result = new TestResult();
                bundle.putSerializable(QuestionShowActivity.ExtraKeys.TEST_RESULT, test_result);
                bundle.putSerializable(QuestionShowActivity.ExtraKeys.QUESTION, Question.first());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
