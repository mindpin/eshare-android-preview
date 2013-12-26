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
public class CorrectPointView extends LinearLayout {
    public CorrectPointView(Context context) {
        super(context);
        add_child_view(context);
    }

    public CorrectPointView(Context context, AttributeSet attrs) {
        super(context, attrs);
        add_child_view(context);
    }

    List<CircleIconView> icon_list;
    int point;

    String COLOR_EMPTY = "#dbdbdb";
    String COLOR_FILL  = "#98cc27";

    private void add_child_view(Context context) {
        icon_list = new ArrayList<CircleIconView>();
        point = 0;

        int size0, size1;
        if (isInEditMode()) {
            size0 = size1 = 20;
        } else {
            size0 = BaseUtils.dp_to_px(20 + 3);
            size1 = BaseUtils.dp_to_px(20);
        }

        for (int i = 0; i < 10; i++) {
            CircleIconView civ = new CircleIconView(context);
            civ.init("#00000000", COLOR_EMPTY, 16, R.string.icon_leaf);
            LayoutParams lp = new LayoutParams(size0, size1);
            civ.setLayoutParams(lp);

            addView(civ);
            icon_list.add(civ);
        }
    }

    public void add_point() {
        icon_list.get(point).set_text_color(COLOR_FILL);
        point = point + 1;
    }

    public void set_point(int point) {
        this.point = point;

        for (int i = 0; i < 10; i++) {
            CircleIconView cv = icon_list.get(i);
            if (i < point) {
                cv.set_text_color(COLOR_FILL);
            } else {
                cv.set_text_color(COLOR_EMPTY);
            }
        }
    }
}
