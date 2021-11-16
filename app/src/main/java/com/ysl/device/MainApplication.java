package com.ysl.device;

import android.app.Application;

import com.library.base.BasicInit;

/**
 * @author 刘元强
 * 2021-11-16
 * 09:23
 */
public class MainApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        BasicInit.init(this);




    }
}
