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

import com.eshare_android_preview.R;

/**
 * Created by Administrator on 13-12-17.
 */
public class CircleIconView extends RelativeLayout {
    private boolean is_on_init = true;
    private int height, width;

    private int bg_color, text_color, text_size;
    private String text;

    public CircleIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        add_text(context, attrs);
    }

    private void add_text(Context context, AttributeSet attrs) {
        bg_color = Color.parseColor(attrs.getAttributeValue(null, "bg_color"));
        text_color = Color.parseColor(attrs.getAttributeValue(null, "text_color"));
        text_size = attrs.getAttributeIntValue(null, "text_size", 20);
        text = attrs.getAttributeValue(null, "text");

        TextView tv = new TextView(context);

        if (!isInEditMode()) {
            Typeface fontawesome_font = Typeface.createFromAsset(context.getAssets(), "fonts/fontawesome-webfont.ttf");
            tv.setTypeface(fontawesome_font);
        }

        tv.setText(text);
        tv.setTextSize(text_size);
        tv.setTextColor(text_color);
        tv.setGravity(Gravity.CENTER);

        LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        tv.setLayoutParams(lp);

        addView(tv);

        setWillNotDraw(false);
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
}
