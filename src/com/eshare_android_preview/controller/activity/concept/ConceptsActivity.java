package com.eshare_android_preview.controller.activity.concept;

import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.controller.activity.base.EshareBaseActivity;
import com.eshare_android_preview.controller.task.BaseAsyncTask;
import com.eshare_android_preview.http.c.UserData;
import com.eshare_android_preview.http.i.concept.IConcept;
import com.eshare_android_preview.http.model.Concept;
import com.eshare_android_preview.view.adapter.ConceptsAdapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 14-1-24.
 */
public class ConceptsActivity extends EshareBaseActivity {
    List<IConcept> concepts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.concepts);
        set_head_text("概念");
        hide_head_setting_button();

        load_data();

        super.onCreate(savedInstanceState);
    }

    private void load_data() {
        new BaseAsyncTask<Void, Void, Void>(this, R.string.now_loading) {
            @Override
            public Void do_in_background(Void... params) throws Exception {
                concepts = UserData.instance().get_current_knowledge_net().concepts(true, null, null);
                Collections.sort(concepts, new Comparator<IConcept>() {
                    @Override
                    public int compare(IConcept iConcept, IConcept iConcept2) {
                        Integer a = (iConcept.is_learned() ? 1 : 0) + (iConcept.is_unlocked() ? 1 : 0);
                        Integer b = (iConcept2.is_learned() ? 1 : 0) + (iConcept2.is_unlocked() ? 1 : 0);

                        return b.compareTo(a);
                    }
                });
                return null;
            }

            @Override
            public void on_success(Void aVoid) {
                ListView concepts_list = (ListView) findViewById(R.id.concepts_list);
                ConceptsAdapter adapter = new ConceptsAdapter(ConceptsActivity.this);
                adapter.add_items(concepts);
                concepts_list.setAdapter(adapter);

//                concepts_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        IConcept c = (IConcept) view.getTag(R.id.adapter_item_tag);
//                        Log.e("", c.get_name());
//                    }
//                });
            }
        }.execute();
    }
}
