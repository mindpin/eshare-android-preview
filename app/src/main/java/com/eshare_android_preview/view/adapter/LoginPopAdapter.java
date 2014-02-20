package com.eshare_android_preview.view.adapter;

import android.view.View;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.controller.activity.base.EshareBaseActivity;
import com.eshare_android_preview.view.adapter.base.EshareBaseAdapter;

public class LoginPopAdapter extends EshareBaseAdapter<String>{

	public LoginPopAdapter(EshareBaseActivity activity) {
		super(activity);
	}

	@Override
    public View inflate_view() {
        return inflate(R.layout.l_login_tag_list_item, null);
    }

    @Override
    public BaseViewHolder build_view_holder(View view) {
        ViewHolder view_holder = new ViewHolder();
        view_holder.login_tv = (TextView) view.findViewById(R.id.login_tv);
        return view_holder;
    }

    @Override
    public void fill_with_data(BaseViewHolder holder, String item, int position) {
        ViewHolder view_holder = (ViewHolder) holder;
        view_holder.login_tv.setText(item);
    }

    private class ViewHolder implements BaseViewHolder {
        TextView login_tv;
    }

}
