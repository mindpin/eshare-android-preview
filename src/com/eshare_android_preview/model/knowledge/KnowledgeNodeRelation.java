package com.eshare_android_preview.model.knowledge;


public class KnowledgeNodeRelation {
	public String parent_id;
	public String child_id;
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getChild_id() {
		return child_id;
	}
	public void setChild_id(String child_id) {
		this.child_id = child_id;
	}
	
	
	public boolean included(String node_id){
		if (node_id == null) {
			return false;
		}
		return node_id.equals(this.child_id) || node_id.equals(this.parent_id);
	}
}
