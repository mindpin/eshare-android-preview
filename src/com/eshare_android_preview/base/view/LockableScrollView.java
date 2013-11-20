package com.eshare_android_preview.base.view;

import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by Administrator on 13-11-20.
 */
public class LockableScrollView extends ScrollView {

    public LockableScrollView(android.content.Context context) {
        super(context);
    }

    public LockableScrollView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    public LockableScrollView(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // false 可滚动
    // true 不可滚动
    public boolean locked = false;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // if we can scroll pass the event to the superclass
                if (!locked) return super.onTouchEvent(ev);
                // only continue to handle the touch event if scrolling enabled
                return !locked;
            default:
                return super.onTouchEvent(ev);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // Don't do anything with intercepted touch events if
        // we are not scrollable
        if (locked) return false;
        else return super.onInterceptTouchEvent(ev);
    }
}
