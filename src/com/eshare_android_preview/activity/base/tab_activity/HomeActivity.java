package com.eshare_android_preview.activity.base.tab_activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.model.knowledge.KnowledgeNet;
import com.eshare_android_preview.model.knowledge.KnowledgeSet;

import java.util.List;


public class HomeActivity extends EshareBaseActivity {

    RelativeLayout nodes_paper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.tab_home);

        _init_knowledge_net();

		super.onCreate(savedInstanceState);
	}

    private void _init_knowledge_net() {
        List<KnowledgeSet> sets = KnowledgeNet.instance().sets;
        nodes_paper = (RelativeLayout) findViewById(R.id.nodes_paper);

        for(KnowledgeSet set : sets) {
            int left = 100;
            int top = set.deep * 100;
            _put_knowledge_node(left, top);
        }
    }

    private void _put_knowledge_node(int left, int top) {
        ImageView iv = new ImageView(this);
        iv.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_c50ce00_circle_flat));
        int px = BaseUtils.dp_to_int_px(60);
        RelativeLayout.LayoutParams layout_params = new RelativeLayout.LayoutParams(px, px);
        layout_params.setMargins(BaseUtils.dp_to_int_px(left), BaseUtils.dp_to_int_px(top), 0, 0);
        iv.setLayoutParams(layout_params);
        nodes_paper.addView(iv);
    }
	
	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
