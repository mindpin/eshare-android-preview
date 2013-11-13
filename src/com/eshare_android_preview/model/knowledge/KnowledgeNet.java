package com.eshare_android_preview.model.knowledge;

import java.util.HashMap;

public class KnowledgeNet extends BaseParse{
	private static final KnowledgeNet m_instance = new KnowledgeNet(); 
	public static KnowledgeNet instance(){
		return m_instance;
	}

	public HashMap<String, KnowledgeSet> sets(){
		return fileParse.node_set_map;
	}
	
	public HashMap<String, KnowledgeCheckpoint> checkpoints(){
		return fileParse.check_point_map;
	}
	
	public KnowledgeSet first_set(){
		for (int i = 0; i < sets().size(); i++) {
			if (sets().get(i).parents.size() == 0) {
				return sets().get(i);
			}
		}
		return null;
	}
}
