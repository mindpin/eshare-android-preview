package com.eshare_android_preview.model.parse.node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.eshare_android_preview.model.knowledge.KnowledgeCheckpoint;
import com.eshare_android_preview.model.knowledge.KnowledgeNet;
import com.eshare_android_preview.model.knowledge.KnowledgeNode;
import com.eshare_android_preview.model.knowledge.KnowledgeNodeRelation;
import com.eshare_android_preview.model.knowledge.KnowledgeSet;
import com.eshare_android_preview.model.knowledge.KnowledgeSetRelation;

public class GistFileParse extends BaseNodeParser{
	
	public List<KnowledgeSet> node_set_list;
	public List<KnowledgeNode> node_list;
	public List<KnowledgeNodeRelation> node_relation_list;
	public List<KnowledgeCheckpoint> check_point_list;
	public List<KnowledgeSetRelation> set_relation_list;
	
	public 	GistFileParse(String nodeUrl){
		super(nodeUrl);
		this.node_list = new ArrayList<KnowledgeNode>();
		this.node_set_list = new ArrayList<KnowledgeSet>();
		this.node_relation_list = new ArrayList<KnowledgeNodeRelation>();
		this.check_point_list = new ArrayList<KnowledgeCheckpoint>();
		this.set_relation_list = new ArrayList<KnowledgeSetRelation>();
	}
	
	@Override
	public List<KnowledgeNet> parse() {
		DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder  builder= factory.newDocumentBuilder();
			Document document = builder.parse(getInputStream());
			
			Element root=document.getDocumentElement();
			NodeList sets=root.getElementsByTagName("set");
			for(int i=0;i<sets.getLength();i++){
				Element setElement=(Element)(sets.item(i));
				
				KnowledgeSet node_set = new KnowledgeSet(); 
				node_set.setId(setElement.getAttribute("id"));
				node_set.setName(setElement.getAttribute("name"));
				node_set.setIcon(setElement.getAttribute("icon"));
				this.node_set_list.add(node_set);
				
				
				NodeList nodes = setElement.getElementsByTagName("node");
				for (int j = 0; j < nodes.getLength(); j++) {
					Element nodeElement=(Element)(nodes.item(j));
					KnowledgeNode node = new KnowledgeNode();
					node.setNode_set_id(node_set.id);
					node.setId(nodeElement.getAttribute("id"));
					node.setName(nodeElement.getAttribute("name"));
					node.setRequired(nodeElement.getAttribute("required"));
					
					NodeList descNodeList = nodeElement.getElementsByTagName("desc");
					if (descNodeList.getLength() > 0) {
						Element descElement= (Element) descNodeList.item(0);
						node.setDesc(descElement.getNodeValue());
					}
					this.node_list.add(node);
				}
				
				
				
				NodeList parent_child = setElement.getElementsByTagName("parent-child");
				for (int j = 0; j < parent_child.getLength(); j++) {
					Element parent_childElement=(Element)(parent_child.item(j));
					KnowledgeNodeRelation nodeRelation = new KnowledgeNodeRelation();
					nodeRelation.setChild_id(parent_childElement.getAttribute("child"));
					nodeRelation.setParent_id(parent_childElement.getAttribute("parent"));
					this.node_relation_list.add(nodeRelation);
				}
			}
			
			Element checkpointsElement = (Element) root.getElementsByTagName("checkpoints").item(0);
			NodeList checkpoints = checkpointsElement.getElementsByTagName("checkpoint");
			for(int i=0;i<checkpoints.getLength();i++){
				Element checkpointElement=(Element)(checkpoints.item(i));
				KnowledgeCheckpoint checkpoint = new KnowledgeCheckpoint();
				List<String> targets = new ArrayList<String>(); 
				
				checkpoint.setId(checkpointElement.getAttribute("id"));
				NodeList learneds = checkpointElement.getElementsByTagName("learned");
				for (int j = 0; j < learneds.getLength(); j++) {
					Element learnedElement=(Element)(learneds.item(j));
					targets.add(learnedElement.getAttribute("target"));
				}
				
				checkpoint.setTargets(targets);
				this.check_point_list.add(checkpoint);
				
			}
			Element relationsElement = (Element) root.getElementsByTagName("relations").item(0);
			NodeList parent_childs=relationsElement.getElementsByTagName("parent-child");
			for(int i=0;i<parent_childs.getLength();i++){
				Element parent_childElement=(Element)(parent_childs.item(i));
				KnowledgeSetRelation setRelation = new KnowledgeSetRelation();
				setRelation.setChild_id(parent_childElement.getAttribute("child"));
				setRelation.setParent_id(parent_childElement.getAttribute("parent"));
				this.set_relation_list.add(setRelation);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
