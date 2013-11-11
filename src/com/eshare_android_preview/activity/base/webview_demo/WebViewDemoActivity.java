package com.eshare_android_preview.activity.base.webview_demo;

import android.os.Bundle;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.view.EshareMarkdownView;

/**
 * Created by kaid on 11/7/13.
 */
public class WebViewDemoActivity extends EshareBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.webview_demo);
        hide_head_setting_button();
        this.setup_content();
        super.onCreate(savedInstanceState);
    }

    private void setup_content() {
        EshareMarkdownView preview = (EshareMarkdownView) findViewById(R.id.markdown_preview);
        String h1 = "文字举例\n" +
                    "====\n";
        String image = " ![image](https://f.cloud.github.com/assets/326242/1425767/ea82eb9c-4049-11e3-8824-1af1c4c63d68.png)\n";
        String code = "```ruby\n" +
                      "if a > 3\n" +
                      "  p \"你好，世界\"\n" +
                      "end\n" +
                      "```";
        preview.setMarkdownContent(h1 + image + code);
    }
}
