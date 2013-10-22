package com.eshare_android_preview.activity.base.notes;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Notes;
import com.eshare_android_preview.widget.adapter.NotesAdapter;

public class NotesActivity extends EshareBaseActivity{
	ListView list_view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.n_notes);
		
		load_list();
	}
	private void load_list() {
		list_view = (ListView)findViewById(R.id.list_view);
		List<Notes> notes_list = HttpApi.get_notes_list();
		NotesAdapter adapter = new NotesAdapter(this);
		adapter.add_items(notes_list);
		list_view.setAdapter(adapter);
		
		list_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list_view, View list_item,int item_id, long position) {
				TextView info_tv = (TextView) list_item.findViewById(R.id.info_tv);
				Notes node = (Notes) info_tv.getTag(R.id.tag_note_uuid);
				BaseUtils.toast(node.question_id);
			}
		});
	}
}
