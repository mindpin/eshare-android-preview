package com.eshare_android_preview.view.ui.auth;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.eshare_android_preview.R;
import com.eshare_android_preview.utils.BaseUtils;
import com.eshare_android_preview.view.ui.FontAwesomeTextView;
import com.eshare_android_preview.view.ui.UiColor;

/**
 * Created by Administrator on 14-1-23.
 */
public class LoginButton extends RelativeLayout {
    Button button;
    FontAwesomeTextView icon;

    public LoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        _init_button();
        _init_icon();
    }

    private void _init_button() {
        button = new Button(getContext());

        button.setGravity(Gravity.CENTER);
        button.setTextSize(18);
        button.setText("继　续");
        button.setTextColor(Color.WHITE);

        final SemiCircleDrawable scb1 = new SemiCircleDrawable(this, UiColor.LOGIN_BUTTON);
        final SemiCircleDrawable scb2 = new SemiCircleDrawable(this, UiColor.LOGIN_BUTTON_PRESSED);

        button.setBackgroundDrawable(scb1);

//        button.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                    button.setBackgroundDrawable(scb2);
//                    return true;
//                }
//
//                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                    button.setBackgroundDrawable(scb1);
//                    return true;
//                }
//
//                return false;
//            }
//        });

        LayoutParams lp = new LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT
        );
        button.setLayoutParams(lp);

        addView(button);
    }

    private void _init_icon() {
        icon = new FontAwesomeTextView(getContext());
        icon.setText(R.string.icon_arrow_right);
        icon.setTextSize(18);
        icon.setTextColor(Color.parseColor("#ffffff"));

        LayoutParams lp = new LayoutParams(
                BaseUtils.dp_to_px(40),
                BaseUtils.dp_to_px(40)
        );
        lp.addRule(ALIGN_PARENT_RIGHT);
        icon.setLayoutParams(lp);

        addView(icon);
    }

    public void setOnClickListener(OnClickListener l) {
        button.setOnClickListener(l);
    }

}
