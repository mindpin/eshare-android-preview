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

    public EshareMarkdownView question_content_webview;

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
        question_result_view.activity = this;

        question_button = (QuestionButton) findViewById(R.id.question_button);

        question_button.set_submit_button_listener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (question_choices_view.is_answer_correct()) {
                    question_result_view.show_correct();
                    test_paper.test_result.increase_point();
                    correct_point_view.add_point();
                } else {
                    question_result_view.show_error();
                    test_paper.test_result.decrease_hp();
                    health_view.break_heart();
                }

                question_button.show_next_button();
            }
        });

        question_button.set_next_button_listener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (test_paper.test_result.is_end()) {
                    to_do_answer_error();
                } else if (test_paper.test_result.is_pass()) {
                    go_success();
                } else {
                    question_result_view.close_animate();
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

    public void load_question() {
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
                question_choices_view.load_question(question);
                question_button.reset();
                question_button.disable_submit();
            }
        }.execute();
    }

    private void to_do_answer_error() {
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
