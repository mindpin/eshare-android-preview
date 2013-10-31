package com.eshare_android_preview.activity.base.knowledge_net;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.KnowledgeNetNode;
import com.eshare_android_preview.widget.adapter.KnowledgeNetNodesAdapter;

import java.util.List;

public class KnowledgeNetNodeListActivity extends EshareBaseActivity {
    GridView grid_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.kn_knowledge_net_node_list);
        hide_head_setting_button();

        set_head_text("知识领域");
        hide_head_bottom_line();

        _set_icon_font();
        super.onCreate(savedInstanceState);
    }

    private void _set_icon_font() {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");
        TextView tv = (TextView) findViewById(R.id.javascript_icon);
        tv.setTypeface(font);
    }

    private void load_list_view() {
        grid_view = (GridView) findViewById(R.id.grid_view);
        List<KnowledgeNetNode> node_list = HttpApi.get_nodes("javascript");
        KnowledgeNetNodesAdapter adapter = new KnowledgeNetNodesAdapter(this);
        adapter.add_items(node_list);
        grid_view.setAdapter(adapter);
        grid_view.setSelector(new ColorDrawable(Color.TRANSPARENT));

        adapter.notifyDataSetChanged();

        grid_view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list_view, View list_item, int item_id, long position) {
                KnowledgeNetNode node = (KnowledgeNetNode) list_item.getTag(R.id.adapter_item_tag);

                Intent intent = new Intent(KnowledgeNetNodeListActivity.this, KnowledgeNetNodeShowActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable(KnowledgeNetNodeShowActivity.ExtraKeys.NODE, node);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        load_list_view();
        super.onResume();
    }
}
