package com.eshare_android_preview.activity.base.tab_activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.application.EshareApplication;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.view.dash_path_view.DashPathEndpoint;
import com.eshare_android_preview.base.view.dash_path_view.DashPathView;
import com.eshare_android_preview.model.knowledge.BaseKnowledgeSet;
import com.eshare_android_preview.model.knowledge.KnowledgeNet;
import com.eshare_android_preview.model.knowledge.KnowledgeSet;

import java.io.IOException;
import java.io.InputStream;
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
    static double screen_width_dp, grid_width_dp, grid_height_dp;
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
        grid_height_dp = grid_width_dp + 30;
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
        dash_path_view.set_color(Color.parseColor("#aaaaaa"));
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
        for (List<SetPosition> list : KnowledgeSetsData.deep_hashmap.values()) {
            for (SetPosition pos : list) {
                if (list.size() == 1) {
                    list.get(0).set_position(2F);
                }

                if (list.size() == 2) {
                    list.get(0).set_position(1.5F);
                    list.get(1).set_position(2.5F);
                }

                _put_knowledge_node_on_grid(pos);
                _put_pos_to_dash_path_endpoint_list(pos);
            }
        }
    }

    private void _put_knowledge_node_on_grid(SetPosition pos) {
        _draw_circle(pos);
        _draw_text(pos);
        _draw_icon(pos);
    }

    private void _draw_circle(SetPosition pos) {
        ImageView iv = new ImageView(this);

        int drawable = pos.is_checkpoint() ? R.drawable.btn_cfccd2d_circle_flat : R.drawable.btn_c1cb0f6_circle_flat;
        iv.setBackgroundDrawable(getResources().getDrawable(drawable));

        int px = BaseUtils.dp_to_int_px((float) SetPosition.CIRCLE_RADIUS_DP * 2);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(px, px);

        params.setMargins(BaseUtils.dp_to_int_px((float) pos.circle_dp_left), BaseUtils.dp_to_int_px((float) pos.circle_dp_top), 0, 0);
        iv.setLayoutParams(params);

        nodes_paper.addView(iv);
    }

    private void _draw_text(SetPosition pos) {
        TextView tv = new TextView(this);

        tv.setText(pos.get_set_name());
        tv.setTextSize(BaseUtils.dp_to_int_px((float) pos.TEXT_SIZE));
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(Color.parseColor("#ffffff"));
        tv.setTextColor(Color.parseColor("#444444"));
//        tv.getPaint().setFakeBoldText(true);

        int width_px = BaseUtils.dp_to_int_px((float) grid_width_dp);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width_px, ViewGroup.LayoutParams.WRAP_CONTENT);

        double top = pos.text_dp_top;
        params.setMargins(BaseUtils.dp_to_int_px((float) pos.grid_dp_left), BaseUtils.dp_to_int_px((float) top), 0, 0);
        tv.setLayoutParams(params);

        nodes_paper.addView(tv);
    }

    private void _draw_icon(SetPosition pos) {
        try {
            ImageView iv = new ImageView(this);

            InputStream stream = getAssets().open("knowledge_icons/knowledge_leaf.png");
            Drawable drawable = Drawable.createFromStream(stream, null);
            stream.close();

            iv.setImageDrawable(drawable);

            int px = BaseUtils.dp_to_int_px((float) SetPosition.CIRCLE_RADIUS_DP * 2);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(px, px);

            params.setMargins(BaseUtils.dp_to_int_px((float) pos.circle_dp_left), BaseUtils.dp_to_int_px((float) pos.circle_dp_top), 0, 0);
            iv.setLayoutParams(params);

            nodes_paper.addView(iv);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void _put_pos_to_dash_path_endpoint_list(SetPosition pos) {
        for(BaseKnowledgeSet parent : pos.set.parents()) {
            SetPosition parent_pos = KnowledgeSetsData.get_pos_of_set(parent);
            float x1 = (float) parent_pos.circle_center_dp_left;
            float y1 = (float) parent_pos.text_dp_top + 22;
            float x2 = (float) pos.circle_center_dp_left;
            float y2 = (float) pos.circle_dp_top - 5;

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
//        final static double CIRCLE_RADIUS_DP = 33.3333D;
        final static double CIRCLE_RADIUS_DP = 30;
        final static double TEXT_SIZE = 9;

        BaseKnowledgeSet set;

        float grid_left;
        float grid_top;

        double grid_dp_left;
        double grid_dp_top;
        double grid_dp_bottom;

        double circle_dp_left;
        double circle_dp_top;

        double circle_center_dp_left;
        double circle_center_dp_top;

        double text_dp_top;

        public SetPosition(BaseKnowledgeSet set, float grid_left) {
            this.set = set;
            set_position(grid_left);
        }

        public void set_position(float grid_left) {
            this.grid_left = grid_left;
            this.grid_top  = set.deep;

            this.grid_dp_left = (this.grid_left - 1) * grid_width_dp;
            this.grid_dp_top  = (this.grid_top  - 1) * grid_height_dp;
            this.grid_dp_bottom = this.grid_dp_top + grid_height_dp;

            this.circle_center_dp_left = this.grid_dp_left + grid_width_dp / 2.0D;
            this.circle_center_dp_top  = this.grid_dp_top  + grid_width_dp / 2.0D;

            this.circle_dp_left = this.circle_center_dp_left - CIRCLE_RADIUS_DP;
            this.circle_dp_top  = this.circle_center_dp_top  - CIRCLE_RADIUS_DP;

            this.text_dp_top = this.circle_center_dp_top + CIRCLE_RADIUS_DP + 4.0D;
        }

        @Override
        public boolean equals(Object o) {
            return this.set.equals(((SetPosition) o).set);
        }

        boolean is_checkpoint() {
            String set_class_name = set.getClass().getName();
            return "com.eshare_android_preview.model.knowledge.KnowledgeCheckpoint".equals(set_class_name);
        }

        String get_set_name() {
            if (is_checkpoint()) {
                return "checkpoint";
            }

            return ((KnowledgeSet) set).name;
        }
    }

    public interface IHasChildren {
        public List<BaseKnowledgeSet> children();
    }
}
