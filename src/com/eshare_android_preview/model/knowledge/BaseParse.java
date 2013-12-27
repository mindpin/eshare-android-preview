package com.eshare_android_preview.model.knowledge;

import com.eshare_android_preview.model.parse.node.GistFileParse;

import java.util.ArrayList;

public abstract class BaseParse {
	public static String JAVASCRIPT_CORE_XML_PATH = "javascript_core.xml";
    public static String ENGLISTH_XML_PATH = "english.xml";
    public static String TEST_XML_PATH = "javascript_core_for_test.xml";
    public static ArrayList<GistFileParse> file_parses;

    static{
        if(file_parses == null){
            file_parses = new ArrayList<GistFileParse>();

            GistFileParse javascript_core_file_parse = new GistFileParse(JAVASCRIPT_CORE_XML_PATH);
            javascript_core_file_parse.parse();

            GistFileParse english_file_parse = new GistFileParse(ENGLISTH_XML_PATH);
            english_file_parse.parse();
            GistFileParse test_file_parse = new GistFileParse(TEST_XML_PATH);
            test_file_parse.parse();

            file_parses.add(javascript_core_file_parse);
            file_parses.add(english_file_parse);
            file_parses.add(test_file_parse);
        }
    }

}
