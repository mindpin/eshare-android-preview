package com.eshare_android_preview.view.ui.auth;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;

import com.eshare_android_preview.R;
import com.eshare_android_preview.utils.BaseUtils;

/**
 * Created by Administrator on 14-1-24.
 */
public class EmailInputerView extends AuthFormInputerView {
    public EmailInputerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        edit_text.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        edit_text.setHint("邮箱");
        edit_text.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edit_text.requestFocus();

        icon.setText(R.string.icon_user);
    }

    public String get_email() {
        return edit_text.getText().toString();
    }

    public void set_email(String email) {
        edit_text.setText(email);
    }

    public boolean is_blank() {
        return BaseUtils.is_str_blank(get_email());
    }
}
