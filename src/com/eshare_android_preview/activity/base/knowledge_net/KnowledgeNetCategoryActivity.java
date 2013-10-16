package com.eshare_android_preview.activity.base.knowledge_net;

import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.widget.adapter.KnoweledgeNetCategoryAdapter;

public class KnowledgeNetCategoryActivity extends EshareBaseActivity{
	EditText search_edit_tv;
	Drawable d_default,d_clear;
	ListView list_view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kn_knowledge_net_category);
		load_search_et();
		load_list_view();
	}
	private void load_search_et() {
		d_default = getResources().getDrawable(R.drawable.txt_search_default);
	    d_clear = getResources().getDrawable(R.drawable.txt_search_clear);
		search_edit_tv = (EditText) findViewById(R.id.search_edit_tv);
		search_edit_tv.addTextChangedListener(tbxSearch_TextChanged);
		search_edit_tv.setOnTouchListener(txtSearch_OnTouch);
	}
	/**
     * 动态搜索
     */
    private TextWatcher tbxSearch_TextChanged = new TextWatcher() {
        //缓存上一次文本框内是否为空
        private boolean isnull = true;
       
        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(s)) {
                if (!isnull) {
                	search_edit_tv.setCompoundDrawablesWithIntrinsicBounds(null,null, d_default, null);
                    isnull = true;
                }
            } else {
                if (isnull) {
                	search_edit_tv.setCompoundDrawablesWithIntrinsicBounds(null,null, d_clear, null);
                    isnull = false;
                }
            }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,int after) {
        }
        /**
         * 随着文本框内容改变动态改变列表内容
         */
        @Override
        public void onTextChanged(CharSequence s, int start, int before,int count) {
        }
    };
    private OnTouchListener txtSearch_OnTouch = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int curX = (int) event.getX();
                if (curX > v.getWidth() - 38
                        && !TextUtils.isEmpty(search_edit_tv.getText())) {
                	search_edit_tv.setText("");
                    int cacheInputType = search_edit_tv.getInputType();// backup  the input type
                    search_edit_tv.setInputType(InputType.TYPE_NULL);// disable soft input
                    search_edit_tv.onTouchEvent(event);// call native handler
                    search_edit_tv.setInputType(cacheInputType);// restore input  type
                    return true;// consume touch even
                }
                break;
            }
            return false;
        }
    };
	
	
	private void load_list_view() {
		list_view = (ListView)findViewById(R.id.list_view);
		List<String> node_list = HttpApi.get_knowledge_net_category();
		KnoweledgeNetCategoryAdapter adapter = new KnoweledgeNetCategoryAdapter(this);
		adapter.add_items(node_list);
		list_view.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		list_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list_view, View list_item,int item_id, long position) {
				TextView info_tv = (TextView) list_item.findViewById(R.id.info_tv);
				String item = (String) info_tv.getTag(R.id.tag_note_uuid);
//				open_activity(KnowledgeNetActivity.class);
				Bundle bundle = new Bundle();
		        bundle.putSerializable("item", item);

		        Intent intent = new Intent(KnowledgeNetCategoryActivity.this,KnowledgeNetActivity.class);
		        intent.putExtras(bundle);
		        startActivity(intent);
				
			}
		});
	}
}
