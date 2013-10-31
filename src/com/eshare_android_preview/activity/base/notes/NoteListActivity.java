package com.eshare_android_preview.activity.base.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Note;
import com.eshare_android_preview.widget.adapter.NotesAdapter;

import java.util.List;

public class NoteListActivity extends EshareBaseActivity{
	ListView list_view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.n_note_list);
		
		hide_head_setting_button();
        set_head_text(R.string.note);

        super.onCreate(savedInstanceState);
    }

    private void load_list() {
        List<Note> note_list = HttpApi.HANote.all();
        if (note_list.size() == 0) {
            process_when_note_list_is_empty();
        } else {
            build_note_list_adapter(note_list);
        }
    }

    private void build_note_list_adapter(List<Note> note_list) {
        NotesAdapter adapter = new NotesAdapter(this);
        adapter.add_items(note_list);

        list_view = (ListView) findViewById(R.id.list_view);
        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list_view, View list_item, int item_id, long position) {
                Note note = (Note) list_item.getTag(R.id.adapter_item_tag);

                Intent intent = new Intent(NoteListActivity.this, NoteShowActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(NoteShowActivity.ExtraKeys.NOTE, note);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void process_when_note_list_is_empty() {
        View note_list_empty_tip_tv = findViewById(R.id.note_list_empty_tip_tv);
        note_list_empty_tip_tv.setVisibility(View.VISIBLE);

        list_view = (ListView)findViewById(R.id.list_view);
        list_view.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        load_list();
        super.onResume();
    }
}
