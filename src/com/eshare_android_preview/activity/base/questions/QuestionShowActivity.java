package com.eshare_android_preview.activity.base.questions;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.task.BaseAsyncTask;
import com.eshare_android_preview.base.view.EshareMarkdownView;
import com.eshare_android_preview.base.view.QuestionChoicesView;
import com.eshare_android_preview.base.view.QuestionResultView;
import com.eshare_android_preview.base.view.ui.CorrectPointView;
import com.eshare_android_preview.base.view.ui.HealthView;
import com.eshare_android_preview.base.view.ui.QuestionButton;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.TestPaper;

public class QuestionShowActivity extends EshareBaseActivity {

    public static class ExtraKeys {
        public static final String QUESTION = "question";
        public static final String TEST_PAPER = "test_paper";
    }

    public TestPaper test_paper;

    EshareMarkdownView question_content_webview;

    Question question;
    QuestionResultView question_result_view;

    HealthView health_view;
    CorrectPointView correct_point_view;
    TextView question_kind_desc_text_view;
    QuestionChoicesView question_choices_view;

    QuestionButton question_button;

    @SuppressLint("WorldReadableFiles")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.q_question_show);

        init_ui();
        load_data(savedInstanceState);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ExtraKeys.TEST_PAPER, this.test_paper);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back(null);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void init_ui() {
        health_view = (HealthView) findViewById(R.id.health_view);
        correct_point_view = (CorrectPointView) findViewById(R.id.correct_point_view);
        question_kind_desc_text_view = (TextView) findViewById(R.id.question_kind_desc_text_view);
        question_choices_view = (QuestionChoicesView) findViewById(R.id.question_choices_view);
        question_choices_view.activity = this;

        question_content_webview = (EshareMarkdownView) findViewById(R.id.question_title);

        question_result_view = (QuestionResultView) findViewById(R.id.question_result_view);

        question_button = (QuestionButton) findViewById(R.id.question_button);

        question_button.set_submit_button_listener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (question_choices_view.is_answer_correct()) {
                    question_result_view.show_true();
                    test_paper.test_result.increase_point();
                    correct_point_view.add_point();
                } else {
                    question_result_view.show_false();
                    test_paper.test_result.decrease_hp();
                    health_view.break_heart();
                }

                show_next_question_btn();
            }
        });

        question_button.set_next_button_listener(new OnClickListener() {
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

    private void load_data(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            test_paper = savedInstanceState.getParcelable(ExtraKeys.TEST_PAPER);
        } else {
            test_paper = getIntent().getParcelableExtra(ExtraKeys.TEST_PAPER);
        }

        health_view.set_hp(test_paper.test_result.hp);
        correct_point_view.set_point(test_paper.test_result.point);

        load_question();
    }

    public void back(View v) {
        new AlertDialog.Builder(QuestionShowActivity.this)
                .setMessage("要退出测试吗？")
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
        new BaseAsyncTask<Void, Void, Void>(this, "正在载入") {

            @Override
            public Void do_in_background(Void... params) throws Exception {
                question = test_paper.get_next_question();
                return null;
            }

            @Override
            public void on_success(Void aVoid) {
                question_kind_desc_text_view.setText(question.get_kind_desc_str());
                question_content_webview.set_markdown_content(question.content);

                //        if (question.is_fill()){
                //            load_question_choices_for_fill();
                //            question_content_webview.set_on_click_listener_for_code_fill(new FillQuestionFillItemListener());
                //        }else{
                //            load_question_choices_for_choice_and_true_false();
                //        }

                question_choices_view.load_question(question);

                findViewById(R.id.question_content_transparent_view).setVisibility(View.GONE);

                question_button.disable_submit();
            }
        }.execute();
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
        question_button.show_next_button();
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
//        int index = question_content_webview.get_codefill_index(code_fill);
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
//        refresh_question_button();
//    }

    // 根据答案选择情况刷新提交按钮
    public void refresh_question_button() {
        if (question_choices_view.is_answer_empty()) {
            question_button.disable_submit();
        } else {
            question_button.enable_submit();
        }
    }
}
