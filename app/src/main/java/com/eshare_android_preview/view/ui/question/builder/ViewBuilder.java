package com.eshare_android_preview.view.ui.question.builder;

import android.content.Context;

import com.eshare_android_preview.http.i.question.IQuestion;

/**
 * Created by Administrator on 14-1-15.
 */
public class ViewBuilder {
    public static IQuItemView build(Context context, IQuestion.ContentToken token) {
        String type = token.type;

        if ("text".equals(type)) return new QuTextView(context, token);
        if ("code".equals(type)) return new QuCodeView(context, token);
        if ("image".equals(type)) return new QuImageView(context, token);

        return null;
    }
}
