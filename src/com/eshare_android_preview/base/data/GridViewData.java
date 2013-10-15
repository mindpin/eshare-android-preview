package com.eshare_android_preview.base.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eshare_android_preview.R;

public class GridViewData {
	public static List<Map<String, Object>> get_tab_home_grid_view_data(){
		List<Map<String, Object>> lists = new ArrayList<Map<String,Object>>();
		
		String[] names = new String[]{"标记","收藏","学习","更多"};
		int[]    imgs = new int[]{R.drawable.tab_address_normal,R.drawable.tab_find_frd_normal,R.drawable.tab_settings_pressed,R.drawable.tab_weixin_normal};
		for (int i = 0; i < names.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", names[i]);
			map.put("img", imgs[i]);
			lists.add(map);
		}
		return lists;
	}
}
