package com.eshare_android_preview.activity.base.notes;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Notes;
import com.eshare_android_preview.widget.adapter.NotesAdapter;

public class NotesActivity extends EshareBaseActivity{
	ListView list_view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.n_notes);
		
		load_list();
		hide_head_setting_button();
        set_head_text(R.string.note);

        super.onCreate(savedInstanceState);
	}
	private void load_list() {
		List<Notes> notes_list = HttpApi.get_notes_list();
        if(notes_list.size() == 0){
            process_when_note_list_is_empty();
        }else{
            build_note_list_adapter(notes_list);
        }
	}

    private void build_note_list_adapter(List<Notes> notes_list) {
		NotesAdapter adapter = new NotesAdapter(this);
		adapter.add_items(notes_list);

		list_view = (ListView)findViewById(R.id.list_view);
		list_view.setAdapter(adapter);
		list_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list_view, View list_item,int item_id, long position) {
				TextView info_tv = (TextView) list_item.findViewById(R.id.info_tv);
				Notes item = (Notes) info_tv.getTag(R.id.tag_note_uuid);
				Bundle bundle = new Bundle();
		        bundle.putSerializable("item", item);

		        Intent intent = new Intent(NotesActivity.this,NotesShowActivity.class);
		        intent.putExtras(bundle);
		        startActivity(intent);
			}
		});

    }

    private void process_when_note_list_is_empty() {
        View note_list_empty_tip_tv = findViewById(R.id.note_list_empty_tip_tv);
        note_list_empty_tip_tv.setVisibility(View.VISIBLE);
    }
}
