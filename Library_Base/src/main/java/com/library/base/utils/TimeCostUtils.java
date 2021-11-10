package com.library.base.utils;


import com.xuexiang.xutil.common.logger.Logger;

public class TimeCostUtils {


    public static long time = 0 ;

    public static void start(){

        time = System.currentTimeMillis();

    }

    public static void getLong(){

        Logger.d(System.currentTimeMillis()  -  time +"毫秒");
        time = System.currentTimeMillis();
    }


    public static void getLong(String what){

        Logger.d( what +":" + (System.currentTimeMillis()  -  time) +"毫秒");
        time = System.currentTimeMillis();
    }

}
