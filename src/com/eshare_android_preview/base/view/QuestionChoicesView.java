package com.eshare_android_preview.base.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.questions.QuestionShowActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.view.ui.CircleIconView;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.QuestionChoice;
import com.eshare_android_preview.model.QuestionSelectAnswer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 13-12-19.
 */
public class QuestionChoicesView extends RelativeLayout {

    public QuestionChoicesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Question question;
    private QuestionSelectAnswer answer;
    private List<View> items_list;

    public QuestionShowActivity activity;

    public void load_question(Question question) {
        this.question = question;
        this.answer = question.build_select_answer();
        this.items_list = new ArrayList<View>();
        removeAllViews();

        if (question.is_fill()) {
            load_fill_kind_content();
            return;
        }

        if (question.is_true_false()) {
            load_tree_false_kind_content();
            relayout_choices();
            return;
        }

        load_select_kind_content();
        relayout_choices();
    }

    private void load_fill_kind_content() {}

    private void load_tree_false_kind_content() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService("layout_inflater");

        RelativeLayout correct_view = (RelativeLayout) inflater.inflate(R.layout.q_question_choice_detail_item, null);
        RelativeLayout error_view = (RelativeLayout) inflater.inflate(R.layout.q_question_choice_detail_item, null);

        int s = BaseUtils.dp_to_px(60);
        CircleIconView correct_icon = new CircleIconView(getContext());
//        correct_icon.init("#E5EDD3", "#82AA2A", 40, R.string.icon_check);
        correct_icon.init("#00000000", "#82AA2A", 40, R.string.icon_check);
        LayoutParams lp_correct = new LayoutParams(s, s);
        correct_icon.setLayoutParams(lp_correct);

        CircleIconView error_icon = new CircleIconView(getContext());
//        error_icon.init("#F6D2D2", "#D62525", 40, R.string.icon_times);
        error_icon.init("#00000000", "#D62525", 40, R.string.icon_times);
        LayoutParams lp_error = new LayoutParams(s, s);
        error_icon.setLayoutParams(lp_error);

        ((RelativeLayout) correct_view.findViewById(R.id.item_content)).addView(correct_icon);
        ((RelativeLayout) error_view.findViewById(R.id.item_content)).addView(error_icon);

        addView(correct_view);
        addView(error_view);

        items_list.add(correct_view);
        items_list.add(error_view);

        correct_view.setOnClickListener(new QuestionChoiceItemListener(correct_view, question.choices_list.get(0)));
        error_view.setOnClickListener(new QuestionChoiceItemListener(error_view, question.choices_list.get(1)));
    }

    private void load_select_kind_content() {
        for (QuestionChoice choice : question.choices_list) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService("layout_inflater");
            RelativeLayout choice_item_view = (RelativeLayout) inflater.inflate(R.layout.q_question_choice_detail_item, null);

            addView(choice_item_view);
            items_list.add(choice_item_view);

            TextView tv = new TextView(getContext());
            tv.setText(choice.content);
            tv.setTextSize(24);
            tv.setTextColor(Color.parseColor("#555555"));

            ((RelativeLayout) choice_item_view.findViewById(R.id.item_content)).addView(tv);

            choice_item_view.setOnClickListener(new QuestionChoiceItemListener(choice_item_view, choice));
        }
    }

    private void relayout_choices() {
        _relayout_horizontal();
    }

    private void _relayout_horizontal() {
        BaseUtils.ScreenSize s = BaseUtils.get_screen_size();
        int width  = s.width_px;

        int w = width / items_list.size();
        int h = width / 4;

        for(int i = 0; i < items_list.size(); i++) {
            View view = items_list.get(i);
            LayoutParams lp = (LayoutParams) view.getLayoutParams();
            lp.width = w;
            lp.height = h;
            lp.leftMargin = w * i;
            view.setLayoutParams(lp);
        }
    }

    public boolean is_answer_empty() {
        return answer.is_empty();
    }

    public boolean is_answer_correct() {
        return answer.is_correct();
    }

    class QuestionChoiceItemListener implements OnClickListener {
        RelativeLayout item_view;
        QuestionChoice choice;

        public QuestionChoiceItemListener(RelativeLayout item_view, QuestionChoice choice) {
            this.item_view = item_view;
            this.choice = choice;
        }

        @Override
        public void onClick(View v) {
            for (View view : items_list) {
                (view.findViewById(R.id.item_border)).setBackgroundColor(Color.parseColor("#eeeeee"));
                (view.findViewById(R.id.item_content)).setBackgroundColor(Color.parseColor("#ffffff"));
            }

            (item_view.findViewById(R.id.item_border)).setBackgroundColor(Color.parseColor("#1CB0F6"));
            (item_view.findViewById(R.id.item_content)).setBackgroundColor(Color.parseColor("#D2EFFD"));

            answer.set_choice(choice);

            activity.refresh_submit_answer_btn_clickable();
        }
    }
}
