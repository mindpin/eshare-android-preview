package com.eshare_android_preview.activity.base.tab_activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.markdown.MarkdownActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;

public class MessageActivity extends EshareBaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.tab_message);

        this.hide_head_go_back_button();
        this.set_head_text(R.string.category_message);
        this.render_markdown_button();
		super.onCreate(savedInstanceState);
	}

    private void render_markdown_button() {
        Button button = (Button) findViewById(R.id.markdown_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_activity(MarkdownActivity.class);
            }
        });
    }

	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
