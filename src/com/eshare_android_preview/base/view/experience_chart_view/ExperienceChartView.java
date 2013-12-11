package com.eshare_android_preview.base.view.experience_chart_view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;



public class ExperienceChartView extends View {

    private Paint paint;
    private float[] xvalues = { 0.0f };
    private float[] yvalues = { 0.0f };
    private float maxx = 0.0f, maxy = 0.0f, minx = 0.0f, miny = 0.0f,
            locxAxis = 0.0f, locyAxis = 0.0f;
    private int vectorLength;
    private int axes = 1;


    public ExperienceChartView(Context context, float[] xvalues, float[] yvalues, int axes) {
        super(context);
        this.xvalues = xvalues;
        this.yvalues = yvalues;
        this.axes = axes;
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
        canvas.drawLine(0, canvasHeight - locxAxisInPixels, canvasWidth,
                canvasHeight - locxAxisInPixels, paint);
        canvas.drawLine(locyAxisInPixels, 0, locyAxisInPixels, canvasHeight,
                paint);



        // Automatic axes markings, modify n to control the number of axes
        // labels


        if (axes != 0) {
            float temp = 0.0f;
            int n = 3;
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(20.0f);
            for (int i = 1; i <= 6; i++) {
                // temp = Math.round(10 * (minx + (i - 1) * (maxx - minx) / n)) / 10;
                temp = (float) i;

                canvas.drawText("" + (int) temp,
                        (float) toPixelInt(canvasWidth, minx, maxx, temp),
                        canvasHeight - locxAxisInPixels + 20, paint);
            }

//            for (int i = 1; i <= 8; i++) {
//
//                temp = Math.round(10 * (miny + (i - 1) * (maxy - miny) / n)) / 10;
//                canvas.drawText("" + temp, locyAxisInPixels + 20, canvasHeight
//                        - (float) toPixelInt(canvasHeight, miny, maxy, temp),
//                        paint);
//            }


            canvas.drawText("" + maxx,
                    (float) toPixelInt(canvasWidth, minx, maxx, maxx),
                    canvasHeight - locxAxisInPixels + 20, paint);
            canvas.drawText("" + maxy, locyAxisInPixels + 20, canvasHeight
                    - (float) toPixelInt(canvasHeight, miny, maxy, maxy), paint);
        }

    }


    private int[] toPixel(float pixels, float min, float max, float[] value) {
        double[] p = new double[value.length];
        int[] pint = new int[value.length];

        for (int i = 0; i < value.length; i++) {
            p[i] = ((value[i] - min) / (max - min)) *  pixels + .1 * pixels;
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

        p = ((value - min) / (max - min)) * pixels + .1 * pixels;
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