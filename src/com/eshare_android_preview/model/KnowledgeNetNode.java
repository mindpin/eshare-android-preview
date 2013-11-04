package com.eshare_android_preview.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.eshare_android_preview.model.parse.KnowledgeNet;

// 课程节点
public class KnowledgeNetNode extends LearningResource implements Serializable {
    public static final String XML_PATH = "javascript_core.xml";

    /**
	 * 
	 */
	private static final long serialVersionUID = 216785L;
	public String node_id;
	public String name;
	public String desc;
	public List<String> list_parents = new ArrayList<String>();
	public List<String> list_children = new ArrayList<String>();

	public KnowledgeNetNode(String node_id, String name, String desc,
                            List<String> list_parents, List<String> list_children) {
		super();
		this.node_id = node_id;
		this.name = name;
		this.desc = desc;
		this.list_parents = list_parents;
		this.list_children = list_children;
	}

    @Override
    public String get_note_foreign_key_id() {
        return this.node_id;
    }

    public static List<KnowledgeNetNode> all(){
        return KnowledgeNet.parse_xml(XML_PATH);
    }

    public static KnowledgeNetNode find(String node_id){
        List<KnowledgeNetNode> list = all();
        for(KnowledgeNetNode node:list){
            if (node.node_id.equals(node_id)) {
                return node;
            }
        }
        return null;
    }

    public static List<KnowledgeNetNode> find(List<String> node_ids){
        ArrayList<KnowledgeNetNode> node_list = new ArrayList<KnowledgeNetNode>();
        List<KnowledgeNetNode> list = all();
        for(String node_id : node_ids){
            for(KnowledgeNetNode node : list){
                if (node.node_id.equals(node_id)) {
                    node_list.add(node);
                }
            }
        }
        return node_list;
    }


}
