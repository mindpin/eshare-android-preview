package com.eshare_android_preview.widget.adapter;

import java.util.Map;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.adapter.EshareBaseAdapter;

public class GridViewAdapter extends EshareBaseAdapter<Map<String, Object>>{

	public GridViewAdapter(EshareBaseActivity activity) {
		super(activity);
	}

	@Override
	public View inflate_view() {
		return inflate(R.layout.group_item_view, null);
	}

	@Override
	public BaseViewHolder build_view_holder(View view) {
		ViewHolder view_holder      = new ViewHolder();
        view_holder.info_tv = (TextView) view.findViewById(R.id.info_tv);
        
        view_holder.group_item_tv = (TextView)view.findViewById(R.id.group_item_tv);
        view_holder.group_item_iv = (ImageView)view.findViewById(R.id.group_item_iv);
		return view_holder;
	}

	@Override
	public void fill_with_data(BaseViewHolder holder,Map<String, Object> item, int position) {
		 ViewHolder view_holder = (ViewHolder) holder;
		 view_holder.info_tv.setTag(R.id.tag_note_uuid, item);
		 view_holder.group_item_tv.setText(item.get("text").toString());
	     view_holder.group_item_iv.setBackgroundResource((Integer)item.get("img"));
	}
	private class ViewHolder implements BaseViewHolder {
    	TextView info_tv;
    	
    	// 个人列表子项显示
    	TextView  group_item_tv;
        ImageView group_item_iv;
	}
}
