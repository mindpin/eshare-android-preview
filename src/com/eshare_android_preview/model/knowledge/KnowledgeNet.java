package com.eshare_android_preview.model.knowledge;

import com.eshare_android_preview.activity.base.tab_activity.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeNet extends BaseParse implements HomeActivity.IHasChildren{
	private static KnowledgeNet m_instance;

    public List<KnowledgeSet> sets;
    public List<KnowledgeCheckpoint> checkpoints;
    public List<KnowledgeSet> root_sets;

    private KnowledgeNet(){
        super();
        this.sets = fileParse.node_set_list;
        this.checkpoints = fileParse.check_point_list;
        this.root_sets = fileParse.root_sets;
    }

    public List<BaseKnowledgeSet> children() {
        ArrayList<BaseKnowledgeSet> re = new ArrayList<BaseKnowledgeSet>();
        for (BaseKnowledgeSet set : root_sets) {
            re.add(set);
        }
        return re;
    }

	public static KnowledgeNet instance(){
        if(m_instance == null){
            m_instance = new KnowledgeNet();
        }
		return m_instance;
	}

    public static KnowledgeNet instance_for_test(){
        BaseParse.XML_PATH = "javascript_core_for_test.xml";
        BaseParse.fileParse = null;
        return new KnowledgeNet();
    }

}