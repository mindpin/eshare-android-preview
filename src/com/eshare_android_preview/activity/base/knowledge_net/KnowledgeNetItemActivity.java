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
import com.eshare_android_preview.model.Favourate;
import com.eshare_android_preview.model.Node;
import com.eshare_android_preview.model.database.FavouratesDBHelper;
import com.eshare_android_preview.widget.adapter.KnowledgeNetAdapter;

import java.util.List;

public class KnowledgeNetItemActivity extends EshareBaseActivity {
    GridView children_grid_view, parents_grid_view;
    Button add_favourate_btn;
    Button cancel_favourate_btn;

    Node node;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.kn_knowledge_net_item);
        hide_head_setting_button();

        String item_id = getIntent().getStringExtra("item_id");
        node = HttpApi.find_by_id(item_id);
        ((TextView) findViewById(R.id.kn_name)).setText(node.name);
        set_head_text("知识点");

        load_list_view();


        Favourate favourate = HttpApi.find_favourate(item_id + "", FavouratesDBHelper.Kinds.NODE);

        if (favourate == null) {
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
        List<Node> children_node_list = HttpApi.get_nodes_by_node_ids(node.list_children);
        KnowledgeNetAdapter child_adapter = new KnowledgeNetAdapter(this);
        child_adapter.add_items(children_node_list);
        children_grid_view.setAdapter(child_adapter);
        child_adapter.notifyDataSetChanged();
        children_grid_view.setSelector(new ColorDrawable(Color.TRANSPARENT));

        children_grid_view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list_view, View list_item, int item_id, long position) {
                Node node = (Node) list_item.getTag(R.id.adapter_item_tag);
                refresh(node);
            }
        });

        // -----------------------------

        parents_grid_view = (GridView) findViewById(R.id.parents_grid_view);
        List<Node> parents_node_list = HttpApi.get_nodes_by_node_ids(node.list_parents);
        KnowledgeNetAdapter parends_adapter = new KnowledgeNetAdapter(this);
        parends_adapter.add_items(parents_node_list);
        parents_grid_view.setAdapter(parends_adapter);
        parends_adapter.notifyDataSetChanged();
        parents_grid_view.setSelector(new ColorDrawable(Color.TRANSPARENT));

        parents_grid_view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list_view, View list_item, int item_id, long position) {
                Node node = (Node) list_item.getTag(R.id.adapter_item_tag);
                refresh(node);
            }
        });

        add_favourate_btn = (Button) findViewById(R.id.add_favourate_btn);
        cancel_favourate_btn = (Button) findViewById(R.id.cancel_favourate_btn);
    }

    public void refresh(Node node) {
        this.node = node;
        load_list_view();
    }

    public void click_notes(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", node);

        Intent intent = new Intent(KnowledgeNetItemActivity.this, AddNoteActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @SuppressLint({"WorldReadableFiles", "WorldWriteableFiles"})
    public void add_favourate(View view) {
        String item_id = getIntent().getStringExtra("item_id");
        node = HttpApi.find_by_id(item_id);

        Favourate favourate = new Favourate(node.id, FavouratesDBHelper.Kinds.NODE);
        HttpApi.create_favourate(favourate);

        add_favourate_btn.setVisibility(View.GONE);
        cancel_favourate_btn.setVisibility(View.VISIBLE);
    }

    @SuppressLint({"WorldReadableFiles", "WorldWriteableFiles"})
    public void cancel_favourate(View view) {
        String item_id = getIntent().getStringExtra("item_id");
        node = HttpApi.find_by_id(item_id);

        Favourate favourate = HttpApi.find_favourate(node.id + "", FavouratesDBHelper.Kinds.NODE);
        HttpApi.cancel_favourate(favourate);

        add_favourate_btn.setVisibility(View.VISIBLE);
        cancel_favourate_btn.setVisibility(View.GONE);
    }
}
