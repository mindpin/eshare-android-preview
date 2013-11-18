package com.eshare_android_preview.base.utils;

import com.eshare_android_preview.base.view.EshareMarkdownView;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaid on 11/14/13.
 */
public class CodefillBridge {
    static public final String NAME = "CodefillBridge";
    private EshareMarkdownView view;
    private List<JSONObject> fields = new ArrayList<JSONObject>();

    private CodefillBridge(EshareMarkdownView view) {
        this.view = view;
        this.view.view.addJavascriptInterface(this, NAME);
    }

    static public CodefillBridge bind(EshareMarkdownView view) {
        return new CodefillBridge(view);
    }

    public void addField(String field) throws JSONException {
        JSONObject rect = new JSONObject(field);
        this.fields.add(rect);
        view.addCodefill(rect);
    }
}
