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

    List<FontAwesomeTextView> icon_list;
    int hp;

    private void add_child_view(Context context) {
        icon_list = new ArrayList<FontAwesomeTextView>();
        hp = 3;

        int size;
        if (isInEditMode()) {
            size = 30;
        } else {
            size = BaseUtils.dp_to_px(30 + 3);
        }

        for (int i = 0; i < hp; i++) {
            FontAwesomeTextView ftv = new FontAwesomeTextView(context);
            ftv.setText(R.string.icon_star);
            ftv.setTextColor(UiColor.HEALTH);
            ftv.setTextSize(20);
            LayoutParams lp = new LayoutParams(size, size);
            ftv.setLayoutParams(lp);

            addView(ftv);
            icon_list.add(ftv);
        }
    }

    public void break_heart() {
        icon_list.get(hp - 1).setTextColor(UiColor.HEALTH_EMPTY);
        hp = hp - 1;
    }

    public void set_hp(int hp) {
        this.hp = hp;

        for (int i = 0; i < 3; i++) {
            FontAwesomeTextView cv = icon_list.get(i);
            if (i < hp) {
                cv.setTextColor(UiColor.HEALTH);
            } else {
                cv.setTextColor(UiColor.HEALTH_EMPTY);
            }
        }
    }
}
