package com.eshare_android_preview.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// 课程节点
public class Node implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int id;
	public String node_id;
	public String name;
	public String desc;
	public List<String> list_parents = new ArrayList<String>();
	public List<String> list_children = new ArrayList<String>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNode_id() {
		return node_id;
	}

	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<String> getList_parents() {
		return list_parents;
	}

	public void setList_parents(List<String> list_parents) {
		this.list_parents = list_parents;
	}

	public List<String> getList_children() {
		return list_children;
	}

	public void setList_children(List<String> list_children) {
		this.list_children = list_children;
	}

	public Node(String node_id, String name, String desc,
			List<String> list_parents, List<String> list_children) {
		super();
		this.node_id = node_id;
		this.name = name;
		this.desc = desc;
		this.list_parents = list_parents;
		this.list_children = list_children;
	}

	public Node(int id, String node_id, String name, String desc,
			List<String> list_parents, List<String> list_children) {
		super();
		this.id = id;
		this.node_id = node_id;
		this.name = name;
		this.desc = desc;
		this.list_parents = list_parents;
		this.list_children = list_children;
	}

//	public Node(String id, String name, String desc, List<String> list_parents,List<String> list_children) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.desc = desc;
//		this.list_parents = list_parents;
//		this.list_children = list_children;
//	}
	
}
