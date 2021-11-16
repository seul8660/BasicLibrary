package com.library.base;

import android.app.Application;

import com.hjq.toast.ToastUtils;
import com.library.base.utils.ActUtils;
import com.library.base.utils.HawkUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/**
 * @author asus
 */
public class BasicInit {

    private static Application mApplication;

    public static void init(Application application){

        mApplication = application;

        //activity管理器初始化
        ActUtils.init(mApplication);

        //key - value 数据库初始化
        HawkUtils.init(mApplication);

        ToastUtils.init(mApplication);

        //下拉刷新组件初始化
        SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) -> {
            layout.setEnableAutoLoadMore(false);
            layout.setEnableLoadMore(true);
            layout.setEnableRefresh(true);
        });

        //初始化图片加载器
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(application));

    }


    public static Application getApplication() throws Exception {

        if(mApplication == null) {
            throw new Exception("Library_Base must be init First");
        }

        return mApplication;
    }
}
