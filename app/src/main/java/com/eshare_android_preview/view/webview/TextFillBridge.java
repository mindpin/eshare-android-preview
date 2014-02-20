package com.eshare_android_preview.view.webview;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kaid on 11/14/13.
 */
public class TextFillBridge {
    private EshareMarkdownView view;

    private TextFillBridge(EshareMarkdownView view) {
        this.view = view;
        this.view.view.addJavascriptInterface(this, "TextFillBridge");
    }

    static public TextFillBridge bind(EshareMarkdownView view) {
        return new TextFillBridge(view);
    }

    public void update_codefill(String field) throws JSONException {
        final JSONObject rect = new JSONObject(field);
        final TextFill fill = view.find_codefill(rect.getInt("fid"));
        if (fill != null) {
            ((Activity) view.getContext()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                try {
                    fill.update_rect(rect);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                }
            });
        }
    }

    public void add_field(String field) throws JSONException {
        JSONObject rect = new JSONObject(field);
        view.add_codefill(rect);
    }
}
