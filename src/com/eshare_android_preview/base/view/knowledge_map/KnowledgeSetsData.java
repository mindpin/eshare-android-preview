package com.eshare_android_preview.base.view.knowledge_map;

import com.eshare_android_preview.model.knowledge.BaseKnowledgeSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 13-12-12.
 */
public class KnowledgeSetsData {
    private KnowledgeMapView map_view;
    private HashMap<BaseKnowledgeSet, SetPosition> pos_hashmap;
    private HashMap<Integer, List<SetPosition>> deep_hashmap;

    public int max_grid_dp_bottom;

    public KnowledgeSetsData(KnowledgeMapView map_view) {
        this.map_view = map_view;
        this.deep_hashmap = new HashMap<Integer, List<SetPosition>>();
        this.pos_hashmap  = new HashMap<BaseKnowledgeSet, SetPosition>();
        this.max_grid_dp_bottom = 0;
    }

    public void put_set_in_map(BaseKnowledgeSet set) {
        List<SetPosition> list = deep_hashmap.get(set.deep);

        if (null == list) {
            list = new ArrayList();
            deep_hashmap.put(set.deep, list);
        }

        SetPosition pos = new SetPosition(set, map_view);

        if (!list.contains(pos)) {
            list.add(pos);
            pos_hashmap.put(set, pos);
            max_grid_dp_bottom = Math.max(pos.grid_dp_bottom, max_grid_dp_bottom);
        }
    }

    public SetPosition get_from(BaseKnowledgeSet set) {
        return pos_hashmap.get(set);
    }

    public Collection<List<SetPosition>> deep_pos_lists() {
        return deep_hashmap.values();
    }
}
