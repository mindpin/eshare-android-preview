package com.eshare_android_preview.base.view.knowledge_map;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.view.CircleView;
import com.nineoldandroids.animation.PropertyValuesHolder;

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

    public AniBundle open(EshareBaseActivity target_activity) {
        map_view.opened_node = this;

        // 复制一个相同位置的 circle_view 和 icon
        CircleView circle_view = pos.clone_circle_view_on(target_activity);
        ImageView icon_view = pos.clone_icon_view_on(target_activity);

        RelativeLayout group = (RelativeLayout) target_activity.findViewById(R.id.animate_layer);
        group.addView(circle_view);
        group.addView(icon_view);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) icon_view.getLayoutParams();
        MarginAni icon_ani  = new MarginAni(
                "icon", icon_view,
                params.leftMargin, TARGET_ICON_VIEW_ABSOLUTE_POS_PX[0],
                params.topMargin, TARGET_ICON_VIEW_ABSOLUTE_POS_PX[1]
        );

        return new AniBundle(icon_ani, circle_view, BaseUtils.dp_to_px(pos.CIRCLE_RADIUS_DP), BaseUtils.dp_to_px(map_view.SCREEN_HEIGHT_DP));
    }

    public static class AniBundle {
        public MarginAni icon_ani;
        public CircleView circle_view;
        public PropertyValuesHolder open_holder, close_holder;

        public AniBundle(MarginAni icon_ani, CircleView circle_view, float r1, float r2) {
            this.icon_ani = icon_ani;
            this.circle_view = circle_view;
            this.open_holder = PropertyValuesHolder.ofFloat("radius", r1, r2);
            this.close_holder = PropertyValuesHolder.ofFloat("radius", r2, r1);
        }
    }
}
