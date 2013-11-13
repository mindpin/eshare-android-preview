package com.eshare_android_preview.base.view.dash_path_view;

/**
 * Created by fushang318 on 13-11-13.
 */
public class DashPath {
    // 单位是 px
    public float start_x;
    public float start_y;

    public float end_x;
    public float end_y;

    public DashPath(float start_x, float start_y, float end_x, float end_y){
        this.start_x = start_x;
        this.start_y = start_y;
        this.end_x = end_x;
        this.end_y = end_y;
    }
}
