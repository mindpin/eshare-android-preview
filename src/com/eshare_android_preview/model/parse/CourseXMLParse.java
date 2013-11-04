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
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;

import com.eshare_android_preview.application.EshareApplication;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Course;
import com.eshare_android_preview.model.database.CourseDBHelper;

// xml pull 解析
public class CourseXMLParse {
	public static List<Course> parse_xml(String xml_path){
		AssetManager asset = EshareApplication.context.getAssets();
		try {
			InputStream inputStream = asset.open(HttpApi.course_xml_path);
			List<Course> list = doc_parse_inputstream(inputStream);
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Element get_root(){
		AssetManager asset = EshareApplication.context.getAssets();
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		try {
			InputStream inputStream = asset.open(HttpApi.course_xml_path);
			
			DocumentBuilder builder= factory.newDocumentBuilder();
			
			Document document= builder.parse(inputStream);
			Element root=document.getDocumentElement();
			return root;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static  List<String> pull_parse_inputstream(InputStream inputStream){
		List<String> list= new ArrayList<String>();
		try {
			XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
			XmlPullParser parser =factory.newPullParser();
			parser.setInput(inputStream, "utf-8");
			
			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					String tagName = parser.getName();
					if (tagName.equals("course")) {
						list.add(parser.getAttributeValue(null, "title"));
						CourseDBHelper.create(new Course(parser.getAttributeValue(null, "title"), false));
					}
				}
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	public static int doc_parse_plan_count(){
		Element root= get_root();
		NodeList notes=root.getElementsByTagName("course");
		return notes.getLength();
	}
	
	public static int doc_parse_plan_id(int id){
		Element root= get_root();
		NodeList notes=root.getElementsByTagName("course");
		Element item=(Element)notes.item(id);
		String title = item.getAttribute("title");
		CourseDBHelper.create(new Course(title, false));
		return ++id;
	}
	
	
	public static  List<Course> doc_parse_inputstream(InputStream inputStream){
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document document=builder.parse(inputStream);
			Element root=document.getDocumentElement();
			
			List<Course> list= new ArrayList<Course>();
			NodeList notes=root.getElementsByTagName("course");
			
			for (int i = 0; i < notes.getLength(); i++) {
				Element item=(Element)notes.item(i);
				String title = item.getAttribute("title");
				CourseDBHelper.create(new Course(title, false));
				list.add(new Course(title, false));
			}
			return list;
			
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
