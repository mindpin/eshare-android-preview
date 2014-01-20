package com.eshare_android_preview.view.ui.navigation;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eshare_android_preview.view.BorderRadiusRelativeLayout;

/**
 * Created by Administrator on 14-1-20.
 */
public class ConceptButtonView extends BorderRadiusRelativeLayout {
    public ConceptButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        TextView tv = new TextView(getContext());
        tv.setText("概念手册");
        tv.setTextColor(Color.WHITE);
        tv.setShadowLayer(1, 1, 1, Color.parseColor("#66000000"));
        tv.setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        tv.setLayoutParams(lp);
        addView(tv);
    }
}
