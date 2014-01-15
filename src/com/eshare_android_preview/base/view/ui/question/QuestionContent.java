package com.eshare_android_preview.base.view.ui.question;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
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
    public Context context;

    public List<View> view_list;
    public List<QuestionFill> fill_list;

    public QuestionContent(Context context, Question question) {
        this.context = context;
        this.view_list = new ArrayList<View>();
        this.fill_list = new ArrayList<QuestionFill>();

        for (Question.ContentToken token : question.content) {
            Item item = new Item();
            item.type = token.type;
            item.data = token.data;

            ViewPack vp = item.get_pack(context);

            if (null != vp.view) {
                view_list.add(vp.view);
            }

            if (null != vp.fill_list) {
                for (QuestionFill qf : vp.fill_list) {
                    qf.text_view = (TextView) vp.view;
                    fill_list.add(qf);
                }
            }
        }

        int i = 0;
        for (QuestionFill qf : fill_list) {
            i++;
            qf.index = i;
        }
    }

    class Item {
        String type;
        HashMap<String, Object> data;

        public ViewPack get_pack(Context context) {
            if ("text".equals(type)) return get_text_pack(context);
            if ("code".equals(type)) return get_code_pack(context);
            if ("image".equals(type)) return get_image_pack(context);
            return null;
        }

        private ViewPack get_text_pack(Context context) {
            TextView tv = new TextView(context);

            String content = (String) data.get("content");
            QuestionFill.Pack p = QuestionFill.get_text_include_fills(content);
            tv.setText(p.string_builder);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            lp.bottomMargin = BaseUtils.dp_to_px(10);
            tv.setLayoutParams(lp);
            tv.setTextSize(18);
            tv.setTypeface(Typeface.MONOSPACE);

            return new ViewPack(tv, p.fill_list);
        }

        private ViewPack get_code_pack(Context context) {
            RichCodeTextView rtv = new RichCodeTextView(context);
            List<List<String>> list = (List<List<String>>) data.get("content");
            List<QuestionFill> fill_list = rtv.set_content(list);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            lp.bottomMargin = BaseUtils.dp_to_px(10);
            rtv.setLayoutParams(lp);
            int p = BaseUtils.dp_to_px(5);
            rtv.setPadding(p, p, p, p);
            rtv.setTextSize(14);

            return new ViewPack(rtv, fill_list);
        }

        private ViewPack get_image_pack(final Context context) {
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

            return new ViewPack(iv, null);
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

    class ViewPack {
        View view;
        List<QuestionFill> fill_list;

        ViewPack(View view, List<QuestionFill> fill_list) {
            this.view = view;
            this.fill_list = fill_list;
        }
    }
}
