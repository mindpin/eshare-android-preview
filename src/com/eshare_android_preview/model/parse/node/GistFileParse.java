package com.eshare_android_preview.model.parse.node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.eshare_android_preview.model.knowledge.BaseKnowledgeSet;
import com.eshare_android_preview.model.knowledge.KnowledgeCheckpoint;
import com.eshare_android_preview.model.knowledge.KnowledgeNet;
import com.eshare_android_preview.model.knowledge.KnowledgeNode;
import com.eshare_android_preview.model.knowledge.KnowledgeNodeRelation;
import com.eshare_android_preview.model.knowledge.KnowledgeSet;
import com.eshare_android_preview.model.knowledge.KnowledgeSetRelation;

public class GistFileParse extends BaseNodeParser{

	public HashMap<String, KnowledgeSet> node_set_map;
	public HashMap<String, KnowledgeNode> node_map;
	public HashMap<String, KnowledgeCheckpoint> check_point_map;
	
	public List<KnowledgeSet> node_set_list;
	public List<KnowledgeNode> node_list;
	public List<KnowledgeCheckpoint> check_point_list;

	public 	GistFileParse(String nodeUrl){
		super(nodeUrl);
		this.node_set_map = new HashMap<String, KnowledgeSet>();
		this.node_map = new HashMap<String, KnowledgeNode>();
		this.check_point_map = new HashMap<String, KnowledgeCheckpoint>();
		
		this.node_set_list = new ArrayList<KnowledgeSet>();
		this.node_list = new ArrayList<KnowledgeNode>();
		this.check_point_list = new ArrayList<KnowledgeCheckpoint>();
	}
	
	public void parse(){
		parse_to_hash();
		return_to_list();
	}
	public void return_to_list(){
		this.node_set_list = (List<KnowledgeSet>) this.node_set_map.values();
		this.node_list = (List<KnowledgeNode>) this.node_map.values();
		this.check_point_list = (List<KnowledgeCheckpoint>) this.check_point_map.values();
		
		KnowledgeNet.first_set = first_set();
	}
	
	public KnowledgeSet first_set(){
		for (KnowledgeSet set : this.node_set_list) {
			if (set.parents.size() == 0) {
				return set;
			}
		}
		return null;
	}
	
	public void parse_to_hash() {
		DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder  builder= factory.newDocumentBuilder();
			Document document = builder.parse(getInputStream());
			
			Element root=document.getDocumentElement();
			NodeList sets=root.getElementsByTagName("set");
			for(int i=0;i<sets.getLength();i++){
				Element setElement=(Element)(sets.item(i));
				
				KnowledgeSet node_set = new KnowledgeSet(setElement.getAttribute("id"),setElement.getAttribute("name"),setElement.getAttribute("icon")); 
				NodeList nodes = setElement.getElementsByTagName("node");
				for (int j = 0; j < nodes.getLength(); j++) {
					Element nodeElement=(Element)(nodes.item(j));
					NodeList descNodeList = nodeElement.getElementsByTagName("desc");
					String desc = "";
					if (descNodeList.getLength() > 0) {
						Element descElement= (Element) descNodeList.item(0);
						desc = descElement.getNodeValue();
					}
					KnowledgeNode node = new KnowledgeNode(node_set.id,nodeElement.getAttribute("id"),nodeElement.getAttribute("name"),nodeElement.getAttribute("required"),desc);
					this.node_map.put(node.id, node);
					node_set.nodes.add(node);
					
				}
				this.node_set_map.put(node_set.id, node_set);
				
				
				NodeList parent_child = setElement.getElementsByTagName("parent-child");
				for (int j = 0; j < parent_child.getLength(); j++) {
					Element parent_childElement=(Element)(parent_child.item(j));
					
					String child_id = parent_childElement.getAttribute("child");
					String parent_id = parent_childElement.getAttribute("parent");
					
					if (node_map.get(parent_id) !=null && node_map.get(child_id) !=null) {
						KnowledgeNodeRelation nodeRelation = new KnowledgeNodeRelation(node_map.get(parent_id),node_map.get(child_id));
						node_map.get(child_id).relations.put(child_id, nodeRelation);
						node_map.get(parent_id).relations.put(parent_id, nodeRelation);
						
						node_map.get(child_id).parents.put(parent_id, node_map.get(parent_id));
						node_map.get(parent_id).children.put(child_id, node_map.get(child_id));
					}
				}
			}
			
			Element checkpointsElement = (Element) root.getElementsByTagName("checkpoints").item(0);
			NodeList checkpoints = checkpointsElement.getElementsByTagName("checkpoint");
			for(int i=0;i<checkpoints.getLength();i++){
				Element checkpointElement=(Element)(checkpoints.item(i));
				
				List<KnowledgeSet> learned_sets = new ArrayList<KnowledgeSet>();
				NodeList learneds = checkpointElement.getElementsByTagName("learned");
				for (int j = 0; j < learneds.getLength(); j++) {
					Element learnedElement=(Element)(learneds.item(j));
					String node_set_id = learnedElement.getAttribute("target");
					learned_sets.add(node_set_map.get(node_set_id));
				}
				KnowledgeCheckpoint checkpoint = new KnowledgeCheckpoint(checkpointElement.getAttribute("id"),learned_sets);
				
				this.check_point_map.put(checkpoint.id, checkpoint);
				
			}
			Element relationsElement = (Element) root.getElementsByTagName("relations").item(0);
			NodeList parent_childs=relationsElement.getElementsByTagName("parent-child");
			for(int i=0;i<parent_childs.getLength();i++){
				Element parent_childElement=(Element)(parent_childs.item(i));
				
				String child_id = parent_childElement.getAttribute("child");
				String parent_id = parent_childElement.getAttribute("parent");
				
				BaseKnowledgeSet child;
				
				if (child_id.indexOf("set-") == 1) {
					child = node_set_map.get(child_id);
				}else{
					child = check_point_map.get(child_id);
				}
				
				BaseKnowledgeSet parent;
				if (parent_id.indexOf("set-") == 1) {
					parent = node_set_map.get(parent_id);
				}else{
					parent = check_point_map.get(parent_id);
				}
				
				if (child != null && parent !=null) {
					KnowledgeSetRelation setRelation = new KnowledgeSetRelation(parent,child);
					child.relations.add(setRelation);
					parent.relations.add(setRelation);
					
					child.parents.add(parent);
					parent.children.add(child);
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
