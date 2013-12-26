package com.eshare_android_preview.base.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshare_android_preview.base.utils.CodefillBridge;
import com.eshare_android_preview.base.utils.HtmlEmbeddable;

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
    public MarkdownWebView view;
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
        if (view != null) removeView(view);
        view = new MarkdownWebView(getContext());
        this.addView(view);
        CodefillBridge.bind(this);
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

    public EshareMarkdownView set_markdown_content(String content) {
        try {
            clear_codefills();
            init();
            view.loadDataWithBaseURL("file:///marup", HtmlEmbeddable.fromString(content).output(), "text/html", "UTF-8", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    private void clear_codefills() {
        for (Codefill fill : codefills) {
            removeView(fill);
        }

        codefills.clear();
    }

    public void disable_touch_event(){
        view.disable_touch_event();
    }

    public Codefill get_first_unfilled_codefill() {
        for (Codefill codefill : this.codefills) {
            if (!codefill.filled) {
                return codefill;
            }
        }
        return null;
    }

    public int get_first_unfilled_codefill_index(){
        Codefill codefill = get_first_unfilled_codefill();
        return get_codefill_index(codefill);
    }

    public int get_codefill_index(Codefill codefill){
        return this.codefills.indexOf(codefill);
    }

    public void set_on_click_listener_for_code_fill(View.OnClickListener listener){
        this.code_fill_on_click_listener = listener;
    }

    public Codefill find_codefill(int fid) {
        Codefill result = null;

        for (Codefill fill : codefills) {
            if (fill.fid == fid) {
                result = fill;
                break;
            }
        }

        return result;
    }

    public class MarkdownWebView extends WebView {
        private boolean disable_touch_event_flag = false;

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
            for (Codefill codefill : EshareMarkdownView.this.codefills) {
                codefill.relocate(l, t);
            }

            super.onScrollChanged(l, t, oldl, oldt);
        }

        public void disable_touch_event(){
            this.disable_touch_event_flag = true;
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            if(this.disable_touch_event_flag){
                return false;
            }else{
                return super.dispatchTouchEvent(ev);
            }
        }
    }

    public class Codefill extends TextView {
        public int fid;
        public boolean filled = false;
        private String text = "";
        public Rect rawRect;

        public Codefill(Context context, JSONObject object) throws JSONException {
            super(context);
            rawRect = new Rect(object.getInt("left"), object.getInt("top"), object.getInt("right"), object.getInt("bottom"));
            this.fid = object.getInt("fid");
            setTypeface(Typeface.MONOSPACE);
            setGravity(Gravity.CENTER);
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
            this.setText(this.text);
            this.filled = true;
            EshareMarkdownView.this.view.loadUrl("javascript: window.setText(" + this.fid + ", " + this.text.length() + ");");
        }

        public void update_rect(JSONObject object) throws JSONException {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
            rawRect = new Rect(object.getInt("left"), object.getInt("top"), object.getInt("right"), object.getInt("bottom"));
            params.height     = rawRect.height();
            params.width      = rawRect.width();
            params.leftMargin = rawRect.left;
            params.topMargin  = rawRect.top;
            this.setLayoutParams(params);
        }

        public void unset_text() {
            this.text = "";
            this.setText(this.text);
            this.filled = false;
            EshareMarkdownView.this.view.loadUrl("javascript: window.setText(" + this.fid + ", " + this.text.length() + ");");
        }

        public void relocate(int l, int t) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
            params.leftMargin = rawRect.left - l;
            params.topMargin  = rawRect.top - t;

            if (in_bound_of_view(EshareMarkdownView.this)) {
                setVisibility(View.VISIBLE);
                requestLayout();
            } else {
                setVisibility(View.GONE);
            }
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
