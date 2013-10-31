package com.eshare_android_preview.widget.adapter;

import android.view.View;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.adapter.EshareBaseAdapter;
import com.eshare_android_preview.model.KnowledgeNetNode;

/**
 * Created by fushang318 on 13-10-28.
 */
public class GroupsAdapter extends EshareBaseAdapter<KnowledgeNetNode.KnowledgeCategory> {

    public GroupsAdapter(EshareBaseActivity activity) {
        super(activity);
    }

    @Override
    public View inflate_view() {
        return inflate(R.layout.g_group_list_item, null);
    }

    @Override
    public BaseViewHolder build_view_holder(View view) {
        ViewHolder view_holder = new ViewHolder();
        view_holder.item_tv = (TextView) view.findViewById(R.id.item_tv);
        return view_holder;
    }

    @Override
    public void fill_with_data(BaseViewHolder holder, KnowledgeNetNode.KnowledgeCategory item, int position) {
        ViewHolder view_holder = (ViewHolder) holder;
        view_holder.item_tv.setText(item.name);
    }

    private class ViewHolder implements BaseViewHolder {
        TextView item_tv;
    }
}
