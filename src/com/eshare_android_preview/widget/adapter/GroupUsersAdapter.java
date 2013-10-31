package com.eshare_android_preview.widget.adapter;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.adapter.EshareBaseAdapter;
import com.eshare_android_preview.base.utils.ImageTools;
import com.eshare_android_preview.model.User;

/**
 * Created by kaid on 10/22/13.
 */
public class GroupUsersAdapter extends EshareBaseAdapter<User> {
    public GroupUsersAdapter(EshareBaseActivity activity) {
        super(activity);
    }

    @Override
    public View inflate_view() {
        return inflate(R.layout.g_group_user_list_item, null);
    }

    @Override
    public BaseViewHolder build_view_holder(View view) {
        ViewHolder view_holder = new ViewHolder();
        view_holder.user_name = (TextView) view.findViewById(R.id.user_name);
        view_holder.user_avatar = (ImageView) view.findViewById(R.id.user_avatar);
        return view_holder;
    }

    @Override
    public void fill_with_data(BaseViewHolder holder, User user, int position) {
        ViewHolder view_holder = (ViewHolder) holder;
        view_holder.user_name.setText(user.username);

        // 设置头像
        BitmapDrawable drawable = ImageTools.toRoundCorner((BitmapDrawable) user.getAvatarDrawable(), 100);
        view_holder.user_avatar.setImageDrawable(drawable);
    }

    private class ViewHolder implements BaseViewHolder {
        TextView user_name;
        ImageView user_avatar;
    }
}
