package com.eshare_android_preview.activity.base;

import android.os.Bundle;
import android.view.KeyEvent;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.view.knowledge_map.KnowledgeMapView;


public class HomeActivity extends EshareBaseActivity {
    public static KnowledgeMapView map_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.home);
        map_view = (KnowledgeMapView) findViewById(R.id.knowledge_map_view);
        map_view.init(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}