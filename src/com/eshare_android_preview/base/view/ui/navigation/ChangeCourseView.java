package com.eshare_android_preview.base.view.ui.navigation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eshare_android_preview.activity.base.ChangeNetActivity;
import com.eshare_android_preview.base.view.BorderRadiusRelativeLayout;

/**
 * Created by Administrator on 14-1-13.
 */
public class ChangeCourseView extends BorderRadiusRelativeLayout {
    public ChangeCourseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        TextView tv = new TextView(getContext());
        tv.setText("更改课程");
        tv.setTextColor(Color.WHITE);
        tv.setShadowLayer(1, 1, 1, Color.parseColor("#66000000"));
        tv.setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        tv.setLayoutParams(lp);
        addView(tv);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChangeNetActivity.class);
                getContext().startActivity(intent);
            }
        });
    }
}
