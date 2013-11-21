package com.eshare_android_preview.base.utils;

import com.eshare_android_preview.base.view.EshareMarkdownView;

import org.json.JSONException;
import org.json.JSONObject;

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

    public void add_field(String field) throws JSONException {
        JSONObject rect = new JSONObject(field);
        view.add_codefill(rect);
    }
}
