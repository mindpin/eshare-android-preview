package com.eshare_android_preview.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fushang318 on 13-11-5.
 */
public class OldQuestionChoice implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int index;
    public String sym;
    public String content;

    public OldQuestionChoice(int index, String sym, String content){
        this.index = index;
        this.sym = sym;
        this.content = content;
    }
    
    public static String syms(List<OldQuestionChoice> list){
    	String syms = "";
    	for (OldQuestionChoice choice : list) {
    		syms += choice.sym;
        }
    	return syms;
    }
}
