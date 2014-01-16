package com.eshare_android_preview.view.ui.question.builder;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.eshare_android_preview.R;
import com.eshare_android_preview.http.i.question.IQuestion;
import com.eshare_android_preview.utils.BaseUtils;
import com.eshare_android_preview.view.ui.question.QuestionFillSpan;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

/**
 * Created by Administrator on 14-1-15.
 */
public class QuImageView extends ImageView implements IQuItemView {
    public QuImageView(Context context, IQuestion.ContentToken token) {
        super(context);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        lp.bottomMargin = BaseUtils.dp_to_px(10);
        setLayoutParams(lp);

        String url = (String) token.data.get("url");
        ImageLoader.getInstance().loadImage(url, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                int w = BaseUtils.get_screen_size().width_px / 2;
                Bitmap b = resize_bitmap_in_box(loadedImage, w, w);
                setImageBitmap(b);
                setBackgroundDrawable(getResources().getDrawable(R.drawable.image_border));
            }
        });
    }

    private Bitmap resize_bitmap_in_box(Bitmap bitmap, int w, int h) {
        int ow = bitmap.getWidth();
        int oh = bitmap.getHeight();

        int rw = ow;
        int rh = oh;

        if (rw > w) {
            rw = w;
            rh = rw * oh / ow;
        }

        if (rh > h) {
            rh = h;
            rw = rh * ow / oh;
        }

        return Bitmap.createScaledBitmap(bitmap, rw, rh, true);
    }

    @Override
    public QuestionFillSpan[] spans() {
        return new QuestionFillSpan[]{};
    }
}
