package com.eshare_android_preview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.eshare_android_preview.R;
import com.eshare_android_preview.controller.activity.questions.QuestionShowActivity;
import com.eshare_android_preview.utils.BaseUtils;
import com.eshare_android_preview.utils.BaseUtils.ScreenSize;
import com.eshare_android_preview.view.ui.UiSound;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by menxu on 13-12-18.
 */
public class QuestionResultView extends RelativeLayout {
    final private static int ANIMATE_DRUATION = 400;

    final private static ScreenSize SCREEN_SIZE = BaseUtils.get_screen_size();
    final private static int WIDTH = BaseUtils.dp_to_px(240);
    final private static int HEIGHT = BaseUtils.dp_to_px(80);
    final private static int LEFT_SHOWING = (SCREEN_SIZE.width_px - WIDTH) / 2;
    final private static int TOP_SHOWING = (SCREEN_SIZE.height_px - HEIGHT) / 2;

    private RelativeLayout correct_view;
    private RelativeLayout error_view;

    public QuestionShowActivity activity;

    public QuestionResultView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init_correct_view();
        init_error_view();
    }

    private void init_correct_view() {
        correct_view = (RelativeLayout) inflate(getContext(), R.layout.q_question_show_correct, null);
        addView(correct_view);
    }

    private void init_error_view() {
        error_view = (RelativeLayout) inflate(getContext(), R.layout.q_question_show_error, null);
        addView(error_view);
    }

    View showing_view;

    public void show_correct() {
        setVisibility(VISIBLE);
        correct_view.setVisibility(VISIBLE);
        error_view.setVisibility(GONE);

        showing_view = correct_view;
        open_animate();

        UiSound.CORRECT.start();
    }

    public void show_error() {
        setVisibility(VISIBLE);
        correct_view.setVisibility(GONE);
        error_view.setVisibility(VISIBLE);

        showing_view = error_view;
        open_animate();

        UiSound.ERROR.start();
    }

    private void open_animate() {
        final LayoutParams lp = (LayoutParams) showing_view.getLayoutParams();
        lp.leftMargin = - WIDTH;
        lp.topMargin = TOP_SHOWING;
        showing_view.setLayoutParams(lp);

        PropertyValuesHolder pvh = PropertyValuesHolder.ofFloat("left", - WIDTH, LEFT_SHOWING);
        ValueAnimator ani = ValueAnimator
                .ofPropertyValuesHolder(pvh)
                .setDuration(ANIMATE_DRUATION);
        ani.setInterpolator(new DecelerateInterpolator());

        ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float left = (Float) valueAnimator.getAnimatedValue("left");
                lp.leftMargin = (int) left;
                showing_view.setLayoutParams(lp);
            }
        });

        ani.start();
    }

    public void close_animate() {
        final LayoutParams lp = (LayoutParams) showing_view.getLayoutParams();
        lp.leftMargin = LEFT_SHOWING;
        lp.topMargin = TOP_SHOWING;
        showing_view.setLayoutParams(lp);

        PropertyValuesHolder pvh = PropertyValuesHolder.ofFloat("left", LEFT_SHOWING, SCREEN_SIZE.width_px);
        ValueAnimator ani = ValueAnimator
                .ofPropertyValuesHolder(pvh)
                .setDuration(ANIMATE_DRUATION);
        ani.setInterpolator(new AccelerateInterpolator());

        ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float left = (Float) valueAnimator.getAnimatedValue("left");
                lp.leftMargin = (int) left;
                showing_view.setLayoutParams(lp);
            }
        });

        ani.addListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                setVisibility(GONE);
                activity.load_question();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        ani.start();
    }
}
