package com.library.base.utils;

import java.text.DecimalFormat;

/**
 * @author 刘元强
 * 2021-11-12
 * 08:54
 */
public class BasicUtils {


    /*保留两位小数*/
    public static String savePoint(Object number) {

        float distanceValue = Math.round((Double.parseDouble(String.valueOf(number)) * 100f)) / 100f;
        //构造方法的字符格式这里如果小数不足2位,会以0补足.
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        //format 返回的是字符串
        return decimalFormat.format(distanceValue);
    }


    /*数字补0*/
    public static <T> String getZero(T value) {
        String mValue = value + "";
        if (mValue.length() == 1) {
            mValue = "0" + mValue;
        }

        return mValue;
    }

    /*数字去0*/
    public static <T> String removeZero(T value) {
        String mValue = value + "";
        if (mValue.length() == 2 && mValue.charAt(0) == '0') {
            mValue = mValue.substring(1);
        }
        return mValue;
    }



}
