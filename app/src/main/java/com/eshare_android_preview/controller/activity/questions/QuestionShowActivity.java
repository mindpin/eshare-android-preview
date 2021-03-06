package com.eshare_android_preview.controller.activity.questions;

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
import com.eshare_android_preview.controller.activity.base.EshareBaseActivity;
import com.eshare_android_preview.controller.task.BaseAsyncTask;
import com.eshare_android_preview.controller.testpaper.TestPaper;
import com.eshare_android_preview.http.i.question.IQuestion;
import com.eshare_android_preview.view.QuestionChoicesView;
import com.eshare_android_preview.view.QuestionResultView;
import com.eshare_android_preview.view.ui.CorrectPointView;
import com.eshare_android_preview.view.ui.HealthView;
import com.eshare_android_preview.view.ui.QuestionButton;
import com.eshare_android_preview.view.ui.question.QuestionContentView;

public class QuestionShowActivity extends EshareBaseActivity {

    public static class ExtraKeys {
        public static final String TEST_PAPER = "test_paper";
    }

    public TestPaper test_paper;

    public QuestionContentView question_content_view;

    IQuestion question;
    QuestionResultView question_result_view;

    HealthView health_view;
    CorrectPointView correct_point_view;
    TextView question_kind_desc_text_view;
    QuestionChoicesView question_choices_view;

    QuestionButton question_button;

    private boolean continue_flag;

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

        question_content_view = (QuestionContentView) findViewById(R.id.question_content_view);

        question_result_view = (QuestionResultView) findViewById(R.id.question_result_view);
        question_result_view.activity = this;

        question_button = (QuestionButton) findViewById(R.id.question_button);

        question_button.set_submit_button_listener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (question_choices_view.is_answer_correct()) {
                    question_result_view.show_correct();
                    test_paper.test_result.increase_point();
                    correct_point_view.add_point();

                    post_a_question_result(true);
                } else {
                    question_result_view.show_error();
                    test_paper.test_result.decrease_hp();
                    health_view.break_heart();

                    post_a_question_result(false);
                }

                question_button.show_next_button();
            }
        });

        question_button.set_next_button_listener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (continue_flag) {
                    if (test_paper.test_result.is_fail()) {
                        go_fail();
                    } else if (test_paper.test_result.is_success()) {
                        go_success();
                    } else {
                        question_result_view.close_animate();
                    }

                    continue_flag = false;
                }
            }
        });
    }

    private void post_a_question_result(final boolean result) {
        new BaseAsyncTask<Void, Void, Void>(this, null) {
            @Override
            public Void do_in_background(Void... params) throws Exception {
                question.do_post_result(result);
                return null;
            }

            @Override
            public void on_success(Void aVoid) {

            }
        }.execute();
    }

    private void load_data(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            test_paper = savedInstanceState.getParcelable(ExtraKeys.TEST_PAPER);
        } else {
            test_paper = getIntent().getParcelableExtra(ExtraKeys.TEST_PAPER);
            load_question();
        }

        health_view.set_hp(test_paper.test_result.hp);
        correct_point_view.set_point(test_paper.test_result.point);
    }

    public void back(View v) {
        new AlertDialog.Builder(QuestionShowActivity.this)
                .setMessage("要退出练习吗？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                }).show();
    }

    public void load_question() {
        new BaseAsyncTask<Void, Void, Void>(this, "正在载入") {

            @Override
            public Void do_in_background(Void... params) throws Exception {
                question = test_paper.get_next_question();
                return null;
            }

            @Override
            public void on_success(Void aVoid) {
                question_kind_desc_text_view.setText(question.kind_desc());
                question_content_view.set_question(question);
                question_choices_view.load_question(question);
                question_button.reset();
                question_button.disable_submit();

                continue_flag = true;
            }
        }.execute();
    }

    private void go_fail() {
        open_activity(TestFailedActivity.class);
        finish();
    }

    // 打开成功画面
    private void go_success() {
        Intent intent = new Intent(this, TestSuccessActivity.class);
        intent.putExtra(TestSuccessActivity.ExtraKeys.TEST_PAPER, test_paper);
        startActivity(intent);
        finish();
    }

    // 根据答案选择情况刷新提交按钮
    public void refresh_question_button() {
        if (question_choices_view.is_answer_empty()) {
            question_button.disable_submit();
        } else {
            question_button.enable_submit();
        }
    }
}
