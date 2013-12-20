package com.eshare_android_preview.activity.base.questions;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.notes.AddNoteActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.task.BaseAsyncTask;
import com.eshare_android_preview.base.view.EshareMarkdownView;
import com.eshare_android_preview.base.view.QuestionChoicesView;
import com.eshare_android_preview.base.view.QuestionResultView;
import com.eshare_android_preview.base.view.ui.CorrectPointView;
import com.eshare_android_preview.base.view.ui.HealthView;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.TestPaper;

public class QuestionShowActivity extends EshareBaseActivity {

    public static class ExtraKeys {
        public static final String QUESTION = "question";
        public static final String TEST_PAPER = "test_paper";
    }

    public TestPaper test_paper;

    EshareMarkdownView question_title_v;

    Button submit_answer_btn;
    Question question;
    QuestionResultView question_result_view;

    HealthView health_view;
    CorrectPointView correct_point_view;
    TextView question_kind_desc_text_view;
    QuestionChoicesView question_choices_view;

    @SuppressLint("WorldReadableFiles")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.q_question_show);
        this.test_paper = getIntent().getParcelableExtra(ExtraKeys.TEST_PAPER);
        init_ui();
        load_question();
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

    public void back(View v) {
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

    private void load_question() {
        new BaseAsyncTask<Void, Void, Void>(this,"正在载入"){

            @Override
            public Void do_in_background(Void... params) throws Exception {
                question = test_paper.get_next_question();
                return null;
            }

            @Override
            public void on_success(Void aVoid) {
                question_kind_desc_text_view.setText(question.get_kind_desc_str());
                question_title_v.set_markdown_content(question.content);

        //        if (question.is_fill()){
        //            load_question_choices_for_fill();
        //            question_title_v.set_on_click_listener_for_code_fill(new FillQuestionFillItemListener());
        //        }else{
        //            load_question_choices_for_choice_and_true_false();
        //        }

                question_choices_view.load_question(question);

                findViewById(R.id.question_content_transparent_view).setVisibility(View.GONE);
                submit_answer_btn.setVisibility(View.VISIBLE);
                refresh_submit_answer_btn_clickable();
            }
        }.execute();
    }

    private void init_ui() {
        question_title_v = (EshareMarkdownView) findViewById(R.id.question_title);
        submit_answer_btn = (Button) findViewById(R.id.submit_answer_btn);

        question_result_view = (QuestionResultView) findViewById(R.id.question_result_view);

        findViewById(R.id.next_question_btn).setVisibility(View.GONE);

        // --- 12.18

        health_view = (HealthView) findViewById(R.id.health_view);
        correct_point_view = (CorrectPointView) findViewById(R.id.correct_point_view);
        question_kind_desc_text_view = (TextView) findViewById(R.id.question_kind_desc_text_view);
        question_choices_view = (QuestionChoicesView) findViewById(R.id.question_choices_view);
        question_choices_view.activity = this;

    }

    // 根据答案选择情况刷新提交按钮
    public void refresh_submit_answer_btn_clickable() {
        if (question_choices_view.is_answer_empty()) {
            submit_answer_btn.setClickable(false);
            submit_answer_btn.setBackgroundResource(R.drawable.btn_c6699bd3b_circle_flat);
            submit_answer_btn.setShadowLayer(0, 0, 0, Color.parseColor("#00000000"));
        } else {
            submit_answer_btn.setClickable(true);
            submit_answer_btn.setBackgroundResource(R.drawable.btn_c99bd3b_circle_flat);
            submit_answer_btn.setShadowLayer(1, 1, 1, Color.parseColor("#66000000"));
        }
    }

    // 提交按钮的点击事件
    public void click_on_submit_answer_btn(View view) {
        if (question_choices_view.is_answer_correct()) {
            question_result_view.show_true();
            test_paper.test_result.increase_correct_count();
            correct_point_view.add_point();
        } else {
            question_result_view.show_false();
            test_paper.test_result.increase_error_count();
            health_view.break_heart();
        }

        show_next_question_btn();
    }

    private void to_do_answer_error() {
        open_activity(TestFailedActivity.class);
        finish();
    }

    private void to_do_answer_pass() {
        Intent intent = new Intent(this, TestSuccessActivity.class);
        intent.putExtra(TestSuccessActivity.ExtraKeys.TEST_PAPER, test_paper);
        startActivity(intent);
        finish();
    }

    private void show_next_question_btn() {
        (findViewById(R.id.question_content_transparent_view)).setVisibility(View.VISIBLE);
        submit_answer_btn.setVisibility(View.GONE);
        Button next_question_btn = (Button) findViewById(R.id.next_question_btn);
        next_question_btn.setVisibility(View.VISIBLE);
        next_question_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (test_paper.test_result.is_end()) {
                    to_do_answer_error();
                } else if (test_paper.test_result.is_pass()) {
                    to_do_answer_pass();
                } else {
                    question_result_view.close();
                    load_question();
                }
            }
        });
    }

//    class FillQuestionFillItemListener implements OnClickListener{
//        @Override
//        public void onClick(View v) {
//            Toast.makeText(QuestionShowActivity.this,"click",2000).show();
//
//            EshareMarkdownView.Codefill code_fill = (EshareMarkdownView.Codefill) v;
//            if(!code_fill.filled){
//                return;
//            }
//
//            unselect_fill_item(code_fill);
//        }
//    }

//    private void unselect_fill_item(EshareMarkdownView.Codefill code_fill){
//        int index = question_title_v.get_codefill_index(code_fill);
//
//        select_answer.set_choice(index+1, null);
//
//        code_fill.unset_text();
//
//        View fill_item_btn = (View)code_fill.getTag();
//        TextView fill_item_text = (TextView)fill_item_btn.findViewById(R.id.fill_item_text);
//        fill_item_text.setTextColor(Color.parseColor("#000000"));
//
//        code_fill.setTag(null);
//        fill_item_btn.setTag(null);
//
//        refresh_submit_answer_btn_clickable();
//    }


    public void click_notes(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AddNoteActivity.ExtraKeys.LEARNING_RESOURCE, question);

        Intent intent = new Intent(QuestionShowActivity.this, AddNoteActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
