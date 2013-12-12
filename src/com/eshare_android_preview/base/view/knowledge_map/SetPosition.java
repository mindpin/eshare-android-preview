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
    public final static double CIRCLE_RADIUS_DP = 30; // 33.3333D;
    public final static double TEXT_SIZE = 9;

    final static float[][] GRID_DATA = new float[][] {
            {},
            {2.0F},
            {1.5F, 2.5F},
            {1.0F, 2.0F, 3.0F}
    };

    public KnowledgeMapView map_view;
    public BaseKnowledgeSet set;
    public AniProxy ani_proxy;

    public int grid_top;
    public int grid_dp_top;
    public int grid_dp_bottom;

    public float grid_left;
    public double grid_dp_left;

    public double circle_dp_left;
    public double circle_dp_top;

    public double circle_center_dp_left;
    public double circle_center_dp_top;

    public double text_dp_top;

    public SetPosition(BaseKnowledgeSet set, KnowledgeMapView map_view) {
        this.map_view = map_view;
        this.set = set;

        set_y_position();
    }

    private void set_y_position() {
        grid_top       = set.deep;
        grid_dp_top    = (grid_top - 1) * map_view.GRID_HEIGHT_DP;
        grid_dp_bottom = grid_dp_top + map_view.GRID_HEIGHT_DP;

        circle_center_dp_top = grid_dp_top + map_view.GRID_WIDTH_DP / 2.0D;
        circle_dp_top = circle_center_dp_top - CIRCLE_RADIUS_DP;

        text_dp_top = circle_center_dp_top + CIRCLE_RADIUS_DP + 4.0D;
    }

    public void set_x_position(float grid_left) {
        this.grid_left = grid_left;
        this.grid_dp_left = (this.grid_left - 1) * map_view.GRID_WIDTH_DP;
        this.circle_center_dp_left = this.grid_dp_left + map_view.GRID_WIDTH_DP / 2.0D;
        this.circle_dp_left = this.circle_center_dp_left - CIRCLE_RADIUS_DP;
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

    private void _draw_circle() {
        CircleView cv = new CircleView(map_view.getContext());
        cv.set_color(get_circle_color());
        cv.set_circle_center_position((float) circle_center_dp_left, (float) circle_center_dp_top);
        cv.set_radius((float) CIRCLE_RADIUS_DP);
        map_view.nodes_paper.addView(cv);

        ani_proxy = new AniProxy(cv, map_view);
    }

    private void _draw_text() {
        TextView tv = new TextView(map_view.getContext());

        tv.setText(set.get_name());
        tv.setTextSize(BaseUtils.dp_to_int_px((float) TEXT_SIZE));
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.parseColor("#444444"));

        int width_px = BaseUtils.dp_to_int_px((float) map_view.GRID_WIDTH_DP);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width_px, ViewGroup.LayoutParams.WRAP_CONTENT);

        double top = text_dp_top;
        params.setMargins(BaseUtils.dp_to_int_px((float) grid_dp_left), BaseUtils.dp_to_int_px((float) top), 0, 0);
        tv.setLayoutParams(params);

        map_view.nodes_paper.addView(tv);

        if (!set.is_checkpoint()) {
            TextView count_tv = new TextView(map_view.getContext());
            KnowledgeSet set = (KnowledgeSet) this.set;
            count_tv.setText(set.get_learned_nodes_count() + "/" + set.nodes.size());
            count_tv.setTextSize(BaseUtils.dp_to_int_px((float) TEXT_SIZE));
            count_tv.setGravity(Gravity.CENTER);
            count_tv.setTextColor(Color.parseColor("#aaaaaa"));

            RelativeLayout.LayoutParams ctv_params = new RelativeLayout.LayoutParams(width_px, ViewGroup.LayoutParams.WRAP_CONTENT);
            ctv_params.setMargins(BaseUtils.dp_to_int_px((float) grid_dp_left), BaseUtils.dp_to_int_px((float) top + 18), 0, 0);
            count_tv.setLayoutParams(ctv_params);

            map_view.nodes_paper.addView(count_tv);
        }
    }

    private void _draw_icon() {
        ImageView iv = new ImageView(map_view.getContext());

        iv.setImageDrawable(get_icon_drawable());

        int px = BaseUtils.dp_to_int_px((float) CIRCLE_RADIUS_DP * 2);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(px, px);
        params.setMargins(BaseUtils.dp_to_int_px((float) circle_dp_left), BaseUtils.dp_to_int_px((float) circle_dp_top), 0, 0);
        iv.setLayoutParams(params);

        map_view.nodes_paper.addView(iv);

        ani_proxy.set_icon_view(iv);
    }

    private void _set_icon_events() {
        ani_proxy.icon_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_unlocked()){
                    map_view.opened_node = ani_proxy;

                    // open activity
                    Intent intent = new Intent(map_view.getContext(), KnowledgeSetShowActivity.class);
                    intent.putExtra("set_id", set.id);
                    map_view.getContext().startActivity(intent);
                }
            }
        });
    }

    public void draw(int list_size, int index) {
        set_x_position(GRID_DATA[list_size][index]);

        _draw_circle();
        _draw_text();
        _draw_icon();
        _set_icon_events();
    }

    public void put_data_into_end_point_list(List<DashPathEndpoint> list) {
        for (BaseKnowledgeSet parent : set.parents()) {
            SetPosition parent_pos = map_view.kdata.get_from(parent);

            float x1 = (float) parent_pos.circle_center_dp_left;
            float y1 = (float) parent_pos.text_dp_top + 24 + (parent_pos.set.is_checkpoint() ? 0 : 18);
            float x2 = (float) circle_center_dp_left;
            float y2 = (float) circle_dp_top - 8;

            list.add(DashPathEndpoint.build_by_dp_point(x1, y1, x2, y2));
        }
    }
}
