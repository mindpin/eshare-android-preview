package com.eshare_android_preview.logic;

import java.util.ArrayList;
import java.util.List;

import com.eshare_android_preview.model.KnowledgeNet;
import com.eshare_android_preview.model.Node;

public class HttpApi {
	public static final String xml_path = "javascript_core.xml";
	
	public static List<String> get_knowledge_net_category(){
		List<String> categorys = new ArrayList<String>();
		String[] cates = {"java",".net","Ruby","C","C++","Php","javascript"};
		for (int i = 0; i < cates.length; i++) {
			categorys.add(cates[i]);
		}
		return categorys;
	}
	public static List<Node> get_nodes(String category){
		List<Node> list = null;
		if (category.equals("javascript")) {
			list = KnowledgeNet.parse_xml(xml_path);
		}
		return list;
	}
	public static List<Node> get_nodes_by_node_ids(List<String> node_ids){
		return KnowledgeNet.array_node_list(KnowledgeNet.parse_xml(xml_path), node_ids);
	}
}
