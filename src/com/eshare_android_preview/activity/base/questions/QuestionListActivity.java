package com.eshare_android_preview.activity.base.questions;

import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.widget.adapter.QuestionsAdapter;

public class QuestionListActivity extends EshareBaseActivity{
	ListView list_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.q_question_list);
		
		set_head_text(R.string.questions_title);
        hide_head_setting_button();
		load_list_view();
        super.onCreate(savedInstanceState);
	}
	
	private void load_list_view() {
		list_view = (ListView)findViewById(R.id.list_view);
		final List<Question> question_list = Question.all();
		QuestionsAdapter adapter = new QuestionsAdapter(this);
		adapter.add_items(question_list);
		list_view.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		list_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list_view, View list_item,int item_id, long position) {
                Question item = (Question) list_item.getTag(R.id.adapter_item_tag);

                Intent intent = new Intent(QuestionListActivity.this, QuestionShowActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(QuestionShowActivity.ExtraKeys.QUESTION, item);
                intent.putExtras(bundle);
		        startActivity(intent);
			}
		});
	}
}
