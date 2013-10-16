package com.eshare_android_preview.widget.adapter;

import android.view.View;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.adapter.EshareBaseAdapter;
import com.eshare_android_preview.model.Node;

public class KnowledgeNetAdapter extends EshareBaseAdapter<Node>{

	public KnowledgeNetAdapter(EshareBaseActivity activity) {
		super(activity);
	}

	@Override
	public View inflate_view() {
		return inflate(R.layout.knowledge_net_list_item, null);
	}

	@Override
	public BaseViewHolder build_view_holder(View view) {
		ViewHolder view_holder      = new ViewHolder();
        view_holder.info_tv = (TextView) view.findViewById(R.id.info_tv);
        
        view_holder.item_tv = (TextView)view.findViewById(R.id.item_tv);
//        view_holder.item_iv = (ImageView)view.findViewById(R.id.item_iv);
		return view_holder;
	}

	@Override
	public void fill_with_data(BaseViewHolder holder,Node item, int position) {
		 ViewHolder view_holder = (ViewHolder) holder;
		 view_holder.info_tv.setTag(R.id.tag_note_uuid, item);
		 view_holder.item_tv.setText(item.name);
//	     view_holder.item_iv.setBackgroundResource((Integer)item.get("img"));
	}
	private class ViewHolder implements BaseViewHolder {
    	TextView info_tv;
    	// 个人列表子项显示
    	TextView  item_tv;
//        ImageView item_iv;
	}
}
