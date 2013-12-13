package com.eshare_android_preview.base.view.knowledge_map;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshare_android_preview.activity.base.knowledge_net.KnowledgeSetShowActivity;
import com.eshare_android_preview.application.EshareApplication;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.view.CircleView;
import com.eshare_android_preview.base.view.dash_path_view.DashPathEndpoint;
import com.eshare_android_preview.model.knowledge.BaseKnowledgeSet;
import com.eshare_android_preview.model.knowledge.KnowledgeSet;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 13-12-12.
 */
public class SetPosition {
    public final static int CIRCLE_RADIUS_DP = 30; // 33;
    public final static int TEXT_SIZE = 9;

    final static float[][] GRID_DATA = new float[][]{
            {},
            {2.0F},
            {1.5F, 2.5F},
            {1.0F, 2.0F, 3.0F}
    };

    public KnowledgeMapView map_view;
    public BaseKnowledgeSet set;
    public AniProxy ani_proxy;

    // grid
    public int grid_top;
    public int grid_dp_top;
    public int grid_dp_bottom;

    public float grid_left;
    public int grid_dp_left;

    // offset
    public int circle_center_offset;
    public int icon_offset;
    public int text_offset_top;

    // views
    public CircleView circle_view;
    public ImageView icon_view;
    public TextView title_view;
    public TextView count_view;

    public SetPosition(BaseKnowledgeSet set, KnowledgeMapView map_view) {
        this.map_view = map_view;
        this.set = set;

        set_y_position();
    }

    private void set_y_position() {
        grid_top = set.deep;
        grid_dp_bottom = grid_top * map_view.GRID_HEIGHT_DP;
        grid_dp_top = grid_dp_bottom - map_view.GRID_HEIGHT_DP;

        circle_center_offset = map_view.GRID_WIDTH_DP / 2;
        icon_offset = circle_center_offset - CIRCLE_RADIUS_DP;
        text_offset_top = circle_center_offset + CIRCLE_RADIUS_DP + 4;
    }

    public void set_x_position(float grid_left) {
        this.grid_left = grid_left;
        this.grid_dp_left = (int) ((grid_left - 1) * map_view.GRID_WIDTH_DP);
    }

    @Override
    public boolean equals(Object o) {
        return this.set.id.equals(((SetPosition) o).set.id);
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

    private void _set_icon_events() {
        icon_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_unlocked()) {
                    map_view.opened_node = ani_proxy;

                    // open activity
                    Intent intent = new Intent(map_view.activity, KnowledgeSetShowActivity.class);
                    intent.putExtra("set_id", set.id);
                    map_view.activity.startActivity(intent);
                }
            }
        });
    }

    // -------------------------------------------------

    RelativeLayout grid;

    public void draw(int list_size, int index) {
        set_x_position(GRID_DATA[list_size][index]);

        grid = new RelativeLayout(map_view.activity);
        _set_dp_params(
                grid,
                map_view.GRID_WIDTH_DP, map_view.GRID_HEIGHT_DP,
                grid_dp_left, grid_dp_top
        );

        ani_proxy = new AniProxy(this);

        _draw_circle_view();
        _draw_icon_view();
        _draw_title_view();
        _draw_count_view();

        map_view.nodes_paper.addView(grid);

        _set_icon_events();
    }

    private void _draw_circle_view() {
        circle_view = new CircleView(map_view.activity);
        circle_view.set_color(get_circle_color());
        circle_view.set_circle_center_position(circle_center_offset, circle_center_offset);
        circle_view.set_radius(CIRCLE_RADIUS_DP);

        grid.addView(circle_view);
    }

    private void _draw_icon_view() {
        icon_view = new ImageView(map_view.activity);
        icon_view.setImageDrawable(get_icon_drawable());

        _set_dp_params(icon_view,
                CIRCLE_RADIUS_DP * 2, CIRCLE_RADIUS_DP * 2,
                icon_offset, icon_offset);

        grid.addView(icon_view);
    }

    private void _draw_title_view() {
        title_view = new TextView(map_view.activity);
        title_view.setText(set.get_name());
        title_view.setTextSize(BaseUtils.dp_to_int_px(TEXT_SIZE));
        title_view.setGravity(Gravity.CENTER);
        title_view.setTextColor(Color.parseColor("#444444"));

        _set_dp_params(title_view,
                map_view.GRID_WIDTH_DP, ViewGroup.LayoutParams.WRAP_CONTENT,
                0, text_offset_top);

        grid.addView(title_view);
    }

    private void _draw_count_view() {
        if (set.is_checkpoint()) return;

        KnowledgeSet set = (KnowledgeSet) this.set;

        count_view = new TextView(map_view.activity);
        count_view.setText(set.get_learned_nodes_count() + "/" + set.nodes.size());
        count_view.setTextSize(BaseUtils.dp_to_int_px(TEXT_SIZE));
        count_view.setGravity(Gravity.CENTER);
        count_view.setTextColor(Color.parseColor("#aaaaaa"));

        _set_dp_params(count_view,
                map_view.GRID_WIDTH_DP, ViewGroup.LayoutParams.WRAP_CONTENT,
                0, text_offset_top + 18);

        grid.addView(count_view);
    }

    public void _set_dp_params(View view, int w, int h, int left, int top) {
        int pxw = (w < 0 ? w : BaseUtils.dp_to_int_px(w));
        int pxh = (h < 0 ? h : BaseUtils.dp_to_int_px(h));

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(pxw, pxh);
        lp.leftMargin = BaseUtils.dp_to_int_px(left);
        lp.topMargin = BaseUtils.dp_to_int_px(top);
        view.setLayoutParams(lp);
    }

    // --------------------------------

    public void put_data_into_end_point_list(List<DashPathEndpoint> list) {
        for (BaseKnowledgeSet parent : set.parents()) {
            SetPosition parent_pos = map_view.kdata.get_from(parent);

            float x1 = parent_pos.grid_dp_left + parent_pos.circle_center_offset;
            float y1 = parent_pos.grid_dp_top + parent_pos.text_offset_top + 24
                    + (parent_pos.set.is_checkpoint() ? 0 : 18);

            float x2 = grid_dp_left + circle_center_offset;
            float y2 = grid_dp_top + icon_offset - 8;

            list.add(DashPathEndpoint.build_by_dp_point(x1, y1, x2, y2));
        }
    }
}
