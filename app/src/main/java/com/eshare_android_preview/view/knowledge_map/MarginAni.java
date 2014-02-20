package com.eshare_android_preview.view.knowledge_map;

import android.view.View;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by Administrator on 13-12-13.
 */
public class MarginAni {
    private String name;
    private View view;
    private float x1, y1, x2, y2;

    private PropertyValuesHolder x_holder, y_holder, reverse_x_holder, reverse_y_holder;

    // 注意，传进来的值都是 px 值
    public MarginAni(String name, View view, float x1, float x2, float y1, float y2) {
        this.name = name;
        this.view = view;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;

        this.x_holder = PropertyValuesHolder.ofFloat(name + "x", x1, x2);
        this.y_holder = PropertyValuesHolder.ofFloat(name + "y", y1, y2);

        this.reverse_x_holder = PropertyValuesHolder.ofFloat(name + "x", x2, x1);
        this.reverse_y_holder = PropertyValuesHolder.ofFloat(name + "y", y2, y1);
    }

    public PropertyValuesHolder get_x_values_holder() {
        return x_holder;
    }
    public PropertyValuesHolder get_y_values_holder() { return y_holder; }

    public PropertyValuesHolder get_reverse_x_values_holder() {return reverse_x_holder; }
    public PropertyValuesHolder get_reverse_y_values_holder() {return reverse_y_holder; }


    public void update_y(ValueAnimator valueAnimator) {
        float new_y = (Float) valueAnimator.getAnimatedValue(name + "y");

        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) view.getLayoutParams();
        p.topMargin = (int) new_y;
        view.setLayoutParams(p);
    }

    public void update_xy(ValueAnimator valueAnimator) {
        float new_x = (Float) valueAnimator.getAnimatedValue(name + "x");
        float new_y = (Float) valueAnimator.getAnimatedValue(name + "y");

        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) view.getLayoutParams();
        p.leftMargin = (int) new_x;
        p.topMargin = (int) new_y;
        view.setLayoutParams(p);
    }
}
