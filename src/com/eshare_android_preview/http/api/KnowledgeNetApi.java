package com.eshare_android_preview.http.api;

import com.eshare_android_preview.base.http.EshareGetRequest;
import com.eshare_android_preview.model.knowledge.KnowledgeNet;

public class KnowledgeNetApi {
	public static final String 经验 = "/knowledge_nets/";
	public static final String 获取 = "/exp_info";
	
	public static KnowledgeNet exp_info(String course_or_id){
		try {
			return new EshareGetRequest<KnowledgeNet>(经验 + course_or_id + 获取) {
				@Override
				public KnowledgeNet on_success(String response_text)throws Exception {
					
					return null;
				}
			}.go();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
