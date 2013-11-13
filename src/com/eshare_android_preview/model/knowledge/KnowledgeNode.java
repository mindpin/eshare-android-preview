package com.eshare_android_preview.model.knowledge;

import java.util.ArrayList;
import java.util.List;


public class KnowledgeNode extends BaseKnowledgeNode{
	public String node_set_id;
	public String id;
	public String name;
	public boolean required;
	public String desc;
	
	
	public String getNode_set_id() {
		return node_set_id;
	}
	public void setNode_set_id(String node_set_id) {
		this.node_set_id = node_set_id;
	}
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
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public void setRequired(String required) {
		this.required = required.equals("true");
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}	
	
	// ------------method-------------
	public List<KnowledgeNodeRelation> relations(){
		return find_node_relation_by(this.id);
	}
	
	public List<KnowledgeNode> parents(){
		return find_node_parent_by(this.id);
	}
	public List<KnowledgeNode> children(){
		return find_node_child_by(this.id);
	}
	

	private List<KnowledgeNode> find_node_parent_by(String node_id){
		List<KnowledgeNode> node_list = new ArrayList<KnowledgeNode>();
		for (int i = 0; i < fileParse.node_relation_list.size(); i++) {
			if (fileParse.node_relation_list.get(i).child_id.equals(node_id)) {
				KnowledgeNode node = find_node_id_by(fileParse.node_relation_list.get(i).parent_id);
				node_list.add(node);
			}
		}
		return node_list;
	}
	
	private List<KnowledgeNode> find_node_child_by(String node_id){
		List<KnowledgeNode> node_list = new ArrayList<KnowledgeNode>();
		for (int i = 0; i < fileParse.node_relation_list.size(); i++) {
			if (fileParse.node_relation_list.get(i).parent_id.equals(node_id)) {
				KnowledgeNode node = find_node_id_by(fileParse.node_relation_list.get(i).child_id);
				node_list.add(node);
			}
		}
		return node_list;
	}
}
