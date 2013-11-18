package com.eshare_android_preview.activity.base.groups;

import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.model.KnowledgeNetCategory;
import com.eshare_android_preview.widget.adapter.KnoweledgeNetCategoriesAdapter;

/**
 * Created by fushang318 on 13-10-28.
 */
public class GroupListActivity extends EshareBaseActivity {
    public void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.g_group_list);
        load_list();
        hide_head_setting_button();
        set_head_text(R.string.group_list_title);
        hide_head_bottom_line();

        super.onCreate(savedInstanceState);
    }

    private void load_list() {
        GridView grid_view = (GridView) findViewById(R.id.grid_view);
        List<KnowledgeNetCategory> node_list = KnowledgeNetCategory.all();
        KnoweledgeNetCategoriesAdapter adapter = new KnoweledgeNetCategoriesAdapter(this);
        adapter.add_items(node_list);
        grid_view.setAdapter(adapter);
        grid_view.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter.notifyDataSetChanged();

        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list_view, View list_item, int item_id, long position) {
                String group_name = ((KnowledgeNetCategory) list_item.getTag(R.id.adapter_item_tag)).name;
                Intent intent = new Intent(GroupListActivity.this, GroupShowUserListActivity.class);
                intent.putExtra(GroupShowUserListActivity.ExtraKeys.GROUP, group_name);
                startActivity(intent);
            }
        });

    }
}