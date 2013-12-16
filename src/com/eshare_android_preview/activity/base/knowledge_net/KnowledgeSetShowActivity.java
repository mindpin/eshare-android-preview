package com.eshare_android_preview.activity.base.knowledge_net;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.questions.QuestionShowActivity;
import com.eshare_android_preview.activity.base.HomeActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.view.knowledge_map.AniProxy;
import com.eshare_android_preview.base.view.knowledge_map.MarginAni;
import com.eshare_android_preview.model.TestPaper;
import com.eshare_android_preview.model.TestResult;
import com.eshare_android_preview.model.knowledge.BaseKnowledgeSet;
import com.eshare_android_preview.model.knowledge.KnowledgeNode;
import com.eshare_android_preview.model.knowledge.KnowledgeSet;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SongLiang on 13-11-21.
 */
public class KnowledgeSetShowActivity extends EshareBaseActivity {
    TextView go_back_icon_tv;
    TextView set_name_tv;
    RelativeLayout top_layout;
    RelativeLayout pager_layout;
    ViewPager view_pager;

    BaseKnowledgeSet set;
    int set_text_color;

    boolean loaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.kn_knowledge_set_show);

        go_back_icon_tv = (TextView) findViewById(R.id.go_back_icon);
        set_name_tv = (TextView) findViewById(R.id.set_name);
        top_layout = (RelativeLayout) findViewById(R.id.top_layout);
        pager_layout = (RelativeLayout) findViewById(R.id.pager);

        Intent intent = getIntent();
        String set_id = intent.getStringExtra("set_id");
        set = BaseKnowledgeSet.find(set_id);

        if (set.is_checkpoint()) {
            set_text_color = Color.parseColor("#844C1D");
        } else {
            set_text_color = Color.parseColor("#ffffff");
        }

        go_back_icon_tv.setTextColor(set_text_color);
        set_fontawesome(go_back_icon_tv);

        set_name_tv.setTextColor(set_text_color);
        set_name_tv.setText(set.get_name());

        _init_view_pager();

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!loaded) {
            page_open_animate();
            loaded = true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish_this(null);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    // called by goback icon
    public void finish_this(View view) {
        page_close_animate();
    }

    private void _init_view_pager() {
        if (set.is_checkpoint()) return;

        KnowledgeSet kset = (KnowledgeSet) set;
        LayoutInflater lf = getLayoutInflater().from(this);
        final List<View> view_list = new ArrayList<View>();
        for(final KnowledgeNode node : kset.nodes) {
            View view = lf.inflate(R.layout.kn_knowledge_set_show_item, null);
            ((TextView) view.findViewById(R.id.node_name)).setText(node.name);
            ((TextView) view.findViewById(R.id.node_desc)).setText(node.desc);
            view_list.add(view);

            View to_question = view.findViewById(R.id.to_question);
            to_question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(KnowledgeSetShowActivity.this, QuestionShowActivity.class);
                    TestResult test_result = new TestResult(3, 10);
                    TestPaper test_paper = new TestPaper(node, test_result);
                    QuestionShowActivity.test_paper = test_paper;
                    startActivity(intent);
                }
            });

        }

        view_pager = (ViewPager) findViewById(R.id.view_pager);
        view_pager.setAdapter(new PagerAdapter() {
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
        });

        view_pager.setPageMargin(- BaseUtils.dp_to_int_px(90));
        view_pager.setOffscreenPageLimit(2);
    }

    // ------------------------------------

    private MarginAni ma_topbar, ma_pager, ma_icon;
    private AniProxy.AniBundle bundle;

    public void page_open_animate() {
        ma_topbar = new MarginAni(
                "topbar", top_layout,
                0, 0,
                BaseUtils.dp_to_px(-50), 0
        );

        ma_pager = new MarginAni(
                "pager",  pager_layout,
                0, 0,
                BaseUtils.dp_to_px(HomeActivity.map_view.SCREEN_HEIGHT_DP), BaseUtils.dp_to_px(146)
        );

        bundle = HomeActivity.map_view.opened_node.open(this);
        ma_icon = bundle.icon_ani;

        ValueAnimator ani = ValueAnimator
                .ofPropertyValuesHolder(
                        ma_topbar.get_y_values_holder(),
                        ma_pager.get_y_values_holder(),
                        ma_icon.get_x_values_holder(),
                        ma_icon.get_y_values_holder(),
                        bundle.open_holder
                )
                .setDuration(AniProxy.ANIMATE_DRUATION);

        ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ma_topbar.update_y(valueAnimator);
                ma_pager.update_y(valueAnimator);
                ma_icon.update_xy(valueAnimator);
                bundle.circle_view.set_radius_px((Float) valueAnimator.getAnimatedValue("radius"));
            }
        });

        ani.start();
    }

    public void page_close_animate() {
        ValueAnimator ani = ValueAnimator
                .ofPropertyValuesHolder(
                        ma_topbar.get_reverse_y_values_holder(),
                        ma_pager.get_reverse_y_values_holder(),
                        ma_icon.get_reverse_x_values_holder(),
                        ma_icon.get_reverse_y_values_holder(),
                        bundle.close_holder
                )
                .setDuration(AniProxy.ANIMATE_DRUATION);

        ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ma_topbar.update_y(valueAnimator);
                ma_pager.update_y(valueAnimator);
                ma_icon.update_xy(valueAnimator);
                bundle.circle_view.set_radius_px((Float) valueAnimator.getAnimatedValue("radius"));
            }
        });

        ani.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {}

            @Override
            public void onAnimationEnd(Animator animator) {
                KnowledgeSetShowActivity.this.finish();
                HomeActivity.map_view.locked = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {}

            @Override
            public void onAnimationRepeat(Animator animator) {}
        });

        ani.start();
    }
}
