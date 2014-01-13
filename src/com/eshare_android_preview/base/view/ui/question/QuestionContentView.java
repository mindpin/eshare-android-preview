package com.eshare_android_preview.base.view.ui.question;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    public void set_content(String content_json_string) {
        QuestionContent qc = new QuestionContent(content_json_string);
        for(QuestionContent.Item item : qc.list) {
            View v = item.get_view(getContext());
            if (null != v) {
                addView(v);
            }
        }
    }
}
