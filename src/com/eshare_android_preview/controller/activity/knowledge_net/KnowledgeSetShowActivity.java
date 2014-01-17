package com.eshare_android_preview.controller.activity.knowledge_net;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.controller.activity.HomeActivity;
import com.eshare_android_preview.controller.activity.base.EshareBaseActivity;
import com.eshare_android_preview.controller.task.BaseAsyncTask;
import com.eshare_android_preview.http.c.UserData;
import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeSet;
import com.eshare_android_preview.utils.BaseUtils;
import com.eshare_android_preview.view.knowledge_map.AniProxy;
import com.eshare_android_preview.view.knowledge_map.MarginAni;
import com.eshare_android_preview.view.knowledge_map.SetPosition;
import com.eshare_android_preview.view.ui.FontAwesomeTextView;
import com.eshare_android_preview.view.ui.KnowledgeSetViewPagerAdapter;
import com.eshare_android_preview.view.ui.UiColor;
import com.eshare_android_preview.view.ui.animate.SimpleAnimatorListener;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by SongLiang on 13-11-21.
 */
public class KnowledgeSetShowActivity extends EshareBaseActivity {
    FontAwesomeTextView go_back_icon_tv;
    TextView set_name_tv;
    RelativeLayout top_layout;
    RelativeLayout pager_layout;
    ViewPager view_pager;

    IUserKnowledgeSet set;

    boolean loaded = false;
    private String set_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.kn_knowledge_set_show);

        Intent intent = getIntent();
        set_id = intent.getStringExtra("set_id");

        top_layout = (RelativeLayout) findViewById(R.id.top_layout);
        pager_layout = (RelativeLayout) findViewById(R.id.pager);
        go_back_icon_tv = (FontAwesomeTextView) findViewById(R.id.go_back_icon);
        set_name_tv = (TextView) findViewById(R.id.set_name);

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

    private void send_http_request_for_nodes(){
        new BaseAsyncTask<Void, Void, Void>(this, R.string.now_loading) {
            @Override
            public Void do_in_background(Void... params) throws Exception {
                set = UserData.instance().get_current_knowledge_net(false).find_by_set_id(set_id);
                if(!set.is_checkpoint()){
                    set.nodes(true); //TODO 只有第一次从网络请求
                }
                return null;
            }

            @Override
            public void on_success(Void result) {

                go_back_icon_tv.setTextColor(UiColor.get_set_text_color(set));
                set_name_tv.setTextColor(UiColor.get_set_text_color(set));
                set_name_tv.setText(set.get_name());

                _init_view_pager();
            }
        }.execute();
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

        view_pager = (ViewPager) findViewById(R.id.view_pager);
        view_pager.setAdapter(new KnowledgeSetViewPagerAdapter(set, this));
        view_pager.setPageMargin(- BaseUtils.dp_to_px(90));
        view_pager.setOffscreenPageLimit(2);

        ValueAnimator ani = ValueAnimator
                .ofPropertyValuesHolder(
                        ma_pager.get_y_values_holder()
                )
                .setDuration(AniProxy.ANIMATE_DRUATION);

        ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ma_pager.update_y(valueAnimator);
            }
        });

        ani.start();
    }

    // ------------------------------------

    private MarginAni ma_topbar, ma_icon, ma_pager;
    private AniProxy.AniBundle bundle;

    public void page_open_animate() {
        SetPosition pos = HomeActivity.map_view.kdata.get_from(set_id);
        AniProxy opened_node = pos.ani_proxy;

        HomeActivity.map_view.dash_path_view.pause();

        // 顶栏，包括返回箭头和单元标题
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

        bundle = opened_node.open(this);
        ma_icon = bundle.icon_ani;

        ValueAnimator ani = ValueAnimator
                .ofPropertyValuesHolder(
                        ma_topbar.get_y_values_holder(),
                        ma_icon.get_x_values_holder(),
                        ma_icon.get_y_values_holder(),
                        bundle.open_holder
                )
                .setDuration(AniProxy.ANIMATE_DRUATION);

        ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ma_topbar.update_y(valueAnimator);
                ma_icon.update_xy(valueAnimator);
                bundle.circle_view.set_radius_px((Float) valueAnimator.getAnimatedValue("radius"));
            }
        });

        ani.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                send_http_request_for_nodes();
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

        ani.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                KnowledgeSetShowActivity.this.finish();
                HomeActivity.map_view.locked = false;
                HomeActivity.map_view.dash_path_view.resume();
            }
        });

        ani.start();
    }
}
