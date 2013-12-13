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


public class ExperienceChartView extends View {

    private Paint paint;
    private float[] yvalues = new float[5];
    private float maxy = 0.0f, miny = 0.0f, vectorLength = 0.0f;


    // 日期信息
    private List<DayExpInfo> logs = ExperienceLog.history_info();


    public ExperienceChartView(Context context) {
        super(context);
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
        this.vectorLength = 6;
        this.yvalues = getYvalues();

        paint = new Paint();

        getAxes(yvalues);
    }

    private float[] getYvalues() {
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
            int yExp = toPixel(yRange, miny, maxy, weekday.exp_num);


            // 输出折线
            if (i < 4) {
                DayExpInfo nextWeekday = logs.get(i + 1);
                paint.setColor(Color.GREEN);
                int xNextPos = (int) (((i + 2) * canvasWidth / vectorLength));

                int yNextExp = toPixel(yRange, miny, maxy, nextWeekday.exp_num);
                canvas.drawLine(xPos, yExp + yTopPos,
                        xNextPos, yNextExp + yTopPos, paint);
            }

            Log.d("------", "-----");
            Log.d("exp_num", weekday.exp_num + "");
            Log.d("miny = ", miny + "");
            Log.d("maxy = ", maxy + "");
            Log.d("yExp = ", yExp + "");
            Log.d("------", "-----");

            paint.setColor(Color.RED);
            canvas.drawCircle(xPos, yExp + yTopPos, 10, paint);

        }

    }


    private void getAxes(float[] yvalues) {

        miny = getMin(yvalues);
        maxy = getMax(yvalues);

    }


    private int toPixel(float pixels, float min, float max, float value) {
        double p;
        int pint;

        p = ((value - min) / (max - min)) * .8 * pixels;

        pint = (int) p;

        return (pint);
    }

    private float getMax(float[] v) {
        float largest = v[0];
        for (int i = 0; i < v.length; i++)
            if (v[i] > largest)
                largest = v[i];
        return largest;
    }

    private float getMin(float[] v) {
        float smallest = v[0];
        for (int i = 0; i < v.length; i++)
            if (v[i] < smallest)
                smallest = v[i];
        return smallest;
    }

}