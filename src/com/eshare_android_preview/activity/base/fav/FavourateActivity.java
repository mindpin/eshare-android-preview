package com.eshare_android_preview.activity.base.fav;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.questions.QuestionShowActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.widget.adapter.QuestionsAdapter;




@SuppressLint("WorldReadableFiles")
public class FavourateActivity extends EshareBaseActivity{
	ListView list_view;
	TextView item_title_tv;
	
	List<Question> favourate_list =  new ArrayList<Question>();
	private static final String FAVOURATE_IDS = "favourate_ids";  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.tab_favourate);
		
		load_list_view();
		
		hide_head_setting_button();
        set_head_text(R.string.favourate_knowledge_list);
        super.onCreate(savedInstanceState);
	}
	
	private void load_list_view() {
		list_view = (ListView)findViewById(R.id.list_view);
		
		SharedPreferences sp = getSharedPreferences(FAVOURATE_IDS, MODE_WORLD_READABLE);  
		String favourate_ids = sp.getString("favourate_ids", "");
		if (favourate_ids.equals("")) {
			return;
		}
		
		Log.d("value = ", favourate_ids);
		
		String[] strArray = favourate_ids.split(",");
		for(int i = 0; i < strArray.length; i++) {
			System.out.println("----------   " + strArray[i]);
			int question_id = Integer.parseInt(strArray[i]);
			Log.d("question_id = ", question_id + "");
			Question question = HttpApi.question_find_by(question_id);
			Log.d("title = ", question.title);
			favourate_list.add(question);
		}
		
	
		QuestionsAdapter adapter = new QuestionsAdapter(this);
		adapter.add_items(favourate_list);
		list_view.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		list_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list_view, View list_item,int item_id, long position) {
				TextView info_tv = (TextView) list_item.findViewById(R.id.info_tv);
				Question item = (Question) info_tv.getTag(R.id.tag_current_question);
                // Question item = (Question) info_tv.getTag(R.id.tag_note_uuid);

                Log.d("value ===", item.title);
				
				Bundle bundle = new Bundle();
		        bundle.putSerializable("item", item);
		        Intent intent = new Intent(FavourateActivity.this,QuestionShowActivity.class);
		        intent.putExtras(bundle);
		        startActivity(intent);
			}
		});
	}
	
	
}