package com.eshare_android_preview.base.view.webview;

import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.eshare_android_preview.base.utils.BaseUtils;

/**
* Created by Administrator on 13-12-30.
*/
public class MarkdownWebView extends WebView {
    public MarkdownWebView(Context context) {
        super(context);

        if(!isInEditMode()){
            set_params();
        }
    }

    private void set_params() {
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);

        getSettings().setJavaScriptEnabled(true);
        getSettings().setDefaultFontSize(BaseUtils.dp_to_px(16));
        getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        getSettings().setBlockNetworkImage(true);

        setPadding(0, 0, 0, 0);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.ALIGN_LEFT | RelativeLayout.ALIGN_TOP, RelativeLayout.TRUE);
        setLayoutParams(params);
    }
}
