package com.eshare_android_preview.activity.base.dash_path_demo;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.view.dash_path_view.DashPath;
import com.eshare_android_preview.base.view.dash_path_view.DashPathView;

import java.util.ArrayList;

/**
 * Created by fushang318 on 13-11-13.
 */
public class DashPathDemoActivity extends EshareBaseActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_path_demo);
        build_view();
    }

    private void build_view() {
        RelativeLayout root_view = (RelativeLayout)findViewById(R.id.dash_path_view_parent);

        DashPathView view = new DashPathView(this);
        ArrayList<DashPath> dash_path_list = new ArrayList<DashPath>();
        dash_path_list.add(new DashPath(10, 400, 700, 100));
        dash_path_list.add(new DashPath(10, 400, 700, 400));
        view.set_dash_path_list(dash_path_list);
        root_view.addView(view);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.height = 2000;
        params.width = 2000;
        view.setLayoutParams(params);
    }
}