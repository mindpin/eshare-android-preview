package com.eshare_android_preview.activity.base.notes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.questions.QuestionShowActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.ImageTools;
import com.eshare_android_preview.model.Notes;

public class NotesShowActivity extends EshareBaseActivity{
	TextView note_content;
	ImageView note_img;
	
	Notes notes;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.n_notes_show);
		Intent intent = getIntent();
		notes = (Notes)intent.getExtras().getSerializable("item");
		
		load_ui();
	}
	private void load_ui() {
		note_content = (TextView)findViewById(R.id.note_content);
		note_img = (ImageView)findViewById(R.id.note_img);
		
		note_content.setText(notes.content);
		if (notes.img != null) {
			Bitmap bitmap = ImageTools.bytesToBimap(notes.img);
			note_img.setBackgroundDrawable(ImageTools.bitmapToDrawableByBD(bitmap));
			note_img.setVisibility(View.VISIBLE);
		}
		
	}
	public void click_show_question_but(View view){
		Intent intent = new Intent();
		intent.setClass(NotesShowActivity.this, QuestionShowActivity.class);
		intent.putExtra("question_id", notes.question_id);
		startActivity(intent);
	}
}
