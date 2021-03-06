package com.eshare_android_preview.view.knowledge_map;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.eshare_android_preview.R;
import com.eshare_android_preview.http.c.UserData;
import com.eshare_android_preview.http.i.knowledge.INetHasChildren;
import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeSet;
import com.eshare_android_preview.utils.BaseUtils;
import com.eshare_android_preview.view.LockableScrollView;
import com.eshare_android_preview.view.dash_path_view.DashPathEndpoint;
import com.eshare_android_preview.view.dash_path_view.DashPathView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 13-12-12.
 */
public class KnowledgeMapView extends LockableScrollView {
    public int SCREEN_WIDTH_DP, SCREEN_HEIGHT_DP, GRID_WIDTH_DP, GRID_HEIGHT_DP;

    public RelativeLayout nodes_paper;
    public RelativeLayout lines_paper;

    public DashPathView dash_path_view;
    private ArrayList<DashPathEndpoint> dash_path_endpoint_list;

    public KnowledgeSetsData kdata;

    public KnowledgeMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init() {
        removeAllViews();

        kdata = new KnowledgeSetsData(this);

        View content = inflate(getContext(), R.layout.home_knowledge_map_view, null);
        addView(content);

        nodes_paper = (RelativeLayout) findViewById(R.id.nodes_paper);
        lines_paper = (RelativeLayout) findViewById(R.id.lines_paper);

        _get_screen_size();

        _r_traversal(UserData.instance().get_current_knowledge_net(false));
        _draw_nodes();
        _draw_dash_path_view();
    }

    private void _get_screen_size() {
        BaseUtils.ScreenSize screen_size = BaseUtils.get_screen_size();

        SCREEN_WIDTH_DP = (int) screen_size.width_dp;
        SCREEN_HEIGHT_DP = (int) screen_size.height_dp;

        GRID_WIDTH_DP = SCREEN_WIDTH_DP / 3;
        GRID_HEIGHT_DP = GRID_WIDTH_DP + 30;
    }

    public void _draw_nodes() {
        dash_path_endpoint_list = new ArrayList<DashPathEndpoint>();

        for (List<SetPosition> list : kdata.deep_pos_lists()) {
            int list_size = list.size();
            for (int index = 0; index < list_size; index++) {
                SetPosition pos = list.get(index);

                pos.draw(list_size, index);
                pos.put_data_into_end_point_list(dash_path_endpoint_list);
            }
        }
    }

    public void _draw_dash_path_view() {
        dash_path_view = new DashPathView(getContext());

        dash_path_view.set_dash_path_endpoint_list(dash_path_endpoint_list);
        dash_path_view.set_color(Color.parseColor("#cccccc"));
        dash_path_view.set_dash_icon_radius(3);

        lines_paper.addView(dash_path_view);

        ViewGroup.LayoutParams params = dash_path_view.getLayoutParams();
        params.height = BaseUtils.dp_to_px(kdata.max_grid_dp_bottom);
        dash_path_view.setLayoutParams(params);
    }

    public void _r_traversal(INetHasChildren node) {
        for (IUserKnowledgeSet set : node.children()) {
            kdata.put_set_in_map(set);
            _r_traversal(set);
        }
    }
}
