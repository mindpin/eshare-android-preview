package com.eshare_android_preview.activity.base.fav;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
        final List<Question> favourate_list = HttpApi.get_favourates();
		if (favourate_list.size() == 0) {
            process_when_fav_list_is_empty();
		}else{
            build_fav_list_adapter(favourate_list);
        }
	}

    private void process_when_fav_list_is_empty() {
        View fav_list_empty_tip_tv = findViewById(R.id.fav_list_empty_tip_tv);
        fav_list_empty_tip_tv.setVisibility(View.VISIBLE);
    }

    private void build_fav_list_adapter(List<Question> favourate_list) {
        list_view = (ListView)findViewById(R.id.list_view);

        QuestionsAdapter adapter = new QuestionsAdapter(this);
        adapter.add_items(favourate_list);
        list_view.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        list_view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list_view, View list_item,int item_id, long position) {
                TextView info_tv = (TextView) list_item.findViewById(R.id.info_tv);
                Question item = (Question) info_tv.getTag(R.id.tag_current_question);

                Intent intent = new Intent(FavourateActivity.this,QuestionShowActivity.class);
                intent.putExtra("item", item);
                intent.putExtra("is_favourated", true);
                startActivity(intent);
            }
        });
    }


}