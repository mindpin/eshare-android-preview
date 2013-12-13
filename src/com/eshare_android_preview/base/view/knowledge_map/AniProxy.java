package com.eshare_android_preview.base.view.knowledge_map;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.view.CircleView;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by Administrator on 13-12-12.
 */
public class AniProxy {
    public static final int ANIMATE_DRUATION = 400;
    public static final int[] TARGET_ICON_VIEW_ABSOLUTE_POS_PX = new int[]{
            BaseUtils.dp_to_int_px(47),
            BaseUtils.dp_to_int_px(5)
    };

    private SetPosition pos;
    private KnowledgeMapView map_view;

    public AniProxy(SetPosition pos) {
        this.pos = pos;
        this.map_view = pos.map_view;
    }

    private CircleView circle_view;
    private ImageView icon_view;
    public MarginAni open(EshareBaseActivity target_activity) {
        // 复制一个相同位置的 circle_view 和 icon
        circle_view = new CircleView(target_activity);
        circle_view.set_color(pos.get_circle_color());
        circle_view.set_circle_center_position(
                pos.grid_dp_left + pos.circle_center_offset,
                pos.grid_dp_top + pos.circle_center_offset
        );
        circle_view.set_radius(pos.CIRCLE_RADIUS_DP);

        icon_view = new ImageView(target_activity);
        icon_view.setImageDrawable(pos.get_icon_drawable());
        pos._set_dp_params(
                icon_view,
                pos.CIRCLE_RADIUS_DP * 2, pos.CIRCLE_RADIUS_DP * 2,
                pos.grid_dp_left + pos.icon_offset,
                pos.grid_dp_top + pos.icon_offset
        );

        RelativeLayout group = (RelativeLayout) target_activity.findViewById(R.id.animate_layer);
        group.addView(circle_view);
        group.addView(icon_view);

        map_view.opened_node = this;

        circle_view.set_radius_animate(map_view.SCREEN_HEIGHT_DP, ANIMATE_DRUATION);

        return new MarginAni(
                "icon", icon_view,
                pos.grid_dp_left + pos.icon_offset, TARGET_ICON_VIEW_ABSOLUTE_POS_PX[0],
                pos.grid_dp_top + pos.icon_offset, TARGET_ICON_VIEW_ABSOLUTE_POS_PX[1]
        );
    }

    public MarginAni close() {
        map_view.opened_node = null;

        circle_view.set_radius_animate(pos.CIRCLE_RADIUS_DP, ANIMATE_DRUATION);

        return new MarginAni(
                "icon", icon_view,
                TARGET_ICON_VIEW_ABSOLUTE_POS_PX[0], pos.grid_dp_left + pos.icon_offset,
                TARGET_ICON_VIEW_ABSOLUTE_POS_PX[1], pos.grid_dp_top + pos.icon_offset
        );
    }
}
