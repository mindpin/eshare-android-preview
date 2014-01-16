package com.eshare_android_preview.base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.view.ui.UiColor;
import com.eshare_android_preview.http.c.UserData;
import com.eshare_android_preview.http.i.knowledge.IUserExp;
import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeNet;
import com.eshare_android_preview.http.model.CurrentState;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by menxu on 13-12-10.
 */
public class ExperienceView extends View {
    private boolean is_inited = false;

    final private static int ANIMATE_DRUATION = 500;

    // 圆
    final private static int CIRCLE_RADIUS = BaseUtils.dp_to_px(18); // 左右圆半径 可以修改

    final private static int LEFT_CIRCLE_LEFT   = BaseUtils.dp_to_px(25); // 左圆圆心相对于view左边缘的距离
    final private static int LEFT_CIRCLE_TOP    = BaseUtils.dp_to_px(25);  // 左圆圆心相对于view顶部的距离

    final private static int RIGHT_CIRCLE_RIGHT = LEFT_CIRCLE_LEFT; // 右圆圆心相对于view右边缘的距离
    final private static int RIGHT_CIRCLE_TOP   = LEFT_CIRCLE_TOP;  // 右圆圆心相对于view顶部的距离

    // 矩形
    final private static int RECT_LEFT   = BaseUtils.dp_to_px(25); // 矩形的左边缘与view左边缘的距离
    final private static int RECT_TOP    = BaseUtils.dp_to_px(10); // 矩形的上边缘与view上边缘的距离
    final private static int RECT_RIGHT  = RECT_LEFT;                  // 矩形的右边缘与view右边缘的距离
    final private static int RECT_BOTTOM = RECT_TOP;                   // 矩形的下边缘与view下边缘的距离

    // 描边
    final private static int LINE_STROKE_WIDTH = BaseUtils.dp_to_px(5);

    // 文字
    final private static int TEXT_SIZE  = BaseUtils.dp_to_px(16);

    // current_level
    private int current_level;

    private int view_width;
    private int view_height;
    private int rect_width;
    private float rect_exp_bar_width; // 矩形里面的经验条的初始长度  (exp_num/level_up_exp_num)
    private Paint paint;

    public ExperienceView(Context context) {
        super(context);
    }

    public ExperienceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void refresh(){
        init_exp_and_level();
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(!is_inited) return;

        paint = new Paint();
        paint.setAntiAlias(true);

        draw_background_rect(canvas);
        draw_exp_rect(canvas);
        draw_left_circle(canvas);
        draw_right_circle(canvas);
        draw_text_left(canvas);
        draw_text_right(canvas);
    }

    public void init() {
        // circle
        if (!is_inited) {
            is_inited = true;

            view_width  = getWidth();
            view_height = getHeight();
            rect_width = view_width - RECT_LEFT - RECT_RIGHT;
        }
        refresh();
    }

    private void init_exp_and_level() {
        if (isInEditMode()) {
            rect_exp_bar_width = 60;
            current_level = 1;
            return;
        }

        IUserKnowledgeNet net = UserData.instance().get_current_knowledge_net(false);
        IUserExp exp = net.get_exp();

        rect_exp_bar_width = rect_width * exp.get_level_exp() / exp.get_next_level_total_exp();
        current_level = exp.get_level();
    }

    private void draw_background_rect(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(UiColor.EXP_BAR_EMPTY);

        canvas.drawRect(
                RECT_LEFT, RECT_TOP,
                view_width - RECT_RIGHT, view_height - RECT_BOTTOM,
                paint
        );

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(LINE_STROKE_WIDTH);
        paint.setColor(UiColor.EXP_STROKE_COLOR);

        canvas.drawRect(
                RECT_LEFT, RECT_TOP,
                view_width - RECT_RIGHT, view_height - RECT_BOTTOM,
                paint
        );
    }

