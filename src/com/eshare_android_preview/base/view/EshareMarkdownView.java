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
    private CodefillBridge codefillBridge;
    private List<Codefill> codefills = new ArrayList<Codefill>();
    {init();}

    public EshareMarkdownView(Context context) {
        super(context);
    }

    public EshareMarkdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EshareMarkdownView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init() {
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
        CodefillBridge.bind(this);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_LEFT | RelativeLayout.ALIGN_TOP, RelativeLayout.TRUE);
        this.addView(view, params);
    }

    @SuppressLint("ResourceAsColor")
    public void addCodefill(final JSONObject rect) throws JSONException {
        final Rect childRect = new Rect(rect.getInt("left"), rect.getInt("top"), rect.getInt("right"), rect.getInt("bottom"));
        int height = rect.getInt("height");
        int width  = rect.getInt("width");

        final Codefill child = new Codefill(getContext());
        child.setRawRect(childRect);

        child.setHeight(height);
        child.setWidth(width);
        child.setBackgroundColor(R.color.black);

        final LayoutParams params = new LayoutParams(width, height);

        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.leftMargin = childRect.left;
        params.topMargin  = childRect.top;
        child.setLayoutParams(params);

        this.codefills.add(child);

        ((Activity) getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                that.addView(child);
            }
        });
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

        public Codefill(Context context) {
            super(context);
        }

        public void setRawRect(Rect rect) {
            rawRect = rect;
        }
    }
}
