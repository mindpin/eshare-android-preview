package com.eshare_android_preview.activity.base.groups;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.model.User;

/**
 * Created by kaid on 10/22/13.
 */
public class UserInfoActivity extends EshareBaseActivity {
    User user;
    ImageView user_avatar;
    TextView user_name;
    TextView user_bio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.g_user_info);
        hide_head_setting_button();
        set_head_text(R.string.group_user_info_title);
        user = (User)getIntent().getExtras().getSerializable("user");
        init_ui();
        load_user();
        super.onCreate(savedInstanceState);
    }

    private void init_ui() {
        user_avatar = (ImageView)findViewById(R.id.user_avatar);
        user_name   = (TextView)findViewById(R.id.user_name);
        user_bio    = (TextView)findViewById(R.id.user_bio);
    }

    private void load_user() {
        user_avatar.setImageDrawable(user.getAvatarDrawable());
        user_name.setText(user.username);
        user_bio.setText(user.bio);
    }
}
