package com.eshare_android_preview.base.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.questions.QuestionShowActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.view.ui.FlatGridView;
import com.eshare_android_preview.base.view.ui.FlowLayout;
import com.eshare_android_preview.base.view.ui.FontAwesomeTextView;
import com.eshare_android_preview.base.view.ui.UiColor;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.QuestionChoice;
import com.eshare_android_preview.model.QuestionSelectAnswer;

import java.io.IOException;

/**
 * Created by Administrator on 13-12-19.
 */
public class QuestionChoicesView extends FlatGridView {

    public QuestionChoicesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Question question;
    private QuestionSelectAnswer answer;

    public QuestionShowActivity activity;

    LayoutInflater inflater;

    public void load_question(Question question) {
        this.question = question;
        this.answer = question.build_select_answer();

        this.inflater = (LayoutInflater) getContext().getSystemService("layout_inflater");

        if (question.is_fill()) {
            load_fill_kind_content();
            activity.question_content_webview.set_on_click_listener_for_code_fill(new CodefillViewListener());
            return;
        }

        if (question.is_true_false()) {
            load_true_false_kind_content();
            return;
        }

        load_select_kind_content();
    }

    private void load_fill_kind_content() {
        set_grid(1, 1);
        set_warp_height();
        FlowLayout fl = new FlowLayout(getContext());
        fl.verticalSpacing = BaseUtils.dp_to_px(3);
        fl.horizontalSpacing = BaseUtils.dp_to_px(3);

        add_view(fl);

        LayoutParams lp = (LayoutParams) fl.getLayoutParams();
        lp.leftMargin = BaseUtils.dp_to_px(2);
        lp.rightMargin = BaseUtils.dp_to_px(2);
        fl.setLayoutParams(lp);

        for (QuestionChoice choice : question.choices_list) {
            RelativeLayout fill_item_view = (RelativeLayout) inflater.inflate(R.layout.q_question_fill_detail_item, null);

            fl.addView(fill_item_view);
            ((TextView) fill_item_view.findViewById(R.id.item_text)).setText(choice.content);

            fill_item_view.setOnClickListener(new QuestionFillItemListener(fill_item_view, choice));
        }
    }

    private void load_true_false_kind_content() {
        set_grid(2, 1);

        RelativeLayout correct_view = (RelativeLayout) inflater.inflate(R.layout.q_question_choice_detail_item, null);
        RelativeLayout error_view = (RelativeLayout) inflater.inflate(R.layout.q_question_choice_detail_item, null);

        int s = BaseUtils.dp_to_px(60);

        FontAwesomeTextView correct_icon = new FontAwesomeTextView(getContext());
        correct_icon.setText(R.string.icon_check);
        correct_icon.setTextColor(UiColor.QUESTION_ANSWER_TRUE);
        correct_icon.setGravity(Gravity.CENTER);
        correct_icon.setTextSize(40);
        LayoutParams lp_correct = new LayoutParams(s, s);
        correct_icon.setLayoutParams(lp_correct);

        FontAwesomeTextView error_icon = new FontAwesomeTextView(getContext());
        error_icon.setText(R.string.icon_times);
        error_icon.setTextColor(UiColor.QUESTION_ANSWER_FALSE);
        error_icon.setGravity(Gravity.CENTER);
        error_icon.setTextSize(40);
        LayoutParams lp_error = new LayoutParams(s, s);
        error_icon.setLayoutParams(lp_error);

        ((RelativeLayout) correct_view.findViewById(R.id.item_content)).addView(correct_icon);
        ((RelativeLayout) error_view.findViewById(R.id.item_content)).addView(error_icon);

        add_view(correct_view);
        add_view(error_view);

        correct_view.setOnClickListener(new QuestionChoiceItemListener(correct_view, question.choices_list.get(0)));
        error_view.setOnClickListener(new QuestionChoiceItemListener(error_view, question.choices_list.get(1)));
    }

    private void load_select_kind_content() {
        if (question.is_choice_contain_image()) {
            // 选项是图片
            set_grid(2, 2);

            for (QuestionChoice choice : question.choices_list) {
                RelativeLayout choice_item_view = (RelativeLayout) inflater.inflate(R.layout.q_question_choice_detail_item, null);

                add_view(choice_item_view);

                RelativeLayout rl = (RelativeLayout) choice_item_view.findViewById(R.id.item_content);

                try {
                    ImageView iv = new ImageView(getContext());
                    String path = choice.content.replace("![file:///android_asset/", "").replace("]", "");
                    Drawable d = Drawable.createFromStream(getContext().getAssets().open(path), null);
                    iv.setImageDrawable(d);
                    rl.addView(iv);

                    iv.setAdjustViewBounds(true);
                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) iv.getLayoutParams();
                    lp.setMargins(6, 6, 6, 6);
                    lp.width = RelativeLayout.LayoutParams.FILL_PARENT;
                    lp.height = RelativeLayout.LayoutParams.FILL_PARENT;

                    iv.setLayoutParams(lp);

                } catch (IOException e) {

                }

                choice_item_view.setOnClickListener(new QuestionChoiceItemListener(choice_item_view, choice));
            }

            return;
        }


