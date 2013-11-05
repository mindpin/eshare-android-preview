package com.eshare_android_preview.activity.base.groups;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.ImageTools;
import com.eshare_android_preview.model.User;

/**
 * Created by kaid on 10/22/13.
 */
public class UserShowActivity extends EshareBaseActivity {
    public static class ExtraKeys{
        public static final String USER = "user";
    }
    User user;
    ImageView user_avatar;
    TextView user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.g_user_show);
        hide_head_setting_button();
        set_head_text(R.string.group_user_info_title);
        user = (User)getIntent().getExtras().getSerializable(ExtraKeys.USER);
        init_ui();
        load_user();
        hide_head_bottom_line();
        super.onCreate(savedInstanceState);
    }

    private void init_ui() {
        user_avatar = (ImageView)findViewById(R.id.user_avatar);
        user_name   = (TextView)findViewById(R.id.user_name);

        set_fontawesome((TextView) findViewById(R.id.header_plan_icon));
        set_fontawesome((TextView) findViewById(R.id.header_note_icon));
        set_fontawesome((TextView) findViewById(R.id.header_fav_icon));
    }

    private void load_user() {
        user_name.setText(user.username);

        BitmapDrawable bd = (BitmapDrawable) user.getAvatarDrawable();
        BitmapDrawable rounded_bitmap = ImageTools.toRoundCorner(bd, 500);
        user_avatar.setBackgroundDrawable(rounded_bitmap);
    }
}
