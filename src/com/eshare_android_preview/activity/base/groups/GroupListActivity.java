package com.eshare_android_preview.activity.base.groups;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.widget.adapter.GroupListAdapter;

import java.util.List;

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
        ListView group_list_view = (ListView) findViewById(R.id.list_view);
        List<HttpApi.KnowledgeCategory> node_list = HttpApi.get_knowledge_net_category();
        GroupListAdapter adapter = new GroupListAdapter(this);
        adapter.add_items(node_list);
        group_list_view.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        group_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list_view, View list_item, int item_id, long position) {
                String group_name = ((HttpApi.KnowledgeCategory) list_item.getTag(R.id.adapter_item_tag)).name;
                Intent intent = new Intent(GroupListActivity.this, GroupActivity.class);
                intent.putExtra("group_name", group_name);
                startActivity(intent);
            }
        });

    }
}