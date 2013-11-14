package com.eshare_android_preview.model.knowledge;

import java.util.List;

public class KnowledgeNet extends BaseParse{
	public static KnowledgeSet first_set;
	private static final KnowledgeNet m_instance = new KnowledgeNet(); 
	public static KnowledgeNet instance(){
		return m_instance;
	}

	public List<KnowledgeSet> sets(){
		return fileParse.node_set_list;
	}
	
	public List<KnowledgeCheckpoint> checkpoints(){
		return fileParse.check_point_list;
	}
}
