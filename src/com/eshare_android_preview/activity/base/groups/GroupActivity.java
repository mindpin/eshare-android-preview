package com.eshare_android_preview.activity.base.groups;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.model.User;
import com.eshare_android_preview.widget.adapter.GroupAdapter;

/**
 * Created by kaid on 10/22/13.
 */
public class GroupActivity extends EshareBaseActivity {
    GridView grid_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String group_name = getIntent().getStringExtra("group_name");
        setContentView(R.layout.g_group);
        load_list(group_name);
        hide_head_setting_button();
        set_head_text(R.string.group_title);
        hide_head_bottom_line();
        super.onCreate(savedInstanceState);
    }

    private void load_list(String group_name) {
        grid_view = (GridView)findViewById(R.id.grid_view);
        GroupAdapter adapter = new GroupAdapter(this);
        adapter.add_items(User.get_users_by_group(group_name));
        grid_view.setAdapter(adapter);
        grid_view.setSelector(new ColorDrawable(Color.TRANSPARENT));

        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(GroupActivity.this, UserInfoActivity.class);
                User user = (User) view.findViewById(R.id.user_name).getTag(R.id.tag_user_uuid);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
