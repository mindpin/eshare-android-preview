package com.eshare_android_preview.view.ui.question;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.eshare_android_preview.http.i.question.IQuestion;
import com.eshare_android_preview.view.ui.question.builder.IQuItemView;
import com.eshare_android_preview.view.ui.question.builder.ViewBuilder;
import com.eshare_android_preview.http.model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 14-1-8.
 */
public class QuestionContentView extends LinearLayout {
    public QuestionContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
    }

    private IQuestion question;
    private List<IQuItemView> item_views;
    private List<QuestionFill> fills;

    public void set_question(IQuestion question) {
        this.question = question;

        removeAllViews();

        parse_views();

        fills = new ArrayList<QuestionFill>();
        int i = 1;
        for(IQuItemView qiv : item_views) {
            for (QuestionFillSpan span : qiv.spans()) {
                QuestionFill qf = new QuestionFill(qiv, span);
                fills.add(qf);
                qf.index = i;
                i++;
            }

            addView((View) qiv);
        }
    }

    private void parse_views() {
        item_views = new ArrayList<IQuItemView>();
        for (IQuestion.ContentToken token : question.content()) {
            item_views.add(ViewBuilder.build(getContext(), token));
        }
    }

    // 获取下一个还没填的空
    public QuestionFill get_next_empty_fill() {
        for (QuestionFill fill : fills) {
            if (fill.is_empty()) return fill;
        }
        return null;
    }
}
