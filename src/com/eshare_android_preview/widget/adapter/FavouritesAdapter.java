package com.eshare_android_preview.widget.adapter;

import android.view.View;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.adapter.EshareBaseAdapter;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Favourite;
import com.eshare_android_preview.model.KnowledgeNetNode;
import com.eshare_android_preview.model.Plan;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.database.FavouriteDBHelper;

public class FavouritesAdapter extends EshareBaseAdapter<Favourite> {

    public FavouritesAdapter(EshareBaseActivity activity) {
        super(activity);
    }

    @Override
    public View inflate_view() {
        return inflate(R.layout.f_favourite_list_item, null);
    }

    @Override
    public BaseViewHolder build_view_holder(View view) {
        ViewHolder view_holder = new ViewHolder();

        view_holder.item_tv = (TextView) view.findViewById(R.id.item_tv);

        return view_holder;
    }

    @Override
    public void fill_with_data(BaseViewHolder holder, Favourite item, int position) {
        ViewHolder view_holder = (ViewHolder) holder;

        if (item.kind.equals(FavouriteDBHelper.Kinds.QUESTION)) {
            Question question = HttpApi.question_find_by(Integer.parseInt(item.favourite_id));
            view_holder.item_tv.setText(question.title);

        } else if (item.kind.equals(FavouriteDBHelper.Kinds.PLAN)) {
            Plan plan = HttpApi.plan_find_by(Integer.parseInt(item.favourite_id));
            view_holder.item_tv.setText(plan.content);

        } else if (item.kind.equals(FavouriteDBHelper.Kinds.NODE)) {
            KnowledgeNetNode node = HttpApi.find_by_id(item.favourite_id);
            view_holder.item_tv.setText(node.name);
        }

    }

    private class ViewHolder implements BaseViewHolder {
        TextView item_tv;
    }
}
