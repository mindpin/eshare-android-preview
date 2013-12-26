package com.eshare_android_preview.model.knowledge;

import com.eshare_android_preview.base.view.knowledge_map.IHasChildren;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeNet extends BaseParse implements IHasChildren{
	private static KnowledgeNet javascript_core_instance;
    private static KnowledgeNet englisth_instance;

    public List<KnowledgeSet> sets;
    public List<KnowledgeCheckpoint> checkpoints;
    public List<KnowledgeSet> root_sets;

    private KnowledgeNet(int index){
        super();
        this.sets = file_parses.get(index).node_set_list;
        this.checkpoints = file_parses.get(index).check_point_list;
        this.root_sets = file_parses.get(index).root_sets;
    }

    public List<BaseKnowledgeSet> children() {
        ArrayList<BaseKnowledgeSet> re = new ArrayList<BaseKnowledgeSet>();
        for (BaseKnowledgeSet set : root_sets) {
            re.add(set);
        }
        return re;
    }

    public static KnowledgeNet instance(){
        return javascript_core_instance();
    }

	public static KnowledgeNet javascript_core_instance(){
        if(javascript_core_instance == null){
            javascript_core_instance = new KnowledgeNet(0);
        }
		return javascript_core_instance;
	}

    public static KnowledgeNet englisth_instance(){
        if(englisth_instance == null){
            englisth_instance = new KnowledgeNet(1);
        }
        return englisth_instance;
    }

    public static KnowledgeNet instance_for_test(){
        return new KnowledgeNet(2);
    }

}