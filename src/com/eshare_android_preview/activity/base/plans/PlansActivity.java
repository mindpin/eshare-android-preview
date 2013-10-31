package com.eshare_android_preview.activity.base.plans;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Plan;
import com.eshare_android_preview.widget.adapter.PlansAdapter;

import java.util.List;

public class PlansActivity extends EshareBaseActivity{
	ListView list_view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.p_plans);

		hide_head_setting_button();
        hide_head_bottom_line();
        set_head_text(R.string.plans_my_plans);
        _set_btn_fonts();
        super.onCreate(savedInstanceState);
    }

    private void _set_btn_fonts() {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");
        Button button = (Button) findViewById(R.id.add_plan);
        button.setTypeface(font);
    }

    private void load_list() {
        list_view = (ListView) findViewById(R.id.list_view);
        list_view.setDivider(null);
		List<Plan> list = HttpApi.get_plan_checked("true");
		PlansAdapter adapter = new PlansAdapter(this);
		adapter.add_items(list);
		list_view.setAdapter(adapter);
		
		list_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list_view, View list_item,int item_id, long position) {
				Plan plan = (Plan) list_item.getTag(R.id.adapter_item_tag);
				
				Intent intent = new Intent(PlansActivity.this,PlanShowActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable(PlanShowActivity.ExtraKeys.PLAN, plan);
                intent.putExtras(bundle);

				startActivity(intent);
			}
		});
	}
	
	public void click_add_plans(View view){
		open_activity(AddPlanActivity.class);
	}

    @Override
    protected void onResume() {
        load_list();
        super.onResume();
    }
}
