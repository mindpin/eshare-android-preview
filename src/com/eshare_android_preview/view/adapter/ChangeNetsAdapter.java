package com.eshare_android_preview.view.adapter;

import android.view.View;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.controller.activity.base.EshareBaseActivity;
import com.eshare_android_preview.http.i.knowledge.IUserSimpleKnowledgeNet;
import com.eshare_android_preview.view.adapter.base.EshareBaseAdapter;

/**
 * Created by fushang318 on 14-1-13.
 */
public class ChangeNetsAdapter extends EshareBaseAdapter<IUserSimpleKnowledgeNet> {

    public ChangeNetsAdapter(EshareBaseActivity activity) {
        super(activity);
    }

    @Override
    public View inflate_view() {
        return inflate(R.layout.change_net_list_item, null);
    }

    @Override
    public BaseViewHolder build_view_holder(View view) {
        ViewHolder view_holder = new ViewHolder();
        view_holder.net_name_tv = (TextView) view.findViewById(R.id.net_name);
        return view_holder;
    }

    @Override
    public void fill_with_data(BaseViewHolder holder, IUserSimpleKnowledgeNet item, int position) {
        ViewHolder view_holder = (ViewHolder) holder;
        view_holder.net_name_tv.setText(item.get_name());
    }

    private class ViewHolder implements BaseViewHolder {
        TextView net_name_tv;
    }
}
