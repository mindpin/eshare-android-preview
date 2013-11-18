package com.eshare_android_preview.model.parse.node;

import java.io.IOException;
import java.io.InputStream;

import com.eshare_android_preview.application.EshareApplication;

public abstract class BaseNodeParser{
	String nodeUrl;
	
	protected BaseNodeParser(String nodeUrl) {
		this.nodeUrl = nodeUrl;
	}
	protected InputStream getInputStream(){
		try {
			return EshareApplication.context.getResources().getAssets().open(this.nodeUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
