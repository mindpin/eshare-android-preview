package com.eshare_android_preview.base.utils;

import android.app.Activity;

import com.eshare_android_preview.base.view.EshareMarkdownView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by kaid on 11/14/13.
 */
public class CodefillBridge {
    static public final String NAME = "CodefillBridge";
    private EshareMarkdownView view;

    private CodefillBridge(EshareMarkdownView view) {
        this.view = view;
        this.view.view.addJavascriptInterface(this, NAME);
    }

    static public CodefillBridge bind(EshareMarkdownView view) {
        return new CodefillBridge(view);
    }

    public void update_codefill(String field) throws JSONException {
        final JSONObject rect = new JSONObject(field);
        final EshareMarkdownView.Codefill fill = view.find_codefill(rect.getInt("fid"));
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
