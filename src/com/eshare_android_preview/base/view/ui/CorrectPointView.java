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

    List<FontAwesomeTextView> icon_list;
    int point;

    private void add_child_view(Context context) {
        icon_list = new ArrayList<FontAwesomeTextView>();
        point = 0;

        int size0, size1;
        if (isInEditMode()) {
            size0 = size1 = 20;
        } else {
            size0 = BaseUtils.dp_to_px(20 + 3);
            size1 = BaseUtils.dp_to_px(20);
        }

        for (int i = 0; i < 10; i++) {
            FontAwesomeTextView ftv = new FontAwesomeTextView(context);
            ftv.setText(R.string.icon_leaf);
            ftv.setTextColor(UiColor.CORRECT_POINT_EMPTY);
            ftv.setTextSize(16);
            LayoutParams lp = new LayoutParams(size0, size1);
            ftv.setLayoutParams(lp);

            addView(ftv);
            icon_list.add(ftv);
        }
    }

    public void add_point() {
        icon_list.get(point).setTextColor(UiColor.CORRECT_POINT);
        point = point + 1;
    }

    public void set_point(int point) {
        this.point = point;

        for (int i = 0; i < 10; i++) {
            FontAwesomeTextView cv = icon_list.get(i);
            if (i < point) {
                cv.setTextColor(UiColor.CORRECT_POINT);
            } else {
                cv.setTextColor(UiColor.CORRECT_POINT_EMPTY);
            }
        }
    }
}
