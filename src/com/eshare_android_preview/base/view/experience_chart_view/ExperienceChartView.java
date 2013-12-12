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

import java.util.ArrayList;
import java.util.List;


public class ExperienceChartView extends View {

    private Paint paint;
    private float[] xvalues = { 0.0f };
    private float[] yvalues = { 0.0f };
    private float maxx = 0.0f, maxy = 0.0f, minx = 0.0f, miny = 0.0f,
            locxAxis = 0.0f, locyAxis = 0.0f,
            yBottomPosition = 0.0f, yTopPosition = 0.0f;
    private int vectorLength;


    public ExperienceChartView(Context context, float[] xvalues, float[] yvalues) {
        super(context);
        this.xvalues = xvalues;
        this.yvalues = yvalues;
        vectorLength = yvalues.length;
        paint = new Paint();

        getAxes(xvalues, yvalues);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        float canvasHeight = getHeight();
        float canvasWidth = getWidth();

        int[] xvaluesInPixels = toPixel(canvasWidth, minx, maxx, xvalues);
        int locxAxisInPixels = toPixelInt(canvasHeight, miny, maxy, locxAxis);
        int locyAxisInPixels = toPixelInt(canvasWidth, minx, maxx, locyAxis);


        float yTopPosition = (float) .2 * canvasHeight;
        float yBottomPosition = canvasHeight - locxAxisInPixels;

        paint.setStrokeWidth(2);
        int grey = 200;
        canvas.drawARGB(255, grey, grey, grey);

        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(20.0f);

        // 画出底部X轴
        canvas.drawLine(0, yBottomPosition, canvasWidth, yBottomPosition, paint);

        // 画出顶部X轴
        canvas.drawLine(0, yTopPosition, canvasWidth, yTopPosition, paint);

        // 控制刻度线粗细
        paint.setStrokeWidth(7);

        List<DayExpInfo> logs = ExperienceLog.history_info();

        // 将X轴分成6等分，画出5条刻度线
        for (int i = 0; i <= 4; i++) {
            int xPosition = (int) (((i + 1) * canvasWidth / 6));

            Log.d("position = ", xPosition + "");
            Log.d("canvasWidth = ", canvasWidth + "");

            DayExpInfo weekday = logs.get(i);
            Log.d("date =",  weekday.day_of_week_str);


            // 显示刻度条
            paint.setColor(Color.BLACK);
            canvas.drawLine(xPosition, yBottomPosition,
                xPosition, yBottomPosition - 50, paint);


            // 显示刻度数字
            canvas.drawText("" + (i + 1), xPosition,
                    yBottomPosition + 20, paint);

            // 显示星期
            canvas.drawText("" + weekday.day_of_week_str, xPosition,
                    yBottomPosition + 50, paint);


            // 显示日期
            canvas.drawText("" + weekday.day_of_month_str, xPosition,
                    yBottomPosition + 80, paint);


            // 画出经验值
            Log.d("iii = ", i + "");
            Log.d("exp = ", yvalues[i] + "");
            paint.setColor(Color.RED);
            int yExp = toHeightPixel(yBottomPosition - yTopPosition, miny, maxy, yvalues[i]);
            canvas.drawCircle(xPosition, yExp + yTopPosition, 10, paint);

            // 输出折线
            paint.setColor(Color.GREEN);
            int xPosition2 = (int) (((i + 2) * canvasWidth / 6));
            int yExp2 = toHeightPixel(yBottomPosition - yTopPosition, miny, maxy, yvalues[i + 1]);
            canvas.drawLine(xPosition, yExp + yTopPosition,
                    xPosition2, yExp2 + yTopPosition, paint);
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