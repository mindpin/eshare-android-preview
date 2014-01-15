package com.eshare_android_preview.base.view.ui.question;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.view.View;

import com.eshare_android_preview.base.view.ui.UiColor;

/**
 * Created by Administrator on 14-1-14.
 */
public class QuestionFill extends ClickableSpan {
    String text;

    public QuestionFill(String text) {
        super();
        this.text = text;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(ds.linkColor);
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View view) {

    }

    public int index;
    public boolean is_empty = true;

    public void set_text(String text) {

    }

    public void clear_text() {

    }

    public void set_linked_view(View view) {

    }

    static public SpannableStringBuilder get_text_include_fills(String text) {
        SpannableStringBuilder re = new SpannableStringBuilder();

        String[] arr = text.split("\\[%]", -1);
        for (int i = 0; i < arr.length; i++) {
            String s = arr[i];
            re.append(s);

            if (i < arr.length - 1) {
                SpannableString s1 = new SpannableString("__");
                s1.setSpan(
                        new BackgroundColorSpan(UiColor.TEXT_FILL),
                        0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
                re.append(s1);
            }
        }

        return re;
    }
}
