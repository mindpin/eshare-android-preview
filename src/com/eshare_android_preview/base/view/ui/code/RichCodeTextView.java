package com.eshare_android_preview.base.view.ui.code;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.widget.TextView;

import com.eshare_android_preview.base.utils.BaseUtils;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 14-1-3.
 */
public class RichCodeTextView extends TextView {

    private ColorTheme theme;

    public RichCodeTextView(Context context) {
        super(context);
        init();
    }

    public RichCodeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        if (isInEditMode()) return;
        theme = new TwilightTheme();
        setBackgroundColor(Color.parseColor("#2b2b2b"));
        setTextColor(Color.parseColor("#ffffff"));
        setTypeface(Typeface.MONOSPACE);
    }

    public void set_content(List<List<String>> list) {
        for (List<String> l : list) {
            String kind  = l.get(0);
            String text  = l.get(1);
            String block = l.size() > 2 ? l.get(2) : null;
            SpannableString ss = theme.get_string(kind, text, block);
            append(ss);
        }
    }

    interface ColorTheme {
        public SpannableString get_string(String kind, String text, String block);
    }
}
