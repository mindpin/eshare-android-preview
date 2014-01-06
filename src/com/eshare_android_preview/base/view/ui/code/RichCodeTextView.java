package com.eshare_android_preview.base.view.ui.code;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.BoringLayout;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.eshare_android_preview.base.utils.BaseUtils;
import com.google.gson.Gson;

import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Administrator on 14-1-3.
 */
public class RichCodeTextView extends TextView {

    private ColorTheme theme;

    public RichCodeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        theme = new TwilightTheme();

        setBackgroundColor(Color.parseColor("#000000"));
        setTextColor(Color.parseColor("#ffffff"));
        setTypeface(Typeface.MONOSPACE);

        List<List<String>> list = get_json();

        for (List<String> l : list) {
            String kind = l.get(0);
            String text = l.get(1);
            SpannableString ss = theme.get_string(kind, text);
            append(ss);
        }
    }

    private List<List<String>> get_json() {
        try {
            AssetManager am = getContext().getAssets();
            InputStream is = am.open("code.json");
            String json = BaseUtils.convert_stream_to_string(is);

            Gson gson = new Gson();
            return gson.fromJson(json, List.class);
        } catch (Exception e) {
            return null;
        }
    }

    interface ColorTheme {
        public SpannableString get_string(String kind, String text);
    }
}
