package com.eshare_android_preview.view.adapter;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.controller.activity.base.EshareBaseActivity;
import com.eshare_android_preview.http.i.concept.IConcept;
import com.eshare_android_preview.view.adapter.base.EshareBaseAdapter;
import com.eshare_android_preview.view.ui.FontAwesomeTextView;
import com.eshare_android_preview.view.ui.UiColor;

/**
 * Created by Administrator on 14-1-24.
 */
public class ConceptsAdapter extends EshareBaseAdapter<IConcept> {

    public ConceptsAdapter(EshareBaseActivity activity) {
        super(activity);
    }

    @Override
    public View inflate_view() {
        View view = inflate(R.layout.concepts_list_item, null);
        return view;
    }

    @Override
    public BaseViewHolder build_view_holder(View view) {
        ViewHolder view_holder = new ViewHolder();
        view_holder.view = view;
        view_holder.concept_icon = (FontAwesomeTextView) view.findViewById(R.id.concept_icon);
        view_holder.concept_icon.setText(R.string.icon_bookmark);
        view_holder.concept_name = (TextView) view.findViewById(R.id.concept_name);
        view_holder.practices_count = (TextView) view.findViewById(R.id.practices_count);
        return view_holder;
    }

    @Override
    public void fill_with_data(BaseViewHolder holder, IConcept item, int position) {
        ViewHolder view_holder = (ViewHolder) holder;

        view_holder.concept_name.setText(item.get_name());

        view_holder.view.setBackgroundColor(Color.WHITE);

        if (item.is_unlocked()) {
            view_holder.concept_icon.setTextColor(UiColor.SET_COLOR);
            view_holder.concept_name.setTextColor(Color.parseColor("#000000"));
            view_holder.practices_count.setVisibility(View.VISIBLE);
            view_holder.practices_count.setText("练习次数：" + item.get_practices_count());
        } else {
            view_holder.concept_icon.setTextColor(Color.parseColor("#999999"));
            view_holder.concept_name.setTextColor(Color.parseColor("#999999"));
            view_holder.practices_count.setVisibility(View.GONE);
        }

        view_holder.view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    view.setBackgroundColor(Color.parseColor("#ffffdc"));
                    return true;
                }

                if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    view.setBackgroundColor(Color.WHITE);
                    return true;
                }

                return false;
            }
        });
    }

    private class ViewHolder implements BaseViewHolder {
        View view;
        FontAwesomeTextView concept_icon;
        TextView concept_name;
        TextView practices_count;
    }
}
