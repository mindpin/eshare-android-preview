package com.eshare_android_preview.model.parse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.res.AssetManager;

import com.eshare_android_preview.application.EshareApplication;
import com.eshare_android_preview.model.KnowledgeNetNode;
import com.eshare_android_preview.model.Relation;

// xml dom解析
public class KnowledgeNet {
	public static List<KnowledgeNetNode> parse_xml(String xml_path){
		AssetManager asset = EshareApplication.context.getAssets();
		try {
			InputStream inputStream = asset.open(xml_path);
			List<KnowledgeNetNode> list = doc_parse_inputstream(inputStream);
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static  List<KnowledgeNetNode> doc_parse_inputstream(InputStream inputStream){
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document document=builder.parse(inputStream);
			Element root=document.getDocumentElement();
			
			List<KnowledgeNetNode> nodes_list = new ArrayList<KnowledgeNetNode>();
			NodeList notes=root.getElementsByTagName("node");
			
			for (int i = 0; i < notes.getLength(); i++) {
				Element item=(Element)notes.item(i);
				String id = item.getAttribute("id");
				
				Element name_element = (Element)item.getElementsByTagName("name").item(0);
				String name = name_element.getFirstChild().getNodeValue();
				
				Element desc_element = (Element)item.getElementsByTagName("desc").item(0);
				boolean b = desc_element.hasChildNodes();
				String desc = "";
				if (b) {
					desc = desc_element.getFirstChild().getNodeValue();
				}
				List<String> childe_list = new ArrayList<String>();
				List<String> parent_list = new ArrayList<String>();
				KnowledgeNetNode node = new KnowledgeNetNode(id, name, desc, childe_list, parent_list);
				nodes_list.add(node);
			}
			
			List<Relation> relations_list = new ArrayList<Relation>();
			NodeList relations= root.getElementsByTagName("relation");
			for (int i = 0; i < relations.getLength(); i++) {
				Element item = (Element) relations.item(i);
				
				Element parent_element = (Element)item.getElementsByTagName("parent").item(0);
				String parent_id = parent_element.getAttribute("node_id");
				
				Element child_element = (Element)item.getElementsByTagName("child").item(0);
				String child_id = child_element.getAttribute("node_id");
				
				Relation relation = new Relation(parent_id, child_id);
				relations_list.add(relation);
				
			}
			
			List<KnowledgeNetNode> after_nodes_list = new ArrayList<KnowledgeNetNode>();
			for (int i = 0; i < nodes_list.size(); i++) {
				KnowledgeNetNode node = nodes_list.get(i);
				for (int j = 0; j < relations_list.size(); j++) {
					if (node.node_id.equals(relations_list.get(j).parent_id) ) {
//						System.out.println("parent_id:" + relations_list.get(j).parent_id + " , child_id: " + relations_list.get(j).child_id);
						node.list_children.add(relations_list.get(j).child_id);
					}
					if (node.node_id.equals(relations_list.get(j).child_id) ) {
						node.list_parents.add(relations_list.get(j).parent_id);
					}
				}
				after_nodes_list.add(node);
			}
			return after_nodes_list;
			
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
