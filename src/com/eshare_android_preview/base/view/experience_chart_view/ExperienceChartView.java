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
        paint.setAntiAlias(true);

        getAxes(yvalues);
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

        float canvasHeight = getHeight();
        float canvasWidth = getWidth();

        float yTopPos = (float) .2 * canvasHeight;
        float yBottomPos = (float) .8 * canvasHeight;

        // 经验值显示的画布范围
        float yRange = yBottomPos - yTopPos;

        // 刻度线 Y轴位置
        float yMarkPos = yBottomPos - 50;

        // 星期线 Y轴位置
        float yWeekPos = yBottomPos + 50;

        // 日期线 Y轴位置
        float yDatePos = yBottomPos + 80;

        paint.setStrokeWidth(2);
        int grey = 200;
        canvas.drawARGB(255, grey, grey, grey);

        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(20.0f);

        // 画出底部X轴
        canvas.drawLine(0, yBottomPos, canvasWidth, yBottomPos, paint);

        // 画出顶部X轴
        canvas.drawLine(0, yTopPos, canvasWidth, yTopPos, paint);

        // 控制刻度线粗细
        paint.setStrokeWidth(7);



        // 将X轴分成6等分，画出5条刻度线
        for (int i = 0; i <= vectorLength - 2; i++) {
            int xPos = (int) (((i + 1) * canvasWidth / vectorLength));

            Log.d("position = ", xPos + "");
            Log.d("canvasWidth = ", canvasWidth + "");

            DayExpInfo weekday = logs.get(i);
            Log.d("date =",  weekday.day_of_week_str);


            // 显示刻度条
            paint.setColor(Color.BLACK);
            canvas.drawLine(xPos, yBottomPos,
                xPos, yMarkPos, paint);


            // 显示刻度数字
            canvas.drawText("" + (i + 1), xPos,
                    yBottomPos + 20, paint);

            // 显示星期
            canvas.drawText("" + weekday.day_of_week_str, xPos,
                    yWeekPos, paint);


            // 显示日期
            canvas.drawText("" + weekday.day_of_month_str, xPos,
                    yDatePos, paint);


            // 画出经验值
            Log.d("iii = ", i + "");
            Log.d("exp = ", weekday.exp_num + "");

            // 经验值在画布 Y轴范围
            yTopPos = yTopPos + 5;
            int yExp = to_pixel(yRange, miny, maxy, yvalues[i]);


            // 输出折线
            if (i < 4) {
                paint.setColor(Color.parseColor("#666666"));
                int xNextPos = (int) (((i + 2) * canvasWidth / vectorLength));

                int yNextExp = to_pixel(yRange, miny, maxy, yvalues[i + 1]);
                canvas.drawLine(xPos, yExp + yTopPos,
                        xNextPos, yNextExp + yTopPos, paint);
            }

            Log.d("------", "-----");
            Log.d("exp_num", weekday.exp_num + "");
            Log.d("miny = ", miny + "");
            Log.d("maxy = ", maxy + "");
            Log.d("yExp = ", yExp + "");
            Log.d("------", "-----");

            // 输出经验值圆圈
            paint.setColor(Color.parseColor("#333333"));
            canvas.drawCircle(xPos, yExp + yTopPos, 30, paint);

        }

    }


    private void getAxes(float[] yvalues) {

        miny = get_min(yvalues);
        maxy = get_max(yvalues);

    }


    private int to_pixel(float pixels, float min, float max, float value) {
        double p;
        int pint;

        p = ((value - min) / (max - min)) * .8 * pixels;

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
        getAxes(yvalues);
        invalidate();
        requestLayout();
    }


    public void run_animation() {
        final int length = vectorLength - 2;
        float prev_exp_pos = yvalues[length];
        float current_exp_pos = yvalues[length] - dynamic_exp;

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