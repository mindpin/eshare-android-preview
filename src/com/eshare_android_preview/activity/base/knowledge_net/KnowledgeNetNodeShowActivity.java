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
import android.widget.GridView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.notes.AddNoteActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.ImageTools;
import com.eshare_android_preview.model.KnowledgeNetNode;
import com.eshare_android_preview.widget.adapter.KnowledgeNetNodesAdapter;

import java.util.List;

public class KnowledgeNetNodeShowActivity extends EshareBaseActivity {
    public static class ExtraKeys {
        public static final String NODE = "node";
    }
    GridView children_grid_view, parents_grid_view;
    TextView add_favourite_btn, cancel_favourite_btn;
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

        if (!node.is_faved()) {
            add_favourite_btn.setVisibility(View.VISIBLE);
            cancel_favourite_btn.setVisibility(View.GONE);
        } else {
            add_favourite_btn.setVisibility(View.GONE);
            cancel_favourite_btn.setVisibility(View.VISIBLE);
        }

        _set_icons();

        super.onCreate(savedInstanceState);
    }

    private void _set_icons() {
        set_fontawesome((TextView) findViewById(R.id.kn_icon));
        set_fontawesome((TextView) findViewById(R.id.kn_prev_icon));
        set_fontawesome((TextView) findViewById(R.id.kn_next_icon));

        BitmapDrawable drawable = ImageTools.toRoundCorner(
                (BitmapDrawable) getResources().getDrawable(R.drawable.lan_js), 100);
        findViewById(R.id.javascript_icon).setBackgroundDrawable(drawable);

        set_fontawesome((TextView) findViewById(R.id.add_note_icon));
        set_fontawesome((TextView) findViewById(R.id.add_fav_icon));
        set_fontawesome((TextView) findViewById(R.id.cancel_fav_icon));
    }

    private void load_list_view() {
        children_grid_view = (GridView) findViewById(R.id.children_grid_view);
        List<KnowledgeNetNode> children_node_list = KnowledgeNetNode.find(node.list_children);
        KnowledgeNetNodesAdapter child_adapter = new KnowledgeNetNodesAdapter(this);
        child_adapter.add_items(children_node_list);
        children_grid_view.setAdapter(child_adapter);
        child_adapter.notifyDataSetChanged();
        children_grid_view.setSelector(new ColorDrawable(Color.TRANSPARENT));

		children_grid_view.setOnItemClickListener(item_click_listener);
		
		// -----------------------------
		
		parents_grid_view = (GridView)findViewById(R.id.parents_grid_view);
		List<KnowledgeNetNode> parents_node_list = KnowledgeNetNode.find(node.list_parents);
        KnowledgeNetNodesAdapter parends_adapter = new KnowledgeNetNodesAdapter(this);
        parends_adapter.add_items(parents_node_list);
        parents_grid_view.setAdapter(parends_adapter);
        parends_adapter.notifyDataSetChanged();
        parents_grid_view.setSelector(new ColorDrawable(Color.TRANSPARENT));

		parents_grid_view.setOnItemClickListener(item_click_listener);

        add_favourite_btn = (TextView) findViewById(R.id.add_fav_icon);
        cancel_favourite_btn = (TextView) findViewById(R.id.cancel_fav_icon);
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
        bundle.putSerializable(AddNoteActivity.ExtraKeys.LEARNING_RESOURCE, node);

        Intent intent = new Intent(KnowledgeNetNodeShowActivity.this, AddNoteActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @SuppressLint({"WorldReadableFiles", "WorldWriteableFiles"})
    public void add_favourite(View view) {
        node.add_to_fav();

        add_favourite_btn.setVisibility(View.GONE);
        cancel_favourite_btn.setVisibility(View.VISIBLE);
    }

    @SuppressLint({"WorldReadableFiles", "WorldWriteableFiles"})
    public void cancel_favourite(View view) {
        node.remove_from_fav();

        add_favourite_btn.setVisibility(View.VISIBLE);
        cancel_favourite_btn.setVisibility(View.GONE);
    }
}
