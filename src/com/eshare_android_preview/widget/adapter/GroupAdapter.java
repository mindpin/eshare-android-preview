package com.eshare_android_preview.widget.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.adapter.EshareBaseAdapter;
import com.eshare_android_preview.model.User;

/**
 * Created by kaid on 10/22/13.
 */
public class GroupAdapter extends EshareBaseAdapter<User> {
    public GroupAdapter(EshareBaseActivity activity) {
        super(activity);
    }

    @Override
    public View inflate_view() {
        return inflate(R.layout.g_group_user, null);
    }

    @Override
    public BaseViewHolder build_view_holder(View view) {
        ViewHolder view_holder = new ViewHolder();
        view_holder.user_name = (TextView)view.findViewById(R.id.user_name);
        view_holder.user_avatar = (ImageView)view.findViewById(R.id.user_avatar);
        return view_holder;
    }

    @Override
    public void fill_with_data(BaseViewHolder holder, User user, int position) {
        ViewHolder view_holder = (ViewHolder) holder;
        view_holder.user_name.setTag(R.id.tag_user_uuid, user);
        view_holder.user_name.setText(user.username);
	    view_holder.user_avatar.setImageDrawable(user.getAvatarDrawable());
    }

    private class ViewHolder implements BaseViewHolder {
        TextView user_name;
        ImageView user_avatar;
    }
}
