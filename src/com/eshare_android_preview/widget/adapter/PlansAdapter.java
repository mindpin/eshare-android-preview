package com.eshare_android_preview.widget.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.adapter.EshareBaseAdapter;
import com.eshare_android_preview.base.utils.ImageTools;
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
        view_holder.view = view;

        view_holder.item_tv = (TextView) view.findViewById(R.id.item_tv);

        Typeface font = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/fontawesome-webfont.ttf");
        view_holder.icon_tv = (TextView) view.findViewById(R.id.icon_tv);
        view_holder.icon_tv.setTypeface(font);

        BitmapDrawable drawable = ImageTools.toRoundCorner(
                (BitmapDrawable) view.getResources().getDrawable(R.drawable.lan_js), 100);
        ImageView lan_icon_iv = (ImageView) view.findViewById(R.id.lan_icon);
        lan_icon_iv.setBackgroundDrawable(drawable);

        return view_holder;
    }

    @Override
    public void fill_with_data(BaseViewHolder holder, Plan item, int position) {
        ViewHolder view_holder = (ViewHolder) holder;
        view_holder.item_tv.setText(item.content);

        if (item.is_in_user_plan(null)) {
            view_holder.icon_tv.setBackgroundDrawable(view_holder.view.getResources().getDrawable(R.drawable.btn_c50ce00_circle_flat));
//            view_holder.icon_tv.setTextColor(Color.parseColor("#EBFE9B"));
            view_holder.icon_tv.setText(R.string.icon_check);
        } else {
            view_holder.icon_tv.setBackgroundDrawable(view_holder.view.getResources().getDrawable(R.drawable.btn_topbar_circle_flat));
//            view_holder.icon_tv.setTextColor(Color.parseColor("#FFFFFF"));
            view_holder.icon_tv.setText(R.string.icon_chevron_right);
        }
    }

    private class ViewHolder implements BaseViewHolder {
        View view;
        TextView item_tv;
        TextView icon_tv;
    }
}
