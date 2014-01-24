package com.eshare_android_preview.view.ui.auth;

import android.content.Context;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;

import com.eshare_android_preview.R;
import com.eshare_android_preview.utils.BaseUtils;

/**
 * Created by Administrator on 14-1-23.
 */
public class PasswordInputerView extends AuthFormInputerView {
    public PasswordInputerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        edit_text.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edit_text.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edit_text.setHint("密码");
        edit_text.setImeOptions(EditorInfo.IME_ACTION_DONE);

        icon.setText(R.string.icon_key);
    }

    public String get_password() {
        return edit_text.getText().toString();
    }

    public boolean is_blank() {
        return BaseUtils.is_str_blank(get_password());
    }
}
