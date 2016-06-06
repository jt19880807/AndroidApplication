package com.JTAppStore.common;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by Devin.Jiang on 2016-06-06.
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 */
public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager instance;
    private AppManager(){}

    /**
     * 单一实例
     * @return
     */
    public static AppManager getInstance() {
        if(instance==null){
            instance=new AppManager();
        }
        return instance;
    }

    /**
     * 将Activity添加到堆栈
     * @param activity
     */
    public void addAvtivity(Activity activity){
        if(activityStack==null){
            activityStack=new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity(堆栈中最后一个压入的)
     * @return
     */
    public Activity currrentActivity(){
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity(){
        Activity activity=activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if(activity!=null){
            activityStack.remove(activity);
            activity.finish();
            activity=null;
        }
    }

    /**
     * 结束指定类名的Activity
     * @param cls
     */
    public void finishActivity(Class<?> cls){
        for (Activity activity:activityStack){
            if (activity.getClass().equals(cls)){
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity(){
        int size=activityStack.size();
        for (int i=0;i<size;i++){
            if (null!=activityStack.get(i)){
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }
}
