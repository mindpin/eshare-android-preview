package com.eshare_android_preview.base.view.knowledge_map;

import com.eshare_android_preview.model.knowledge.BaseKnowledgeSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 13-12-12.
 */
public class KnowledgeSetsData {
    public HashMap<Integer, List<SetPosition>> deep_hashmap;
    public HashMap<BaseKnowledgeSet, SetPosition> pos_hashmap;
    public double paper_bottom;

    public KnowledgeSetsData() {
        deep_hashmap = new HashMap<Integer, List<SetPosition>>();
        pos_hashmap = new HashMap<BaseKnowledgeSet, SetPosition>();
        paper_bottom = 0;
    }

    public void put_set_in_map(BaseKnowledgeSet set, KnowledgeMapView map_view) {
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

    public SetPosition get_pos_of_set(BaseKnowledgeSet set) {
        return pos_hashmap.get(set);
    }
}
