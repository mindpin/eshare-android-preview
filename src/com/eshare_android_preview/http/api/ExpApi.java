package com.eshare_android_preview.http.api;

import com.eshare_android_preview.http.base.EshareGetRequest;
import com.eshare_android_preview.http.model.CurrentState;
import com.google.gson.Gson;

public class ExpApi {
	public static final String 经验 = "/knowledge_nets/";
	public static final String 获取 = "/exp_info";
	
	public static CurrentState exp_info(String net_id){
		try {
			return new EshareGetRequest<CurrentState>(经验 + net_id + 获取) {
				@Override
				public CurrentState on_success(String response_text)throws Exception {
					Gson gson = new Gson();
					return gson.fromJson(response_text, CurrentState.class);
				}
			}.go();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
