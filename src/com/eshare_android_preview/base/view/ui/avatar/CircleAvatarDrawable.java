package com.eshare_android_preview.base.view.ui.avatar;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 13-12-27.
 */
public class CircleAvatarDrawable extends Drawable {
    private final Bitmap bitmap;
    private final Paint paint;
    private final RectF rectf;
    private final int bitmap_width;
    private final int bitmap_height;

    public CircleAvatarDrawable(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.rectf = new RectF();

        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setDither(true);

        final BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);

        bitmap_width = bitmap.getWidth();
        bitmap_height = bitmap.getHeight();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawOval(rectf, paint);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);

        rectf.set(bounds);
    }

    @Override
    public void setAlpha(int alpha) {
        if (paint.getAlpha() != alpha) {
            paint.setAlpha(alpha);
            invalidateSelf();
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return bitmap_width;
    }

    @Override
    public int getIntrinsicHeight() {
        return bitmap_height;
    }

    public void setAntiAlias(boolean aa) {
        paint.setAntiAlias(aa);
        invalidateSelf();
    }

    @Override
    public void setFilterBitmap(boolean filter) {
        paint.setFilterBitmap(filter);
        invalidateSelf();
    }

    @Override
    public void setDither(boolean dither) {
        paint.setDither(dither);
        invalidateSelf();
    }

    public Bitmap get_bitmap() {
        return bitmap;
    }
}
