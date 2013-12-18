package com.eshare_android_preview.activity.base.questions;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.notes.AddNoteActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.utils.BaseUtils.ScreenSize;
import com.eshare_android_preview.base.utils.ImageTools;
import com.eshare_android_preview.base.view.EshareMarkdownView;
import com.eshare_android_preview.model.MultipleChoiceQuestionSelectAnswer;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.QuestionChoice;
import com.eshare_android_preview.model.QuestionSelectAnswer;
import com.eshare_android_preview.model.SingleChoiceQuestionSelectAnswer;
import com.eshare_android_preview.model.TestPaper;
import com.eshare_android_preview.model.TrueFalseQuestionSelectAnswer;
import com.eshare_android_preview.model.elog.CurrentState;
import com.eshare_android_preview.model.elog.ExperienceLog;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class QuestionShowActivity extends EshareBaseActivity {

    public static class ExtraKeys {
        public static final String QUESTION = "question";
    }
    public static TestPaper test_paper;

    TextView question_kind_tv;
    EshareMarkdownView  question_title_v;
    LinearLayout choices_detail_ll;
    Button add_favourite_btn;
    Button cancel_favourite_btn;

    Button submit_answer_btn;
    Question question;
    QuestionSelectAnswer select_answer;
    View tip_tv;

    @SuppressLint("WorldReadableFiles")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.q_question_show);
        hide_head_setting_button();

        question = test_paper.get_next_question();
        select_answer = question.build_select_answer();

        init_ui();

        refresh_test_result();
        init_faved_button();
        load_question_msg();

        _set_icon_fonts();

        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back(null);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void back(View v){
        new AlertDialog.Builder(QuestionShowActivity.this)
                .setMessage("确定退出测试吗？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                }).show();
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
        ((TextView) findViewById(R.id.correct_count)).setText(test_paper.test_result.current_correct_count + "/" + test_paper.test_result.needed_correct_count);
        ((TextView) findViewById(R.id.error_count)).setText(test_paper.test_result.current_error_count() + "/" + test_paper.test_result.allowed_error_count);
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
        question_title_v = (EshareMarkdownView) findViewById(R.id.question_title);
        choices_detail_ll = (LinearLayout) findViewById(R.id.choices_detail_ll);
        submit_answer_btn = (Button) findViewById(R.id.submit_answer_btn);

        findViewById(R.id.answer_correct_tip_tv).setVisibility(View.GONE);
        findViewById(R.id.answer_error_tip_tv).setVisibility(View.GONE);
        findViewById(R.id.next_question_btn).setVisibility(View.GONE);
    }

    private void load_question_msg() {
        set_head_text("答题");
        question_kind_tv.setText(question.get_kind_str());
        question_title_v.set_markdown_content(question.content);

        if (question.is_fill()){
            load_question_choices_for_fill();
            question_title_v.set_on_click_listener_for_code_fill(new FillQuestionFillItemListener());
        }else{
            load_question_choices_for_choice_and_true_false();
        }
    }

    // 为填空类型的题目加载并显示选项
    private void load_question_choices_for_fill() {
        for (QuestionChoice choice : question.choices_list) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            FrameLayout fill_item_view = (FrameLayout) inflater.inflate(R.layout.q_question_fill_detail_item, null);

            FrameLayout fill_item_btn = (FrameLayout) fill_item_view.findViewById(R.id.fill_item_btn);
            fill_item_btn.setOnClickListener(new QuestionChoiceItemListener(choice));

            TextView fill_item_text_tv  = (TextView) fill_item_view.findViewById(R.id.fill_item_text);
            TextView fill_item_placeholder_bg_text_tv  = (TextView) fill_item_view.findViewById(R.id.fill_item_placeholder_bg);
            fill_item_text_tv.setText(choice.content);
            fill_item_placeholder_bg_text_tv.setText(choice.content);
            fill_item_text_tv.setTag(choice);

            choices_detail_ll.setOrientation(LinearLayout.HORIZONTAL);
            choices_detail_ll.addView(fill_item_view);
        }
    }

    // 为多选，单选和判断类型的题目加载并显示选项
    private void load_question_choices_for_choice_and_true_false() {
        for (QuestionChoice choice : question.choices_list) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            RelativeLayout choice_item_view = (RelativeLayout) inflater.inflate(R.layout.q_question_choice_detail_item, null);

            RelativeLayout choice_item_btn = (RelativeLayout) choice_item_view.findViewById(R.id.choice_item_btn);
            choice_item_btn.setOnClickListener(new QuestionChoiceItemListener(choice));

            TextView choice_item_sym  = (TextView) choice_item_view.findViewById(R.id.choice_item_sym);
            choice_item_sym.setText(choice.sym);

            EshareMarkdownView choice_item_text = (EshareMarkdownView) choice_item_view.findViewById(R.id.choice_item_text);
            choice_item_text.set_markdown_content(choice.content);
            choice_item_text.disable_touch_event();

            choices_detail_ll.addView(choice_item_view);
        }
    }
    
    public void click_on_submit_answer_btn(View view) {
        if (select_answer.is_correct()) {
            tip_tv = findViewById(R.id.answer_correct_tip_tv);
            test_paper.test_result.increase_correct_count();
        } else {
            tip_tv = findViewById(R.id.answer_error_tip_tv);
            test_paper.test_result.increase_error_count();
        }
        refresh_test_result();
        
        ((Button)findViewById(R.id.question_content_transparent_view)).setVisibility(View.VISIBLE);
        open_tip_tv_animation(tip_tv);
        
        if (test_paper.test_result.is_end()) {
		   to_do_answer_error();
        }else if(test_paper.test_result.is_pass()){
            to_do_answer_pass();
		}else{
		   to_do_next_question();
		}
    }
    
    private void open_tip_tv_animation(View tip_tv){
    	tip_tv.setVisibility(View.VISIBLE);
    	ScreenSize ss = BaseUtils.get_screen_size();
    	ObjectAnimator anim_scale = ObjectAnimator.ofFloat(tip_tv, "y", ss.height_dp+50,ss.height_dp/2);
        anim_scale.setDuration(500);
        anim_scale.start();
    }
    private void close_tip_tv_animation(View tip_tv){
    	int duration = 500;
    	ScreenSize ss = BaseUtils.get_screen_size();
    	final int[] location = new int[2];
    	tip_tv.getLocationOnScreen(location);
    	
    	AnimatorSet animSet = new AnimatorSet();
    	ObjectAnimator pvh_x = ObjectAnimator.ofFloat(tip_tv, "x", location[0],ss.width_dp+50);
    	pvh_x.setDuration(duration);
    	ObjectAnimator pvh_alpha = ObjectAnimator.ofFloat(tip_tv, "alpha", 1f, 0f);
    	pvh_alpha.setDuration(duration);
    	
    	animSet.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator arg0) {}
			@Override
			public void onAnimationRepeat(Animator arg0) {}
			@Override
			public void onAnimationEnd(Animator arg0) {
				Intent intent = new Intent(QuestionShowActivity.this, QuestionShowActivity.class);
                startActivity(intent);
                finish();
			}
			@Override
			public void onAnimationCancel(Animator arg0) {}
		});
    	animSet.play(pvh_x).with(pvh_alpha);
    	animSet.start();
        
    }

    private void to_do_answer_error() {
        open_activity(TestFailedActivity.class);
        finish();
	}

    private void to_do_answer_pass(){
        TestSuccessActivity.test_paper = this.test_paper;
        open_activity(TestSuccessActivity.class);
        finish();
    }

	private void to_do_next_question(){
    	 submit_answer_btn.setVisibility(View.GONE);
         Button next_question_btn = (Button) findViewById(R.id.next_question_btn);
         next_question_btn.setVisibility(View.VISIBLE);
         next_question_btn.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {
            	 close_tip_tv_animation(tip_tv);
             }
         });
    }

    private void refresh_submit_answer_btn_clickable(){
        if (select_answer.is_empty()) {
            submit_answer_btn.setClickable(false);
            submit_answer_btn.setBackgroundResource(R.drawable.btn_c6699bd3b_circle_flat);
            submit_answer_btn.setShadowLayer(0, 0, 0, Color.parseColor("#00000000"));
        } else {
            submit_answer_btn.setClickable(true);
            submit_answer_btn.setBackgroundResource(R.drawable.btn_c99bd3b_circle_flat);
            submit_answer_btn.setShadowLayer(1, 1, 1, Color.parseColor("#66000000"));
        }
    }
    
    class QuestionChoiceItemListener implements OnClickListener {
        QuestionChoice choice;

        public QuestionChoiceItemListener(QuestionChoice choice) {
            this.choice = choice;
        }

        @Override
        public void onClick(View v) {
            if(question.is_single_choice()){
                on_click_for_single_choice();
            }else if(question.is_multiple_choice()){
                on_click_for_multiple_choice();
            }else if(question.is_true_false()){
                on_click_for_true_false();
            }else if(question.is_fill()){
                on_click_for_fill(v);
            }

            refresh_submit_answer_btn_clickable();
        }

        private void on_click_for_fill(View v){
            Object tag = v.getTag();
            if(tag == null){
                on_click_for_fill_select(v);
            }else{
                on_click_for_fill_unselect(v);
            }
        }

        private void on_click_for_fill_unselect(View v){
            EshareMarkdownView.Codefill code_fill = (EshareMarkdownView.Codefill)v.getTag();
            unselect_fill_item(code_fill);
        }

        private void on_click_for_fill_select(View v){
            int index = question_title_v.get_first_unfilled_codefill_index();
            if(index == -1){
                return;
            }

            select_answer.set_choice(index+1, choice);
            TextView fill_item_text = (TextView)v.findViewById(R.id.fill_item_text);
            fill_item_text.setTextColor(Color.parseColor("#ffffff"));

            EshareMarkdownView.Codefill code_fill = question_title_v.get_first_unfilled_codefill();

            String str = fill_item_text.getText().toString();
            Toast.makeText(QuestionShowActivity.this, str,5000).show();
            code_fill.set_text(str);

            v.setTag(code_fill);
            code_fill.setTag(v);
        }

        private void on_click_for_single_choice(){
            select_answer.set_choice(choice);

            _set_choices_color_before();
            int index = ((SingleChoiceQuestionSelectAnswer) select_answer).select_choice.index;
            _set_choice_color_after(choices_detail_ll.getChildAt(index));
        }

        private void on_click_for_true_false(){
            select_answer.set_choice(choice);

            _set_choices_color_before();
            int index = ((TrueFalseQuestionSelectAnswer) select_answer).select_choice.index;
            _set_choice_color_after(choices_detail_ll.getChildAt(index));
        }

        private void on_click_for_multiple_choice(){
            select_answer.add_or_remove_choice(choice);

            _set_choices_color_before();
            for (QuestionChoice qc : ((MultipleChoiceQuestionSelectAnswer) select_answer).select_choices) {
                _set_choice_color_after(choices_detail_ll.getChildAt(qc.index));
            }
        }

        private void _set_choices_color_before() {
            for(int i = 0; i < choices_detail_ll.getChildCount(); i++) {
                View view = choices_detail_ll.getChildAt(i);
                view.findViewById(R.id.choice_item_bg).setBackgroundColor(Color.parseColor("#ffffff"));
                view.findViewById(R.id.choice_item_bg0).setBackgroundColor(Color.parseColor("#aaaaaa"));
                view.findViewById(R.id.choice_item_sym).setBackgroundResource(R.drawable.btn_c444_circle_flat);

            }
        }

        private void _set_choice_color_after(View view) {
            view.findViewById(R.id.choice_item_bg).setBackgroundColor(Color.parseColor("#99BD3B"));
            view.findViewById(R.id.choice_item_bg0).setBackgroundColor(Color.parseColor("#82AA2A"));
            view.findViewById(R.id.choice_item_sym).setBackgroundResource(R.drawable.btn_c435816_circle_flat);
        }

    }

    class FillQuestionFillItemListener implements OnClickListener{
        @Override
        public void onClick(View v) {
            Toast.makeText(QuestionShowActivity.this,"click",2000).show();

            EshareMarkdownView.Codefill code_fill = (EshareMarkdownView.Codefill) v;
            if(!code_fill.filled){
                return;
            }

            unselect_fill_item(code_fill);
        }
    }

    private void unselect_fill_item(EshareMarkdownView.Codefill code_fill){
        int index = question_title_v.get_codefill_index(code_fill);

        select_answer.set_choice(index+1, null);

        code_fill.unset_text();

        View fill_item_btn = (View)code_fill.getTag();
        TextView fill_item_text = (TextView)fill_item_btn.findViewById(R.id.fill_item_text);
        fill_item_text.setTextColor(Color.parseColor("#000000"));

        code_fill.setTag(null);
        fill_item_btn.setTag(null);

        refresh_submit_answer_btn_clickable();
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
