package com.eshare_android_preview.controller.activity.base;

import java.util.Stack;

// 用于管理和回收activities的堆栈，单例模式
public class ActivitiesStackSingleton {
    private static ActivitiesStackSingleton instance = new ActivitiesStackSingleton();

    private Stack<EshareBaseActivity> activities_stack;

    private ActivitiesStackSingleton() {
        activities_stack = new Stack<EshareBaseActivity>();
    }

    private static Stack<EshareBaseActivity> get_activities_stack() {
        return instance.activities_stack;
    }

    // 关闭所有堆栈中的activity
    protected static void clear_activities_stack() {
        Stack<EshareBaseActivity> activities_stack = get_activities_stack();

        int size = activities_stack.size();
        for (int i = 0; i < size; i++) {
        	EshareBaseActivity activity = activities_stack.pop();
            activity.finish();
        } 
    }

    // 从堆栈中移除一个实例
    protected static void remove_activity(EshareBaseActivity activity) {
        get_activities_stack().remove(activity);
    }


    protected static void tidy_and_push_activity(EshareBaseActivity new_activity) {
        Class<?> cls = new_activity.getClass();

        // System.out.println(cls + "create");
        // System.out.println("处理前：activities堆栈包含"+ activities_stack.size()
        // +"个实例");

        // 先遍历查找相同类型的 activitiy，如果存在，就清除并关闭两个activity之间的所有实例
        // 先查找类型相同的实例的下标
        Stack<EshareBaseActivity> activities_stack = get_activities_stack();

        int index = -1;
        int size = activities_stack.size();
        for (int i = 0; i < size; i++) {
        	EshareBaseActivity activity = activities_stack.get(i);
            if (cls == activity.getClass()) {
                index = i;
                break;
            }
        }

        // 如果找到，清除之
        if (index > -1) {
            int pops_count = size - index;
            for (int i = 0; i < pops_count; i++) {
            	EshareBaseActivity item = activities_stack.pop();
                item.finish();
            }
        }
        activities_stack.push(new_activity);
        // System.out.println("处理后：activities堆栈包含"+ activities_stack.size()
        // +"个实例");
    }
}
