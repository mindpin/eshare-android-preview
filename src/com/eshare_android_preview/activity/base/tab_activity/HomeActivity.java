package com.eshare_android_preview.activity.base.tab_activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.application.EshareApplication;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.view.CircleView;
import com.eshare_android_preview.base.view.LockableScrollView;
import com.eshare_android_preview.base.view.dash_path_view.DashPathEndpoint;
import com.eshare_android_preview.base.view.dash_path_view.DashPathView;
import com.eshare_android_preview.model.knowledge.BaseKnowledgeSet;
import com.eshare_android_preview.model.knowledge.KnowledgeNet;
import com.eshare_android_preview.model.knowledge.KnowledgeSet;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeActivity extends EshareBaseActivity {

    LockableScrollView scroll_view;

    RelativeLayout nodes_paper;
    RelativeLayout lines_paper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.tab_home);

        _init_knowledge_net();

        super.onCreate(savedInstanceState);
    }

    ArrayList<DashPathEndpoint> dash_path_endpoint_list;
    static double SCREEN_WIDTH_DP, SCREEN_HEIGHT_DP, GRID_WIDTH_DP, GRID_HEIGHT_DP;
    DashPathView dash_path_view;

    private void _init_knowledge_net() {
        KnowledgeSetsData.init();
        AniProxy.init();

        _init_screen_width_dp();
        _init_paper();
        _init_dash_path_view();

        _r_traversal(KnowledgeNet.instance());

        _draw_nodes();
        _draw_dash_path_view();
    }

    private void _init_screen_width_dp() {
        BaseUtils.ScreenSize screen_size = BaseUtils.get_screen_size();
        SCREEN_WIDTH_DP = screen_size.width_dp;
        SCREEN_HEIGHT_DP = screen_size.height_dp;

        GRID_WIDTH_DP = SCREEN_WIDTH_DP / 3.0D;
        GRID_HEIGHT_DP = GRID_WIDTH_DP + 30;
    }

    private void _init_paper() {
        nodes_paper = (RelativeLayout) findViewById(R.id.nodes_paper);
        lines_paper = (RelativeLayout) findViewById(R.id.lines_paper);
        scroll_view = (LockableScrollView) findViewById(R.id.scroll_view);
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

        ViewGroup.LayoutParams params = dash_path_view.getLayoutParams();
        params.height = BaseUtils.dp_to_int_px((float) KnowledgeSetsData.paper_bottom);
        dash_path_view.setLayoutParams(params);
    }

    private void _r_traversal(IHasChildren node) {
        for (BaseKnowledgeSet set : node.children()) {
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
        CircleView cv = new CircleView(this);
        cv.set_color(pos.get_circle_color());
        cv.set_circle_center_position((float) pos.circle_center_dp_left, (float) pos.circle_center_dp_top);
        cv.set_radius((float) SetPosition.CIRCLE_RADIUS_DP);
        nodes_paper.addView(cv);

        pos.ani_proxy = new AniProxy(cv);
    }

    private void _draw_text(SetPosition pos) {
        TextView tv = new TextView(this);

        tv.setText(pos.get_set_name());
        tv.setTextSize(BaseUtils.dp_to_int_px((float) pos.TEXT_SIZE));
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.parseColor("#444444"));

        int width_px = BaseUtils.dp_to_int_px((float) GRID_WIDTH_DP);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width_px, ViewGroup.LayoutParams.WRAP_CONTENT);

        double top = pos.text_dp_top;
        params.setMargins(BaseUtils.dp_to_int_px((float) pos.grid_dp_left), BaseUtils.dp_to_int_px((float) top), 0, 0);
        tv.setLayoutParams(params);

        nodes_paper.addView(tv);

        if (!pos.is_checkpoint()) {
            TextView count_tv = new TextView(this);
            KnowledgeSet set = (KnowledgeSet) pos.set;
            count_tv.setText(set.get_learned_nodes_count() + "/" + set.nodes.size());
            count_tv.setTextSize(BaseUtils.dp_to_int_px((float) pos.TEXT_SIZE));
            count_tv.setGravity(Gravity.CENTER);
            count_tv.setTextColor(Color.parseColor("#aaaaaa"));

            RelativeLayout.LayoutParams ctv_params = new RelativeLayout.LayoutParams(width_px, ViewGroup.LayoutParams.WRAP_CONTENT);
            ctv_params.setMargins(BaseUtils.dp_to_int_px((float) pos.grid_dp_left), BaseUtils.dp_to_int_px((float) top + 18), 0, 0);
            count_tv.setLayoutParams(ctv_params);

            nodes_paper.addView(count_tv);
        }
    }

    private void _draw_icon(final SetPosition pos) {
        ImageView iv = new ImageView(this);

        iv.setImageDrawable(pos.get_icon_drawable());

        int px = BaseUtils.dp_to_int_px((float) SetPosition.CIRCLE_RADIUS_DP * 2);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(px, px);
        params.setMargins(BaseUtils.dp_to_int_px((float) pos.circle_dp_left), BaseUtils.dp_to_int_px((float) pos.circle_dp_top), 0, 0);
        iv.setLayoutParams(params);

        nodes_paper.addView(iv);

        pos.ani_proxy.set_icon_view(iv);

        _set_icon_events(pos);
    }

    private void _set_icon_events(final SetPosition pos) {
        final AniProxy proxy = pos.ani_proxy;

        proxy.icon_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos.is_unlocked()){
                    proxy.toggle();
                    scroll_view.locked = proxy.is_open;
                }
            }
        });
    }

    private void _put_pos_to_dash_path_endpoint_list(SetPosition pos) {
        for (BaseKnowledgeSet parent : pos.set.parents()) {
            SetPosition parent_pos = KnowledgeSetsData.get_pos_of_set(parent);
            float x1 = (float) parent_pos.circle_center_dp_left;
            float y1 = (float) parent_pos.text_dp_top + 24 + (parent_pos.is_checkpoint() ? 0 : 18);
            float x2 = (float) pos.circle_center_dp_left;
            float y2 = (float) pos.circle_dp_top - 8;

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

    public interface IHasChildren {
        public List<BaseKnowledgeSet> children();
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
        final static double CIRCLE_RADIUS_DP = 30; // 33.3333D;
        final static double TEXT_SIZE = 9;

        BaseKnowledgeSet set;
        AniProxy ani_proxy;

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
            this.grid_top = set.deep;

            this.grid_dp_left = (this.grid_left - 1) * GRID_WIDTH_DP;
            this.grid_dp_top = (this.grid_top - 1) * GRID_HEIGHT_DP;
            this.grid_dp_bottom = this.grid_dp_top + GRID_HEIGHT_DP;

            this.circle_center_dp_left = this.grid_dp_left + GRID_WIDTH_DP / 2.0D;
            this.circle_center_dp_top = this.grid_dp_top + GRID_WIDTH_DP / 2.0D;

            this.circle_dp_left = this.circle_center_dp_left - CIRCLE_RADIUS_DP;
            this.circle_dp_top = this.circle_center_dp_top - CIRCLE_RADIUS_DP;

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

        int get_circle_color() {
            if (!is_unlocked()) {
                return Color.parseColor("#eeeeee");
            }

            if (is_checkpoint()) {
                return Color.parseColor("#fccd2d");
            }

            return Color.parseColor("#1cb0f6");
        }

        Drawable get_icon_drawable() {
            String path = "knowledge_icons/basic_locked.png";

            if (is_unlocked()) {
                path = "knowledge_icons/basic.png";
            }

            if (is_checkpoint()) {
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

        String get_set_name() {
            if (is_checkpoint()) {
                return "综合测试";
            }

            return ((KnowledgeSet) set).name;
        }

        // 节点是否解锁（可学）
        boolean is_unlocked() {
            return set.is_unlocked();
        }
    }

    static class AniProxy {
        static AniProxy opened_node;
        static int[] tagget_icon_view_absolute_pos_px;

        CircleView circle_view;
        ImageView icon_view;

        boolean is_open = false;

        int icon_view_left_margin_px;
        int icon_view_top_margin_px;

        public AniProxy(CircleView circle_view) {
            this.circle_view = circle_view;
        }

        public static void init() {
            opened_node = null;
            tagget_icon_view_absolute_pos_px = new int[]{
                    BaseUtils.dp_to_int_px(0),
                    BaseUtils.dp_to_int_px(30)
            };
        }

        public void set_icon_view(ImageView icon_view) {
            this.icon_view = icon_view;

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) icon_view.getLayoutParams();
            icon_view_left_margin_px = params.leftMargin;
            icon_view_top_margin_px = params.topMargin;
        }

        public void toggle() {
            if (opened_node != null && !opened_node.equals(this)) {
                return;
            }

            circle_view.bringToFront();
            icon_view.bringToFront();

            if (is_open) {
                is_open = false;
                opened_node = null;

                circle_view.set_radius_animate((float) SetPosition.CIRCLE_RADIUS_DP, 400);
                set_icon_view_margin(icon_view_left_margin_px, icon_view_top_margin_px);

            } else {
                is_open = true;
                opened_node = this;

                circle_view.set_radius_animate((float) SCREEN_HEIGHT_DP, 400);
                int[] margins = get_target_margin();
                set_icon_view_margin(margins[0], margins[1]);
            }
        }

        private void set_icon_view_margin(int margin_left, int margin_top) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) icon_view.getLayoutParams();


            PropertyValuesHolder pvh_left = PropertyValuesHolder.ofFloat("margin_left", params.leftMargin, margin_left);
            PropertyValuesHolder pvh_top = PropertyValuesHolder.ofFloat("margin_top", params.topMargin, margin_top);

            ValueAnimator ani = ValueAnimator
                    .ofPropertyValuesHolder(pvh_left, pvh_top)
                    .setDuration(400);

            ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float margin_left = (Float) valueAnimator.getAnimatedValue("margin_left");
                    float margin_top = (Float) valueAnimator.getAnimatedValue("margin_top");

                    RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) icon_view.getLayoutParams();

                    p.leftMargin = (int) margin_left;
                    p.topMargin = (int) margin_top;
                    icon_view.setLayoutParams(p);
                }
            });

            ani.start();
        }

        private int[] get_target_margin() {
            int[] current_icon_view_absolute_pos_px = new int[2];
            icon_view.getLocationInWindow(current_icon_view_absolute_pos_px);

            int x_off = current_icon_view_absolute_pos_px[0] - tagget_icon_view_absolute_pos_px[0];
            int y_off = current_icon_view_absolute_pos_px[1] - tagget_icon_view_absolute_pos_px[1];

            return new int[]{
                    icon_view_left_margin_px - x_off,
                    icon_view_top_margin_px - y_off
            };
        }
    }
}
