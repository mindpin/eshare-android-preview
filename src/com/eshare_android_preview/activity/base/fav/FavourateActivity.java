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
import com.eshare_android_preview.activity.base.plans.PlanShowActivity;
import com.eshare_android_preview.activity.base.questions.QuestionShowActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Favourate;
import com.eshare_android_preview.model.Plan;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.database.FavouratesDBHelper;
import com.eshare_android_preview.widget.adapter.FavouratesAdapter;
import com.eshare_android_preview.widget.adapter.PlanAdapter;
import com.eshare_android_preview.widget.adapter.QuestionsAdapter;




@SuppressLint("WorldReadableFiles")
public class FavourateActivity extends EshareBaseActivity{
	ListView list_view;
	TextView item_title_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.tab_favourate);
		
		load_list_view();
		
		hide_head_setting_button();
        set_head_text(R.string.favourate_knowledge_list);
        super.onCreate(savedInstanceState);
	}
	
	private void load_list_view() {
        final List<Favourate> favourate_list = HttpApi.get_favourates();
		if (favourate_list.size() == 0) {
            process_when_fav_list_is_empty();
		} else {
            build_fav_list_adapter(favourate_list);
        }
	}

    private void process_when_fav_list_is_empty() {
        View fav_list_empty_tip_tv = findViewById(R.id.fav_list_empty_tip_tv);
        fav_list_empty_tip_tv.setVisibility(View.VISIBLE);
    }

    private void build_fav_list_adapter(List<Favourate> favourate_list) {
        list_view = (ListView)findViewById(R.id.list_view);

        FavouratesAdapter adapter = new FavouratesAdapter(this);
        adapter.add_items(favourate_list);
        list_view.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        list_view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list_view, View list_item,int item_id, long position) {
                TextView info_tv = (TextView) list_item.findViewById(R.id.info_tv);

                Favourate item = (Favourate) info_tv.getTag(R.id.tag_favourate);

                if (item.kind.equals(FavouratesDBHelper.Kinds.QUESTION)) {
                    Intent intent = new Intent(FavourateActivity.this, QuestionShowActivity.class);
                    Question question = HttpApi.question_find_by(item.favourate_id);
                    intent.putExtra("item", question);
                    startActivity(intent);

                } else if (item.kind.equals(FavouratesDBHelper.Kinds.PLAN)) {
                    Intent intent = new Intent(FavourateActivity.this, PlanShowActivity.class);
                    Plan plan = HttpApi.plan_find_by(item.favourate_id);
                    intent.putExtra("item", plan);
                    intent.putExtra("item_id", plan.id+"");
                    startActivity(intent);
                }


            }
        });
    }




}