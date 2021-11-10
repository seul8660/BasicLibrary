package com.library.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;

import com.library.base.utils.ActUtils;
import com.library.base.utils.BindUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import me.yokeyword.fragmentation.SupportActivity;

public class AbstractActivity extends SupportActivity {


    public AbstractFragment curFragment;
    protected Activity mContext;
    /**设置 fragment 容器(必须)*/
    private int containerId;
    private LinkedHashMap<Class, AbstractFragment> fragments;
    /**数据传递*/
    private HashMap<String, Object> datas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

       /*新建activity时加入act管理器*/
        ActUtils.addAct(this);
        datas = new HashMap<>();
    }

    /**跳转至新的activity*/
    public void turnTo(Class cls){

        ActUtils.turnTo(this,cls);
    }

    /**移除当前activity*/
    public void backTo(){

        ActUtils.removeLastAct(this);
    }

    /**获取当前view*/
    public View getContentView(){
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        ViewGroup content = (ViewGroup) view.getChildAt(0);
        return content.getChildAt(0);
    }

    /**自动绑定view*/
    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);
        BindUtils.bindViews(this, getWindow().getDecorView());
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        BindUtils.bindViews(this, getWindow().getDecorView());
    }

    @CallSuper
    public void setContainerView(int containerId){

        this.containerId = containerId;

        fragments = new LinkedHashMap<>();
    }

    public void turnToChild(Class key){

        if(!fragments.containsKey(key)) {
            try {

                AbstractFragment fragment = (AbstractFragment) key.newInstance();

                fragments.put(key,fragment);

                curFragment = fragment;

                if(getTopFragment()==null) {
                    loadRootFragment(containerId,curFragment);
                } else {
                    start(curFragment);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            curFragment = fragments.get(key);
            showHideFragment(curFragment);

            /*将当前fragment移至栈顶*/
            fragments.remove(key);
            fragments.put(key,curFragment);

        }
    }

    public void removeFragment(AbstractFragment fragment){


        if(fragments.containsValue(fragment)){

            fragments.remove(fragment.getClass());

        }

    }

    /**将最后一个页面移出堆栈，展示前一个页面*/
    public void removeLastFragment(){

        curFragment.onFinish();

        fragments.remove(curFragment.getClass());

        Iterator<Map.Entry<Class, AbstractFragment>> iterator = fragments.entrySet().iterator();

        Map.Entry<Class, AbstractFragment> entry = null;

        while (iterator.hasNext()){

            entry = iterator.next();
        }

        if(entry!=null) {
            curFragment = entry.getValue();
        }

        curFragment.onReLoad();

        turnToChild(curFragment.getClass());

    }

    public <T> T get(String key, T defaultValue){

        if(datas==null) {
            datas = new HashMap<>();
        }

        if(datas.get(key)==null) {
            return defaultValue;
        } else {
            return  (T)datas.get(key);
        }
    }

    public void put(String key, Object value){

        if(datas==null) {
            datas = new HashMap<>();
        }
        datas.put(key,value);

    }

}
