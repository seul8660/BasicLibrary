package com.library.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.library.base.utils.BindUtils;
import com.library.base.utils.ViewUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;



/**
 * @author asus
 */
public abstract class AbstractFragment extends SupportFragment implements View.OnTouchListener {


    private ViewUtils viewUtils;
    private AbstractActivity mContext;
    private Map<Class, AbstractFragment> fragments;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mContext = (AbstractActivity) _mActivity;

    }

    @CallSuper
    protected void setContentView(int layout){

        viewUtils = new ViewUtils(mContext,layout);
        /*自动绑定view*/
        BindUtils.bindViews(this, viewUtils.getConvertView());

    }

    protected View findViewById(int id){

        return viewUtils.getView(id);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /*消费掉点击事件，防止点击穿透至下一层*/
        viewUtils.getConvertView().setOnTouchListener(this);

        return viewUtils.getConvertView();
    }

    protected void turnTo(Class cls){

        mContext.turnToChild(cls);
    }

    protected void turnToAct(Class cls){

        mContext.turnTo(cls);
    }

    protected void turnToWithFinish(Class cls){

        mContext.turnToChild(cls);
        mContext.removeFragment(this);
    }

    protected void backTo(){

        mContext.removeLastFragment();

    }

    protected void turnToChild(Class cls){

        showHideFragment(fragments.get(cls));

    }

    protected void initChildFragment(int layoutId, List<Class> childes){
        fragments = new HashMap<>();

        List<ISupportFragment> list = new ArrayList<>();
        for(Class cls :childes){

            try {
                AbstractFragment fragment = (AbstractFragment) cls.newInstance();
                fragments.put(cls,fragment);
                list.add(fragment);

            } catch (java.lang.InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }


        }

        loadMultipleRootFragment(layoutId,0,list.toArray(new ISupportFragment[0]));


    }


    public <T> T get(String key, T defaultValue){

       return mContext.get(key,defaultValue);
    }

    public void put(String key, Object value){

        mContext.put(key, value);
    }

    /**重新进入该界面，相当于activity 的 resume*/
    protected abstract void onReLoad();

    /** 结束该界面*/
    protected abstract void onFinish();

    /**消费掉点击事件,防止跑到下一层去*/
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

}
