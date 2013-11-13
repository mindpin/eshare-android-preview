package com.eshare_android_preview.model.knowledge;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeSet extends BaseKnowledgeSet{
	public String id;
	public String name;
	public String icon;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}

	//  ---------method--------
	public List<KnowledgeNode> nodes(){
		return find_node_list_by_node_set_id(this.id);
	}
	
	public KnowledgeNode first_node(){
		if (nodes().size() == 0) {
			return null;
		}
		return nodes().get(0);
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

	private List<KnowledgeNode> find_node_list_by_node_set_id(String node_set_id){
		List<KnowledgeNode> node_list = new ArrayList<KnowledgeNode>();
		for (int i = 0; i < fileParse.node_list.size(); i++) {
			if (fileParse.node_list.get(i).node_set_id.equals(node_set_id)) {
				node_list.add(fileParse.node_list.get(i));
			}
		}
		return node_list;
	}
}
