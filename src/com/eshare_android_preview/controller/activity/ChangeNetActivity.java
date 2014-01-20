package com.eshare_android_preview.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.controller.activity.base.EshareBaseActivity;
import com.eshare_android_preview.controller.task.BaseAsyncTask;
import com.eshare_android_preview.http.c.UserData;
import com.eshare_android_preview.http.i.knowledge.IUserSimpleKnowledgeNet;
import com.eshare_android_preview.view.adapter.ChangeNetsAdapter;

import java.util.List;

/**
 * Created by Administrator on 13-12-26.
 */
public class ChangeNetActivity extends EshareBaseActivity {
    private List<IUserSimpleKnowledgeNet> nets;
    private ListView net_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.change_net_list);

        set_head_text("选择课程");
        hide_head_setting_button();

        net_list = (ListView) findViewById(R.id.change_net_list);

        get_net_list();
        super.onCreate(savedInstanceState);
    }

    private void get_net_list() {
        new BaseAsyncTask<Void, Void, Void>(this, R.string.now_loading) {
            @Override
            public Void do_in_background(Void... params) throws Exception {
                nets = UserData.instance().get_knowledge_net_ids(true);
                return null;
            }

            @Override
            public void on_success(Void result) {
                ChangeNetsAdapter adapter = new ChangeNetsAdapter(ChangeNetActivity.this);
                adapter.add_items(nets);
                net_list.setAdapter(adapter);
                net_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        IUserSimpleKnowledgeNet net = (IUserSimpleKnowledgeNet) view.getTag(R.id.adapter_item_tag);
                        Intent intent = new Intent(ChangeNetActivity.this, HomeActivity.class);
                        intent.putExtra(HomeActivity.ExtraKeys.CHANGE_NET, true);
                        UserData.instance().set_current_knowledge_net_id(net.get_id());
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }.execute();
    }

}
