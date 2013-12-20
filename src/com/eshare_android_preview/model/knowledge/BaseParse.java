package com.eshare_android_preview.model.knowledge;

import com.eshare_android_preview.model.parse.node.GistFileParse;

public abstract class BaseParse {
	public static String XML_PATH = "javascript_core.xml";
	public static GistFileParse fileParse;
    static{
        if (fileParse == null) {
            fileParse = new GistFileParse(XML_PATH);
            fileParse.parse();
        }
    }

}
