package com.eshare_android_preview.activity.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.knowledge_net.KnowledgeSetShowActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.view.CircleView;
import com.eshare_android_preview.base.view.dash_path_view.DashPathEndpoint;
import com.eshare_android_preview.base.view.knowledge_map.KnowledgeMapView;
import com.eshare_android_preview.base.view.knowledge_map.SetPosition;
import com.eshare_android_preview.model.knowledge.BaseKnowledgeSet;
import com.eshare_android_preview.model.knowledge.KnowledgeNet;
import com.eshare_android_preview.model.knowledge.KnowledgeSet;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeActivity extends EshareBaseActivity {
    public static int ANIMATE_DRUATION = 400;
    public static HomeActivity instance;

    public static KnowledgeMapView map_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.home);
        instance = this;
        _init_knowledge_net();
        super.onCreate(savedInstanceState);
    }

    private void _init_knowledge_net() {
        KnowledgeSetsData.init();
        AniProxy.init();

        map_view = (KnowledgeMapView) findViewById(R.id.knowledge_map_view);

        _r_traversal(KnowledgeNet.instance());

        _draw_nodes();
        _draw_dash_path_view();
    }



    private void _draw_dash_path_view() {
        map_view.dash_path_view.set_dash_path_endpoint_list(map_view.dash_path_endpoint_list);
        map_view.dash_path_view.set_color(Color.parseColor("#aaaaaa"));
        map_view.dash_path_view.set_dash_icon_radius(3);
        map_view.lines_paper.addView(map_view.dash_path_view);

        ViewGroup.LayoutParams params = map_view.dash_path_view.getLayoutParams();
        params.height = BaseUtils.dp_to_int_px((float) KnowledgeSetsData.paper_bottom);
        map_view.dash_path_view.setLayoutParams(params);
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
        map_view.nodes_paper.addView(cv);

        pos.ani_proxy = new AniProxy(cv);
    }

    private void _draw_text(SetPosition pos) {
        TextView tv = new TextView(this);

        tv.setText(pos.set.get_name());
        tv.setTextSize(BaseUtils.dp_to_int_px((float) pos.TEXT_SIZE));
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.parseColor("#444444"));

        int width_px = BaseUtils.dp_to_int_px((float) map_view.GRID_WIDTH_DP);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width_px, ViewGroup.LayoutParams.WRAP_CONTENT);

        double top = pos.text_dp_top;
        params.setMargins(BaseUtils.dp_to_int_px((float) pos.grid_dp_left), BaseUtils.dp_to_int_px((float) top), 0, 0);
        tv.setLayoutParams(params);

        map_view.nodes_paper.addView(tv);

        if (!pos.set.is_checkpoint()) {
            TextView count_tv = new TextView(this);
            KnowledgeSet set = (KnowledgeSet) pos.set;
            count_tv.setText(set.get_learned_nodes_count() + "/" + set.nodes.size());
            count_tv.setTextSize(BaseUtils.dp_to_int_px((float) pos.TEXT_SIZE));
            count_tv.setGravity(Gravity.CENTER);
            count_tv.setTextColor(Color.parseColor("#aaaaaa"));

            RelativeLayout.LayoutParams ctv_params = new RelativeLayout.LayoutParams(width_px, ViewGroup.LayoutParams.WRAP_CONTENT);
            ctv_params.setMargins(BaseUtils.dp_to_int_px((float) pos.grid_dp_left), BaseUtils.dp_to_int_px((float) top + 18), 0, 0);
            count_tv.setLayoutParams(ctv_params);

            map_view.nodes_paper.addView(count_tv);
        }
    }

    private void _draw_icon(final SetPosition pos) {
        ImageView iv = new ImageView(this);

        iv.setImageDrawable(pos.get_icon_drawable());

        int px = BaseUtils.dp_to_int_px((float) SetPosition.CIRCLE_RADIUS_DP * 2);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(px, px);
        params.setMargins(BaseUtils.dp_to_int_px((float) pos.circle_dp_left), BaseUtils.dp_to_int_px((float) pos.circle_dp_top), 0, 0);
        iv.setLayoutParams(params);

        map_view.nodes_paper.addView(iv);

        pos.ani_proxy.set_icon_view(iv);

        _set_icon_events(pos);
    }

    private void _set_icon_events(final SetPosition pos) {
        final AniProxy proxy = pos.ani_proxy;

        proxy.icon_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos.is_unlocked()){
                    AniProxy.opened_node = pos.ani_proxy;

                    // open activity
                    Intent intent = new Intent(HomeActivity.this, KnowledgeSetShowActivity.class);
                    intent.putExtra("set_id", pos.set.id);
                    startActivity(intent);
                }
            }
        });
    }

    public void run_open_animate() {
        if (null != AniProxy.opened_node) {
            AniProxy.opened_node.toggle();
            map_view.locked = true;
        }
    }

    public void run_close_animate() {
        if (null != AniProxy.opened_node) {
            AniProxy.opened_node.toggle();
            map_view.locked = false;
        }
    }

    private void _put_pos_to_dash_path_endpoint_list(SetPosition pos) {
        for (BaseKnowledgeSet parent : pos.set.parents()) {
            SetPosition parent_pos = KnowledgeSetsData.get_pos_of_set(parent);
            float x1 = (float) parent_pos.circle_center_dp_left;
            float y1 = (float) parent_pos.text_dp_top + 24 + (parent_pos.set.is_checkpoint() ? 0 : 18);
            float x2 = (float) pos.circle_center_dp_left;
            float y2 = (float) pos.circle_dp_top - 8;

            DashPathEndpoint p1 = DashPathEndpoint.build_by_dp_point(x1, y1, x2, y2);
            map_view.dash_path_endpoint_list.add(p1);
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

            SetPosition pos = new SetPosition(set, list.size() + 1, map_view);

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

    public static class AniProxy {
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
                    BaseUtils.dp_to_int_px(47),
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

                circle_view.set_radius_animate((float) SetPosition.CIRCLE_RADIUS_DP, ANIMATE_DRUATION);
                set_icon_view_margin(icon_view_left_margin_px, icon_view_top_margin_px);

            } else {
                is_open = true;
                opened_node = this;

                circle_view.set_radius_animate((float) map_view.SCREEN_HEIGHT_DP, ANIMATE_DRUATION);
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
                    .setDuration(ANIMATE_DRUATION);

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
