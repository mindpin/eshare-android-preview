package com.eshare_android_preview.activity.base.questions;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.notes.AddNoteActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.QuestionChoice;
import com.eshare_android_preview.model.TestResult;

public class QuestionShowActivity extends EshareBaseActivity{
    public static class ExtraKeys {
        public static final String QUESTION = "question";
        public static final String TEST_RESULT = "test_result";
    }

    TestResult test_result;
    TextView test_result_tv;

	TextView question_kind_tv, question_title_tv;
	LinearLayout choices_detail_ll, choices_symbol_ll;
	Button add_favourite_btn;
	Button cancel_favourite_btn;
	
	EditText answer_et;
	Button submit_answer_btn;
	List<QuestionChoice> select_choices = new ArrayList<QuestionChoice>();
	Question question;

	@SuppressLint("WorldReadableFiles")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.q_question_show);
        hide_head_setting_button();

        Bundle bundle = getIntent().getExtras();
        question = (Question)bundle.getSerializable(ExtraKeys.QUESTION);
        test_result = (TestResult)bundle.getSerializable(ExtraKeys.TEST_RESULT);

		init_ui();

        refresh_test_result();
        init_faved_button();
		load_question_msg();

        super.onCreate(savedInstanceState);
	}

    private void refresh_test_result() {
        test_result_tv = (TextView)findViewById(R.id.test_result_tv);
        test_result_tv.setText("正确:" + test_result.correct_count + " 错误:" + test_result.error_count);
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
		question_kind_tv = (TextView)findViewById(R.id.question_kind);
		question_title_tv = (TextView)findViewById(R.id.question_title);
		
		choices_detail_ll = (LinearLayout)findViewById(R.id.choices_detail_ll);
		choices_symbol_ll = (LinearLayout)findViewById(R.id.choices_symbol_ll);
		
		answer_et = (EditText)findViewById(R.id.answer_et);
        submit_answer_btn = (Button)findViewById(R.id.submit_answer_btn);
	}
	
	private void load_question_msg() {
        set_head_text(BaseUtils.sub_string_by(question.title, 10));
		question_kind_tv.setText(question.kind);
		question_title_tv.setText(question.title);
		
		load_choice(choices_detail_ll,R.layout.q_question_choice_detail_item);
		load_choice(choices_symbol_ll,R.layout.q_question_choice_symbol_item);
	}

	private void load_choice(LinearLayout view,int item_view_layout){
		for (QuestionChoice choice : question.choices_list) {
			LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			RelativeLayout choice_item_view = (RelativeLayout) inflater.inflate(item_view_layout, null); 
			Button choice_item_btn = (Button) choice_item_view.findViewById(R.id.choice_item_btn);

            String btn_text;
            if(item_view_layout == R.layout.q_question_choice_detail_item){
                btn_text = choice.content;
            }else{
                btn_text = choice.sym;
            }

            choice_item_btn.setText(btn_text);
			view.addView(choice_item_view);
			choice_item_btn.setOnClickListener(new ClickItemListener(choice, choice_item_btn));
		}
	}
	
	public void click_on_submit_answer_btn(View view){
        View tip_tv;
        boolean is_answer_correct = true;
        if(is_answer_correct){
            tip_tv = findViewById(R.id.answer_correct_tip_tv);
            test_result.increase_correct_count();
        }else{
            tip_tv = findViewById(R.id.answer_error_tip_tv);
            test_result.increase_error_count();
        }
        refresh_test_result();
        tip_tv.setVisibility(View.VISIBLE);
        submit_answer_btn.setVisibility(View.GONE);
        Button next_question_btn = (Button)findViewById(R.id.next_question_btn);
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
	
	class ClickItemListener implements OnClickListener{
        QuestionChoice choice;
        View view;
		public ClickItemListener(QuestionChoice choice,View view){
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
                if(select_choices.indexOf(choice) != -1){
                    select_choices.remove(choice);
                }else{
                    select_choices.add(choice);
                }
			}

			set_choice_item_background(choices_detail_ll);
			set_choice_item_background(choices_symbol_ll);

            String answer = "";
            for(QuestionChoice sc : select_choices){
                answer += sc.sym;
            }
			answer_et.setText(answer);
			submit_answer_btn.setClickable(select_choices.size() != 0);
		}
		
	}
	
	private void set_choice_item_background(LinearLayout layout){
		if (question.is_single_choice() || question.is_true_false()) {
			for (int j = 0; j < layout.getChildCount(); j++) {
				layout.getChildAt(j).findViewById(R.id.choice_item_btn).setBackgroundResource(R.color.choice_before);
			}
			layout.getChildAt(select_choices.get(0).index).findViewById(R.id.choice_item_btn).setBackgroundResource(R.color.choice_selected);
		}
		
		if (question.is_multiple_choice()) {
			for (int j = 0; j < layout.getChildCount(); j++) {
				layout.getChildAt(j).findViewById(R.id.choice_item_btn).setBackgroundResource(R.color.choice_before);
			}

            for(QuestionChoice qc : select_choices){
			    layout.getChildAt(qc.index).findViewById(R.id.choice_item_btn).setBackgroundResource(R.color.choice_selected);
            }
		}
	}
	
	public void click_notes(View view){
		Bundle bundle = new Bundle();
        bundle.putSerializable(AddNoteActivity.ExtraKeys.LEARNING_RESOURCE, question);

        Intent intent = new Intent(QuestionShowActivity.this,AddNoteActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
	}
	
	@SuppressLint({ "WorldReadableFiles", "WorldWriteableFiles" })
	public void add_favourite(View view) {
        question.add_to_fav();

		add_favourite_btn.setVisibility(View.GONE);
		cancel_favourite_btn.setVisibility(View.VISIBLE);
	}
	
	@SuppressLint({ "WorldReadableFiles", "WorldWriteableFiles" })
	public void cancel_favourite(View view) {
        question.remove_from_fav();

		add_favourite_btn.setVisibility(View.VISIBLE);
		cancel_favourite_btn.setVisibility(View.GONE);
	}
}
