package com.eshare_android_preview.activity.base.fav;

import java.io.Serializable;
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
import com.eshare_android_preview.activity.base.knowledge_net.KnowledgeNetItemActivity;
import com.eshare_android_preview.activity.base.plans.PlanShowActivity;
import com.eshare_android_preview.activity.base.questions.QuestionShowActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Favourate;
import com.eshare_android_preview.model.Node;
import com.eshare_android_preview.model.Plan;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.database.FavouratesDBHelper;
import com.eshare_android_preview.widget.adapter.FavouratesAdapter;




@SuppressLint("WorldReadableFiles")
public class FavourateActivity extends EshareBaseActivity {
	ListView list_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.tab_favourate);
		
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

        list_view = (ListView)findViewById(R.id.list_view);
        list_view.setVisibility(View.GONE);

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
                Favourate item = (Favourate) list_item.getTag(R.id.adapter_item_tag);

                if (item.kind.equals(FavouratesDBHelper.Kinds.QUESTION)) {
                    Intent intent = new Intent(FavourateActivity.this, QuestionShowActivity.class);
                    Question question = HttpApi.question_find_by(Integer.parseInt(item.favourate_id));

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(QuestionShowActivity.ExtraKeys.QUESTION, question);
                    intent.putExtras(bundle);

                    startActivity(intent);

                } else if (item.kind.equals(FavouratesDBHelper.Kinds.PLAN)) {
                    Intent intent = new Intent(FavourateActivity.this, PlanShowActivity.class);
                    Plan plan = HttpApi.plan_find_by(Integer.parseInt(item.favourate_id));

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(PlanShowActivity.ExtraKeys.PLAN, plan);
                    intent.putExtras(bundle);

                    startActivity(intent);

                } else if (item.kind.equals(FavouratesDBHelper.Kinds.NODE)) {
                    Intent intent = new Intent(FavourateActivity.this, KnowledgeNetItemActivity.class);
                    Node node = HttpApi.find_by_id(item.favourate_id);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KnowledgeNetItemActivity.ExtraKeys.NODE, node);
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