package com.eshare_android_preview.model.knowledge;

import java.util.List;

public class KnowledgeNet extends BaseParse{
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

	public static KnowledgeNet instance(){
        if(m_instance == null){
            m_instance = new KnowledgeNet();
        }
		return m_instance;
	}

}