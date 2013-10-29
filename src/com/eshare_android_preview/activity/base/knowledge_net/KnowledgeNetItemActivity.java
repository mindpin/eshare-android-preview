package com.eshare_android_preview.activity.base.knowledge_net;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.notes.AddNoteActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Favourate;
import com.eshare_android_preview.model.Node;
import com.eshare_android_preview.model.database.FavouratesDBHelper;
import com.eshare_android_preview.widget.adapter.KnowledgeNetItemAdapter;

public class KnowledgeNetItemActivity extends EshareBaseActivity{
    TextView node_name;
    TextView node_desc;
	ListView child_list_view,parends_list_view;
    Button add_favourate_btn;
    Button cancel_favourate_btn;
	
	Node node;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.kn_knowledge_net_item);
        hide_head_setting_button();
		
		String item_id = getIntent().getStringExtra("item_id");
		node = HttpApi.find_by_id(item_id);
        set_head_text(node.name);
		
		load_node_msg();
		load_list_view();


        Favourate favourate = HttpApi.find_favourate(item_id + "", FavouratesDBHelper.Kinds.NODE);

        if (favourate == null) {
            add_favourate_btn.setVisibility(View.VISIBLE);
            cancel_favourate_btn.setVisibility(View.GONE);
        } else {
            add_favourate_btn.setVisibility(View.GONE);
            cancel_favourate_btn.setVisibility(View.VISIBLE);
        }


        super.onCreate(savedInstanceState);
	}
	
	private void load_node_msg() {
		node_name = (TextView)findViewById(R.id.node_name);
		node_desc = (TextView)findViewById(R.id.node_desc);

		node_name.setText(node.name);
		node_desc.setText(node.desc);
		
	}

	private void load_list_view() {
		child_list_view = (ListView)findViewById(R.id.child_list_view);
		List<Node> child_node_list = HttpApi.get_nodes_by_node_ids(node.list_children);
		KnowledgeNetItemAdapter child_adapter = new KnowledgeNetItemAdapter(this);
		child_adapter.add_items(child_node_list);
		child_list_view.setAdapter(child_adapter);
		child_adapter.notifyDataSetChanged();
		
		child_list_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list_view, View list_item,int item_id, long position) {
				TextView info_tv = (TextView) list_item.findViewById(R.id.info_tv);
				Node node = (Node) info_tv.getTag(R.id.tag_note_uuid);
				BaseUtils.toast(node.list_parents.toString());
			}
		});
		child_list_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list_view, View list_item,int item_id, long position) {
				TextView info_tv = (TextView) list_item.findViewById(R.id.info_tv);
				Node node = (Node) info_tv.getTag(R.id.tag_note_uuid);
//				BaseUtils.toast(node.list_parents.toString());
				refresh(node);
			}
		});
		
		// -----------------------------
		
		parends_list_view = (ListView)findViewById(R.id.parends_list_view);
		List<Node> parends_node_list = HttpApi.get_nodes_by_node_ids(node.list_parents);
		KnowledgeNetItemAdapter parends_adapter = new KnowledgeNetItemAdapter(this);
		parends_adapter.add_items(parends_node_list);
		parends_list_view.setAdapter(parends_adapter);
		parends_adapter.notifyDataSetChanged();
		
		parends_list_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list_view, View list_item,int item_id, long position) {
				TextView info_tv = (TextView) list_item.findViewById(R.id.info_tv);
				Node node = (Node) info_tv.getTag(R.id.tag_note_uuid);
//				BaseUtils.toast(node.list_parents.toString());
				refresh(node);
			}
		});

        add_favourate_btn = (Button) findViewById(R.id.add_favourate_btn);
        cancel_favourate_btn = (Button) findViewById(R.id.cancel_favourate_btn);
	}
	
	public void refresh(Node node){
		this.node = node;
		load_node_msg();
		load_list_view();
	}
	
	public void click_notes(View view){
		Bundle bundle = new Bundle();
        bundle.putSerializable("item", node);

        Intent intent = new Intent(KnowledgeNetItemActivity.this,AddNoteActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
	}

    @SuppressLint({ "WorldReadableFiles", "WorldWriteableFiles" })
    public void add_favourate(View view) {
        String item_id = getIntent().getStringExtra("item_id");
        node = HttpApi.find_by_id(item_id);

        Favourate favourate = new Favourate(node.id, FavouratesDBHelper.Kinds.NODE);
        HttpApi.create_favourate(favourate);

        add_favourate_btn.setVisibility(View.GONE);
        cancel_favourate_btn.setVisibility(View.VISIBLE);
    }

    @SuppressLint({ "WorldReadableFiles", "WorldWriteableFiles" })
    public void cancel_favourate(View view) {
        String item_id = getIntent().getStringExtra("item_id");
        node = HttpApi.find_by_id(item_id);

        Favourate favourate = HttpApi.find_favourate(node.id + "", FavouratesDBHelper.Kinds.NODE);
        HttpApi.cancel_favourate(favourate);

        add_favourate_btn.setVisibility(View.VISIBLE);
        cancel_favourate_btn.setVisibility(View.GONE);
    }
}
