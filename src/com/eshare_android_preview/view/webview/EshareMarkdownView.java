package com.eshare_android_preview.view.webview;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaid on 11/11/13.
 */
public class EshareMarkdownView extends RelativeLayout {
    public MarkdownWebView view;

    private List<TextFill> fills = new ArrayList<TextFill>();

    private OnClickListener code_fill_on_click_listener;

    public EshareMarkdownView(Context context) {
        super(context);
        init();
    }

    public EshareMarkdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EshareMarkdownView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        removeAllViews();

        view = new MarkdownWebView(getContext());
        addView(view);
        TextFillBridge.bind(this);
    }

    public TextFill add_codefill(final JSONObject rect) throws JSONException {
        final TextFill child = new TextFill(this, getContext(), rect);
        this.fills.add(child);

        ((Activity) getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EshareMarkdownView.this.addView(child);
            }
        });

        child.setOnClickListener(code_fill_on_click_listener);

        return child;
    }

    public void set_markdown_content(String content) {
        clear_codefills();
        init();
        view.loadDataWithBaseURL(null, get_html_page_string(content), "text/html", "UTF-8", null);
        // null as "about:blank"
    }

    private void clear_codefills() {
        for (TextFill fill : fills) {
            removeView(fill);
        }

        fills.clear();
    }

    public TextFill get_first_unfilled_codefill() {
        for (TextFill codefill : this.fills) {
            if (!codefill.filled) {
                return codefill;
            }
        }
        return null;
    }

    public int get_first_unfilled_codefill_index(){
        TextFill codefill = get_first_unfilled_codefill();
        return get_codefill_index(codefill);
    }

    public int get_codefill_index(TextFill codefill){
        return this.fills.indexOf(codefill);
    }

    public void set_on_click_listener_for_code_fill(View.OnClickListener listener){
        this.code_fill_on_click_listener = listener;
    }

    public TextFill find_codefill(int fid) {
        TextFill result = null;

        for (TextFill fill : fills) {
            if (fill.fid == fid) {
                result = fill;
                break;
            }
        }

        return result;
    }

    private String get_html_page_string(String content) {
        return
        "<!DOCTYPE html>" +
        "<html>" +
            "<head>" +
                "<link href='file:///android_asset/webview/coderay_twilight.css' rel='stylesheet' />" +
            "</head>" +
            "<body>" +
                "<div class='content'>" +
                    content +
                "</div>" +
                "<script src='file:///android_asset/webview/jquery-2.0.3.min.js'></script>" +
                "<script src='file:///android_asset/webview/codefill.js'></script>" +
            "</body>" +
        "</html>";
    }
}
