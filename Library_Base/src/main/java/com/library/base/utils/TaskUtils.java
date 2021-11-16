package com.library.base.utils;


import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TaskUtils {

    private static TaskUtils taskUtils;
    private final Map<Object,List<Runnable>> allTasks = new HashMap<>();
    private Handler handler = new Handler();

    public static TaskUtils get(){

        if(taskUtils ==null) taskUtils = new TaskUtils();

        return taskUtils;
    }


    public void putTask(Context context, int delay,final CallBack callBack){


        Runnable runnable = callBack::onBack;

        if(!allTasks.containsKey(context))  allTasks.put(context,new ArrayList<Runnable>());
        Objects.requireNonNull(allTasks.get(context)).add(runnable);
        handler.postDelayed(runnable,delay);

    }


    public void putTask(String  key, int delay,final CallBack callBack){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                callBack.onBack();
            }
        };

        if(!allTasks.containsKey(key))  allTasks.put(key,new ArrayList<Runnable>());
        Objects.requireNonNull(allTasks.get(key)).add(runnable);
        handler.postDelayed(runnable,delay);

    }

    public void putTask(int delay,final int loop,String key,final CallBack callBack){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                handler.postDelayed(this,loop);
                callBack.onBack();

            }
        };

        if(!allTasks.containsKey(key)) allTasks.put(key,new ArrayList<Runnable>());
        Objects.requireNonNull(allTasks.get(key)).add(runnable);
        handler.postDelayed(runnable,delay);

    }


    public void putTask(int delay,final int loop,final CallBack callBack){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                taskUtils.handler.postDelayed(this,loop);
                callBack.onBack();
            }
        };

        handler.postDelayed(runnable,delay);

    }


    public void clearTask(Context context){

        if(allTasks.get(context)!=null){

            for(Runnable runnable: Objects.requireNonNull(allTasks.get(context))){

                handler.removeCallbacks(runnable);

            }

            allTasks.remove(context);

        }




    }

    public void clearTask(String key){

        if(allTasks.get(key)!=null) {

            for (Runnable runnable : Objects.requireNonNull(allTasks.get(key))) {

                handler.removeCallbacks(runnable);

            }

            allTasks.remove(key);

        }

    }


    public void clearTask(){

        Iterator iterator = allTasks.keySet().iterator();

        while (iterator.hasNext()) {
            Object key = iterator.next();

            if(key instanceof Context){

                for(Runnable runnable: Objects.requireNonNull(allTasks.get(key))){

                    handler.removeCallbacks(runnable);

                }

                iterator.remove();
                allTasks.remove(key);
            }

        }

    }

    public void clearAllTask(){

        Iterator iterator = allTasks.keySet().iterator();

        while (iterator.hasNext()) {
            Object key = iterator.next();

             for(Runnable runnable: Objects.requireNonNull(allTasks.get(key))){

             handler.removeCallbacks(runnable);

             iterator.remove();

             allTasks.remove(key);
            }

        }

    }

    public void putTask(int delay,final CallBack callBack) {

        Runnable runnable = callBack::onBack;

        handler.postDelayed(runnable,delay);

    }

    public interface CallBack{

        void onBack();
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
