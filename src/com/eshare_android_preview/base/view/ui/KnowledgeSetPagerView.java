package com.eshare_android_preview.base.view.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.view.BorderRadiusRelativeLayout;
import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeNode;

/**
 * Created by Administrator on 14-1-2.
 */
public class KnowledgeSetPagerView extends RelativeLayout {
    IUserKnowledgeNode node;
    View view;

    public KnowledgeSetPagerView(Context context, IUserKnowledgeNode node) {
        super(context);

        this.node = node;
        init();
    }

    private void init() {
        LayoutInflater lf = (LayoutInflater) getContext().getSystemService("layout_inflater");
        view = lf.inflate(R.layout.kn_knowledge_set_show_item, null);
        refresh_data();
        addView(view);
    }

    public void refresh_data() {
        set_name();
        set_desc();
        set_required_desc();
        set_learned_icon();
        set_locked_icon();

        set_clickable();
    }

    private void set_name() {
        ((TextView) view.findViewById(R.id.node_name)).setText(node.get_name());
    }

    private void set_desc() {
        ((TextView) view.findViewById(R.id.node_desc)).setText(node.get_desc());
    }

    private void set_required_desc() {
        if (node.get_required()) {
            ((TextView) view.findViewById(R.id.required)).setText("必学");
        } else {
            ((TextView) view.findViewById(R.id.required)).setText("选学");
        }
    }

    private void set_learned_icon() {
        if (node.is_learned()) {
            view.findViewById(R.id.learned_icon).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.learned_icon).setVisibility(View.GONE);
        }
    }

    private void set_locked_icon() {
        if (node.is_unlocked()) {
            view.findViewById(R.id.lock_icon).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.lock_icon).setVisibility(View.VISIBLE);
        }
    }

    private void set_clickable() {
        BorderRadiusRelativeLayout bl = (BorderRadiusRelativeLayout) view.findViewById(R.id.start_btn);
        TextView tv = (TextView) view.findViewById(R.id.start_btn_text);

        if (node.is_unlocked()) {
            tv.setText("开始练习");
            bl.setBackgroundColor(UiColor.VIEW_PAGER_BUTTON_UNLOCKED);
        } else {
            tv.setText("尚未解锁");
            bl.setBackgroundColor(UiColor.VIEW_PAGER_BUTTON_LOCKED);
        }
    }

    public void set_listener(OnClickListener l) {
        view.findViewById(R.id.start_btn).setOnClickListener(l);
    }
}
