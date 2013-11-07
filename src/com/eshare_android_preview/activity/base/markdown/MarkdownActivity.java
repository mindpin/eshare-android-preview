package com.eshare_android_preview.activity.base.markdown;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.webkit.WebView;
import android.widget.EditText;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.github.rjeschke.txtmark.Processor;

/**
 * Created by kaid on 11/7/13.
 */
public class MarkdownActivity extends EshareBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.markdown_show);
        hide_head_setting_button();
        this.setup_input();
        super.onCreate(savedInstanceState);
    }

    private void setup_input() {
        final EditText input_area = (EditText) findViewById(R.id.markdown_input);
        final WebView preview = (WebView) findViewById(R.id.markdown_preview);
        input_area.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String markdown = input_area.getText().toString();
                String html = Processor.process(markdown);
                preview.loadData(html, "text/html", null);
            }
        });
    }
}
