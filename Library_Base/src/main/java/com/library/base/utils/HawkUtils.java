package com.library.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.Storage;
import com.xuexiang.xutil.app.ProcessUtils;
import com.xuexiang.xutil.data.ACache;

public class HawkUtils {


    private static ACache aCache;

    public static void init(Context context){

        Hawk.init(context).setStorage(new XSharedPreferencesStorage(context, ProcessUtils.getCurrentProcessName())).build();

        aCache = ACache.get(context.getCacheDir());
    }

    public static ACache getCache(){

        return aCache;
    }

    public static <T> void put(String key,T t){

        Hawk.put(key,t);

    }


    public static <T> T get(String key,T t){

        if(Hawk.contains(key) && Hawk.get(key)!=null) return Hawk.get(key);

        else return t;

    }

    public static void delete(String key){

        Hawk.delete(key);
    }

    public static boolean contain(String key){

        return Hawk.contains(key);
    }



    private static class XSharedPreferencesStorage implements Storage {


        private final SharedPreferences preferences;

        public XSharedPreferencesStorage(Context context, String tag) {
            preferences = context.getSharedPreferences(tag, Context.MODE_PRIVATE);
        }

        @Override public <T> boolean put(String key, T value) {

            if(value == null) throw new NullPointerException(key + " should not be null");

            return getEditor().putString(key, String.valueOf(value)).commit();
        }

        @SuppressWarnings("unchecked")
        @Override public <T> T get(String key) {
            return (T) preferences.getString(key, null);
        }

        @Override public boolean delete(String key) {
            return getEditor().remove(key).commit();
        }

        @Override public boolean contains(String key) {
            return preferences.contains(key);
        }

        @Override public boolean deleteAll() {
            return getEditor().clear().commit();
        }

        @Override public long count() {
            return preferences.getAll().size();
        }

        private SharedPreferences.Editor getEditor() {
            return preferences.edit();
        }


    }


}
