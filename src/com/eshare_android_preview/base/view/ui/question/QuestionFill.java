package com.eshare_android_preview.base.view.ui.question;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.eshare_android_preview.base.view.ui.UiColor;
import com.eshare_android_preview.http.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 14-1-14.
 */
public class QuestionFill {
    TextView text_view;
    public SpannableStringBuilder string_builder;
    BackgroundColorSpan span;

    public int index;
    public String filled_text;

    public void fill_text(String text) {
        this.filled_text = text;

        int start = string_builder.getSpanStart(span);
        int end = string_builder.getSpanEnd(span);
        string_builder = string_builder.replace(start, end, text);
        text_view.setText(string_builder);
    }

    public void clear_text() {
        this.filled_text = null;

        int start = string_builder.getSpanStart(span);
        int end = string_builder.getSpanEnd(span);
        string_builder = string_builder.replace(start, end, "__");
        text_view.setText(string_builder);
    }

    public void set_linked_view(View view) {

    }

    static public Pack get_text_include_fills(String text) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        List<QuestionFill> fill_list = new ArrayList<QuestionFill>();

        String[] arr = text.split("\\[%]", -1);
        for (int i = 0; i < arr.length; i++) {
            String s = arr[i];
            builder.append(s);

            if (i < arr.length - 1) {
                QuestionFill qf = new QuestionFill();
                qf.string_builder = builder;
                qf.span = new BackgroundColorSpan(UiColor.TEXT_FILL);
                fill_list.add(qf);

                SpannableString s1 = new SpannableString("__");
                s1.setSpan(
                        qf.span,
                        0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
                builder.append(s1);
            }
        }

        return new Pack(builder, fill_list);
    }

    public static class Pack {
        public SpannableStringBuilder string_builder;
        public List<QuestionFill> fill_list;

        Pack(SpannableStringBuilder string_builder, List<QuestionFill> fill_list) {
            this.string_builder = string_builder;
            this.fill_list = fill_list;
        }
    }
}
