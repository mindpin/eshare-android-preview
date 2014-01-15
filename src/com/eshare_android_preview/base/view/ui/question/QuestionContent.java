package com.eshare_android_preview.base.view.ui.question;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.view.ui.code.RichCodeTextView;
import com.eshare_android_preview.http.model.Question;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 14-1-8.
 */
public class QuestionContent {
    public List<Item> list;
    public List<QuestionFill> fill_list;

    public QuestionContent(Question question) {
        list = new ArrayList<Item>();
        for (Question.ContentToken token : question.content) {
            Item item = new Item();
            item.type = token.type;
            item.data = token.data;
            list.add(item);
        }

        fill_list = new ArrayList<QuestionFill>();
    }

    class Item {
        String type;
        HashMap<String, Object> data;

        public View get_view(Context context) {
            if ("text".equals(type)) return get_text_view(context);
            if ("code".equals(type)) return get_code_view(context);
            if ("image".equals(type)) return get_image_view(context);
            return null;
        }

        private View get_text_view(Context context) {
            TextView tv = new TextView(context);

            String content = (String) data.get("content");
            SpannableStringBuilder ssb = QuestionFill.get_text_include_fills(content);
            tv.setText(ssb);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            lp.bottomMargin = BaseUtils.dp_to_px(10);
            tv.setLayoutParams(lp);
            tv.setTextSize(18);
            tv.setTypeface(Typeface.MONOSPACE);

            return tv;
        }

        private View get_code_view(Context context) {
            RichCodeTextView rtv = new RichCodeTextView(context);
            List<List<String>> list = (List<List<String>>) data.get("content");
            rtv.set_content(list);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            lp.bottomMargin = BaseUtils.dp_to_px(10);
            rtv.setLayoutParams(lp);
            int p = BaseUtils.dp_to_px(5);
            rtv.setPadding(p, p, p, p);
            rtv.setTextSize(14);

            return rtv;
        }

        private View get_image_view(final Context context) {
            final ImageView iv = new ImageView(context);
            iv.setBackgroundColor(Color.parseColor("#0000ff"));

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            lp.bottomMargin = BaseUtils.dp_to_px(10);
            iv.setLayoutParams(lp);


            String url = (String) data.get("url");
            ImageLoader.getInstance().loadImage(url, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    int w = BaseUtils.get_screen_size().width_px / 3;
                    Bitmap b = resize_bitmap_in_box(loadedImage, w, w);
                    iv.setImageBitmap(b);

                    iv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.image_border));
                }
            });
            return iv;
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
    }
}
