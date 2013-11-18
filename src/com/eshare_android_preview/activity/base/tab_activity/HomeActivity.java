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
import java.util.Set;


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
        params.height = BaseUtils.dp_to_int_px((float) KnowledgeSetsData.paper_bottom);
        dash_path_view.setLayoutParams(params);
    }

    private void _r_traversal(IHasChildren node) {
        for(BaseKnowledgeSet set : node.children()) {
            KnowledgeSetsData.put_set_in_map(set);
            _r_traversal(set);
        }
    }

    private void _draw_nodes() {
        for (SetPosition pos : KnowledgeSetsData.pos_hashmap.values()) {
            _put_knowledge_node_on_grid(pos);
            _put_pos_to_dash_path_endpoint_list(pos);
        }
    }

    private void _put_knowledge_node_on_grid(SetPosition pos) {
        int drawable = pos.is_checkpoint() ? R.drawable.btn_cfccd2d_circle_flat : R.drawable.btn_c1cb0f6_circle_flat;

        ImageView iv = new ImageView(this);
        iv.setBackgroundDrawable(getResources().getDrawable(drawable));

        int px = BaseUtils.dp_to_int_px((float) SetPosition.CIRCLE_RADIUS_DP * 2);
        RelativeLayout.LayoutParams layout_params = new RelativeLayout.LayoutParams(px, px);
        layout_params.setMargins(BaseUtils.dp_to_int_px((float) pos.circle_dp_left), BaseUtils.dp_to_int_px((float) pos.circle_dp_top), 0, 0);
        iv.setLayoutParams(layout_params);
        nodes_paper.addView(iv);
    }

    private void _put_pos_to_dash_path_endpoint_list(SetPosition pos) {
        for(BaseKnowledgeSet parent : pos.set.parents()) {
            SetPosition parent_pos = KnowledgeSetsData.get_pos_of_set(parent);
            float x1 = (float) parent_pos.circle_center_dp_left;
            float y1 = (float) parent_pos.circle_center_dp_top;
            float x2 = (float) pos.circle_center_dp_left;
            float y2 = (float) pos.circle_center_dp_top;

            DashPathEndpoint p1 = DashPathEndpoint.build_by_dp_point(x1, y1, x2, y2);
            dash_path_endpoint_list.add(p1);
        }
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
        static HashMap<BaseKnowledgeSet, SetPosition> pos_hashmap;
        static double paper_bottom;

        static void init() {
            deep_hashmap = new HashMap<Integer, List<SetPosition>>();
            pos_hashmap = new HashMap<BaseKnowledgeSet, SetPosition>();
            paper_bottom = 0;
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
                pos_hashmap.put(set, pos);
                if (pos.grid_dp_bottom > paper_bottom) {
                    paper_bottom = pos.grid_dp_bottom;
                }
            }
        }

        static SetPosition get_pos_of_set(BaseKnowledgeSet set) {
            return pos_hashmap.get(set);
        }
    }

    static private class SetPosition {
        final static double CIRCLE_RADIUS_DP = 30.0D;

        BaseKnowledgeSet set;

        int grid_left;
        int grid_top;

        double grid_dp_left;
        double grid_dp_top;
        double grid_dp_bottom;

        double circle_dp_left;
        double circle_dp_top;

        double circle_center_dp_left;
        double circle_center_dp_top;

        public SetPosition(BaseKnowledgeSet set, int grid_left) {
            this.set = set;

            this.grid_left = grid_left;
            this.grid_top = set.deep;

            this.grid_dp_left = (grid_left - 1) * grid_width_dp;
            this.grid_dp_top  = (grid_top  - 1) * grid_width_dp;
            this.grid_dp_bottom = this.grid_dp_top + grid_width_dp;

            this.circle_center_dp_left = this.grid_dp_left + grid_width_dp / 2.0D;
            this.circle_center_dp_top  = this.grid_dp_top  + grid_width_dp / 2.0D;

            this.circle_dp_left = this.circle_center_dp_left - CIRCLE_RADIUS_DP;
            this.circle_dp_top  = this.circle_center_dp_top  - CIRCLE_RADIUS_DP;

        }

        @Override
        public boolean equals(Object o) {
            return this.set.equals(((SetPosition) o).set);
        }

        boolean is_checkpoint() {
            String set_class_name = set.getClass().getName();
            return "com.eshare_android_preview.model.knowledge.KnowledgeCheckpoint".equals(set_class_name);
        }
    }

    public interface IHasChildren {
        public List<BaseKnowledgeSet> children();
    }
}
