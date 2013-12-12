package com.eshare_android_preview.base.view.knowledge_map;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.view.CircleView;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by Administrator on 13-12-12.
 */
public class AniProxy {
    public static final int ANIMATE_DRUATION = 400;
    public static final int[] TAGGET_ICON_VIEW_ABSOLUTE_POS_PX = new int[]{
            BaseUtils.dp_to_int_px(47),
            BaseUtils.dp_to_int_px(30)
    };

    public KnowledgeMapView map_view;

    CircleView circle_view;
    public ImageView icon_view;

    boolean is_open = false;

    int icon_view_left_margin_px;
    int icon_view_top_margin_px;

    public AniProxy(CircleView circle_view, KnowledgeMapView map_view) {
        this.map_view = map_view;
        this.circle_view = circle_view;
    }

    public void set_icon_view(ImageView icon_view) {
        this.icon_view = icon_view;

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) icon_view.getLayoutParams();
        icon_view_left_margin_px = params.leftMargin;
        icon_view_top_margin_px = params.topMargin;
    }

    public void toggle() {
        if (map_view.opened_node != null && !map_view.opened_node.equals(this)) {
            return;
        }

        circle_view.bringToFront();
        icon_view.bringToFront();

        if (is_open) {
            is_open = false;
            map_view.opened_node = null;

            circle_view.set_radius_animate((float) SetPosition.CIRCLE_RADIUS_DP, ANIMATE_DRUATION);
            set_icon_view_margin(icon_view_left_margin_px, icon_view_top_margin_px);

        } else {
            is_open = true;
            map_view.opened_node = this;

            circle_view.set_radius_animate((float) map_view.SCREEN_HEIGHT_DP, ANIMATE_DRUATION);
            int[] margins = get_target_margin();
            set_icon_view_margin(margins[0], margins[1]);
        }
    }

    private void set_icon_view_margin(int margin_left, int margin_top) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) icon_view.getLayoutParams();


        PropertyValuesHolder pvh_left = PropertyValuesHolder.ofFloat("margin_left", params.leftMargin, margin_left);
        PropertyValuesHolder pvh_top = PropertyValuesHolder.ofFloat("margin_top", params.topMargin, margin_top);

        ValueAnimator ani = ValueAnimator
                .ofPropertyValuesHolder(pvh_left, pvh_top)
                .setDuration(ANIMATE_DRUATION);

        ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float margin_left = (Float) valueAnimator.getAnimatedValue("margin_left");
                float margin_top = (Float) valueAnimator.getAnimatedValue("margin_top");

                RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) icon_view.getLayoutParams();

                p.leftMargin = (int) margin_left;
                p.topMargin = (int) margin_top;
                icon_view.setLayoutParams(p);
            }
        });

        ani.start();
    }

    private int[] get_target_margin() {
        int[] current_icon_view_absolute_pos_px = new int[2];
        icon_view.getLocationInWindow(current_icon_view_absolute_pos_px);

        int x_off = current_icon_view_absolute_pos_px[0] - TAGGET_ICON_VIEW_ABSOLUTE_POS_PX[0];
        int y_off = current_icon_view_absolute_pos_px[1] - TAGGET_ICON_VIEW_ABSOLUTE_POS_PX[1];

        return new int[]{
                icon_view_left_margin_px - x_off,
                icon_view_top_margin_px - y_off
        };
    }
}
