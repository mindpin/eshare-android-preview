package com.eshare_android_preview.base.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.eshare_android_preview.base.utils.CodefillBridge;
import com.eshare_android_preview.base.utils.HtmlEmbeddable;
import com.eshare_android_preview.base.utils.SimpleMarkupParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaid on 11/11/13.
 */
public class EshareMarkdownView extends RelativeLayout {
    private int default_text_size = 18;
    public WebView view;
    // WebView上绑定的JSInterface
    private CodefillBridge codefillBridge;
    // 填空遮盖view的集合
    private List<Codefill> codefills = new ArrayList<Codefill>();

    private OnClickListener code_fill_on_click_listener;

    public EshareMarkdownView(Context context) {
        super(context);
        init();
    }

    public EshareMarkdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        default_text_size = attrs.getAttributeIntValue(null,"text_size",18);
        init();
    }

    public EshareMarkdownView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        view = new MarkdownWebView(getContext());
        this.addView(view);
        codefillBridge = CodefillBridge.bind(this);
    }

    public Codefill add_codefill(final JSONObject rect) throws JSONException {
        final Codefill child = new Codefill(getContext(), rect);
        this.codefills.add(child);

        ((Activity) getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EshareMarkdownView.this.addView(child);
            }
        });

        child.setOnClickListener(code_fill_on_click_listener);

        return child;
    }

    public EshareMarkdownView set_markdown_content(String markup) {
        try {
            String html = SimpleMarkupParser.from_string(markup).output();
            view.loadDataWithBaseURL(null, HtmlEmbeddable.fromString(html).output(), "text/html", "UTF-8", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Codefill get_first_unapplied_codefill() {
        for (Codefill codefill : this.codefills) {
            if (codefill.getChildCount() == 0) {
                return codefill;
            }
        }
        return null;
    }

    public int get_first_unapplied_codefill_index(){
        Codefill codefill = get_first_unapplied_codefill();
        return get_codefill_index(codefill);
    }

    public int get_codefill_index(Codefill codefill){
        return this.codefills.indexOf(codefill);
    }

    public void set_on_click_listener_for_code_fill(View.OnClickListener listener){
        this.code_fill_on_click_listener = listener;
    }

    public class MarkdownWebView extends WebView {
        public MarkdownWebView(Context context) {
            super(context);
            // 当 isInEditMode() 为 true 时，表示是 ide 预览界面
            // 下面的 if 判断，是为了让 ide 预览界面不报空指针异常
            if(!isInEditMode()){
                set_params();
            }
        }

        private void set_params() {
            this.getSettings().setJavaScriptEnabled(true);
            this.set_font_size(EshareMarkdownView.this.default_text_size);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_LEFT | RelativeLayout.ALIGN_TOP, RelativeLayout.TRUE);
            this.setLayoutParams(params);
        }

        public void set_font_size(int size) {
            this.getSettings().setDefaultFontSize(size);
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            EshareMarkdownView that = EshareMarkdownView.this;

            for (Codefill codefill : that.codefills) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) codefill.getLayoutParams();
                params.leftMargin = codefill.rawRect.left - l;
                params.topMargin  = codefill.rawRect.top - t;

                if (codefill.in_bound_of_view(that)) {
                    codefill.setVisibility(View.VISIBLE);
                    codefill.requestLayout();
                } else {
                    codefill.setVisibility(View.GONE);
                }
            }

            super.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public class Codefill extends LinearLayout {
        private int fid;
        public boolean filled = false;
        private String text = "  ";
        public Rect rawRect;

        public Codefill(Context context, JSONObject object) throws JSONException {
            super(context);
            rawRect = new Rect(object.getInt("left"), object.getInt("top"), object.getInt("right"), object.getInt("bottom"));
            this.fid = object.getInt("fid");
            set_params();
        }

        public boolean in_bound_of_view(View view) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) this.getLayoutParams();
            int x = params.leftMargin , y = params.topMargin, offset = 4;
            boolean isXInBound = (x + this.getWidth()  > 0 - offset) && (x < view.getWidth()  + offset);
            boolean isYInBound = (y + this.getHeight() > 0 - offset) && (y < view.getHeight() + offset);

            return isXInBound && isYInBound;
        }

        public void set_text(String text) {
            this.text = text;
            this.filled = true;
            EshareMarkdownView.this.view.loadUrl("javascript: window.setText(" + this.fid + ", " + this.text + ");");
        }

        public void unset_text() {
            this.text = "  ";
            this.filled = false;
            EshareMarkdownView.this.view.loadUrl("javascript: window.setText(" + this.fid + ", " + this.text + ");");
        }

        @SuppressLint("ResourceAsColor")
        private void set_params() {
            this.setBackgroundColor(Color.parseColor("#ff0040"));
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(rawRect.width(), rawRect.height());
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params.leftMargin = rawRect.left;
            params.topMargin  = rawRect.top;
            this.setLayoutParams(params);
        }
    }
}
