package com.eshare_android_preview.widget.adapter;

import java.util.HashMap;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.adapter.EshareBaseAdapter;
import com.eshare_android_preview.model.Plan;

public class PlanAdapter extends EshareBaseAdapter<Plan>{
	
	//控制CheckBox选中情况
	private static HashMap<Integer,Boolean> isSelected;
	
	public PlanAdapter(EshareBaseActivity activity) {
		super(activity);
		isSelected=new HashMap<Integer, Boolean>();
		initData();
	}
	private void initData(){//初始化isSelected的数据  
        for(int i=0;i<getCount();i++){  
            getIsSelected().put(i,false);  
              
        }     
    }
	public static HashMap<Integer, Boolean> getIsSelected(){  
        return isSelected;  
    }

	@Override
	public View inflate_view() {
		return inflate(R.layout.p_plan_list_item, null);
	}

	@Override
	public BaseViewHolder build_view_holder(View view) {
		ViewHolder view_holder      = new ViewHolder();
        view_holder.info_tv = (TextView) view.findViewById(R.id.info_tv);
        
        view_holder.item_tv = (TextView)view.findViewById(R.id.item_tv);
        view_holder.item_checked = (CheckBox)view.findViewById(R.id.item_checked);
//        view_holder.item_iv = (ImageView)view.findViewById(R.id.item_iv);
        
        view.setTag(view_holder);
		return view_holder;
	}

	@Override
	public void fill_with_data(BaseViewHolder holder,Plan item, int position) {
		 ViewHolder view_holder = (ViewHolder) holder;
		 view_holder.info_tv.setTag(R.id.tag_note_uuid, item);
		 view_holder.item_tv.setText(item.content);
//	     view_holder.item_iv.setBackgroundResource((Integer)item.get("img"));
	}
	private class ViewHolder implements BaseViewHolder {
    	TextView info_tv;
    	// 个人列表子项显示
    	TextView  item_tv;
    	CheckBox item_checked;
//        ImageView item_iv;
	}
}
