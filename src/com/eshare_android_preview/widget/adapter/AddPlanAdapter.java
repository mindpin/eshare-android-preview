package com.eshare_android_preview.widget.adapter;

import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.application.EshareApplication;
import com.eshare_android_preview.model.Plan;
import com.eshare_android_preview.widget.model.ViewHolder;

public class AddPlanAdapter extends BaseAdapter{
	private List<Plan> list;
	private static HashMap<Integer,Boolean> isSelected;
	private LayoutInflater inflater=null;//导入布局 
	
	public AddPlanAdapter(List<Plan> list) {  
        this.list = list;  
        inflater=LayoutInflater.from(EshareApplication.context);  
        isSelected=new HashMap<Integer, Boolean>();  
        initData();  
    }
	private void initData(){
        for(int i=0;i<list.size();i++){  
            getIsSelected().put(i,false);  
              
        }     
    } 
	
	@Override
	public int getCount() {
		return list.size();  
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;    
        if (convertView==null) { 
            convertView=inflater.inflate(R.layout.p_add_plan_list_item, null);
            holder=new ViewHolder();  
            holder.tv=(TextView)convertView.findViewById(R.id.item_tv);  
            holder.cb=(CheckBox)convertView.findViewById(R.id.item_cb);  
            convertView.setTag(holder);
                       
        }else{
            holder=(ViewHolder) convertView.getTag(); 
        }  
        holder.tv.setText(list.get(position).content);  
        // 根据isSelected来设置checkbox的选中状况  
        holder.cb.setChecked(getIsSelected().get(position));     
        return convertView;  
	}
	public static HashMap<Integer, Boolean>getIsSelected(){  
        return isSelected;  
    }  
    public static void setIsSelected(HashMap<Integer, Boolean> isSelected){  
    	AddPlanAdapter.isSelected=isSelected;  
    } 
}
