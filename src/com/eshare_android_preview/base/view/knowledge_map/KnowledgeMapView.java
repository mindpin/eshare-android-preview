package com.eshare_android_preview.base.view.knowledge_map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.view.LockableScrollView;
import com.eshare_android_preview.base.view.dash_path_view.DashPathEndpoint;
import com.eshare_android_preview.base.view.dash_path_view.DashPathView;

import java.util.ArrayList;

/**
 * Created by Administrator on 13-12-12.
 */
public class KnowledgeMapView extends LockableScrollView {
    public RelativeLayout lines_paper;
    public RelativeLayout nodes_paper;

    public int SCREEN_WIDTH_DP, SCREEN_HEIGHT_DP, GRID_WIDTH_DP, GRID_HEIGHT_DP;

    public ArrayList<DashPathEndpoint> dash_path_endpoint_list;
    public DashPathView dash_path_view;

    public KnowledgeMapView(Context context) {
        super(context);
        init();
    }

    public KnowledgeMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KnowledgeMapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        View content = inflate(getContext(), R.layout.home_knowledge_map_view, null);
        this.addView(content);

        nodes_paper = (RelativeLayout) findViewById(R.id.nodes_paper);
        lines_paper = (RelativeLayout) findViewById(R.id.lines_paper);

        get_base_size();

        _init_dash_path_view();
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        SCREEN_WIDTH_DP  = MeasureSpec.getSize(widthMeasureSpec);
//        SCREEN_HEIGHT_DP = MeasureSpec.getSize(heightMeasureSpec);
//
//        GRID_WIDTH_DP  = SCREEN_WIDTH_DP / 3;
//        GRID_HEIGHT_DP = GRID_WIDTH_DP + 30;
//    }

    private void get_base_size() {
        BaseUtils.ScreenSize screen_size = BaseUtils.get_screen_size();

        SCREEN_WIDTH_DP  = (int) screen_size.width_dp;
        SCREEN_HEIGHT_DP = (int) screen_size.height_dp;

        GRID_WIDTH_DP  = SCREEN_WIDTH_DP / 3;
        GRID_HEIGHT_DP = GRID_WIDTH_DP + 30;
    }

    private void _init_dash_path_view() {
        dash_path_view = new DashPathView(getContext());
        dash_path_endpoint_list = new ArrayList<DashPathEndpoint>();
    }
}
