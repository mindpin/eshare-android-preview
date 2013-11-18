package com.eshare_android_preview.model.knowledge;

import com.eshare_android_preview.activity.base.tab_activity.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeNet extends BaseParse implements HomeActivity.IHasChildren{
	private static KnowledgeNet m_instance;

    public List<KnowledgeSet> sets;
    public List<KnowledgeCheckpoint> checkpoints;
    public KnowledgeSet first_set;

    private KnowledgeNet(){
        super();
        this.sets = fileParse.node_set_list;
        this.checkpoints = fileParse.check_point_list;
        this.first_set = fileParse.first_set;
    }

    // 返回所有没有前置节点的 sets
    public List<BaseKnowledgeSet> root_sets() {
        ArrayList<BaseKnowledgeSet> re = new ArrayList<BaseKnowledgeSet>();
        re.add(this.first_set);
        return re;
    }

    public List<BaseKnowledgeSet> children() {
        return root_sets();
    }

	public static KnowledgeNet instance(){
        if(m_instance == null){
            m_instance = new KnowledgeNet();
        }
		return m_instance;
	}

}