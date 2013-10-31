package com.eshare_android_preview.activity.base.notes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.knowledge_net.KnowledgeNetItemActivity;
import com.eshare_android_preview.activity.base.plans.PlanShowActivity;
import com.eshare_android_preview.activity.base.questions.QuestionShowActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.ImageTools;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Node;
import com.eshare_android_preview.model.Note;
import com.eshare_android_preview.model.Plan;
import com.eshare_android_preview.model.Question;

public class NoteShowActivity extends EshareBaseActivity{
    public static class ExtraKeys{
        public static final String NOTE = "note";
    }

	TextView note_content;
	ImageView note_img;
	Button submit_but;
	
	Note note;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.n_note_show);
		
		Intent intent = getIntent();
		note = (Note)intent.getExtras().getSerializable(ExtraKeys.NOTE);
		load_ui();
		
		hide_head_setting_button();
        set_head_text(R.string.show_note_title);
        super.onCreate(savedInstanceState);
	}
	private void load_ui() {
		note_content = (TextView)findViewById(R.id.note_content);
		note_img = (ImageView)findViewById(R.id.note_img);
		submit_but = (Button)findViewById(R.id.submit_but);
		set_ui_and_intent();
		note_content.setText(note.content);
		if (note.img != null) {
			Bitmap bitmap = ImageTools.bytesToBimap(note.img);
			note_img.setBackgroundDrawable(ImageTools.bitmapToDrawableByBD(bitmap));
			note_img.setVisibility(View.VISIBLE);
		}
	}
	public void click_show_old_but(View view){
		Intent intent = set_ui_and_intent();
		startActivity(intent);
	}
	
	private Intent set_ui_and_intent(){
		Intent intent = new Intent();
		int show_str_id = R.string.show_question;;
		if (note.type.indexOf(Note.Type.QUESTION) != -1) {
			intent.setClass(NoteShowActivity.this, QuestionShowActivity.class);

            Bundle bundle = new Bundle();
            Question question = HttpApi.question_find_by(Integer.parseInt(note.type_id));
            bundle.putSerializable(QuestionShowActivity.ExtraKeys.QUESTION, question);
            intent.putExtras(bundle);

			show_str_id = R.string.show_question;

		}
		if (note.type.indexOf(Note.Type.NODE)  != -1) {
			intent.setClass(NoteShowActivity.this, KnowledgeNetItemActivity.class);

            Bundle bundle = new Bundle();
            Node node = HttpApi.find_by_id(note.type_id);
            bundle.putSerializable(KnowledgeNetItemActivity.ExtraKeys.NODE, node);
            intent.putExtras(bundle);

			show_str_id = R.string.show_node;


		}
		if (note.type.indexOf(Note.Type.PLAN)  != -1) {
			intent.setClass(NoteShowActivity.this, PlanShowActivity.class);

            Bundle bundle = new Bundle();
            Plan plan = HttpApi.plan_find_by(Integer.parseInt(note.type_id));
            bundle.putSerializable(PlanShowActivity.ExtraKeys.PLAN, plan);
            intent.putExtras(bundle);

			show_str_id = R.string.show_plan;
		}
		submit_but.setText(show_str_id);
		return intent.putExtra("item_id", note.type_id);
	}
}
