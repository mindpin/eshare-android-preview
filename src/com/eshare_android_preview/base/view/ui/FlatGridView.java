package com.eshare_android_preview.base.view.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.eshare_android_preview.base.utils.BaseUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 13-12-25.
 */
public class FlatGridView extends LinearLayout {
    public int x_count;
    public int y_count;

    List<LinearLayout> lines;
    public List<View> children;

    final private static BaseUtils.ScreenSize SCREEN_SIZE = BaseUtils.get_screen_size();

    public FlatGridView(Context context) {
        super(context);
    }

    public FlatGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void set_grid(int xc, int yc) {
        removeAllViews();

        x_count = xc;
        y_count = yc;

        setOrientation(VERTICAL);
        lines = new ArrayList<LinearLayout>();
        children = new ArrayList<View>();

        for (int i = 0; i < yc; i++) {
            LinearLayout ll = new LinearLayout(getContext());
            ll.setOrientation(HORIZONTAL);

            addView(ll);
            lines.add(ll);

            LayoutParams lp = (LayoutParams) ll.getLayoutParams();
            lp.width = LayoutParams.FILL_PARENT;

            if (xc == 1) {
                lp.height = SCREEN_SIZE.width_px / 7;
            } else if (xc == 2 && yc == 2) {
                lp.height = SCREEN_SIZE.width_px / 3;
            } else {
                lp.height = SCREEN_SIZE.width_px / 4;
            }

            if (i != yc - 1) {
                lp.bottomMargin = BaseUtils.dp_to_px(4);
            }

            ll.setLayoutParams(lp);
        }
    }

    public void add_view(View view) {
        int max_count = x_count * y_count;
        int count = children.size();

        if (count == max_count) return;

        children.add(view);

        int line = count / x_count;

        lines.get(line).addView(view);
        LayoutParams lp = (LayoutParams) view.getLayoutParams();
        lp.height = LayoutParams.FILL_PARENT;
        lp.width = 0;
        lp.weight = 1;
        view.setLayoutParams(lp);
    }

    public void set_warp_height() {
        for (LinearLayout ll : lines) {
            LayoutParams lp = (LayoutParams) ll.getLayoutParams();
            lp.height = LayoutParams.WRAP_CONTENT;
            ll.setLayoutParams(lp);
        }
    }
}
