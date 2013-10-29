package com.eshare_android_preview.widget.adapter;

import android.view.View;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.adapter.EshareBaseAdapter;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Favourate;
import com.eshare_android_preview.model.Plan;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.database.FavouratesDBHelper;

public class FavouratesAdapter extends EshareBaseAdapter<Favourate> {

    public FavouratesAdapter(EshareBaseActivity activity) {
        super(activity);
    }

    @Override
    public View inflate_view() {
        return inflate(R.layout.knowledge_net_category_list_item, null);
    }

    @Override
    public BaseViewHolder build_view_holder(View view) {
        ViewHolder view_holder = new ViewHolder();

        view_holder.info_tv = (TextView) view.findViewById(R.id.info_tv);
        view_holder.item_tv = (TextView)view.findViewById(R.id.item_tv);

        return view_holder;
    }

    @Override
    public void fill_with_data(BaseViewHolder holder, Favourate item, int position) {
        ViewHolder view_holder = (ViewHolder) holder;

        view_holder.info_tv.setTag(R.id.tag_favourate, item);

        if (item.kind.equals(FavouratesDBHelper.Kinds.QUESTION)) {
            Question question = HttpApi.question_find_by(item.favourate_id);
            view_holder.item_tv.setText(question.title);

        } else if (item.kind.equals(FavouratesDBHelper.Kinds.PLAN)) {
            Plan plan = HttpApi.plan_find_by(item.favourate_id);
            view_holder.item_tv.setText(plan.content);
        }

    }
    private class ViewHolder implements BaseViewHolder {
        TextView info_tv;
        TextView  item_tv;
    }
}
