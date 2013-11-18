package com.eshare_android_preview.activity.base.tab_activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.view.dash_path_view.DashPathEndpoint;
import com.eshare_android_preview.base.view.dash_path_view.DashPathView;
import com.eshare_android_preview.model.knowledge.BaseKnowledgeSet;
import com.eshare_android_preview.model.knowledge.KnowledgeNet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeActivity extends EshareBaseActivity {

    RelativeLayout nodes_paper;
    RelativeLayout lines_paper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.tab_home);

        _init_knowledge_net();

        super.onCreate(savedInstanceState);
    }

    ArrayList<DashPathEndpoint> dash_path_endpoint_list;
    int max_top;
    static double screen_width_dp, grid_width_dp;
    DashPathView dash_path_view;

    private void _init_knowledge_net() {
        KnowledgeSetsData.init();

        _init_screen_width_dp();
        _init_paper();
        _init_dash_path_view();

        _r_traversal(KnowledgeNet.instance());

        _draw_nodes();
        _draw_dash_path_view();
    }

    private void _init_screen_width_dp() {
        BaseUtils.ScreenSize screen_size = BaseUtils.get_screen_size();
        screen_width_dp = screen_size.width_dp;
        grid_width_dp = screen_width_dp / 3.0D;
    }

    private void _init_paper() {
        nodes_paper = (RelativeLayout) findViewById(R.id.nodes_paper);
        lines_paper = (RelativeLayout) findViewById(R.id.lines_paper);
    }

    private void _init_dash_path_view() {
        dash_path_view = new DashPathView(this);
        dash_path_endpoint_list = new ArrayList<DashPathEndpoint>();
    }

    private void _draw_dash_path_view() {
        dash_path_view.set_dash_path_endpoint_list(dash_path_endpoint_list);
        dash_path_view.set_color(Color.parseColor("#999999"));
        dash_path_view.set_dash_icon_radius(3);
        lines_paper.addView(dash_path_view);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) dash_path_view.getLayoutParams();
        params.height = BaseUtils.dp_to_int_px(max_top);
        dash_path_view.setLayoutParams(params);
    }

    private void _r_traversal(IHasChildren node) {
        for(BaseKnowledgeSet set : node.children()) {
            KnowledgeSetsData.put_set_in_map(set);
            _r_traversal(set);
        }
    }

    private void _draw_nodes() {
        for (Map.Entry<Integer, List<SetPosition>> entry : KnowledgeSetsData.deep_hashmap.entrySet()) {
            int deep = entry.getKey();

            List<SetPosition> grid_pos_list = KnowledgeSetsData.deep_hashmap.get(deep);

            for (SetPosition pos : grid_pos_list) {
                _put_knowledge_node_on_grid(pos);
            }
        }
    }

    private void _put_knowledge_node_on_grid(SetPosition pos) {
        double top = pos.circle_dp_top;

        int pos_bottom = (int) (top + SetPosition.CIRCLE_DIAMETER_DP);

        if (max_top < pos_bottom) max_top = pos_bottom;

        KnowledgeSetsData.put_set_position(pos.set, pos);

        for(BaseKnowledgeSet parent : pos.set.parents()) {
            SetPosition parent_pos = KnowledgeSetsData.get_set_position(parent);
            float x1 = (float) parent_pos.circle_center_dp_left;
            float y1 = (float) parent_pos.circle_center_dp_top;
            float x2 = (float) pos.circle_center_dp_left;
            float y2 = (float) pos.circle_center_dp_top;

            DashPathEndpoint p1 = DashPathEndpoint.build_by_dp_point(x1, y1, x2, y2);
            dash_path_endpoint_list.add(p1);
        }

        int drawable = R.drawable.btn_cfccd2d_circle_flat;
        String classname = pos.set.getClass().getName();
        if ("com.eshare_android_preview.model.knowledge.KnowledgeSet".equals(classname)) {
            drawable = R.drawable.btn_c1cb0f6_circle_flat;
        }

        ImageView iv = new ImageView(this);
        iv.setBackgroundDrawable(getResources().getDrawable(drawable));
        int px = BaseUtils.dp_to_int_px(60);
        RelativeLayout.LayoutParams layout_params = new RelativeLayout.LayoutParams(px, px);
        layout_params.setMargins(BaseUtils.dp_to_int_px((float) pos.circle_dp_left), BaseUtils.dp_to_int_px((float) pos.circle_dp_top), 0, 0);
        iv.setLayoutParams(layout_params);
        nodes_paper.addView(iv);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private static class KnowledgeSetsData {
        static HashMap<Integer, List<SetPosition>> deep_hashmap;
        static HashMap<BaseKnowledgeSet, SetPosition> points_map;

        static void init() {
            deep_hashmap = new HashMap<Integer, List<SetPosition>>();
            points_map = new HashMap<BaseKnowledgeSet, SetPosition>();
        }

        static void put_set_in_map(BaseKnowledgeSet set) {
            List<SetPosition> list = deep_hashmap.get(set.deep);
            if (null == list) {
                list = new ArrayList();
                deep_hashmap.put(set.deep, list);
            }

            SetPosition pos = new SetPosition(set, list.size() + 1);

            if (!list.contains(pos)) {
                list.add(pos);
            }
        }

        static void put_set_position(BaseKnowledgeSet set, SetPosition position) {
            points_map.put(set, position);
        }

        static SetPosition get_set_position(BaseKnowledgeSet set) {
            return points_map.get(set);
        }
    }

    static private class SetPosition {
        static HashMap<BaseKnowledgeSet, SetPosition> pos_hashmap = new HashMap<BaseKnowledgeSet, SetPosition>();

        final static double CIRCLE_DIAMETER_DP = 60.0D;

        BaseKnowledgeSet set;

        int grid_left;
        int grid_top;

        double circle_dp_left;
        double circle_dp_top;

        double circle_center_dp_left;
        double circle_center_dp_top;

        public SetPosition(BaseKnowledgeSet set, int grid_left) {
            this.set = set;

            this.grid_left = grid_left;
            this.grid_top = set.deep;

            this.circle_dp_left = (this.grid_left - 0.5D) * grid_width_dp - CIRCLE_DIAMETER_DP / 2.0D;
            this.circle_dp_top  = (this.grid_top  - 0.5D) * grid_width_dp - CIRCLE_DIAMETER_DP / 2.0D;

            this.circle_center_dp_left = this.circle_dp_left + CIRCLE_DIAMETER_DP / 2.0D;
            this.circle_center_dp_top  = this.circle_dp_top  + CIRCLE_DIAMETER_DP / 2.0D;

            pos_hashmap.put(set, this);
        }

        @Override
        public boolean equals(Object o) {
            return this.set.equals(((SetPosition) o).set);
        }

        static public SetPosition get_pos_of_set(BaseKnowledgeSet set) {
            return pos_hashmap.get(set);
        }
    }

    public interface IHasChildren {
        public List<BaseKnowledgeSet> children();
    }
}
