package com.eshare_android_preview.widget.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.adapter.EshareBaseAdapter;
import com.eshare_android_preview.model.KnowledgeNetNode;

public class KnowledgeNetNodesAdapter extends EshareBaseAdapter<KnowledgeNetNode> {

    public KnowledgeNetNodesAdapter(EshareBaseActivity activity) {
        super(activity);
    }

    @Override
    public View inflate_view() {
        return inflate(R.layout.knowledge_net_node_list_item, null);
    }

    @Override
    public BaseViewHolder build_view_holder(View view) {
        ViewHolder view_holder = new ViewHolder();

        view_holder.item_tv = (TextView) view.findViewById(R.id.item_tv);
        view_holder.item_icon = (TextView) view.findViewById(R.id.item_icon);
        view_holder.note_icon = (TextView) view.findViewById(R.id.note_icon);
        view_holder.fav_icon = (TextView) view.findViewById(R.id.fav_icon);

        view_holder.fontawesome_font = Typeface.createFromAsset(
                view.getContext().getAssets(), "fonts/fontawesome-webfont.ttf");
        _set_fontawesome_fonts(view_holder);

        return view_holder;
    }

    private void _set_fontawesome_fonts(ViewHolder view_holder) {
        view_holder.item_icon.setTypeface(view_holder.fontawesome_font);
        view_holder.note_icon.setTypeface(view_holder.fontawesome_font);
        view_holder.fav_icon.setTypeface(view_holder.fontawesome_font);

        view_holder.note_icon.setText(R.string.icon_pencil_square);
        view_holder.fav_icon.setText(R.string.icon_star);
    }

    @Override
    public void fill_with_data(BaseViewHolder holder, KnowledgeNetNode item, int position) {
        ViewHolder view_holder = (ViewHolder) holder;
        view_holder.item_tv.setText(item.name);

        if (item.has_note()) {
            view_holder.note_icon.setTextColor(Color.parseColor("#888888"));
        } else {
            view_holder.note_icon.setTextColor(Color.parseColor("#e7e7e7"));
        }

        if (item.is_faved()) {
            view_holder.fav_icon.setTextColor(Color.parseColor("#FEE79B"));
        } else {
            view_holder.fav_icon.setTextColor(Color.parseColor("#e7e7e7"));
        }
    }

    private class ViewHolder implements BaseViewHolder {
        TextView item_tv;
        TextView item_icon;
        TextView note_icon;
        TextView fav_icon;

        Typeface fontawesome_font;
    }
}
