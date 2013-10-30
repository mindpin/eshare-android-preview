package com.eshare_android_preview.activity.base.questions;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Favourate;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.database.FavouratesDBHelper;

public class QuestionShowActivity extends EshareBaseActivity{
	TextView item_title_tv,question_kind,question_title;
	LinearLayout choices_ll, choices_list_ll;
	Button add_favourate_btn;
	Button cancel_favourate_btn;
	
	EditText answer_et;
	Button submit_but;
	
	String answer="";
	String[] a_z = "A,B,C,D,E,F,G,H,I,J,K".split(",");
	Question question;

    public static class ExtraKeys {
        public static final String QUESTION = "question";
    }
	
	@SuppressLint("WorldReadableFiles")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.q_question_show);
        hide_head_setting_button();

        Bundle bundle = getIntent().getExtras();
        question = (Question)bundle.getSerializable(QuestionShowActivity.ExtraKeys.QUESTION);


		if (question.kind.equals(Question.Type.TRUE_FALSE)) {
			List<String> list = new ArrayList<String>();
			list.add("正确");
			list.add("错误");
			question.choices_list = list;
			a_z = "T,F".split(",");
		}

		
		init_ui();
		load_question_msg();

        Favourate favourate = HttpApi.find_favourate(question.id + "", FavouratesDBHelper.Kinds.QUESTION);

		if (favourate == null) {
            add_favourate_btn.setVisibility(View.VISIBLE);
            cancel_favourate_btn.setVisibility(View.GONE);
		} else {
            add_favourate_btn.setVisibility(View.GONE);
            cancel_favourate_btn.setVisibility(View.VISIBLE);
		}

        super.onCreate(savedInstanceState);
	}
	
	private void init_ui() {
		question_kind = (TextView)findViewById(R.id.question_kind);
		question_title = (TextView)findViewById(R.id.question_title);
		
		choices_ll = (LinearLayout)findViewById(R.id.choices_ll);
		choices_list_ll = (LinearLayout)findViewById(R.id.choices_list_ll);
		
		answer_et = (EditText)findViewById(R.id.answer_et);
		submit_but = (Button)findViewById(R.id.submit_but);
		
		add_favourate_btn = (Button) findViewById(R.id.add_favourate_btn);
		cancel_favourate_btn = (Button) findViewById(R.id.cancel_favourate_btn);
	}
	
	private void load_question_msg() {
        set_head_text(BaseUtils.sub_string_by(question.title, 10));
		question_kind.setText(question.kind);
		question_title.setText(question.title);
		
		load_choice(choices_ll,R.layout.q_question_choice_item);
		load_choice(choices_list_ll,R.layout.q_question_choice_list_item);
	}

	private void load_choice(LinearLayout view,int item_view_layout){
		for (int i = 0; i < question.choices_list.size(); i++) {
			LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			RelativeLayout choice_item_view = (RelativeLayout) inflater.inflate(item_view_layout, null); 
			Button choice_item_but = (Button) choice_item_view.findViewById(R.id.choice_item_but);
			String but_text = item_view_layout == R.layout.q_question_choice_item ? a_z[i] + " " +  question.choices_list.get(i): a_z[i];
			choice_item_but.setText(but_text);
			view.addView(choice_item_view);
			choice_item_but.setOnClickListener(new ClickItemListener(i, choice_item_but));
		}
	}
	
	public void click_on_submit_but(View view){
		new AlertDialog.Builder(this)
					  	.setTitle("提示")
					  	.setMessage("答题正确")
					  	.setPositiveButton("确定", null)
					  	.show();
	}
	
	class ClickItemListener implements OnClickListener{
		int item_id;
		View view;
		public ClickItemListener(int id,View view){
			this.item_id = id;
			this.view = view;
		}
		@Override
		public void onClick(View v) {
			if (question.kind.equals(Question.Type.SINGLE_CHOICE)) {
				answer = a_z[item_id];
			}
			if (question.kind.equals(Question.Type.MULTIPLE_CHOICE)) {
				Object obj = view.getTag(R.id.tag_choice_uuid);
				boolean is_checked = obj != null ? (Boolean)obj:false;
				answer = is_checked ? answer.replace(a_z[item_id], ""):answer.replace(a_z[item_id], "")+a_z[item_id];
			}
			if (question.kind.equals(Question.Type.TRUE_FALSE)) {
				answer = a_z[item_id];
			}
			set_choice_item_and_list_item_background(choices_ll);
			set_choice_item_and_list_item_background(choices_list_ll);
			answer_et.setText(answer);
			submit_but.setClickable(!BaseUtils.is_str_blank(answer));
		}
		
	}
	
	private void set_choice_item_and_list_item_background(LinearLayout layout){
		if (question.kind.equals(Question.Type.SINGLE_CHOICE) || question.kind.equals(Question.Type.TRUE_FALSE)) {
			for (int j = 0; j < layout.getChildCount(); j++) {
				layout.getChildAt(j).findViewById(R.id.choice_item_but).setBackgroundResource(R.color.choice_after);
			}
			layout.getChildAt(BaseUtils.char_at_array_index(a_z, answer)).findViewById(R.id.choice_item_but).setBackgroundResource(R.color.choice_selected);
		}
		
		if (question.kind.equals(Question.Type.MULTIPLE_CHOICE)) {
			for (int j = 0; j < layout.getChildCount(); j++) {
				layout.getChildAt(j).findViewById(R.id.choice_item_but).setTag(R.id.tag_choice_uuid, false);
				layout.getChildAt(j).findViewById(R.id.choice_item_but).setBackgroundResource(R.color.choice_after);
			}
			
			List<Integer> list = BaseUtils.str_at_array_array(a_z, answer);
			for (int i = 0; i < list.size(); i++) {
				layout.getChildAt(list.get(i)).findViewById(R.id.choice_item_but).setTag(R.id.tag_choice_uuid, true);
				layout.getChildAt(list.get(i)).findViewById(R.id.choice_item_but).setBackgroundResource(R.color.choice_selected);
			}
		}
	}
	
	public void onClick(View view){
		
	}
	public void click_notes(View view){
//		open_activity(AddNoteActivity.class);
		Bundle bundle = new Bundle();
        bundle.putSerializable("item", question);

        Intent intent = new Intent(QuestionShowActivity.this,AddNoteActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
	}
	
	@SuppressLint({ "WorldReadableFiles", "WorldWriteableFiles" })
	public void add_favourate(View view) {
        Favourate favourate = new Favourate(question.id + "", FavouratesDBHelper.Kinds.QUESTION);
        HttpApi.create_favourate(favourate);
		
		add_favourate_btn.setVisibility(View.GONE);
		cancel_favourate_btn.setVisibility(View.VISIBLE);
	}
	
	@SuppressLint({ "WorldReadableFiles", "WorldWriteableFiles" })
	public void cancel_favourate(View view) {
        Favourate favourate = HttpApi.find_favourate(question.id + "", FavouratesDBHelper.Kinds.QUESTION);
        HttpApi.cancel_favourate(favourate);
		
		add_favourate_btn.setVisibility(View.VISIBLE);
		cancel_favourate_btn.setVisibility(View.GONE);
	}
}
