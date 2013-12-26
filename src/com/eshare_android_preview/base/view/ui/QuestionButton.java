package com.eshare_android_preview.base.view.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.eshare_android_preview.R;

/**
 * Created by Administrator on 13-12-24.
 */
public class QuestionButton extends RelativeLayout {
    Button submit_button;
    Button next_button;

    public QuestionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        submit_button = new Button(getContext());
        submit_button.setBackgroundResource(R.drawable.btn_cdbdbdb_circle_flat);
        submit_button.setTextSize(18);
        submit_button.setTextColor(Color.parseColor("#ffffff"));
        submit_button.setGravity(Gravity.CENTER);
        submit_button.setText("确定");
        addView(submit_button);

        LayoutParams lp = (LayoutParams) submit_button.getLayoutParams();
        lp.width = LayoutParams.FILL_PARENT;
        lp.height = LayoutParams.FILL_PARENT;
        submit_button.setLayoutParams(lp);

        // ---------------

        next_button = new Button(getContext());
        next_button.setBackgroundResource(R.drawable.btn_c98cc27_circle_flat);
        next_button.setTextSize(18);
        next_button.setTextColor(Color.parseColor("#ffffff"));
        submit_button.setGravity(Gravity.CENTER);
        next_button.setText("继续");
        addView(next_button);

        LayoutParams lp1 = (LayoutParams) next_button.getLayoutParams();
        lp1.width = LayoutParams.FILL_PARENT;
        lp1.height = LayoutParams.FILL_PARENT;
        next_button.setLayoutParams(lp1);

        next_button.setVisibility(GONE);
        next_button.setShadowLayer(1, 1, 1, Color.parseColor("#22000000"));
    }

    public void set_submit_button_listener(OnClickListener l) {
        submit_button.setOnClickListener(l);
    }

    public void set_next_button_listener(OnClickListener l) {
        next_button.setOnClickListener(l);
    }

    public void enable_submit() {
        submit_button.setClickable(true);
        submit_button.setBackgroundResource(R.drawable.btn_c98cc27_circle_flat);
        submit_button.setShadowLayer(1, 1, 1, Color.parseColor("#22000000"));
    }

    public void disable_submit() {
        submit_button.setClickable(false);
        submit_button.setBackgroundResource(R.drawable.btn_cdbdbdb_circle_flat);
        submit_button.setShadowLayer(0, 0, 0, Color.parseColor("#00000000"));
    }

    public void reset() {
        submit_button.setVisibility(VISIBLE);
        next_button.setVisibility(GONE);
    }

    public void show_next_button() {
        submit_button.setVisibility(GONE);
        next_button.setVisibility(VISIBLE);
    }
}
