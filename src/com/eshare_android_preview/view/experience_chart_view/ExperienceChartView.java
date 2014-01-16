package com.eshare_android_preview.view.experience_chart_view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.eshare_android_preview.http.model.DayExp;
import com.eshare_android_preview.view.ui.UiColor;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.ArrayList;
import java.util.List;


public class ExperienceChartView extends View {

    private Paint paint;
    private List<Float> exps;
    private float max_exp;

    float canvas_height;
    float canvas_width;

    // 坐标轴线条宽度
    final private float AXIS_WIDTH = 2.0F;


    // 日期信息
    private List<DayExp> day_exps;

    private boolean inited = false;

    public ExperienceChartView(Context context) {
        super(context);
    }

    public ExperienceChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExperienceChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void init(List<DayExp> day_exps){
        if (isInEditMode()) return;

        this.day_exps = day_exps;

        // 读取每个节点的经验值
        get_exps();

        // 计算最大经验值
        get_max_exp();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.MONOSPACE);

        inited = true;
        invalidate();
    }

    private void get_exps() {
        exps = new ArrayList<Float>();
        for(int i = 0; i < 5; i++) {
            exps.add((float) day_exps.get(i).exp_num);
        }
    }

    private void get_max_exp() {
        max_exp = 0;
        for(float exp : exps) {
            if (exp > max_exp) {
                max_exp = exp;
            }
        }

        max_exp = max_exp + 20;
    }

    private float top_line_y;
    private float bottom_line_y;
    private float axis_left_offset = 50; // 坐标轴横线相对于绘图区域左侧的偏移量
    private int axis_font_size = 24; // 坐标轴文字大小
    private int day_font_size = 20; // 日期星期文字大小
    private float circle_line_width = 6; // 圆连线宽度
    int circle_radius = 12; // 圆圈半径

    @Override
    protected void onDraw(Canvas canvas) {
        if (isInEditMode()) return;

        if(!inited) return;

        canvas_height = getHeight();
        canvas_width = getWidth();

        // 顶部线的 Y 坐标
        top_line_y = 40;

        // 底部线的 Y 坐标
        bottom_line_y = canvas_height - 80;

        draw_axis(canvas);

        draw_scale_marks(canvas);
    }

    // 画坐标系
    private void draw_axis(Canvas canvas) {
        // 底X轴
        draw_horizontal_line(bottom_line_y, 0, canvas);

        // 顶X轴
        draw_horizontal_line(top_line_y, max_exp, canvas);

        // top_line_y : max_exp
        // bottom_line_y : 0
        // 每隔 delta 画一根横线
        int delta;

        if (max_exp > 100) {
            delta = 50;
        } else if (max_exp > 50) {
            delta = 20;
        } else {
            delta = 10;
        }

        int e = delta;
        while (e < max_exp) {
            float y = get_relative_y(e);
            draw_horizontal_line(y, e, canvas);
            e = e + delta;
        }
    }

    private void draw_horizontal_line(float y, float exp, Canvas canvas) {
        String exp_text = (int) exp + "";

        paint.setColor(UiColor.EXP_CHART_AXIS);
        paint.setStrokeWidth(AXIS_WIDTH);
        paint.setTextSize(axis_font_size);

        canvas.drawLine(axis_left_offset, y, canvas_width - axis_left_offset, y, paint);

        Rect bounds = new Rect();
        paint.getTextBounds(exp_text, 0, exp_text.length(), bounds);

        canvas.drawText(exp_text,
                20 - bounds.width() / 2,
                y + bounds.height() / 2,
                paint
        );
    }

    private float get_relative_y(float exp) {
        return bottom_line_y - exp * (bottom_line_y - top_line_y) / max_exp;
    }


    // 画刻度线
    private void draw_scale_marks(Canvas canvas) {
        int length = exps.size();
        float grid_width = (canvas_width - axis_left_offset) / length;

        for (int i = 0; i < length; i++) {
            float mark_x = grid_width * i + axis_left_offset;
            float mark_y = bottom_line_y;

            // 画刻度
            paint.setColor(UiColor.EXP_CHART_SCALE_MARK);
            paint.setStrokeWidth(AXIS_WIDTH);
            canvas.drawLine(mark_x, mark_y, mark_x, mark_y - 5, paint);

            // 画星期文字
            DayExp day = day_exps.get(i);
            Rect bounds = new Rect();
            paint.setColor(UiColor.EXP_CHART_SCALE_MARK_TEXT);
            paint.setTextSize(day_font_size);
            paint.getTextBounds(day.week_day, 0, day.week_day.length(), bounds);
            canvas.drawText(day.week_day,
                    mark_x - bounds.width() / 2,
                    mark_y + 31 + bounds.height() / 2,
                    paint
            );

            // 画日期文字
            paint.getTextBounds(day.month_day, 0, day.month_day.length(), bounds);
            canvas.drawText(day.month_day,
                    mark_x - bounds.width() / 2,
                    mark_y + 31 + 30 + bounds.height() / 2,
                    paint
            );

            // 画折线
            paint.setColor(UiColor.EXP_CHART_CIRCLE_LINE);
            paint.setStrokeWidth(circle_line_width);

            if (i < length - 1) {
                // 只有前 4 个点才需画线
                float exp = exps.get(i);
                float exp_next = exps.get(i + 1);

                float y1 = get_relative_y(exp);
                float y2 = get_relative_y(exp_next);
                float x1 = mark_x;
                float x2 = mark_x + grid_width;

                canvas.drawLine(x1, y1, x2, y2, paint);
            }
        }

        // 再循环一次 画圆
        for (int i = 0; i < length; i++) {
            float cx = grid_width * i + axis_left_offset;
            float cy = get_relative_y(exps.get(i));

            if (i < length - 1) {
                paint.setColor(UiColor.EXP_CHART_CIRCLE_BORDER);
                canvas.drawCircle(cx, cy, circle_radius + 2, paint);

                paint.setColor(UiColor.EXP_CHART_CIRCLE);
                canvas.drawCircle(cx, cy, circle_radius - 2, paint);
            } else {
                paint.setColor(UiColor.EXP_CHART_CURRENT_CIRCLE_BORDER);
                canvas.drawCircle(cx, cy, circle_radius + 2, paint);

                paint.setColor(UiColor.EXP_CHART_CURRENT_CIRCLE);
                canvas.drawCircle(cx, cy, circle_radius - 2, paint);
            }
        }
    }

    public void run_animation(int dynamic_exp) {
        float exp = exps.get(exps.size() - 1);
        float after_exp = exp + dynamic_exp;

        ValueAnimator animation = ValueAnimator.ofFloat(exp, after_exp);
        animation.setDuration(500);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (Float) animation.getAnimatedValue();
                exps.set(4, v);
                invalidate();
            }
        });

        animation.start();
    }

}