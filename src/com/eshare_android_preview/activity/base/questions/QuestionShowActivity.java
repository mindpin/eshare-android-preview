package com.eshare_android_preview.activity.base.questions;

import java.util.ArrayList;
import java.util.List;

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
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.model.Question;

public class QuestionShowActivity extends EshareBaseActivity{
	TextView item_title_tv,question_kind,question_title;
	LinearLayout choices_ll, choices_list_ll;
	
	EditText answer_et;
	Button submit_but;
	
	String answer="";
	String[] a_z = "A,B,C,D,E,F,G,H,I,J,K".split(",");
	Question question;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.q_question_show);
		
		Intent intent = getIntent();
		question = (Question)intent.getExtras().getSerializable("item");
		
		init_ui();
		load_question_msg();
		load_choices();
		load_choices_list();
	}
	
	private void init_ui() {
		item_title_tv = (TextView)findViewById(R.id.item_title_tv);
		question_kind = (TextView)findViewById(R.id.question_kind);
		question_title = (TextView)findViewById(R.id.question_title);
		
		choices_ll = (LinearLayout)findViewById(R.id.choices_ll);
		choices_list_ll = (LinearLayout)findViewById(R.id.choices_list_ll);
		
		answer_et = (EditText)findViewById(R.id.answer_et);
		submit_but = (Button)findViewById(R.id.submit_but);
	}
	
	private void load_question_msg() {
		item_title_tv.setText(question.title);
		question_kind.setText(question.kind);
		question_title.setText(question.title);
	}
	
	private void load_choices() {
		for (int i = 0; i < question.choices_list.size(); i++) {
			final int choice_answer = i;
			LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			RelativeLayout choice_item_view = (RelativeLayout) inflater.inflate(R.layout.q_question_choice_item, null); 
			final Button choice_item_tv = (Button) choice_item_view.findViewById(R.id.choice_item_tv);
			choice_item_tv.setText(a_z[i] + " " +  question.choices_list.get(i));
			choices_ll.addView(choice_item_view);
			
			choice_item_tv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (question.kind.equals(Question.Type.SINGLE_CHOICE)) {
						answer = a_z[choice_answer];
					}
					
					if (question.kind.equals(Question.Type.MULTIPLE_CHOICE)) {
						Object obj = choice_item_tv.getTag(R.id.tag_choice_uuid);
						boolean is_checked = obj != null ? (Boolean)obj:false;
						answer = is_checked ? answer.replace(a_z[choice_answer], ""):answer.replace(a_z[choice_answer], "")+a_z[choice_answer];
					}
					set_choice_item_and_list_item_background();
				}
			});
		}
	}
	
	private void load_choices_list() {
		for (int i = 0; i < question.choices_list.size(); i++) {
			final int choice_answer = i;
			
			LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			RelativeLayout choice_item_view = (RelativeLayout) inflater.inflate(R.layout.q_question_choice_list_item, null); 
			final Button choice_item_but = (Button) choice_item_view.findViewById(R.id.choice_item_but);
			choice_item_but.setText(a_z[i]);
			choices_list_ll.addView(choice_item_view);
			choice_item_but.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (question.kind.equals(Question.Type.SINGLE_CHOICE)) {
						answer = a_z[choice_answer];
					}
					
					if (question.kind.equals(Question.Type.MULTIPLE_CHOICE)) {
						Object obj = choice_item_but.getTag(R.id.tag_choice_uuid);
						boolean is_checked = obj != null ? (Boolean)obj:false;
						answer = is_checked ? answer.replace(a_z[choice_answer], ""):answer.replace(a_z[choice_answer], "")+a_z[choice_answer];
					}
					set_choice_item_and_list_item_background();
				}
			});
		}
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
			set_choice_item_and_list_item_background();
		}
		
	}
	
	private void set_choice_item_and_list_item_background(){
		// 单选
		if (question.kind.equals(Question.Type.SINGLE_CHOICE)) {
			for (int j = 0; j < choices_ll.getChildCount(); j++) {
				choices_ll.getChildAt(j).findViewById(R.id.choice_item_tv).setBackgroundResource(R.color.choice_after);
			}
			choices_ll.getChildAt(get_array_at()).findViewById(R.id.choice_item_tv).setBackgroundResource(R.color.choice_selected);
			
			for (int j = 0; j < choices_list_ll.getChildCount(); j++) {
				choices_list_ll.getChildAt(j).findViewById(R.id.choice_item_but).setBackgroundResource(R.color.choice_after);
			}
			choices_list_ll.getChildAt(get_array_at()).findViewById(R.id.choice_item_but).setBackgroundResource(R.color.choice_selected);
		}
		
		//多选情况下
		if (question.kind.equals(Question.Type.MULTIPLE_CHOICE)) {
			// 中间选项的设置
			for (int j = 0; j < choices_ll.getChildCount(); j++) {
				choices_ll.getChildAt(j).findViewById(R.id.choice_item_tv).setTag(R.id.tag_choice_uuid, false);
				choices_ll.getChildAt(j).findViewById(R.id.choice_item_tv).setBackgroundResource(R.color.choice_after);
			}
			for (int i = 0; i < get_array_at_array().size(); i++) {
				choices_ll.getChildAt(get_array_at_array().get(i)).findViewById(R.id.choice_item_tv).setTag(R.id.tag_choice_uuid, true);
				choices_ll.getChildAt(get_array_at_array().get(i)).findViewById(R.id.choice_item_tv).setBackgroundResource(R.color.choice_selected);
			}
			//   底部的选项设置
			for (int j = 0; j < choices_list_ll.getChildCount(); j++) {
				choices_list_ll.getChildAt(j).findViewById(R.id.choice_item_but).setTag(R.id.tag_choice_uuid, false);
				choices_list_ll.getChildAt(j).findViewById(R.id.choice_item_but).setBackgroundResource(R.color.choice_after);
			}
			for (int i = 0; i < get_array_at_array().size(); i++) {
				choices_list_ll.getChildAt(get_array_at_array().get(i)).findViewById(R.id.choice_item_but).setTag(R.id.tag_choice_uuid, true);
				choices_list_ll.getChildAt(get_array_at_array().get(i)).findViewById(R.id.choice_item_but).setBackgroundResource(R.color.choice_selected);
			}
		}
		answer_et.setText(answer);
	}
	
	private int get_array_at(){
		for (int i = 0; i < a_z.length; i++) {
			if (a_z[i].equals(answer)) {
				return i;
			}
		}
		return -1;
	}
	
	private List<Integer> get_array_at_array(){
		List<Integer> answer_list = new ArrayList<Integer>();
		for (int j = 0; j < answer.length(); j++) {
			for (int i = 0; i < a_z.length; i++) {
				if (a_z[i].equals(answer.charAt(j)+"")) {
					answer_list.add(i);
				}
			}
		}
		return answer_list;
	}
}
