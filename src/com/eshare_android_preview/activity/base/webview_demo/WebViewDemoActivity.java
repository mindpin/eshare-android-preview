package com.eshare_android_preview.activity.base.webview_demo;

import android.os.Bundle;
import android.webkit.WebView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.CodeSnippet;
import com.eshare_android_preview.base.utils.HtmlEmbeddable;

/**
 * Created by kaid on 11/7/13.
 */
public class WebViewDemoActivity extends EshareBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.markdown_show);
        hide_head_setting_button();
        this.setup_content();
        super.onCreate(savedInstanceState);
    }

    private void setup_content() {
        WebView preview = (WebView) findViewById(R.id.markdown_preview);
        String snippet = CodeSnippet.fromString(
            "var a = 3;\n" +
            "a === 4;\n" +
            "\"哈哈一个字符串\";"
        ).withLanguage("javascript").withLineNumbers().output();
        String html = HtmlEmbeddable.fromString(snippet).output();

        preview.getSettings().setJavaScriptEnabled(true);
        preview.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
    }
}
