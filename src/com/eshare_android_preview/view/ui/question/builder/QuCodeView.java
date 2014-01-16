package com.eshare_android_preview.view.ui.question.builder;

import android.content.Context;
import android.text.SpannedString;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.eshare_android_preview.http.i.question.IQuestion;
import com.eshare_android_preview.utils.BaseUtils;
import com.eshare_android_preview.view.ui.question.QuestionFillSpan;
import com.eshare_android_preview.view.ui.question.code.RichCodeTextView;
import com.eshare_android_preview.http.model.Question;

import java.util.List;

/**
 * Created by Administrator on 14-1-15.
 */
public class QuCodeView extends RichCodeTextView implements IQuItemView {
    public QuCodeView(Context context, IQuestion.ContentToken token) {
        super(context);

        List<List<String>> list = (List<List<String>>) token.data.get("content");
        set_content(list);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        lp.bottomMargin = BaseUtils.dp_to_px(10);
        setLayoutParams(lp);
        int p1 = BaseUtils.dp_to_px(5);
        int p2 = BaseUtils.dp_to_px(8);
        setPadding(p2, p1, p2, p1);
        setTextSize(14);
    }

    @Override
    public QuestionFillSpan[] spans() {
        SpannedString ss = (SpannedString) getText();
        return ss.getSpans(0, ss.length(), QuestionFillSpan.class);
    }
}
