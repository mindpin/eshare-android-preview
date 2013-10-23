package com.eshare_android_preview.activity.base.tab_activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;




@SuppressLint("WorldReadableFiles")
public class FavourateActivity extends EshareBaseActivity{
	
	private static final String FAVOURATE_IDS = "favourate_ids";  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_favourate);
		
		SharedPreferences sp = getSharedPreferences(FAVOURATE_IDS, MODE_WORLD_READABLE);  
		String favourate_ids = sp.getString("favourate_ids", "");
		
		Log.d("value = ", favourate_ids);
		
		String[] strArray = favourate_ids.split(",");
		int[] favourate_ids_arr = new int[strArray.length];
		for(int i = 0; i < strArray.length; i++) {
			favourate_ids_arr[i] = Integer.parseInt(strArray[i]);
		}
	}
	
	
}