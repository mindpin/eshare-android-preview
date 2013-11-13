package com.eshare_android_preview.model.knowledge;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseKnowledgeNode extends BaseParse{
	public static KnowledgeNode find_node_id_by(String node_id){
		for (int i = 0; i < fileParse.node_list.size(); i++) {
			if (fileParse.node_list.get(i).id.equals(node_id)) {
				return fileParse.node_list.get(i);
			}
		}
		return null;
	}
	
	public List<KnowledgeNodeRelation> find_node_relation_by(String node_id){
		List<KnowledgeNodeRelation> node_relation_list = new ArrayList<KnowledgeNodeRelation>();
		for (int i = 0; i < fileParse.node_relation_list.size(); i++) {
			if (fileParse.node_relation_list.get(i).included(node_id)) {
				node_relation_list.add(fileParse.node_relation_list.get(i));
			}
		}
		return node_relation_list;
	}
}
