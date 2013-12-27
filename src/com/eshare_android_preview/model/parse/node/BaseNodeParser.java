package com.eshare_android_preview.model.parse.node;

import java.io.IOException;
import java.io.InputStream;

import com.eshare_android_preview.application.EshareApplication;

public abstract class BaseNodeParser{
	String xml_path;
	
	protected BaseNodeParser(String xml_path) {
		this.xml_path = xml_path;
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
