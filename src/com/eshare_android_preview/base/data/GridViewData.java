package com.eshare_android_preview.base.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eshare_android_preview.R;

public class GridViewData {
	public static List<Map<String, Object>> get_tab_home_grid_view_data(){
		List<Map<String, Object>> lists = new ArrayList<Map<String,Object>>();
		
		String[] names = new String[]{"计划","笔记","收藏","学习","题目","分组"};
		
		int[]    imgs = new int[]{
					R.drawable.tab_qa_btn_image_bg_normal,
					R.drawable.tab_find_frd_normal,
					R.drawable.tab_message_btn_image_bg_selected,
					R.drawable.tab_find_frd_normal,
					R.drawable.tab_message_btn_image_bg_selected,
					R.drawable.tab_home_btn_image_bg_normal
				};
		
		for (int i = 0; i < names.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", names[i]);
			map.put("img", imgs[i]);
			lists.add(map);
		}
		return lists;
	}
}
