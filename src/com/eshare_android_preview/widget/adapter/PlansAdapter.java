package com.eshare_android_preview.widget.adapter;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.adapter.EshareBaseAdapter;
import com.eshare_android_preview.model.Plan;

public class PlansAdapter extends EshareBaseAdapter<Plan> {
    public PlansAdapter(EshareBaseActivity activity) {
        super(activity);
    }

    @Override
    public View inflate_view() {
        return inflate(R.layout.p_plan_list_item, null);
    }

    @Override
    public BaseViewHolder build_view_holder(View view) {
        ViewHolder view_holder = new ViewHolder();

        view_holder.item_tv = (TextView) view.findViewById(R.id.item_tv);
//        view_holder.item_tv.getPaint().setFakeBoldText(true); // 设置粗体

        Typeface font = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/fontawesome-webfont.ttf");
        view_holder.icon_tv = (TextView) view.findViewById(R.id.icon_tv);
        view_holder.icon_tv.setTypeface(font);

        return view_holder;
    }

    @Override
    public void fill_with_data(BaseViewHolder holder, Plan item, int position) {
        ViewHolder view_holder = (ViewHolder) holder;
        view_holder.item_tv.setText(item.content);
    }

    private class ViewHolder implements BaseViewHolder {
        TextView item_tv;
        TextView icon_tv;
    }
}
