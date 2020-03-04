package com.eeepay.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * Created by Ching on 2018/4/25.
 */

public class AppManager {

    private static Stack<Activity> activityStack;

    private static AppManager instance;

    public AppManager() {
    }

    public static AppManager getAppManager() {
        if (instance == null)
            instance = new AppManager();
        return instance;
    }

    /**
     * @param activity 添加activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null)
            activityStack = new Stack<>();
        activityStack.add(activity);
    }

    /**
     * @return 获取当前activity
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * @param activity 结束指定activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        finishActivity(activityStack.lastElement());
    }

    /**
     * @param clz
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> clz) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(clz))
                finishActivity(activity);
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityStack.get(i) != null)
                activityStack.get(i).finish();
        }
        activityStack.clear();
    }

    /**
     * @param context
     * 退出APP
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
