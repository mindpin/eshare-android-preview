package com.eshare_android_preview.base.view.experience_chart_view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import com.eshare_android_preview.model.DayExpInfo;
import com.eshare_android_preview.model.elog.ExperienceLog;
import java.util.List;


public class ExperienceChartView extends View {

    private Paint paint;
    private float[] xvalues = { 0.0f };
    private float[] yvalues = { 0.0f };
    private float maxx = 0.0f, maxy = 0.0f, minx = 0.0f, miny = 0.0f,
            locxAxis = 0.0f, locyAxis = 0.0f,
            vectorLength = 0.0f;


    public ExperienceChartView(Context context, float[] xvalues, float[] yvalues) {
        super(context);
        this.xvalues = xvalues;
        this.yvalues = yvalues;
        this.vectorLength = xvalues.length;
        paint = new Paint();

        getAxes(xvalues, yvalues);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        float canvasHeight = getHeight();
        float canvasWidth = getWidth();

        int yAxisInPixels = toPixelInt(canvasHeight, miny, maxy, locyAxis);

        float yTopPos = (float) .2 * canvasHeight;
        float yBottomPos = canvasHeight - yAxisInPixels;

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

        // 日期信息
        List<DayExpInfo> logs = ExperienceLog.history_info();

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
            paint.setColor(Color.RED);

            // 经验值在画布 Y轴范围
            float experienceScale = yBottomPos - yTopPos;

            int yExp = toHeightPixel(experienceScale, miny, maxy, weekday.exp_num);
            canvas.drawCircle(xPos, yExp + yTopPos, 10, paint);

            // 输出折线
            if (i < 4) {
                DayExpInfo nextWeekday = logs.get(i + 1);
                paint.setColor(Color.GREEN);
                int xNextPos = (int) (((i + 2) * canvasWidth / vectorLength));
                int yNextExp = toHeightPixel(experienceScale, miny, maxy, nextWeekday.exp_num);
                canvas.drawLine(xPos, yExp + yTopPos,
                        xNextPos, yNextExp + yTopPos, paint);
            }

        }

    }


    private int[] toPixel(float pixels, float min, float max, float[] value) {
        double[] p = new double[value.length];
        int[] pint = new int[value.length];

        for (int i = 0; i < value.length; i++) {
            p[i] = ((value[i] - min) / (max - min)) *  pixels + .2 * pixels;
            pint[i] = (int) p[i];
        }

        return (pint);
    }

    private void getAxes(float[] xvalues, float[] yvalues) {

        minx = getMin(xvalues);
        miny = getMin(yvalues);
        maxx = getMax(xvalues);
        maxy = getMax(yvalues);

    }

    private int toPixelInt(float pixels, float min, float max, float value) {
        double p;
        int pint;

        p = ((value - min) / (max - min)) * pixels + .2 * pixels;
        pint = (int) p;

        return (pint);
    }

    private int toHeightPixel(float pixels, float min, float max, float value) {
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