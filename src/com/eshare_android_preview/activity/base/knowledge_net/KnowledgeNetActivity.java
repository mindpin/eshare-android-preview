package com.eshare_android_preview.activity.base.knowledge_net;

import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Node;
import com.eshare_android_preview.widget.adapter.KnowledgeNetAdapter;

public class KnowledgeNetActivity extends EshareBaseActivity{
	GridView grid_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.kn_knowledge_net);
        hide_head_setting_button();

//		Intent intent = getIntent();
//		String item_name = (String) intent.getExtras().getSerializable("item");
        set_head_text("知识领域");
		hide_head_bottom_line();
		load_list_view();
        _set_icon_font();
        super.onCreate(savedInstanceState);
	}

    private void _set_icon_font() {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");
        TextView tv = (TextView) findViewById(R.id.javascript_icon);
        tv.setTypeface(font);
    }
	
	private void load_list_view() {
		grid_view = (GridView)findViewById(R.id.grid_view);
		List<Node> node_list = HttpApi.get_nodes("javascript");
		KnowledgeNetAdapter adapter = new KnowledgeNetAdapter(this);
		adapter.add_items(node_list);
		grid_view.setAdapter(adapter);
        grid_view.setSelector(new ColorDrawable(Color.TRANSPARENT));

		adapter.notifyDataSetChanged();
		
		grid_view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list_view, View list_item, int item_id, long position) {
                TextView item_tv = (TextView) list_item.findViewById(R.id.item_tv);
                Node node = (Node) item_tv.getTag();

                Intent intent = new Intent(KnowledgeNetActivity.this, KnowledgeNetItemActivity.class);
                intent.putExtra("item_id", node.node_id);
                startActivity(intent);
            }
        });
	}
}
