package com.eshare_android_preview.controller.activity.dash_path_demo;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.controller.activity.base.EshareBaseActivity;
import com.eshare_android_preview.utils.BaseUtils;
import com.eshare_android_preview.view.CircleView;
import com.eshare_android_preview.view.dash_path_view.DashPathEndpoint;
import com.eshare_android_preview.view.dash_path_view.DashPathView;

import java.util.ArrayList;

/**
 * Created by fushang318 on 13-11-13.
 */
public class DashPathDemoActivity extends EshareBaseActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_path_demo);
        build_view();
        build_info_view();
    }

    private void build_info_view() {
        TextView tv = (TextView) findViewById(R.id.dash_path_view_info_tv);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        String info = "";
        info = info + "屏幕宽度分辨率: " + dm.widthPixels + "px" + "\n";
        info = info + "屏幕高度分辨率: " + dm.heightPixels + "px" + "\n";
        info = info + "屏幕宽度dp: " + dm.widthPixels/dm.density + "dp" + "\n";
        info = info + "屏幕高度dp: " + dm.heightPixels/dm.density + "dp" + "\n";

        BaseUtils.ScreenSize ss = BaseUtils.get_screen_size();
        info = info + ss.width_dp + "\n";
        info = info + ss.height_dp + "\n";

        tv.setText(info);
    }

    private void build_view() {
        RelativeLayout root_view = (RelativeLayout)findViewById(R.id.dash_path_view_parent);

        final DashPathView view = new DashPathView(this);
        ArrayList<DashPathEndpoint> dash_path_endpoint_list = new ArrayList<DashPathEndpoint>();

        dash_path_endpoint_list.add(DashPathEndpoint.build_by_px_point(10, 400, 700, 100));
        dash_path_endpoint_list.add(DashPathEndpoint.build_by_dp_point(10, 400, 700, 400));
        view.set_dash_path_endpoint_list(dash_path_endpoint_list);
        view.set_color(Color.RED);
        view.set_dash_icon_radius(5);
        root_view.addView(view);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.height = 2000;
        params.width = 2000;
        view.setLayoutParams(params);

        CircleView cv = new CircleView(this);
        cv.set_color(Color.parseColor("#aaaaaa"));
        cv.set_circle_center_position(100,100);
        cv.set_radius(20);


        view.set_duration(2000);
        Button bn = new Button(this);
        bn.setText("p/r");
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getTag() == null){
                    view.pause();
                    v.setTag(true);
                }else{
                    view.resume();
                    v.setTag(null);
                }
            }
        });
        root_view.addView(cv);
        root_view.addView(bn);
    }
}