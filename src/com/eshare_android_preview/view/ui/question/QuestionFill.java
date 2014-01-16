package com.eshare_android_preview.view.ui.question;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.eshare_android_preview.view.ui.question.builder.IQuItemView;

/**
 * Created by Administrator on 14-1-15.
 */
public class QuestionFill {
    public int index;

    IQuItemView view;
    QuestionFillSpan span;
    String filled_text;

    public QuestionFill(IQuItemView view, QuestionFillSpan span) {
        this.view = view;
        this.span = span;
    }

    public boolean is_empty() {
        return null == filled_text;
    }

    public void fill_text(String text) {
        filled_text = text;
        replace_span_text(text);
    }

    public void clear_text() {
        filled_text = null;
        replace_span_text("__");
    }

    private void replace_span_text(String text) {
        TextView tv = (TextView) view;
        SpannableStringBuilder ssb = new SpannableStringBuilder(tv.getText());
        int start = ssb.getSpanStart(span);
        int end = ssb.getSpanEnd(span);
        ssb = ssb.replace(start, end, text);
        tv.setText(ssb);
    }

    public void set_linked_view(View view) {

    }

    static public SpannableStringBuilder parse(String text) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        String[] arr = text.split("\\[%]", -1);
        for (int i = 0; i < arr.length; i++) {
            String s = arr[i];
            builder.append(s);

            if (i < arr.length - 1) {
                SpannableString s1 = new SpannableString("__");
                s1.setSpan(
                        new QuestionFillSpan(),
                        0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
                builder.append(s1);
            }
        }
        return builder;
    }
}
