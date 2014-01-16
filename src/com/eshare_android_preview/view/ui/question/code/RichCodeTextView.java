package com.eshare_android_preview.view.ui.question.code;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;
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
        SpannableStringBuilder ssb = new SpannableStringBuilder();

        for (Token t : _make_token_list(list)) {
            SpannableStringBuilder s = theme.get_string(t);
            ssb.append(s);
        }

        setText(ssb);
    }

    private List<Token> _make_token_list(List<List<String>> list) {
        List<Token> token_list = new ArrayList<Token>();

        for (List<String> l : list) {
            String kind  = l.get(0);
            String text  = l.get(1);
            String block = l.size() > 2 ? l.get(2) : null;

            Token token = new Token(kind, text, block);

            if (token_list.isEmpty()) {
                token_list.add(token);
                continue;
            }

            Token last_token = token_list.get(token_list.size() - 1);

            if (token.key_equal(last_token)) {
                last_token.text = last_token.text + token.text;
                continue;
            }

            token_list.add(token);
        }

        return token_list;
    }

    class Token {
        public String kind;
        public String text;
        public String block;

        public Token(String kind, String text, String block) {
            this.kind = kind;
            this.text = text;
            this.block = block;
        }

        public boolean key_equal(Token token) {
            if (null == token) return false;
            return token.key().equals(key());
        }

        public String key() {
            if (block == null) {
                return kind;
            }

            return block + "." + kind;
        }
    }

    interface ColorTheme {
        public SpannableStringBuilder get_string(Token token);
    }
}
