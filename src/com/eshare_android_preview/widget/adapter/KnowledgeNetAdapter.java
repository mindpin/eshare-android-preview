package com.eshare_android_preview.widget.adapter;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.adapter.EshareBaseAdapter;
import com.eshare_android_preview.model.Node;

public class KnowledgeNetAdapter extends EshareBaseAdapter<Node> {

    public KnowledgeNetAdapter(EshareBaseActivity activity) {
        super(activity);
    }

    @Override
    public View inflate_view() {
        return inflate(R.layout.knowledge_net_list_item, null);
    }

    @Override
    public BaseViewHolder build_view_holder(View view) {
        ViewHolder view_holder = new ViewHolder();
        view_holder.view = view;
        view_holder.item_tv = (TextView) view.findViewById(R.id.item_tv);
        view_holder.item_icon = (TextView) view.findViewById(R.id.item_icon);
        return view_holder;
    }

    @Override
    public void fill_with_data(BaseViewHolder holder, Node item, int position) {
        ViewHolder view_holder = (ViewHolder) holder;
        view_holder.item_tv.setTag(item);
        view_holder.item_tv.setText(item.name);

        Typeface font = Typeface.createFromAsset(
                view_holder.view.getContext().getAssets(), "fonts/fontawesome-webfont.ttf");
        view_holder.item_icon.setTypeface(font);

    }

    private class ViewHolder implements BaseViewHolder {
        View view;
        TextView item_tv;
        TextView item_icon;
    }
}
