package com.eshare_android_preview.widget.adapter;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.adapter.EshareBaseAdapter;
import com.eshare_android_preview.base.utils.ImageTools;
import com.eshare_android_preview.logic.HttpApi;

public class KnoweledgeNetCategoriesAdapter extends EshareBaseAdapter<HttpApi.KnowledgeCategory> {

    public KnoweledgeNetCategoriesAdapter(EshareBaseActivity activity) {
        super(activity);
    }

    @Override
    public View inflate_view() {
        return inflate(R.layout.kn_knowledge_net_category_list_item, null);
    }

    @Override
    public BaseViewHolder build_view_holder(View view) {
        ViewHolder view_holder = new ViewHolder();
        view_holder.view = view;
        view_holder.item_tv = (TextView) view.findViewById(R.id.item_tv);
        view_holder.item_iv = (ImageView) view.findViewById(R.id.item_iv);
        return view_holder;
    }

    @Override
    public void fill_with_data(BaseViewHolder holder, HttpApi.KnowledgeCategory item, int position) {
        ViewHolder view_holder = (ViewHolder) holder;
        view_holder.item_tv.setText(item.name);

        BitmapDrawable drawable = ImageTools.toRoundCorner(
                (BitmapDrawable) view_holder.view.getResources().getDrawable(item.res_id), 100);
        view_holder.item_iv.setBackgroundDrawable(drawable);
    }

    private class ViewHolder implements BaseViewHolder {
        View view;
        TextView item_tv;
        ImageView item_iv;
    }
}
