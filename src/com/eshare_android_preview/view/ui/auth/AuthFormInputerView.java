package com.eshare_android_preview.view.ui.auth;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.eshare_android_preview.R;
import com.eshare_android_preview.utils.BaseUtils;
import com.eshare_android_preview.view.ui.FontAwesomeTextView;

/**
 * Created by Administrator on 14-1-24.
 */
abstract public class AuthFormInputerView extends RelativeLayout {
    EditText edit_text;
    FontAwesomeTextView icon;

    public AuthFormInputerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        _init();
    }

    private void _init() {
        _init_edit_text();
        _init_icon();
    }

    private void _init_edit_text() {
        final SemiCircleDrawable scd1 = new SemiCircleDrawable(this, Color.parseColor("#ffffff"));
        final SemiCircleDrawable scd2 = new SemiCircleDrawable(this, Color.parseColor("#ffffdc"));

        edit_text = new EditText(getContext());
        edit_text.setBackgroundDrawable(scd1);
        edit_text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        edit_text.setTextSize(18);
        edit_text.setPadding(BaseUtils.dp_to_px(40), 0, 0, 0);

        LayoutParams lp = new LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT
        );
        edit_text.setLayoutParams(lp);

        edit_text.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean has_focus) {
                if (has_focus) {
                    edit_text.setBackgroundDrawable(scd2);
                } else {
                    edit_text.setBackgroundDrawable(scd1);
                }
            }
        });

        addView(edit_text);
    }

    private void _init_icon() {
        icon = new FontAwesomeTextView(getContext());
        icon.setText(R.string.icon_user);
        icon.setTextSize(18);
        icon.setTextColor(Color.parseColor("#99000000"));

        LayoutParams lp = new LayoutParams(
                BaseUtils.dp_to_px(40),
                BaseUtils.dp_to_px(40)
        );
        icon.setLayoutParams(lp);

        addView(icon);
    }
}
