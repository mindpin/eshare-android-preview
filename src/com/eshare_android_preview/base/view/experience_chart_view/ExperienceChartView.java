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

        // 去掉锯齿
        paint.setAntiAlias(true);

        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(20.0f);

        get_axes(yvalues);
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

        // 默认画线粗细
        paint.setStrokeWidth(2);

        float canvas_height = getHeight();
        float canvas_width = getWidth();

        float top_pos = (float) .1 * canvas_height;
        float y_top_pos = (float) .2 * canvas_height;
        float y_bottom_pos = (float) .85 * canvas_height;

        // 经验值显示的画布范围
        float y_range = (float) ((y_bottom_pos - y_top_pos) * .8);

        // 刻度线 Y轴位置, 在X轴上方30像素
        float y_mark_pos = y_bottom_pos - 30;

        // 星期线 Y轴位置, 在X轴下方50像素
        float y_week_pos = y_bottom_pos + 50;

        // 日期线 Y轴位置, 在X轴上方80像素
        float y_date_pos = y_bottom_pos + 80;

        // 画出底部X轴
        canvas.drawLine(0, y_bottom_pos, canvas_width, y_bottom_pos, paint);

        // 画出顶部X轴
        canvas.drawLine(0, top_pos, canvas_width, top_pos, paint);

        // 控制刻度线粗细
        paint.setStrokeWidth(7);

        // 将X轴分成6等分，画出5条刻度线
        for (int i = 0; i <= vectorLength - 2; i++) {
            int x_pos = (int) (((i + 1) * canvas_width / vectorLength));

            DayExpInfo weekday = logs.get(i);

            // 显示刻度条
            paint.setColor(Color.BLACK);
            canvas.drawLine(x_pos, y_bottom_pos,
                x_pos, y_mark_pos, paint);


            // 显示刻度数字
            canvas.drawText("" + (i + 1), x_pos,
                    y_bottom_pos + 20, paint);

            // 显示星期
            canvas.drawText("" + weekday.day_of_week_str, x_pos,
                    y_week_pos, paint);


            // 显示日期
            canvas.drawText("" + weekday.day_of_month_str, x_pos,
                    y_date_pos, paint);


            // 经验值在画布 Y轴范围, 为了不碰到最上面的线，所以多加 10
            int yExp = to_pixel(y_range, miny, maxy, yvalues[i]);


            // 输出折线
            if (i < 4) {
                paint.setColor(Color.parseColor("#666666"));
                int xNextPos = (int) (((i + 2) * canvas_width / vectorLength));

                int yNextExp = to_pixel(y_range, miny, maxy, yvalues[i + 1]);
                canvas.drawLine(x_pos, yExp + y_top_pos,
                        xNextPos, yNextExp + y_top_pos, paint);
            }

            Log.d("------", "-----");
            Log.d("exp_num", weekday.exp_num + "");
            Log.d("miny = ", miny + "");
            Log.d("maxy = ", maxy + "");
            Log.d("yExp = ", yExp + "");
            Log.d("------", "-----");


            // 输出经验值圆圈
            paint.setColor(Color.parseColor("#333333"));
            canvas.drawCircle(x_pos, yExp + y_top_pos, 20, paint);

        }

    }


    private void get_axes(float[] yvalues) {
        // 比例范围是从经验值里取最大值跟最小值分别加10 跟 减10
        miny = get_min(yvalues) - 10;
        maxy = get_max(yvalues) + 10;
    }


    private int to_pixel(float pixels, float min, float max, float value) {
        double p;
        int pint;

        p = ((value - min) / (max - min)) * pixels;

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
        get_axes(yvalues);
        invalidate();
        requestLayout();
    }


    public void run_animation() {
        float prev_exp_pos = yvalues[vectorLength - 2];
        float current_exp_pos = prev_exp_pos - dynamic_exp;

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