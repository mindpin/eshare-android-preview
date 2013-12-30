package com.eshare_android_preview.base.view.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshare_android_preview.base.view.ui.UiColor;

import org.json.JSONException;
import org.json.JSONObject;

/**
* Created by Administrator on 13-12-30.
*/
public class TextFill extends TextView {
    private EshareMarkdownView eshareMarkdownView;
    public int fid;
    public boolean filled = false;

    public TextFill(EshareMarkdownView eshareMarkdownView, Context context, JSONObject object) throws JSONException {
        super(context);

        this.eshareMarkdownView = eshareMarkdownView;
        this.fid = object.getInt("fid");

        setBackgroundColor(UiColor.TEXT_FILL);

        update_rect(object);
    }

    public void update_rect(JSONObject object) throws JSONException {
        Rect rect = new Rect(
                object.getInt("left"),
                object.getInt("top"),
                object.getInt("right"),
                object.getInt("bottom")
        );

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(rect.width(), rect.height());

        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lp.leftMargin = rect.left;
        lp.topMargin  = rect.top;

        setLayoutParams(lp);
    }

    public void set_text(String text) {
        this.filled = true;
        String t = JSONObject.quote(text);
        eshareMarkdownView.view.loadUrl("javascript: window.setText(" + this.fid + ", " + t + ");");
    }

    public void unset_text() {
        this.filled = false;
        eshareMarkdownView.view.loadUrl("javascript: window.setText(" + this.fid + ", '_');");
    }
}
