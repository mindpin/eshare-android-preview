package com.eshare_android_preview.activity.base.knowledge_net;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.notes.AddNoteActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.ImageTools;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Favourite;
import com.eshare_android_preview.model.KnowledgeNetNode;
import com.eshare_android_preview.model.database.FavouriteDBHelper;
import com.eshare_android_preview.widget.adapter.KnowledgeNetNodesAdapter;

import java.util.List;

public class KnowledgeNetNodeShowActivity extends EshareBaseActivity {
    public static class ExtraKeys {
        public static final String NODE = "node";
    }
    GridView children_grid_view, parents_grid_view;
    Button add_favourate_btn;
    Button cancel_favourate_btn;
	KnowledgeNetNode node;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.kn_knowledge_net_node_show);
        hide_head_setting_button();

        Bundle bundle = getIntent().getExtras();
        node = (KnowledgeNetNode)bundle.getSerializable(KnowledgeNetNodeShowActivity.ExtraKeys.NODE);
        ((TextView) findViewById(R.id.kn_name)).setText(node.name);
        set_head_text("知识点");

        load_list_view();


        Favourite favourite = HttpApi.find_favourate(node.node_id, FavouriteDBHelper.Kinds.NODE);

        if (favourite == null) {
            add_favourate_btn.setVisibility(View.VISIBLE);
            cancel_favourate_btn.setVisibility(View.GONE);
        } else {
            add_favourate_btn.setVisibility(View.GONE);
            cancel_favourate_btn.setVisibility(View.VISIBLE);
        }

        _set_icons();

        super.onCreate(savedInstanceState);
    }

    private void _set_icons() {
//        findViewById(R.id.progress).bringToFront();

        set_fontawesome((TextView) findViewById(R.id.kn_icon));
        set_fontawesome((TextView) findViewById(R.id.kn_prev_icon));
        set_fontawesome((TextView) findViewById(R.id.kn_next_icon));

        BitmapDrawable drawable = ImageTools.toRoundCorner(
                (BitmapDrawable) getResources().getDrawable(R.drawable.lan_js), 100);
        findViewById(R.id.javascript_icon).setBackgroundDrawable(drawable);
    }

    private void load_list_view() {
        children_grid_view = (GridView) findViewById(R.id.children_grid_view);
        List<KnowledgeNetNode> children_node_list = HttpApi.get_nodes_by_node_ids(node.list_children);
        KnowledgeNetNodesAdapter child_adapter = new KnowledgeNetNodesAdapter(this);
        child_adapter.add_items(children_node_list);
        children_grid_view.setAdapter(child_adapter);
        child_adapter.notifyDataSetChanged();
        children_grid_view.setSelector(new ColorDrawable(Color.TRANSPARENT));

		children_grid_view.setOnItemClickListener(item_click_listener);
		
		// -----------------------------
		
		parents_grid_view = (GridView)findViewById(R.id.parents_grid_view);
		List<KnowledgeNetNode> parents_node_list = HttpApi.get_nodes_by_node_ids(node.list_parents);
        KnowledgeNetNodesAdapter parends_adapter = new KnowledgeNetNodesAdapter(this);
        parends_adapter.add_items(parents_node_list);
        parents_grid_view.setAdapter(parends_adapter);
        parends_adapter.notifyDataSetChanged();
        parents_grid_view.setSelector(new ColorDrawable(Color.TRANSPARENT));

		parents_grid_view.setOnItemClickListener(item_click_listener);

        add_favourate_btn = (Button) findViewById(R.id.add_favourate_btn);
        cancel_favourate_btn = (Button) findViewById(R.id.cancel_favourate_btn);
	}
	
	// TODO
	OnItemClickListener item_click_listener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> list_view, View list_item, int item_id, long position) {
            KnowledgeNetNode node = (KnowledgeNetNode) list_item.getTag(R.id.adapter_item_tag);
			Intent intent = new Intent(KnowledgeNetNodeShowActivity.this,KnowledgeNetNodeShowActivity.class);

            Bundle bundle = new Bundle();
            bundle.putSerializable(KnowledgeNetNodeShowActivity.ExtraKeys.NODE, node);
            intent.putExtras(bundle);

			startActivity(intent);
			finish();
		}
	};
	
	public void click_notes(View view){
		Bundle bundle = new Bundle();
        bundle.putSerializable("item", node);

        Intent intent = new Intent(KnowledgeNetNodeShowActivity.this, AddNoteActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @SuppressLint({"WorldReadableFiles", "WorldWriteableFiles"})
    public void add_favourate(View view) {
        Favourite favourite = new Favourite(node.node_id, FavouriteDBHelper.Kinds.NODE);
        HttpApi.create_favourate(favourite);

        add_favourate_btn.setVisibility(View.GONE);
        cancel_favourate_btn.setVisibility(View.VISIBLE);
    }

    @SuppressLint({"WorldReadableFiles", "WorldWriteableFiles"})
    public void cancel_favourate(View view) {
        Favourite favourite = HttpApi.find_favourate(node.node_id + "", FavouriteDBHelper.Kinds.NODE);
        HttpApi.cancel_favourate(favourite);

        add_favourate_btn.setVisibility(View.VISIBLE);
        cancel_favourate_btn.setVisibility(View.GONE);
    }
}
