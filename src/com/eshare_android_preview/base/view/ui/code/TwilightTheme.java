package com.eshare_android_preview.base.view.ui.code;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.eshare_android_preview.base.view.ui.question.QuestionFill;

import java.util.HashMap;

/**
 * Created by Administrator on 14-1-6.
 */
public class TwilightTheme implements RichCodeTextView.ColorTheme {
    HashMap<String, TextStyle> map;

    public TwilightTheme() {
        map = new HashMap<String, TextStyle>();

        put("string.delimiter",     "#8F9D6A");
        put("string.content",       "#8F9D6A");
        put("keyword",              "#CDA869", true);
        put("class",                "#9B703F", true);
        put("symbol",               "#CF6A4C");
        put("method",               "#dacf85"); // function in css
        put("instance_variable",    "#7587A6"); // instance-variable in css

        put("integer",              "#ddf2a3");
        put("octal",                "#9266ff", true);
        put("hex",                  "#4dbcff", true);

        put("regexp.delimiter",     "#E9C062");
        put("regexp.content",       "#E9C062");
        put("regexp.modifier",      "#cc22cc");
        put("regexp.function",      "#440044", true);
        put("regexp.char",          "#CF7D34");

//        put("annotation",           "#000077");
//        put("attribute-name",       "#ff0088");
//        put("attribute-value",      "#770000");
//        put("binary",               "#550099", true);
//        put("comment",              "#5F5A60");
//        put("char",                 "#0044dd");
//
//        put("complex",              "#aa0088", true);
//        put("constant",             "#9B5C2E");
//        put("color",                "#00AA00");
//        put("class-variable",       "#7587A6");
//        put("decorator",            "#BB00BB");
//        put("definition",           "#009999", true);
//        put("directive",            "#008888", true);
//        put("delimiter",            "#7E8C59");
//        put("doc",                  "#997700");
//        put("doctype",              "#3344bb");
//        put("doc-string",           "#DD4422", true);
//        put("escape",               "#666666", true);
//        put("entity",               "#880000", true);
//        put("error",                "#FF0000");
//        put("exception",            "#CC0000", true);
//        put("filename",             "#009999");
//
//        put("global-variable",      "#9a859c", true);
//
//
//        put("include",              "#bb4444", true);
//        put("inline",               "#DAEFA3");
//        put("inline-delimiter",     "#dd1144");
//        put("important",            "#ff0000");
//        put("interpreted",          "#bb22bb", true);
//
//        put("label",                "#997700", true);
//        put("local-variable",       "#996633");
//
//        put("predefined-constant",  "#ffffff", true);
//        put("predefined",           "#336699", true);
//        put("preprocessor",         "#557799");
//        put("pseudo-class",         "#0000CC", true);
//        put("predefined-type",      "#007744", true);
//        put("reserved",             "#CDA869", true);
//
//
//        put("key",                  "#880088");
//        put("value",                "#008888");
//
//        put("regexp",               "#880088");
//
//        put("string",               "#8F9D6A");
//
//        put("shell",                "#dd1144");
//
//
//
//        put("tag",                  "#007700");
//        put("tag-special",          "#dd7700", true);
//        put("type",                 "#333399", true);
//        put("variable",             "#003366");
//
//        put("insert",               "#aaffaa");
//        put("deleta",               "#ffaaaa");
//        put("change",               "#aaaaff");
//        put("head",                 "#ff88ff");
    }

    @Override
    public QuestionFill.Pack get_string(RichCodeTextView.Token token) {
        QuestionFill.Pack p = QuestionFill.get_text_include_fills(token.text);
        SpannableStringBuilder ssb = p.string_builder;

        String key = token.key();
        TextStyle style = map.get(key);

        if (null != style) {
            ssb.setSpan(
                    new ForegroundColorSpan(style.color),
                    0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );

            if (style.bold) {
                ssb.setSpan(
                        new StyleSpan(Typeface.BOLD),
                        0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }
        }

        return p;
    }

    private void put(String key, String color_s) {
        put(key, color_s, false);
    }

    private void put(String key, String color_s, boolean bold) {
        map.put(key, new TextStyle(color_s, bold));
    }

    private class TextStyle {
        boolean bold;
        int color;

        public TextStyle(String color_s, boolean bold) {
            this.color = Color.parseColor(color_s);
            this.bold = bold;
        }
    }
}
