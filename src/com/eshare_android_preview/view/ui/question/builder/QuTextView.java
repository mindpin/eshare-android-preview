package com.eshare_android_preview.view.ui.question.builder;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eshare_android_preview.http.i.question.IQuestion;
import com.eshare_android_preview.utils.BaseUtils;
import com.eshare_android_preview.view.ui.question.QuestionFill;
import com.eshare_android_preview.view.ui.question.QuestionFillSpan;
import com.eshare_android_preview.http.model.Question;

/**
 * Created by Administrator on 14-1-15.
 */
public class QuTextView extends TextView implements IQuItemView {
    private SpannableStringBuilder string_builder;

    public QuTextView(Context context, IQuestion.ContentToken token) {
        super(context);

        String content = (String) token.data.get("content");
        string_builder = QuestionFill.parse(content);
        setText(string_builder);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        lp.bottomMargin = BaseUtils.dp_to_px(10);
        setLayoutParams(lp);
        setTextSize(18);
        setTypeface(Typeface.MONOSPACE);
    }

    @Override
    public QuestionFillSpan[] spans() {
        return string_builder.getSpans(0, string_builder.length(), QuestionFillSpan.class);
    }
}
