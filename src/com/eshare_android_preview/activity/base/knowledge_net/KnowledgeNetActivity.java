package com.eshare_android_preview.activity.base.knowledge_net;

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
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Node;
import com.eshare_android_preview.widget.adapter.KnowledgeNetAdapter;

public class KnowledgeNetActivity extends EshareBaseActivity{
	ListView list_view;
	TextView item_title_tv;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kn_knowledge_net);
		
		Intent intent = getIntent();
		String item = (String) intent.getExtras().getSerializable("item");
		item_title_tv = (TextView)findViewById(R.id.item_title_tv);
		item_title_tv.setText(item);
		
		load_list_view();
	}
	private void load_list_view() {
		list_view = (ListView)findViewById(R.id.list_view);
		List<Node> node_list = HttpApi.get_nodes("javascript");
		KnowledgeNetAdapter adapter = new KnowledgeNetAdapter(this);
		adapter.add_items(node_list);
		list_view.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		list_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list_view, View list_item,int item_id, long position) {
				TextView info_tv = (TextView) list_item.findViewById(R.id.info_tv);
				Node node = (Node) info_tv.getTag(R.id.tag_note_uuid);
				BaseUtils.toast(node.list_parents.toString());
				
				Bundle bundle = new Bundle();
		        bundle.putSerializable("node", node);

		        Intent intent = new Intent(KnowledgeNetActivity.this,KnowledgeNetItemActivity.class);
		        intent.putExtras(bundle);
		        startActivity(intent);
			}
		});
	}
}
