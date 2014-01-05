package com.eshare_android_preview.base.view.ui;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
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

    public RichCodeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        setBackgroundColor(Color.parseColor("#000000"));
        setTextColor(Color.parseColor("#ffffff"));
        setTypeface(Typeface.MONOSPACE);

        List<List<String>> list = get_json();

        String text = "";
        for (List<String> l : list) {
            text = text + l.get(1);
        }

//        CharSequence cs = Html.fromHtml(
//                json,
//                null,
//                new Html.TagHandler() {
//                    @Override
//                    public void handleTag(boolean opening, String tag, Editable output, XMLReader xml_reader) {
//                        try {
//                            if ("span".equalsIgnoreCase(tag)) {
//                                if (opening) {
//                                    startspan(tag, output, xml_reader);
//                                } else {
//                                    endspan(tag, output, xml_reader);
//                                }
//                            }
//                        } catch (Exception e) {
//
//                        }
//                    }
//                }
//        );

        setText(text);
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

    private int start_index = 0;
    private int stop_index = 0;
    private int color = Color.parseColor("#ffffff");

    private void startspan(String tag, Editable output, XMLReader xml_reader) {
        start_index = output.length();

        try {
            Field elementField = xml_reader.getClass().getDeclaredField("theNewElement");
            elementField.setAccessible(true);
            Object element = elementField.get(xml_reader);
            Field attsField = element.getClass().getDeclaredField("theAtts");
            attsField.setAccessible(true);
            Object atts = attsField.get(element);
            Field dataField = atts.getClass().getDeclaredField("data");
            dataField.setAccessible(true);
            String[] data = (String[])dataField.get(atts);
            Field lengthField = atts.getClass().getDeclaredField("length");
            lengthField.setAccessible(true);
            int len = (Integer)lengthField.get(atts);

            String klass = null;

            for(int i = 0; i < len; i++) {
                if("class".equals(data[i * 5 + 1])) {
                    klass = data[i * 5 + 4];
                }
            }

            if ("keyword".equals(klass)) {
                color = Color.parseColor("#CDA869");
            } else if ("string".equals(klass)) {
                color = Color.parseColor("#8F9D6A");
            } else if ("delimiter".equals(klass)) {
                color = Color.parseColor("#7E8C59");
            } else if ("content".equals(klass)) {
                color = Color.parseColor("#8F9D6A");
            } else {
                color = Color.parseColor("#ffffff");
            }
        } catch (Exception e) {
            color = Color.parseColor("#ffffff");
        }
    }

    private void endspan(String tag, Editable output, XMLReader xml_reader) {
        stop_index = output.length();

        output.setSpan(
                new ForegroundColorSpan(color),
                start_index, stop_index,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        output.setSpan(
                new StyleSpan(Typeface.BOLD),
                start_index, stop_index,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
    }
}
