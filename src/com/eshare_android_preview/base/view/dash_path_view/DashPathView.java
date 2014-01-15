package com.eshare_android_preview.base.view.dash_path_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.eshare_android_preview.base.utils.BaseUtils;

import java.util.ArrayList;

/**
 * Created by fushang318 on 13-11-12.
 */
public class DashPathView extends View {
    ComposePathEffectManager effect_manager;
    boolean is_init = false;
    boolean animation_is_start = false;
    boolean pause = false;
    int duration = 500;
    // 线的画笔
    Paint paint;
    // 虚线的颜色，默认是黑色
    int color = Color.BLACK;
    // 实心小圆的半径，默认是 10px
    int dash_icon_radius = 10;
    // 需要绘制的虚线的路径 DashPathEndpoint 对象数组
    private ArrayList<DashPathEndpoint> dash_path_endpoint_list;
    // 需要绘制的虚线的路径 path 对象数组
    private ArrayList<Path> line_path_list = new ArrayList<Path>();
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(!pause){
                effect_manager.move_to_next_effect();
                DashPathView.this.postInvalidate();
            }
            DashPathView.this.request_next_frame();
        }
    };

    public DashPathView(Context context) {
        super(context);
    }

    public DashPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DashPathView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!is_init){
            init();
            is_init = true;
        }

        paint.setPathEffect(effect_manager.get_current_effect());

        for(Path line_path : this.line_path_list){
            canvas.drawPath(line_path, paint);
        }

        if(!animation_is_start){
            request_next_frame();
            animation_is_start = true;
        }
    }

    private void request_next_frame(){
        handler.sendEmptyMessageDelayed(0, this.effect_manager.get_effect_frame_time());
    }

    private void init(){
        // 构建画笔对象
        build_paint();
        // 构建虚线 Path 对象
        build_line_path_list();
        // 构建虚线特效对象
        this.effect_manager = new ComposePathEffectManager(this.dash_icon_radius, this.duration);
    }

    private void build_paint(){
        // Paint.ANTI_ALIAS_FLAG 抗锯齿
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 画笔设置为黑色
        paint.setColor(color);
        // 画笔设置为填充
        paint.setStyle(Paint.Style.FILL);
    }

    private void build_line_path_list() {
        line_path_list = new ArrayList<Path>();
        for(DashPathEndpoint dash_path : this.dash_path_endpoint_list){
            Path line_path = new Path();
            line_path.moveTo(dash_path.start_x, dash_path.start_y);
            line_path.lineTo(dash_path.end_x, dash_path.end_y);
            line_path_list.add(line_path);
        }
    }

    // 设置要绘制的多条虚线的起始点数据
    public void set_dash_path_endpoint_list(ArrayList<DashPathEndpoint> dash_path_endpoint_list) {
        this.dash_path_endpoint_list = dash_path_endpoint_list;
    }

    public void set_color(int color){
        this.color = color;
    }

    public void set_dash_icon_radius(int radius_dp){
        int radius = (int) BaseUtils.dp_to_px(radius_dp);
        this.dash_icon_radius = radius;
    }

    public void set_duration(int duration){
        this.duration = duration;
    }

    public void pause(){
        this.pause = true;
    }

    public void resume(){
        this.pause = false;
    }

    class ComposePathEffectManager{
        private Path dash_icon;
        private int dash_icon_radius = 10;
        private ArrayList<ComposePathEffect> effect_list;
        private int current_effect_index = 0;
        private int duration = 500;

        public ComposePathEffectManager(int dash_icon_radius, int duration){
            this.dash_icon_radius = dash_icon_radius;
            this.duration = duration;
            this.init();
        }

        private void init(){
            this.build_dash_icon();
            this.effect_list = new ArrayList<ComposePathEffect>();
            // 线条的转弯处，用曲线平滑处理
            CornerPathEffect cpe = new CornerPathEffect(10);
            int advance = this.get_hash_icon_advance();
            for(int phase=0; phase>-advance; phase--){
                // 用自定义的小图标做虚线
                // PathDashPathEffect.Style.TRANSLATE,表示小图标的角度不受线的走向影响
                // phase 数字代表虚线的最小图标和端点的偏移量
                PathDashPathEffect pdpe = new PathDashPathEffect(this.dash_icon, advance, phase,
                        PathDashPathEffect.Style.TRANSLATE);
                // 混合使用上面定义的两种效果
                this.effect_list.add(new ComposePathEffect(cpe, pdpe));
            }
        }

        // 实心小圆两个圆心之间的距离
        private int get_hash_icon_advance(){
            return this.dash_icon_radius*3;
        }

        private void build_dash_icon(){
            this.dash_icon = new Path();
            // Path.Direction.CW 表示顺时针画圆，这个参数是卖萌用的么，完全没用啊 -_-!
            this.dash_icon.addCircle(0, 0, this.dash_icon_radius, Path.Direction.CW);
        }

        private ComposePathEffect get_current_effect(){
            return this.effect_list.get(this.current_effect_index);
        }

        private void move_to_next_effect(){
            this.current_effect_index = (this.current_effect_index+1) % this.effect_list.size();
        }

        public int get_effect_frame_time(){
            return this.duration/this.effect_list.size();
        }

    }
}
