package com.eshare_android_preview.base.view.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 13-12-27.
 */
public class CircleImageView extends View {

    public CircleImageView(Context context) {
        super(context);
        init();
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private Bitmap bitmap;
    private Rect bitmap_rect = new Rect();
    private PaintFlagsDrawFilter pdf = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    private Paint paint = new Paint();

    {
        paint.setStyle(Paint.Style.STROKE);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
    }

    private Bitmap mdstb = null;
    private PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY);

    private void init() {
    }

    public void set_image_bitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    private Bitmap make_dst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.parseColor("#ffffffff"));
        c.drawOval(new RectF(0, 0, w, h), p);
        return bm;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (null == bitmap) return;

        int w = getWidth();
        int h = getHeight();

        if (null == mdstb) {
            mdstb = make_dst(w, h);
        }

        bitmap_rect.set(0, 0, w, h);
        canvas.save();
        canvas.setDrawFilter(pdf);
        canvas.drawBitmap(mdstb, 0, 0, paint);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(bitmap, null, bitmap_rect, paint);
        paint.setXfermode(null);
        canvas.restore();
    }
}
