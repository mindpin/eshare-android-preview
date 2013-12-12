package com.eshare_android_preview.base.view.knowledge_map;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.eshare_android_preview.activity.base.HomeActivity;
import com.eshare_android_preview.application.EshareApplication;
import com.eshare_android_preview.model.knowledge.BaseKnowledgeSet;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 13-12-12.
 */
public class SetPosition {
    public final static double CIRCLE_RADIUS_DP = 30; // 33.3333D;
    public final static double TEXT_SIZE = 9;

    public KnowledgeMapView map_view;

    public BaseKnowledgeSet set;
    public HomeActivity.AniProxy ani_proxy;

    public float grid_left;
    public float grid_top;

    public double grid_dp_left;
    public double grid_dp_top;
    public double grid_dp_bottom;

    public double circle_dp_left;
    public double circle_dp_top;

    public double circle_center_dp_left;
    public double circle_center_dp_top;

    public double text_dp_top;

    public SetPosition(BaseKnowledgeSet set, float grid_left, KnowledgeMapView map_view) {
        this.map_view = map_view;
        this.set = set;
        set_position(grid_left);
    }

    public void set_position(float grid_left) {
        this.grid_left = grid_left;
        this.grid_top = set.deep;

        this.grid_dp_left = (this.grid_left - 1) * map_view.GRID_WIDTH_DP;
        this.grid_dp_top = (this.grid_top - 1) * map_view.GRID_HEIGHT_DP;
        this.grid_dp_bottom = this.grid_dp_top + map_view.GRID_HEIGHT_DP;

        this.circle_center_dp_left = this.grid_dp_left + map_view.GRID_WIDTH_DP / 2.0D;
        this.circle_center_dp_top = this.grid_dp_top + map_view.GRID_WIDTH_DP / 2.0D;

        this.circle_dp_left = this.circle_center_dp_left - CIRCLE_RADIUS_DP;
        this.circle_dp_top = this.circle_center_dp_top - CIRCLE_RADIUS_DP;

        this.text_dp_top = this.circle_center_dp_top + CIRCLE_RADIUS_DP + 4.0D;
    }

    @Override
    public boolean equals(Object o) {
        return this.set.equals(((SetPosition) o).set);
    }

    public int get_circle_color() {
        if (!is_unlocked()) {
            return Color.parseColor("#eeeeee");
        }

        if (set.is_checkpoint()) {
            return Color.parseColor("#fccd2d");
        }

        return Color.parseColor("#1cb0f6");
    }

    public Drawable get_icon_drawable() {
        String path = "knowledge_icons/basic_locked.png";

        if (is_unlocked()) {
            path = "knowledge_icons/basic.png";
        }

        if (set.is_checkpoint()) {
            path = "knowledge_icons/checkpoint.png";
        }

        try {
            InputStream stream = EshareApplication.context.getAssets().open(path);
            Drawable drawable = Drawable.createFromStream(stream, null);
            stream.close();

            return drawable;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 节点是否解锁（可学）
    public boolean is_unlocked() {
        return set.is_unlocked();
    }
}
