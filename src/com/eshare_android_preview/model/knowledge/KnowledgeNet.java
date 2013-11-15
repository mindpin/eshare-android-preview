package com.eshare_android_preview.model.knowledge;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeNet extends BaseParse{
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
    public List<KnowledgeSet> root_sets() {
        ArrayList<KnowledgeSet> re = new ArrayList<KnowledgeSet>();
        re.add(this.first_set);
        return re;
    }

	public static KnowledgeNet instance(){
        if(m_instance == null){
            m_instance = new KnowledgeNet();
        }
		return m_instance;
	}

}