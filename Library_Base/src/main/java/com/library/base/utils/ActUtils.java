package com.library.base.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.Iterator;
import java.util.Stack;

public class ActUtils {

    // 单例模式
    private static ActUtils instance;
    // Activity栈
    private Stack<Activity> activityStack;
    private Context context;

    /**
     * 单一实例
     */
    public static void init(Context context) {
        if (instance == null) {
            instance = new ActUtils();
            instance.context = context;
        }

    }

    /**
     * 添加Activity到堆栈
     */
    public static void addAct(Activity activity) {
        if (instance.activityStack == null) {
            instance.activityStack = new Stack<>();
        }



        if(curAct()==null){
            instance.activityStack.add(activity);
        }
        else{
            Iterator<Activity> it = instance.activityStack.iterator();

            while (it.hasNext()){
                Activity act = it.next();
                if(act!=activity&&act.getClass().equals(activity.getClass())) {
                    act.finish();
                    it.remove();
                }
            }
            instance.activityStack.add(activity);
        }
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public static Activity curAct() {

        Activity activity =null;

        try {
            activity = instance.activityStack.lastElement();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return activity;
    }

    public static Activity previousAct(){

        Activity activity =null;

        try {
            activity = instance.activityStack.get(instance.activityStack.size()-2);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return activity;

    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public static void removeAct() {

        try {
            Activity activity = instance.activityStack.lastElement();
            removeAct(activity);
        }
        catch (Exception e){
        }

    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public static void removeLastAct(Activity activity) {

        if(activity==curAct()){

            removeAct(activity);
        }
        else{
            activity.finish();
        }

    }

    /**
     * 结束指定的Activity
     */
    public static void removeAct(Activity activity) {
        if (activity != null) {
            instance.activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public static void removeAct(Class<?> cls) {

        Iterator<Activity> it = instance.activityStack.iterator();
        while (it.hasNext()){
            Activity act = it.next();
            if(act.getClass().equals(cls)) {
                act.finish();
                it.remove();
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public static void removeAllAct() {

        Iterator<Activity> it = instance.activityStack.iterator();
        while (it.hasNext()){
            Activity act = it.next();
                it.remove();
                act.finish();

        }

    }

    public static void removeOtherAct(){

        Iterator<Activity> it = instance.activityStack.iterator();
        while (it.hasNext()){
            Activity act = it.next();

            if(act!=curAct()){
                it.remove();
                act.finish();
            }
        }

    }

    public static void turnTo(Activity context, Class cls){

        Intent intent = new Intent(context,cls);
        context.startActivity(intent);
    }

    public static void turnTo(Class cls){

        Intent intent = new Intent(instance.context,cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        instance.context.startActivity(intent);

    }

    /**
     * 退出应用程序
     */
    @SuppressLint("MissingPermission")
    public static void appExit() {
        try {
            removeAllAct();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public Activity lastAct()
    {
        if(activityStack.size()>=2)
        return activityStack.get(activityStack.size()-2);

        else return null;
    }
}
