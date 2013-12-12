package com.eshare_android_preview.activity.base;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.view.knowledge_map.KnowledgeMapView;
import com.eshare_android_preview.base.view.knowledge_map.SetPosition;
import com.eshare_android_preview.model.knowledge.BaseKnowledgeSet;
import com.eshare_android_preview.model.knowledge.KnowledgeNet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeActivity extends EshareBaseActivity {
    public static KnowledgeMapView map_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.home);
        _init_knowledge_net();
        super.onCreate(savedInstanceState);
    }

    private void _init_knowledge_net() {
        map_view = (KnowledgeMapView) findViewById(R.id.knowledge_map_view);

        KnowledgeSetsData.init();

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

                pos._put_knowledge_node_on_grid();
                pos._put_pos_to_dash_path_endpoint_list();
            }
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

    public static class KnowledgeSetsData {
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

        public static SetPosition get_pos_of_set(BaseKnowledgeSet set) {
            return pos_hashmap.get(set);
        }
    }
}
