package com.eshare_android_preview.base.view.ui;

import android.graphics.Color;

import com.eshare_android_preview.model.knowledge.BaseKnowledgeSet;

/**
 * Created by Administrator on 13-12-27.
 */
public class UiColor {
    // knowledge map
    public final static int LOCKED_SET_COLOR = Color.parseColor("#eeeeee");
    public final static int SET_COLOR = Color.parseColor("#1cb0f6");
    public final static int CHECKPOINT_COLOR = Color.parseColor("#fccd2d");

    public static int get_set_color(BaseKnowledgeSet set) {
        if (set.is_checkpoint()) {
            return CHECKPOINT_COLOR;
        }

        return set.is_unlocked() ? SET_COLOR : LOCKED_SET_COLOR;
    }

    // question icons
//    public final static String HEALTH_STRING = "#D62525"; // 红心
    public final static String HEALTH_STRING = "#fccd2d"; // 黄色星星
    public final static String HEALTH_EMPTY_STRING = "#dbdbdb";

    // experience bar
    public final static int EXP_LEFT_CIRCLE = Color.parseColor("#fccd2d");
    public final static int EXP_RIGHT_CIRCLE = Color.parseColor("#dbdbdb");
    public final static int EXP_BAR_EMPTY = Color.parseColor("#dbdbdb");
    public final static int EXP_BAR_FILL = Color.parseColor("#FF8857"); // "#FF6A2D"
    public final static int EXP_CIRCLE_TEXT = Color.parseColor("#555555");
    public final static int EXP_STROKE_COLOR = Color.parseColor("#f1f1f1");
}
