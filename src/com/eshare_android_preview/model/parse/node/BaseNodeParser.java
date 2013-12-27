package com.eshare_android_preview.model.parse.node;

import java.io.IOException;
import java.io.InputStream;

import com.eshare_android_preview.application.EshareApplication;

public abstract class BaseNodeParser{
	String xml_path;
    public String name;
	
	protected BaseNodeParser(String name) {
        String xml_path = "knowledge_nets/" + name + ".xml";
		this.xml_path = xml_path;
        this.name = name;
	}
	protected InputStream getInputStream(){
		try {
			return EshareApplication.context.getResources().getAssets().open(this.xml_path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
