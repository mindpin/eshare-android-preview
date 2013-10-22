package com.eshare_android_preview.activity.base.groups;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.model.User;
import com.eshare_android_preview.widget.adapter.GroupAdapter;

/**
 * Created by kaid on 10/22/13.
 */
public class GroupActivity extends EshareBaseActivity {
    ListView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.g_group);
        load_list();
    }

    private void load_list() {
        list_view = (ListView)findViewById(R.id.list_view);
        GroupAdapter adapter = new GroupAdapter(this);
        adapter.add_items(User.all());
        list_view.setAdapter(adapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(GroupActivity.this, UserInfoActivity.class);
                User user = (User)view.findViewById(R.id.user_name).getTag(R.id.tag_user_uuid);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
