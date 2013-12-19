package com.eshare_android_preview.base.view.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 13-12-17.
 */
public class CircleIconView extends RelativeLayout {
    private boolean is_on_init = true;
    private int height, width;

    private int bg_color, text_color, text_size;
    private String text;

    private TextView text_view;

    public CircleIconView(Context context) {
        super(context);
    }

    public CircleIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        add_text(context, attrs);
    }

    private void add_text(Context context, AttributeSet attrs) {
        init_params(attrs);
        init_text_view(context);
        setWillNotDraw(false);
    }

    // 用程序构建时调用此方法
    public void init(String bg_color_s, String text_color_s, int text_size_ipt, int text_id) {
        bg_color = Color.parseColor(bg_color_s);
        text_color = Color.parseColor(text_color_s);
        text_size = text_size_ipt;
        text = getContext().getResources().getString(text_id);

        init_text_view(getContext());
        setWillNotDraw(false);
    }

    private void init_params(AttributeSet attrs) {
        bg_color = Color.parseColor(attrs.getAttributeValue(null, "bg_color"));
        text_color = Color.parseColor(attrs.getAttributeValue(null, "text_color"));
        text_size = attrs.getAttributeIntValue(null, "text_size", 20);
        text = attrs.getAttributeValue(null, "text");
    }

    private void init_text_view(Context context) {
        text_view = new TextView(context);

        if (!isInEditMode()) {
            Typeface fontawesome_font = Typeface.createFromAsset(context.getAssets(), "fonts/fontawesome-webfont.ttf");
            text_view.setTypeface(fontawesome_font);
        }

        text_view.setText(text);
        text_view.setTextSize(text_size);
        text_view.setTextColor(text_color);
        text_view.setGravity(Gravity.CENTER);

        LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        text_view.setLayoutParams(lp);

        addView(text_view);
    }

    private void init() {
        if (is_on_init) {
            is_on_init = false;

            height = getHeight();
            width  = getWidth();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        init();

        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(bg_color);
        p.setStyle(Paint.Style.FILL);

        canvas.drawCircle(width / 2, height / 2, width / 2, p);
    }

    public void set_text_color(String color) {
        text_view.setTextColor(Color.parseColor(color));
    }
}
