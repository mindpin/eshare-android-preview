package com.eshare_android_preview.base.view.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.eshare_android_preview.activity.base.questions.QuestionShowActivity;
import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeNode;
import com.eshare_android_preview.http.model.KnowledgeSet;
import com.eshare_android_preview.model.TestPaper;
import com.eshare_android_preview.model.TestResult;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 14-1-2.
 */
public class KnowledgeSetViewPagerAdapter extends PagerAdapter {
    KnowledgeSet set;
    Activity activity;
    List<View> view_list;

    public KnowledgeSetViewPagerAdapter(KnowledgeSet set, Activity activity) {
        super();

        this.set = set;
        this.activity = activity;
        this.view_list = new ArrayList<View>();

        init();
    }

    private void init() {
        LayoutInflater lf = activity.getLayoutInflater().from(activity);

        for(final IUserKnowledgeNode node : set.nodes(false)) {
            KnowledgeSetPagerView kspv = new KnowledgeSetPagerView(activity, node);
            kspv.set_listener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!node.is_unlocked()) return;

                    Intent intent = new Intent(activity, QuestionShowActivity.class);
                    TestResult test_result = new TestResult(3, 10);
                    TestPaper test_paper = new TestPaper(node.get_id(), test_result);
                    intent.putExtra(QuestionShowActivity.ExtraKeys.TEST_PAPER, test_paper);
                    activity.startActivity(intent);
                }
            });

            view_list.add(kspv);
        }
    }

    @Override
    public int getCount() {
        return view_list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = view_list.get(position);
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(view_list.get(position));
    }
}
