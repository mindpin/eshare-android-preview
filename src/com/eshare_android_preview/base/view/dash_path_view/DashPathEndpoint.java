package com.eshare_android_preview.base.view.dash_path_view;

import com.eshare_android_preview.base.utils.BaseUtils;

/**
 * Created by fushang318 on 13-11-13.
 */
public class DashPathEndpoint {
    // 单位是 px
    public float start_x;
    public float start_y;

    public float end_x;
    public float end_y;

    private DashPathEndpoint(float start_x, float start_y, float end_x, float end_y){
        this.start_x = start_x;
        this.start_y = start_y;
        this.end_x = end_x;
        this.end_y = end_y;
    }

    public static DashPathEndpoint build_by_px_point(float start_x, float start_y, float end_x, float end_y){
        return new DashPathEndpoint(start_x, start_y, end_x, end_y);
    }

    public static DashPathEndpoint build_by_dp_point(float start_dp_x, float start_dp_y, float end_dp_x, float end_dp_y){
        float start_x = BaseUtils.dp_to_px(start_dp_x);
        float start_y = BaseUtils.dp_to_px(start_dp_y);
        float end_x = BaseUtils.dp_to_px(end_dp_x);
        float end_y = BaseUtils.dp_to_px(end_dp_y);
        return new DashPathEndpoint(start_x, start_y, end_x, end_y);
    }
}