    private void draw_exp_rect(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(UiColor.EXP_BAR_FILL);

        canvas.drawRect(
                RECT_LEFT + LINE_STROKE_WIDTH / 2, RECT_TOP + LINE_STROKE_WIDTH / 2,
                RECT_LEFT + rect_exp_bar_width + LINE_STROKE_WIDTH / 2, view_height - RECT_BOTTOM - LINE_STROKE_WIDTH / 2,
                paint
        );
    }

    private void draw_left_circle(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(UiColor.EXP_LEFT_CIRCLE);

        canvas.drawCircle(LEFT_CIRCLE_LEFT, LEFT_CIRCLE_TOP, CIRCLE_RADIUS, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(LINE_STROKE_WIDTH);
        paint.setColor(UiColor.EXP_STROKE_COLOR);

        canvas.drawCircle(LEFT_CIRCLE_LEFT, LEFT_CIRCLE_TOP, CIRCLE_RADIUS, paint);
    }

    private void draw_right_circle(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(UiColor.EXP_RIGHT_CIRCLE);

        canvas.drawCircle(view_width - RIGHT_CIRCLE_RIGHT, RIGHT_CIRCLE_TOP, CIRCLE_RADIUS, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(LINE_STROKE_WIDTH);
        paint.setColor(UiColor.EXP_STROKE_COLOR);

        canvas.drawCircle(view_width - RIGHT_CIRCLE_RIGHT, RIGHT_CIRCLE_TOP, CIRCLE_RADIUS, paint);
    }

    private void draw_text_left(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(UiColor.EXP_CIRCLE_TEXT);
        paint.setTextSize(TEXT_SIZE);
        paint.setFakeBoldText(true);

        String text = current_level + "";
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        canvas.drawText(
                text,
                LEFT_CIRCLE_LEFT - paint.measureText(text) / 2,
                LEFT_CIRCLE_TOP + bounds.height() / 2,
                paint
        );
    }

    private void draw_text_right(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(UiColor.EXP_CIRCLE_TEXT);
        paint.setTextSize(TEXT_SIZE);
        paint.setFakeBoldText(true);

        String text = current_level + 1 + "";
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        canvas.drawText(
                text,
                view_width - RIGHT_CIRCLE_RIGHT - paint.measureText(text) / 2,
                RIGHT_CIRCLE_TOP + bounds.height() / 2,
                paint
        );
    }

    private void set_rect_width(float rect_width) {
        rect_exp_bar_width = rect_width;
        invalidate();
        requestLayout();
    }

    private float get_added_rect_width(int added_exp) {
        IUserExp state = UserData.instance().get_current_knowledge_net(false).get_exp();
        float added_width = rect_width * added_exp / state.get_next_level_total_exp();
        return added_width + rect_exp_bar_width;
    }

    // 根据传入的半径值和动画时间来产生动画效果
    // 升级时的逻辑可能有些问题，回头改
    public void add(int added_exp) {
        float old_rect_width = rect_exp_bar_width;
        final float new_rect_width = get_added_rect_width(added_exp);

        ValueAnimator animation = ValueAnimator.ofFloat(old_rect_width, new_rect_width);
        animation.setDuration(ANIMATE_DRUATION);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                value = value >= rect_width ? rect_width : value;
                set_rect_width(value);
            }
        });
        animation.addListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator arg0) {}

            @Override
            public void onAnimationRepeat(Animator arg0) {}

            @Override
            public void onAnimationEnd(Animator arg0) {
                if (rect_width <= new_rect_width) {
                    level_up();
                    float current_count = ((new_rect_width - rect_width) / rect_width) * CurrentState.get_level_up_exp(current_level);
                    set_rect_width(0F);
                    add((int) current_count);
                }
            }

            @Override
            public void onAnimationCancel(Animator arg0) {
            }
        });
        animation.start();
    }

    private void level_up() {
        current_level = current_level + 1;
        invalidate();
        requestLayout();
    }
}
