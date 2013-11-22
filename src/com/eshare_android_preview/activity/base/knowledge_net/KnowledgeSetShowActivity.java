package com.eshare_android_preview.activity.base.knowledge_net;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.activity.base.tab_activity.HomeActivity;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.model.knowledge.BaseKnowledgeSet;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by SongLiang on 13-11-21.
 */
public class KnowledgeSetShowActivity extends EshareBaseActivity {
    TextView go_back_icon_tv;
    TextView set_name_tv;
    RelativeLayout top_layout;
    RelativeLayout pager_layout;

    BaseKnowledgeSet set;
    int set_text_color;

    boolean view_on_init = true;

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

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (view_on_init) {
            page_open_animate();
            view_on_init = false;
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

    public void finish_this(View view) {
        page_close_animate();
    }

    public void page_open_animate() {
        PropertyValuesHolder topbar_pvh = PropertyValuesHolder.ofFloat("topbar", BaseUtils.dp_to_px(-50), 0);
        PropertyValuesHolder pager_pvh = PropertyValuesHolder.ofFloat("pager", BaseUtils.dp_to_px((float) HomeActivity.SCREEN_HEIGHT_DP), BaseUtils.dp_to_px(146));

        ValueAnimator ani = ValueAnimator
                .ofPropertyValuesHolder(topbar_pvh, pager_pvh)
                .setDuration(HomeActivity.ANIMATE_DRUATION);

        ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float margin_top_topbar = (Float) valueAnimator.getAnimatedValue("topbar");
                float margin_top_pager = (Float) valueAnimator.getAnimatedValue("pager");

                RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) top_layout.getLayoutParams();
                p.topMargin = (int) margin_top_topbar;
                top_layout.setLayoutParams(p);

                RelativeLayout.LayoutParams p1 = (RelativeLayout.LayoutParams) pager_layout.getLayoutParams();
                p1.topMargin = (int) margin_top_pager;
                pager_layout.setLayoutParams(p1);
            }
        });

        ani.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                HomeActivity.instance.run_open_animate();
            }

            @Override
            public void onAnimationEnd(Animator animator) {}

            @Override
            public void onAnimationCancel(Animator animator) {}

            @Override
            public void onAnimationRepeat(Animator animator) {}
        });

        ani.start();
    }

    public void page_close_animate() {
        PropertyValuesHolder topbar_pvh = PropertyValuesHolder.ofFloat("topbar", 0, BaseUtils.dp_to_px(-50));
        PropertyValuesHolder pager_pvh = PropertyValuesHolder.ofFloat("pager", BaseUtils.dp_to_px(146), BaseUtils.dp_to_px((float) HomeActivity.SCREEN_HEIGHT_DP));

        ValueAnimator ani = ValueAnimator
                .ofPropertyValuesHolder(topbar_pvh, pager_pvh)
                .setDuration(HomeActivity.ANIMATE_DRUATION);

        ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float margin_top_topbar = (Float) valueAnimator.getAnimatedValue("topbar");
                float margin_top_pager = (Float) valueAnimator.getAnimatedValue("pager");

                RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) top_layout.getLayoutParams();
                p.topMargin = (int) margin_top_topbar;
                top_layout.setLayoutParams(p);

                RelativeLayout.LayoutParams p1 = (RelativeLayout.LayoutParams) pager_layout.getLayoutParams();
                p1.topMargin = (int) margin_top_pager;
                pager_layout.setLayoutParams(p1);
            }
        });

        ani.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                HomeActivity.instance.run_close_animate();
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                KnowledgeSetShowActivity.this.finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {}

            @Override
            public void onAnimationRepeat(Animator animator) {}
        });

        ani.start();
    }
}
