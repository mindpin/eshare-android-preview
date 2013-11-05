package com.eshare_android_preview.activity.base.fav;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.knowledge_net.KnowledgeNetNodeShowActivity;
import com.eshare_android_preview.activity.base.plans.PlanShowActivity;
import com.eshare_android_preview.activity.base.questions.QuestionShowActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.model.Course;
import com.eshare_android_preview.model.Favourite;
import com.eshare_android_preview.model.KnowledgeNetNode;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.TestResult;
import com.eshare_android_preview.widget.adapter.FavouritesAdapter;


@SuppressLint("WorldReadableFiles")
public class FavouriteListActivity extends EshareBaseActivity {
	ListView list_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.f_favourite_list);
		
		hide_head_setting_button();
        set_head_text(R.string.favourite_knowledge_list);
        super.onCreate(savedInstanceState);
	}
	
	private void load_list_view() {
        final List<Favourite> favourite_list = Favourite.all();
		if (favourite_list.size() == 0) {
            process_when_fav_list_is_empty();
		} else {
            build_fav_list_adapter(favourite_list);
        }
	}

    private void process_when_fav_list_is_empty() {
        View fav_list_empty_tip_tv = findViewById(R.id.fav_list_empty_tip_tv);
        fav_list_empty_tip_tv.setVisibility(View.VISIBLE);

        list_view = (ListView)findViewById(R.id.list_view);
        list_view.setVisibility(View.GONE);

    }

    private void build_fav_list_adapter(List<Favourite> favourite_list) {
        list_view = (ListView)findViewById(R.id.list_view);

        FavouritesAdapter adapter = new FavouritesAdapter(this);
        adapter.add_items(favourite_list);
        list_view.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        list_view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list_view, View list_item,int item_id, long position) {
                Favourite item = (Favourite) list_item.getTag(R.id.adapter_item_tag);

                if (item.is_belong_question()) {
                    Intent intent = new Intent(FavouriteListActivity.this, QuestionShowActivity.class);
                    Question question = Question.find(Integer.parseInt(item.favourite_id));
                    Bundle bundle = new Bundle();
                    TestResult test_result = new TestResult();
                    bundle.putSerializable(QuestionShowActivity.ExtraKeys.TEST_RESULT, test_result);
                    bundle.putSerializable(QuestionShowActivity.ExtraKeys.QUESTION, question);
                    intent.putExtras(bundle);

                    startActivity(intent);

                } else if (item.is_belong_course()) {
                    Intent intent = new Intent(FavouriteListActivity.this, PlanShowActivity.class);
                    Course plan = Course.find(Integer.parseInt(item.favourite_id));

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(PlanShowActivity.ExtraKeys.PLAN, plan);
                    intent.putExtras(bundle);

                    startActivity(intent);

                } else if (item.is_belong_knowledge_net_node()) {
                    Intent intent = new Intent(FavouriteListActivity.this, KnowledgeNetNodeShowActivity.class);
                    KnowledgeNetNode node = KnowledgeNetNode.find(item.favourite_id);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KnowledgeNetNodeShowActivity.ExtraKeys.NODE, node);
                    intent.putExtras(bundle);

                    startActivity(intent);
                }


            }
        });
    }

    @Override
    protected void onResume() {
        load_list_view();
        super.onResume();
    }
}