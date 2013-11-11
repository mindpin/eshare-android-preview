package com.eshare_android_preview.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.eshare_android_preview.base.utils.HtmlEmbeddable;

import org.markdown4j.Markdown4jProcessor;

import java.io.IOException;

/**
 * Created by kaid on 11/11/13.
 */
public class EshareMarkdownView extends WebView {
    private String content = "";
    {this.getSettings().setJavaScriptEnabled(true);}

    public EshareMarkdownView(Context context) {
        super(context);
    }

    public EshareMarkdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EshareMarkdownView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public EshareMarkdownView setMarkdownContent(String markdown) {
        try {
            String html = new Markdown4jProcessor().process(markdown);
            this.content = HtmlEmbeddable.fromString(html).output();
            this.loadDataWithBaseURL(null, this.content, "text/html", "UTF-8", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
}
