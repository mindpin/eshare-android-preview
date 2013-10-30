package com.eshare_android_preview.activity.base.notes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.knowledge_net.KnowledgeNetItemActivity;
import com.eshare_android_preview.activity.base.plans.PlanShowActivity;
import com.eshare_android_preview.activity.base.questions.QuestionShowActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.utils.ImageTools;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Node;
import com.eshare_android_preview.model.Notes;
import com.eshare_android_preview.model.Plan;
import com.eshare_android_preview.model.Question;
import com.eshare_android_preview.model.database.PlanDBHelper;

public class NotesShowActivity extends EshareBaseActivity{
	TextView note_content;
	ImageView note_img;
	Button submit_but;
	
	Notes notes;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.n_notes_show);
		
		Intent intent = getIntent();
		notes = (Notes)intent.getExtras().getSerializable("item");
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
		note_content.setText(notes.content);
		if (notes.img != null) {
			Bitmap bitmap = ImageTools.bytesToBimap(notes.img);
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
		if (notes.type.indexOf(Notes.Type.QUESTION) != -1) {
			intent.setClass(NotesShowActivity.this, QuestionShowActivity.class);

            Bundle bundle = new Bundle();
            Question question = HttpApi.question_find_by(Integer.parseInt(notes.type_id));
            bundle.putSerializable(QuestionShowActivity.ExtraKeys.QUESTION, question);
            intent.putExtras(bundle);

			show_str_id = R.string.show_question;

		}
		if (notes.type.indexOf(Notes.Type.NODE)  != -1) {
			intent.setClass(NotesShowActivity.this, KnowledgeNetItemActivity.class);

            Bundle bundle = new Bundle();
            Node node = HttpApi.find_by_id(notes.type_id);
            bundle.putSerializable(KnowledgeNetItemActivity.ExtraKeys.NODE, node);
            intent.putExtras(bundle);

			show_str_id = R.string.show_node;


		}
		if (notes.type.indexOf(Notes.Type.PLAN)  != -1) {
			intent.setClass(NotesShowActivity.this, PlanShowActivity.class);

            Bundle bundle = new Bundle();
            Plan plan = HttpApi.plan_find_by(Integer.parseInt(notes.type_id));
            bundle.putSerializable(PlanShowActivity.ExtraKeys.PLAN, plan);
            intent.putExtras(bundle);

			show_str_id = R.string.show_plan;
		}
		submit_but.setText(show_str_id);
		return intent.putExtra("item_id", notes.type_id);
	}
}
