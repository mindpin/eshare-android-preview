package com.eshare_android_preview.base.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eshare_android_preview.R;

public class GridViewData {
	public static List<Map<String, Object>> get_tab_home_grid_view_data() {
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();

		String[] names = new String[] { "计划", "笔记", "收藏", "学习", "题目", "分组" };

		int[] icons = new int[] { 
				R.string.icon_list,
				R.string.icon_pencil,
				R.string.icon_star,
				R.string.icon_leaf,
				R.string.icon_lightbulb,
				R.string.icon_group };

		for (int i = 0; i < names.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", names[i]);
			map.put("icon", icons[i]);
			lists.add(map);
		}
		return lists;
	}
}
