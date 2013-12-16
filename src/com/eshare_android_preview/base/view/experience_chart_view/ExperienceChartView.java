package com.eshare_android_preview.base.view.experience_chart_view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.eshare_android_preview.model.DayExpInfo;
import com.eshare_android_preview.model.elog.ExperienceLog;
import java.util.List;

import com.nineoldandroids.animation.ValueAnimator;



public class ExperienceChartView extends View {

    private Paint paint;
    private int dynamic_exp, vectorLength = 6;
    private float[] yvalues = new float[5];
    private float maxy = 0.0f, miny = 0.0f;


    float canvas_height;
    float canvas_width;

    // 字体大小
    float text_size = 20.0f;

    // 默认画线宽度
    float line_width = 2.0f;

    // 默认线颜色
    String line_color = "#000000";

    // 圆圈半径
    float radius = 20.0f;

    // 圆连线宽度
    float circle_line_width = 8;

    // 圆连线颜色
    String circle_line_color = "#666666";

    // 圆的颜色
    String circle_color = "#333333";

    // 刻度线宽度
    float mark_line_width = 7;

    // 顶部线颜色
    String top_line_color = "#000000";

    // 底部线颜色
    String bottom_line_color = "#000000";

    // 刻度线高度
    float mark_height = 30.0f;

    // 顶部线的Y轴位置
    float top_pos;

    // 底部线的Y轴位置
    float bottom_pos;

    // 经验值显示的画布范围
    float y_top_pos;
    float y_bottom_pos;
    float y_range;

    // 刻度线 Y轴位置
    float y_mark_pos;

    // 星期线 Y轴位置
    float y_week_pos;

    // 日期线 Y轴位置
    float y_date_pos;


    // 星期跟底部线的间隔高度
    float week_space_with_bottom_pos = 30;

    // 日期跟星期的间隔高度
    float date_space_with_week_pos = 20;


    // 日期信息
    private List<DayExpInfo> logs = ExperienceLog.history_info();

    public ExperienceChartView(Context context) {
        super(context);
        init();
    }


    public ExperienceChartView(Context context, int dynamic) {
        super(context);
        this.dynamic_exp = dynamic;
        init();
    }

    public ExperienceChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExperienceChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        this.yvalues = get_yvalues();

        paint = new Paint();

        get_axes();
    }

    private float[] get_yvalues() {
        float[] yList = new float[5];
        for (int i = 0; i <= vectorLength - 2; i++) {
            DayExpInfo weekday = logs.get(i);
            yList[i] = weekday.exp_num;
        }

        return yList;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawARGB(255, 200, 200, 200);

        canvas_height = getHeight();
        canvas_width = getWidth();

        // 顶部线的Y轴位置
        top_pos = (float) .1 * canvas_height;

        // 底部线的Y轴位置
        bottom_pos = (float) .85 * canvas_height;


        // 经验值显示的画布范围
        y_top_pos = top_pos + radius + 10;
        y_bottom_pos = bottom_pos - radius - mark_height - 10;
        y_range = y_bottom_pos - y_top_pos;

        // 刻度线 Y轴位置
        y_mark_pos = bottom_pos - mark_height;

        // 星期线 Y轴位置, 底部X轴 + 间隔区 + 文字大小
        y_week_pos = bottom_pos + week_space_with_bottom_pos + text_size;

        // 日期线 Y轴位置, 星期线位置 + 间隔区 + 文字大小
        y_date_pos = y_week_pos + date_space_with_week_pos + text_size;

        // 初始化画笔
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor(line_color));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(text_size);

        // 画出底部X轴
        paint.setColor(Color.parseColor(bottom_line_color));
        paint.setStrokeWidth(line_width);
        canvas.drawLine(0, bottom_pos, canvas_width, bottom_pos, paint);

        // 画出顶部X轴
        paint.setColor(Color.parseColor(top_line_color));
        paint.setStrokeWidth(line_width);
        canvas.drawLine(0, top_pos, canvas_width, top_pos, paint);


        // 将X轴分成6等分，画出5条刻度线
        paint.setColor(Color.parseColor(line_color));
        for (int i = 0; i <= vectorLength - 2; i++) {
            int x_pos = (int) (((i + 1) * canvas_width / vectorLength));

            DayExpInfo weekday = logs.get(i);

            // 显示刻度条
            paint.setStrokeWidth(mark_line_width);
            canvas.drawLine(x_pos, bottom_pos,
                x_pos, y_mark_pos, paint);

            // 显示星期
            paint.setStrokeWidth(line_width);
            canvas.drawText(weekday.day_of_week_str, x_pos,
                    y_week_pos, paint);


            // 显示日期
            canvas.drawText(weekday.day_of_month_str, x_pos,
                    y_date_pos, paint);


            // 输出折线
            paint.setColor(Color.parseColor(circle_line_color));
            paint.setStrokeWidth(circle_line_width);
            int y_exp = to_pixel(yvalues[i]);
            if (i < 4) {
                int x_next_pos = (int) (((i + 2) * canvas_width / vectorLength));

                int y_next_exp = to_pixel(yvalues[i + 1]);
                canvas.drawLine(x_pos, y_exp,
                        x_next_pos, y_next_exp, paint);
            }

            // 输出经验值圆圈
            paint.setColor(Color.parseColor(circle_color));
            canvas.drawCircle(x_pos, y_exp, radius, paint);

        }

    }


    private void get_axes() {
        // miny = get_min(yvalues);
        miny = 0;
        maxy = get_max(yvalues) + 10;
    }



    private int to_pixel(float value) {
        double p;
        int pint;

        p = ((value - miny) / (maxy - miny)) * y_range;

        p = y_bottom_pos - p;

        pint = (int) p;

        return (pint);
    }

    private float get_max(float[] v) {
        float largest = v[0];
        for (int i = 0; i < v.length; i++)
            if (v[i] > largest)
                largest = v[i];
        return largest;
    }

    private float get_min(float[] v) {
        float smallest = v[0];
        for (int i = 0; i < v.length; i++)
            if (v[i] < smallest)
                smallest = v[i];
        return smallest;
    }


    public void set_yvalue(Float value){
        yvalues[yvalues.length - 1] = value;
        get_axes();
        invalidate();
        requestLayout();
    }


    public void run_animation() {
        float prev_exp_pos = yvalues[vectorLength - 2];
        float current_exp_pos = prev_exp_pos + dynamic_exp;

        ValueAnimator animation = ValueAnimator.ofFloat(prev_exp_pos, current_exp_pos );
        animation.setDuration(500);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float v = (Float) animation.getAnimatedValue();
                set_yvalue(v);
            }
        });
        animation.start();
    }

}