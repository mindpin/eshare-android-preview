package com.eshare_android_preview.model.knowledge;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseKnowledgeSet extends BaseParse{
	private final String SET_TYPE = "set-";
	private final String CHECK_POINT_TYPE = "checkpoint-";

	public List<KnowledgeSetRelation> relations(){
		return null;
	}
	public List<BaseKnowledgeSet> parents(){
		return null;
	}
	public List<BaseKnowledgeSet> children(){
		return null;
	}
	
	
	public List<KnowledgeSetRelation> find_set_relation_by(String node_set_id){
		List<KnowledgeSetRelation> set_relation_lsit = new ArrayList<KnowledgeSetRelation>();
		for (int i = 0; i < fileParse.set_relation_list.size(); i++) {
			KnowledgeSetRelation relation = fileParse.set_relation_list.get(i);
			if (node_set_id.equals(relation.child_id) || node_set_id.equals(relation.parent_id) ) {
				set_relation_lsit.add(relation);
			}
		}
		return set_relation_lsit;
	}
	public List<BaseKnowledgeSet> find_node_set_parent_by(String node_set_id){
		List<BaseKnowledgeSet> base_node_set_list = new ArrayList<BaseKnowledgeSet>();
		for (int i = 0; i < fileParse.set_relation_list.size(); i++) {
			if (node_set_id.equals(fileParse.set_relation_list.get(i).child_id)) {
				base_node_set_list.add(find_base_set_by(fileParse.set_relation_list.get(i).parent_id));
			}
		}
		return base_node_set_list;
	}
	
	public List<BaseKnowledgeSet> find_node_set_child_by(String node_set_id){
		List<BaseKnowledgeSet> base_node_set_list = new ArrayList<BaseKnowledgeSet>();
		for (int i = 0; i < fileParse.set_relation_list.size(); i++) {
			if (node_set_id.equals(fileParse.set_relation_list.get(i).parent_id)) {
				base_node_set_list.add(find_base_set_by(fileParse.set_relation_list.get(i).child_id));
			}
		}
		return base_node_set_list;
	}
	
	public BaseKnowledgeSet find_base_set_by(String base_set_id){
		BaseKnowledgeSet base_set = null;
		if (base_set_id.indexOf(SET_TYPE) == 1) {
			base_set = find_node_set_by(base_set_id);
		}
		if (base_set_id.indexOf(CHECK_POINT_TYPE) == 1) {
			base_set =  find_check_point_by(base_set_id);
		}
		return base_set;
	}
	
	public KnowledgeSet find_node_set_by(String node_set_id){
		for (int i = 0; i < fileParse.node_set_list.size(); i++) {
			if (node_set_id.equals(fileParse.node_set_list.get(i).id)) {
				return fileParse.node_set_list.get(i);
			}
		}
		return null;
	}
	
	public KnowledgeCheckpoint find_check_point_by(String check_point_id){
		for (int i = 0; i < fileParse.check_point_list.size(); i++) {
			if (check_point_id.equals(fileParse.check_point_list.get(i).id)) {
				return fileParse.check_point_list.get(i);
			}
		}
		return null;
	}
}
