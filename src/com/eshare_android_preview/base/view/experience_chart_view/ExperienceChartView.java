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
            locxAxis = 0.0f, locyAxis = 0.0f;
    private int vectorLength;


    public ExperienceChartView(Context context, float[] xvalues, float[] yvalues) {
        super(context);
        this.xvalues = xvalues;
        this.yvalues = yvalues;
        vectorLength = xvalues.length;
        paint = new Paint();

        getAxes(xvalues, yvalues);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        float canvasHeight = getHeight();
        float canvasWidth = getWidth();
        int[] xvaluesInPixels = toPixel(canvasWidth, minx, maxx, xvalues);
        int[] yvaluesInPixels = toPixel(canvasHeight, miny, maxy, yvalues);
        int locxAxisInPixels = toPixelInt(canvasHeight, miny, maxy, locxAxis);
        int locyAxisInPixels = toPixelInt(canvasWidth, minx, maxx, locyAxis);

        paint.setStrokeWidth(2);
        int grey = 200;
        canvas.drawARGB(255, grey, grey, grey);

        // 输出折线
//        for (int i = 0; i < vectorLength - 1; i++) {
//            paint.setColor(Color.RED);
//            canvas.drawLine(xvaluesInPixels[i], canvasHeight
//                    - yvaluesInPixels[i], xvaluesInPixels[i + 1], canvasHeight
//                    - yvaluesInPixels[i + 1], paint);
//        }

        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(20.0f);

        // 画出X轴
        canvas.drawLine(0, canvasHeight - locxAxisInPixels, canvasWidth,
                canvasHeight - locxAxisInPixels, paint);


        // 控制刻度线粗细
        paint.setStrokeWidth(7);

        List<DayExpInfo> logs = ExperienceLog.history_info();



        // 将X轴分成6等分，画出5条刻度线
        for (int i = 0; i <= 4; i++) {
            int xPosition = (int) (((i + 1) * canvasWidth / 6));
            float yPosition = canvasHeight - locxAxisInPixels;

            Log.d("position = ", xPosition + "");
            Log.d("canvasWidth = ", canvasWidth + "");

            DayExpInfo weekday = logs.get(i);
            Log.d("date =",  weekday.day_of_week_str);


            // 显示刻度条
            canvas.drawLine(xPosition, yPosition,
                xPosition, yPosition - 50, paint);


            // 显示刻度数字
            canvas.drawText("" + (i + 1), xPosition,
                    canvasHeight - locxAxisInPixels + 20, paint);

            // 显示星期
            canvas.drawText("" + weekday.day_of_week_str, xPosition,
                    canvasHeight - locxAxisInPixels + 50, paint);


            // 显示日期
            canvas.drawText("" + weekday.day_of_month_str, xPosition,
                    canvasHeight - locxAxisInPixels + 80, paint);
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