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
    int screen_width_dp;
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
        screen_width_dp = (int) screen_size.width_dp;
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
                _put_knowledge_node_on_grid(pos.grid_left, pos.grid_top, pos.set);
            }
        }
    }

    private void _put_knowledge_node_on_grid(int x, int y, BaseKnowledgeSet set) {
        double grid_width = (int) screen_width_dp / 3;
        double diameter = 60;

        double left = (x - 0.5) * grid_width - diameter / 2.0;
        double top = (y - 0.5) * grid_width - diameter / 2.0;

        if (top + diameter > max_top) max_top = (int) (top + diameter);

        _put_knowledge_node((int) left, (int) top, (int) diameter, set);
    }

    private void _put_knowledge_node(int left, int top, int diameter, BaseKnowledgeSet set) {
        KnowledgeSetsData.put_set_position(set, new P(left, top));

        for(BaseKnowledgeSet parent : set.parents()) {
            P p = KnowledgeSetsData.get_set_position(parent);
            int x1 = p.left + diameter / 2;
            int y1 = p.top + diameter / 2;
            int x2 = left + diameter / 2;
            int y2 = top + diameter / 2;

            DashPathEndpoint p1 = DashPathEndpoint.build_by_dp_point(x1, y1, x2, y2);
            dash_path_endpoint_list.add(p1);
        }

        int drawable = R.drawable.btn_cfccd2d_circle_flat;
        String classname = set.getClass().getName();
        if ("com.eshare_android_preview.model.knowledge.KnowledgeSet".equals(classname)) {
            drawable = R.drawable.btn_c1cb0f6_circle_flat;
        }

        ImageView iv = new ImageView(this);
        iv.setBackgroundDrawable(getResources().getDrawable(drawable));
        int px = BaseUtils.dp_to_int_px(diameter);
        RelativeLayout.LayoutParams layout_params = new RelativeLayout.LayoutParams(px, px);
        layout_params.setMargins(BaseUtils.dp_to_int_px(left), BaseUtils.dp_to_int_px(top), 0, 0);
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
        static HashMap<BaseKnowledgeSet, P> points_map;

        static void init() {
            deep_hashmap = new HashMap<Integer, List<SetPosition>>();
            points_map = new HashMap<BaseKnowledgeSet, P>();
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

        static void put_set_position(BaseKnowledgeSet set, P position) {
            points_map.put(set, position);
        }

        static P get_set_position(BaseKnowledgeSet set) {
            return points_map.get(set);
        }
    }

    static private class SetPosition {
        BaseKnowledgeSet set;

        int grid_left;
        int grid_top;

        int dp_left;
        int dp_top;

        public SetPosition(BaseKnowledgeSet set, int grid_left) {
            this.set = set;
            this.grid_left = grid_left;
            this.grid_top = set.deep;
        }

        @Override
        public boolean equals(Object o) {
            return this.set.equals(((SetPosition) o).set);
        }
    }

    static private class P {
        int left;
        int top;

        public P(int left, int top) {
            this.left = left;
            this.top = top;
        }
    }

    public interface IHasChildren {
        public List<BaseKnowledgeSet> children();
    }
}