        boolean is_long_choice = question.choice_max_length() > 3;

        if (is_long_choice) {
            set_grid(1, question.choices_list.size());
        } else {
            set_grid(question.choices_list.size(), 1);
        }

        for (QuestionChoice choice : question.choices_list) {
            RelativeLayout choice_item_view = (RelativeLayout) inflater.inflate(R.layout.q_question_choice_detail_item, null);

            add_view(choice_item_view);

            RelativeLayout rl = (RelativeLayout) choice_item_view.findViewById(R.id.item_content);

            TextView tv = new TextView(getContext());
            tv.setText(choice.content);
            if (is_long_choice) {
                tv.setTextSize(BaseUtils.dp_to_px(12));
                rl.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                rl.setPadding(BaseUtils.dp_to_px(10), 0, 0, 0);
            } else {
                tv.setTextSize(BaseUtils.dp_to_px(16));
            }
            tv.setTextColor(Color.parseColor("#555555"));
            tv.setTypeface(Typeface.MONOSPACE);

            rl.addView(tv);

            choice_item_view.setOnClickListener(new QuestionChoiceItemListener(choice_item_view, choice));
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
        boolean selected = false;

        public QuestionChoiceItemListener(RelativeLayout item_view, QuestionChoice choice) {
            this.item_view = item_view;
            this.choice = choice;
        }

        @Override
        public void onClick(View v) {
            if (question.is_single_choice() || question.is_true_false()) {
                click_single();
            }

            if (question.is_multiple_choice()) {
                click_multiple();
            }

            activity.refresh_question_button();
        }

        public void click_single() {
            for (View view : children) {
                (view.findViewById(R.id.item_border)).setBackgroundColor(UiColor.CHOICE_BORDER);
                (view.findViewById(R.id.item_content)).setBackgroundColor(UiColor.CHOICE_BG);
            }

            (item_view.findViewById(R.id.item_border)).setBackgroundColor(UiColor.CHOICE_BORDER_ACTIVE);
            (item_view.findViewById(R.id.item_content)).setBackgroundColor(UiColor.CHOICE_BG_ACTIVE);

            answer.set_choice(choice);
        }

        public void click_multiple() {
            answer.add_or_remove_choice(choice);

            if(selected) {
                (item_view.findViewById(R.id.item_border)).setBackgroundColor(UiColor.CHOICE_BORDER);
                (item_view.findViewById(R.id.item_content)).setBackgroundColor(UiColor.CHOICE_BG);
            } else {
                (item_view.findViewById(R.id.item_border)).setBackgroundColor(UiColor.CHOICE_BORDER_ACTIVE);
                (item_view.findViewById(R.id.item_content)).setBackgroundColor(UiColor.CHOICE_BG_ACTIVE);
            }

            selected = !selected;
        }
    }

    class QuestionFillItemListener implements OnClickListener {
        RelativeLayout item_view;
        QuestionChoice choice;

//        EshareMarkdownView.Codefill binded_codefill;

        public QuestionFillItemListener(RelativeLayout item_view, QuestionChoice choice) {
            this.item_view = item_view;
            this.choice = choice;
        }

        @Override
        public void onClick(View v) {
            if (v.getTag() == null) {
                select(v);
            } else {
                unselect(v);
            }

            activity.refresh_question_button();
        }

        public void select(View v) {
            // 设置答案
            int index = activity.question_content_webview.get_first_unfilled_codefill_index();
            if (index == -1) {
                return;
            }

            answer.set_choice(index + 1, choice);

            // 设置底部选项样式
            (item_view.findViewById(R.id.item_border)).setBackgroundColor(UiColor.CHOICE_BORDER_ACTIVE);
            (item_view.findViewById(R.id.item_content)).setBackgroundColor(UiColor.CHOICE_BG_ACTIVE);

            // 设置 CodeFill 样式
            EshareMarkdownView.Codefill cf = activity.question_content_webview.get_first_unfilled_codefill();
            cf.set_text(choice.content);

            v.setTag(cf);
            cf.setTag(v);
        }

        public void unselect(View v) {
            unselect_fill_choice((EshareMarkdownView.Codefill) v.getTag(), v);
        }
    }

    class CodefillViewListener implements OnClickListener {
        @Override
        public void onClick(View view) {
            EshareMarkdownView.Codefill code_fill = (EshareMarkdownView.Codefill) view;
            if (!code_fill.filled) {
                return;
            }

            unselect_fill_choice(code_fill, (View) code_fill.getTag());

            activity.refresh_question_button();
        }
    }

    private void unselect_fill_choice(EshareMarkdownView.Codefill code_fill, View item_view) {
        // 设置答案
        int index = activity.question_content_webview.get_codefill_index(code_fill);
        answer.set_choice(index + 1, null);
        code_fill.unset_text();

        // 设置底部选项样式
        (item_view.findViewById(R.id.item_border)).setBackgroundColor(UiColor.CHOICE_BORDER);
        (item_view.findViewById(R.id.item_content)).setBackgroundColor(UiColor.CHOICE_BG);

        item_view.setTag(null);
        code_fill.setTag(null);
    }
}
