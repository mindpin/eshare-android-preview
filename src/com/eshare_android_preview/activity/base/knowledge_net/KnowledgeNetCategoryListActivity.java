package com.eshare_android_preview.activity.base.knowledge_net;

import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.KnowledgeNetNode;
import com.eshare_android_preview.widget.adapter.KnoweledgeNetCategoriesAdapter;

public class KnowledgeNetCategoryListActivity extends EshareBaseActivity {
    EditText search_edit_tv;
    Drawable d_default, d_clear;
    GridView grid_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.kn_knowledge_net_category_list);
        hide_head_setting_button();
        set_head_text(R.string.knowedge_title);
        load_search_et();
        load_list_view();
        hide_head_bottom_line();
        super.onCreate(savedInstanceState);
    }
    
    private void load_search_et() {
        d_default = getResources().getDrawable(R.drawable.txt_search_default);
        d_clear = getResources().getDrawable(R.drawable.txt_search_clear);
    }

    private void load_list_view() {
        grid_view = (GridView) findViewById(R.id.grid_view);
        List<KnowledgeNetNode.KnowledgeCategory> node_list = HttpApi.HANode.get_knowledge_net_category();
        KnoweledgeNetCategoriesAdapter adapter = new KnoweledgeNetCategoriesAdapter(this);
        adapter.add_items(node_list);
        grid_view.setAdapter(adapter);
        grid_view.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter.notifyDataSetChanged();

        grid_view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list_view, View list_item, int item_id, long position) {
                Intent intent = new Intent(KnowledgeNetCategoryListActivity.this, KnowledgeNetNodeListActivity.class);
                startActivity(intent);
            }
        });
    }
}
