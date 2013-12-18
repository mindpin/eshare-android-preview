package com.eshare_android_preview.base.view.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.utils.BaseUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 13-12-18.
 */
public class HealthView extends LinearLayout {
    public HealthView(Context context) {
        super(context);
        add_child_view(context);
    }

    public HealthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        add_child_view(context);
    }

    List<CircleIconView> icon_list;
    int hp;

    private void add_child_view(Context context) {
        icon_list = new ArrayList<CircleIconView>();
        hp = 3;

        int size;
        if (isInEditMode()) {
            size = 30;
        } else {
            size = BaseUtils.dp_to_px(30 + 3);
        }

        for (int i = 0; i < hp; i++) {
            CircleIconView civ = new CircleIconView(context);
            civ.init("#00000015", "#D62525", 20, R.string.icon_heart);
            LayoutParams lp = new LayoutParams(size, size);
            civ.setLayoutParams(lp);

            addView(civ);
            icon_list.add(civ);
        }
    }

    public void break_heart() {
        icon_list.get(hp - 1).set_text_color("#aaaaaa");
        hp = hp - 1;
    }
}
