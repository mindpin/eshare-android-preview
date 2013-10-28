package com.eshare_android_preview.activity.base.questions;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.widget.adapter.QuestionsAdapter;

public class KnowledgeNetQuestionActivity extends EshareBaseActivity{
	ListView list_view;
	TextView item_title_tv;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.q_knowledge_net_question);
		
//		Intent intent = getIntent();
//		String item = (String) intent.getExtras().getSerializable("item");
//		item_title_tv = (TextView)findViewById(R.id.item_title_tv);
//		item_title_tv.setText(item);
		set_head_text(R.string.questions_title);
        hide_head_setting_button();
		load_list_view();
        super.onCreate(savedInstanceState);
	}
	
	private void load_list_view() {
		list_view = (ListView)findViewById(R.id.list_view);
		final List<Question> node_list = HttpApi.get_questions();
		QuestionsAdapter adapter = new QuestionsAdapter(this);
		adapter.add_items(node_list);
		list_view.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		list_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list_view, View list_item,int item_id, long position) {
				TextView info_tv = (TextView) list_item.findViewById(R.id.info_tv);
				Question item = (Question) info_tv.getTag(R.id.tag_note_uuid);
				
		        Intent intent = new Intent(KnowledgeNetQuestionActivity.this,QuestionShowActivity.class);
		        intent.putExtra("item_id",item.id+"");
		        startActivity(intent);
			}
		});
	}
}
