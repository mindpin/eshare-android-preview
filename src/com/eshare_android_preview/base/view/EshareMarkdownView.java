package com.eshare_android_preview.base.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
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
    // WebView上绑定的JSInterface
    private CodefillBridge codefillBridge;
    // 填空遮盖view的集合
    private List<Codefill> codefills = new ArrayList<Codefill>();

    {
        view = new MarkdownWebView(getContext());
        this.addView(view);
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

    public Codefill addCodefill(final JSONObject rect) throws JSONException {
        final Codefill child = new Codefill(getContext(), rect);
        this.codefills.add(child);

        ((Activity) getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EshareMarkdownView.this.addView(child);
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

    public Codefill getFirstUnappliedCodefill() {
        Codefill firstUnapplied = null;

        for (Codefill codefill : this.codefills) {
            if (codefill.appliedChoice == null) {
                firstUnapplied = codefill;
                break;
            }
        }

        return firstUnapplied;
    }

    public class MarkdownWebView extends WebView {
        public MarkdownWebView(Context context) {
            super(context);
            setParams();
        }

        private void setParams() {
            this.getSettings().setJavaScriptEnabled(true);
            this.setFontSize(16);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_LEFT | RelativeLayout.ALIGN_TOP, RelativeLayout.TRUE);
            this.setLayoutParams(params);
        }

        public void setFontSize(int size) {
            this.getSettings().setDefaultFontSize(size);
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            EshareMarkdownView that = EshareMarkdownView.this;

            for (Codefill codefill : that.codefills) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) codefill.getLayoutParams();
                params.leftMargin = codefill.rawRect.left - l;
                params.topMargin  = codefill.rawRect.top - t;

                if (codefill.inBoundOfView(that)) {
                    codefill.setVisibility(View.VISIBLE);
                    codefill.requestLayout();
                } else {
                    codefill.setVisibility(View.GONE);
                }
            }

            super.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public class Codefill extends TextView {
        public Rect rawRect;
        public TextView appliedChoice;

        public Codefill(Context context, JSONObject object) throws JSONException {
            super(context);
            rawRect = new Rect(object.getInt("left"), object.getInt("top"), object.getInt("right"), object.getInt("bottom"));
            setParams();
        }

        public boolean inBoundOfView(View view) {
            LayoutParams params = (LayoutParams) this.getLayoutParams();
            int x = params.leftMargin , y = params.topMargin, offset = 4;
            boolean isXInBound = (x + this.getWidth()  > 0 - offset) && (x < view.getWidth()  + offset);
            boolean isYInBound = (y + this.getHeight() > 0 - offset) && (y < view.getHeight() + offset);

            return isXInBound && isYInBound;
        }

        @SuppressLint("ResourceAsColor")
        private void setParams() {
            this.setBackgroundColor(R.color.black);
            LayoutParams params = new LayoutParams(rawRect.width(), rawRect.height());
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params.leftMargin = rawRect.left;
            params.topMargin  = rawRect.top;
            this.setLayoutParams(params);
        }
    }
}
