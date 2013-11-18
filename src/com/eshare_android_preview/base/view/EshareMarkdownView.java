package com.eshare_android_preview.base.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.utils.CodefillBridge;
import com.eshare_android_preview.base.utils.HtmlEmbeddable;

import org.json.JSONException;
import org.json.JSONObject;
import org.markdown4j.Markdown4jProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaid on 11/11/13.
 */
public class EshareMarkdownView extends RelativeLayout {
    public WebView view;
    final private EshareMarkdownView that = this;
    // WebView上绑定的JSInterface
    private CodefillBridge codefillBridge;
    // 填空遮盖view的集合
    private List<Codefill> codefills = new ArrayList<Codefill>();

    {
        setupWebView();
        codefillBridge = CodefillBridge.bind(this);
    }

    public EshareMarkdownView(Context context) {
        super(context);
    }

    public EshareMarkdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EshareMarkdownView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void setupWebView() {
        view = new WebView(getContext()) {
            @Override
            protected void onScrollChanged(int l, int t, int oldl, int oldt) {
                if (that.codefills.size() > 0) {
                    for (Codefill codefill : that.codefills) {
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) codefill.getLayoutParams();
                        params.leftMargin = codefill.rawRect.left - l;
                        params.topMargin  = codefill.rawRect.top - t;
                        codefill.requestLayout();
                    }
                }
                super.onScrollChanged(l, t, oldl, oldt);
            }
        };
        view.getSettings().setJavaScriptEnabled(true);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_LEFT | RelativeLayout.ALIGN_TOP, RelativeLayout.TRUE);
        this.addView(view, params);
    }

    @SuppressLint("ResourceAsColor")
    public Codefill addCodefill(final JSONObject rect) throws JSONException {
        final Codefill child = new Codefill(getContext(), rect);
        this.codefills.add(child);

        ((Activity) getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                that.addView(child);
            }
        });

        return child;
    }

    public EshareMarkdownView setMarkdownContent(String markdown) {
        try {
            String html = new Markdown4jProcessor().process(markdown);
            view.loadDataWithBaseURL(null, HtmlEmbeddable.fromString(html).output(), "text/html", "UTF-8", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public static class Codefill extends TextView {
        public Rect rawRect;
        public boolean filled = false;

        public Codefill(Context context, JSONObject object) throws JSONException {
            super(context);
            rawRect = new Rect(object.getInt("left"), object.getInt("top"), object.getInt("right"), object.getInt("bottom"));;
            setParams(object.getInt("width"), object.getInt("height"));
        }

        private void setParams(int width, int height) {
            this.setHeight(height);
            this.setWidth(width);
            this.setBackgroundColor(R.color.black);
            LayoutParams params = new LayoutParams(width, height);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params.leftMargin = rawRect.left;
            params.topMargin  = rawRect.top;
            this.setLayoutParams(params);
        }
    }
}
