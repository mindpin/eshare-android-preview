package com.eshare_android_preview.model.knowledge;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeCheckpoint extends BaseKnowledgeSet{
	public String id;
	public List<String> targets;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getTargets() {
		return targets;
	}
	public void setTargets(List<String> targets) {
		this.targets = targets;
	}
	
	public List<KnowledgeSet> learned_sets(){
		List<KnowledgeSet> set_list = new ArrayList<KnowledgeSet>();
		for (int i = 0; i < targets.size(); i++) {
			set_list.add(find_node_set_by(targets.get(i)));
		}
		return set_list;
	}
	public List<KnowledgeSetRelation> relations(){
		return find_set_relation_by(this.id);
	}
	public List<BaseKnowledgeSet> parents(){
		return find_node_set_parent_by(this.id);
	}
	public List<BaseKnowledgeSet> children(){
		return find_node_set_child_by(this.id);
	}
}
