package com.eshare_android_preview.view.ui.navigation;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.eshare_android_preview.R;
import com.eshare_android_preview.view.ui.FontAwesomeTextView;

/**
 * Created by Administrator on 14-1-14.
 */
public class AboutView extends FontAwesomeTextView {
    private View about_layer;

    public AboutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOnClickListener(open_listener);
    }

    private OnClickListener open_listener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (null == about_layer) {
                Activity activity = (Activity) getContext();
                about_layer = inflate(activity, R.layout.home_about, null);
                activity.addContentView(about_layer, new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                ));
                about_layer.setOnClickListener(close_listener);
            }
            about_layer.setVisibility(VISIBLE);
        }
    };

    private OnClickListener close_listener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            about_layer.setVisibility(GONE);
        }
    };
}
