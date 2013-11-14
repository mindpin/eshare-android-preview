package com.eshare_android_preview.activity.base.questions;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.notes.AddNoteActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.ImageTools;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.QuestionChoice;
import com.eshare_android_preview.model.TestResult;

public class QuestionShowActivity extends EshareBaseActivity {
    public static class ExtraKeys {
        public static final String QUESTION = "question";
        public static final String TEST_RESULT = "test_result";
    }

    TestResult test_result;

    TextView question_kind_tv, question_title_tv;
    LinearLayout choices_detail_ll;
    Button add_favourite_btn;
    Button cancel_favourite_btn;

    Button submit_answer_btn;
    List<QuestionChoice> select_choices = new ArrayList<QuestionChoice>();
    Question question;

    @SuppressLint("WorldReadableFiles")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.q_question_show);
        hide_head_setting_button();

        Bundle bundle = getIntent().getExtras();
        question = (Question) bundle.getSerializable(ExtraKeys.QUESTION);
        test_result = (TestResult) bundle.getSerializable(ExtraKeys.TEST_RESULT);
        if (test_result == null) {
            test_result = new TestResult(3,Question.all().size());
        }

        init_ui();

        refresh_test_result();
        init_faved_button();
        load_question_msg();

        _set_icon_fonts();

        super.onCreate(savedInstanceState);
    }

    private void _set_icon_fonts() {
        BitmapDrawable drawable = ImageTools.toRoundCorner(
                (BitmapDrawable) getResources().getDrawable(R.drawable.lan_js), 100);
        ImageView lan_icon_iv = (ImageView) findViewById(R.id.lan_icon);
        lan_icon_iv.setBackgroundDrawable(drawable);

        set_fontawesome((TextView) findViewById(R.id.correct_icon));
        set_fontawesome((TextView) findViewById(R.id.error_icon));
    }

    private void refresh_test_result() {
        ((TextView) findViewById(R.id.correct_count)).setText(test_result.current_correct_count + "/" + test_result.needed_correct_count);
        ((TextView) findViewById(R.id.error_count)).setText(test_result.current_error_count() + "/" + test_result.allowed_error_count);
    }

    private void init_faved_button() {
        add_favourite_btn = (Button) findViewById(R.id.add_favourite_btn);
        cancel_favourite_btn = (Button) findViewById(R.id.cancel_favourite_btn);
        if (!question.is_faved()) {
            add_favourite_btn.setVisibility(View.VISIBLE);
            cancel_favourite_btn.setVisibility(View.GONE);
        } else {
            add_favourite_btn.setVisibility(View.GONE);
            cancel_favourite_btn.setVisibility(View.VISIBLE);
        }
    }

    private void init_ui() {
        question_kind_tv = (TextView) findViewById(R.id.question_kind);
        question_title_tv = (TextView) findViewById(R.id.question_title);
        choices_detail_ll = (LinearLayout) findViewById(R.id.choices_detail_ll);
        submit_answer_btn = (Button) findViewById(R.id.submit_answer_btn);

        findViewById(R.id.answer_correct_tip_tv).setVisibility(View.GONE);
        findViewById(R.id.answer_error_tip_tv).setVisibility(View.GONE);
        findViewById(R.id.next_question_btn).setVisibility(View.GONE);
    }

    private void load_question_msg() {
        set_head_text("答题");
        question_kind_tv.setText(question.get_kind_str());
        question_title_tv.setText(question.title);

        load_choice(choices_detail_ll, R.layout.q_question_choice_detail_item);
    }

    // 加载并显示选项
    private void load_choice(LinearLayout view, int item_view_layout) {
        for (QuestionChoice choice : question.choices_list) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            RelativeLayout choice_item_view = (RelativeLayout) inflater.inflate(item_view_layout, null);

            RelativeLayout choice_item_btn = (RelativeLayout) choice_item_view.findViewById(R.id.choice_item_btn);
            TextView choice_item_sym  = (TextView) choice_item_view.findViewById(R.id.choice_item_sym);
            TextView choice_item_text = (TextView) choice_item_view.findViewById(R.id.choice_item_text);

            choice_item_sym.setText(choice.sym);
            choice_item_text.setText(choice.content);
            choice_item_btn.setOnClickListener(new ClickItemListener(choice, choice_item_btn));

            view.addView(choice_item_view);
        }
    }

    public void click_on_submit_answer_btn(View view) {
        View tip_tv;
        boolean is_answer_correct = question.answer(QuestionChoice.syms(select_choices));
        if (is_answer_correct) {
            tip_tv = findViewById(R.id.answer_correct_tip_tv);
            test_result.increase_correct_count();
        } else {
            tip_tv = findViewById(R.id.answer_error_tip_tv);
            test_result.increase_error_count();
        }
        refresh_test_result();
        tip_tv.setVisibility(View.VISIBLE);
        
        if (test_result.is_end()) {
		   to_do_answer_error();
		}else{
		   to_do_next_question();
		}
    }
    private void to_do_answer_error() {
    	new AlertDialog.Builder(QuestionShowActivity.this)
    	.setTitle("提示")
    	.setMessage("答题错误已经超过 " + test_result.allowed_error_count + " 次,需要重新答题")
    	.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				finish();
			}
		}).show();
	}

	private void to_do_next_question(){
    	 submit_answer_btn.setVisibility(View.GONE);
         Button next_question_btn = (Button) findViewById(R.id.next_question_btn);
         next_question_btn.setVisibility(View.VISIBLE);
         next_question_btn.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(QuestionShowActivity.this, QuestionShowActivity.class);
                 Bundle bundle = new Bundle();
                 bundle.putSerializable(QuestionShowActivity.ExtraKeys.TEST_RESULT, test_result);
                 bundle.putSerializable(QuestionShowActivity.ExtraKeys.QUESTION, question.next());
                 intent.putExtras(bundle);
                 startActivity(intent);
                 finish();
             }
         });
    }
    
    

    class ClickItemListener implements OnClickListener {
        QuestionChoice choice;
        View view;

        public ClickItemListener(QuestionChoice choice, View view) {
            this.choice = choice;
            this.view = view;
        }

        @Override
        public void onClick(View v) {

            if (question.is_single_choice() || question.is_true_false()) {
                select_choices.clear();
                select_choices.add(choice);
            }
            if (question.is_multiple_choice()) {
                if (select_choices.indexOf(choice) != -1) {
                    select_choices.remove(choice);
                } else {
                    select_choices.add(choice);
                }
            }

            set_choice_item_background(choices_detail_ll);

            if (select_choices.size() == 0) {
                submit_answer_btn.setClickable(false);
                submit_answer_btn.setBackgroundResource(R.drawable.btn_c6699bd3b_circle_flat);
                submit_answer_btn.setShadowLayer(0, 0, 0, Color.parseColor("#00000000"));
            } else {
                submit_answer_btn.setClickable(true);
                submit_answer_btn.setBackgroundResource(R.drawable.btn_c99bd3b_circle_flat);
                submit_answer_btn.setShadowLayer(1, 1, 1, Color.parseColor("#66000000"));
            }
        }

    }

    private void set_choice_item_background(LinearLayout layout) {

        if (question.is_single_choice() || question.is_true_false()) {
            _set_choices_color_before(layout);
            _set_choice_color_after(layout.getChildAt(select_choices.get(0).index));
        }

        if (question.is_multiple_choice()) {
            _set_choices_color_before(layout);

            for (QuestionChoice qc : select_choices) {
                _set_choice_color_after(layout.getChildAt(qc.index));
            }
        }
    }

    private void _set_choices_color_before(LinearLayout layout) {
        for(int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);
            view.findViewById(R.id.choice_item_bg).setBackgroundColor(Color.parseColor("#ffffff"));
            view.findViewById(R.id.choice_item_bg0).setBackgroundColor(Color.parseColor("#aaaaaa"));
            view.findViewById(R.id.choice_item_sym).setBackgroundResource(R.drawable.btn_c444_circle_flat);
            ((TextView) view.findViewById(R.id.choice_item_text)).setTextColor(Color.parseColor("#444444"));
            ((TextView) view.findViewById(R.id.choice_item_text)).setShadowLayer(0, 0, 0, Color.parseColor("#00000000"));
        }
    }

    private void _set_choice_color_after(View view) {
        view.findViewById(R.id.choice_item_bg).setBackgroundColor(Color.parseColor("#99BD3B"));
        view.findViewById(R.id.choice_item_bg0).setBackgroundColor(Color.parseColor("#82AA2A"));
        view.findViewById(R.id.choice_item_sym).setBackgroundResource(R.drawable.btn_c435816_circle_flat);
        ((TextView) view.findViewById(R.id.choice_item_text)).setTextColor(Color.parseColor("#ffffff"));
        ((TextView) view.findViewById(R.id.choice_item_text)).setShadowLayer(1, 1, 1, Color.parseColor("#66000000"));
    }

    public void click_notes(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AddNoteActivity.ExtraKeys.LEARNING_RESOURCE, question);

        Intent intent = new Intent(QuestionShowActivity.this, AddNoteActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @SuppressLint({"WorldReadableFiles", "WorldWriteableFiles"})
    public void add_favourite(View view) {
        question.add_to_fav();

        add_favourite_btn.setVisibility(View.GONE);
        cancel_favourite_btn.setVisibility(View.VISIBLE);
    }

    @SuppressLint({"WorldReadableFiles", "WorldWriteableFiles"})
    public void cancel_favourite(View view) {
        question.remove_from_fav();

        add_favourite_btn.setVisibility(View.VISIBLE);
        cancel_favourite_btn.setVisibility(View.GONE);
    }
}
