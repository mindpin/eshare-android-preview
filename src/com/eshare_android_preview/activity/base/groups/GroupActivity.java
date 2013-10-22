package com.eshare_android_preview.activity.base.groups;

import android.os.Bundle;
import android.widget.ListView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.model.User;
import com.eshare_android_preview.widget.adapter.GroupAdapter;

import java.util.List;

/**
 * Created by kaid on 10/22/13.
 */
public class GroupActivity extends EshareBaseActivity {
    ListView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.g_group_list);
        load_list();
    }

    private void load_list() {
        list_view = (ListView)findViewById(R.id.list_view);
        GroupAdapter adapter = new GroupAdapter(this);
        adapter.add_items(User.all());
        list_view.setAdapter(adapter);
    }
}
