package com.eshare_android_preview.base.view.ui.question;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.eshare_android_preview.http.model.Question;

/**
 * Created by Administrator on 14-1-8.
 */
public class QuestionContentView extends LinearLayout {
    public QuestionContentView(Context context) {
        super(context);
        init();
    }

    public QuestionContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
    }

    private QuestionContent question_content;

    public void set_content(Question question) {
        removeAllViews();

        question_content = new QuestionContent(question);
        for (QuestionContent.Item item : question_content.list) {
            View v = item.get_view(getContext());
            if (null != v) {
                addView(v);
            }
        }
    }

    // 获取下一个还没填的空
    public QuestionFill get_next_empty_fill() {
        for (QuestionFill fill : question_content.fill_list) {
            if (fill.is_empty) return fill;
        }

        return null;
    }
}
