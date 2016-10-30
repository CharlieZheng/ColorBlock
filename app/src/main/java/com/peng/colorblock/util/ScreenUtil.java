package com.peng.colorblock.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Hanrong on 2016/10/30.
 */
public class ScreenUtil {
//    private static ScreenUtil instance;
//
//    private static Context context;
//    public synchronized  static ScreenUtil getInstance(Context context) {
//        if (instance == null) {
//            instance = new ScreenUtil(context);
//        }
//        return instance;
//    }
//    private ScreenUtil(Context context) {
//        this.context = context;
//    }

    public static int getHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float density = dm.density; // 屏幕密度（像素比例：./././.）
        int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：///）
        float xdpi = dm.xdpi;
        float ydpi = dm.ydpi;
        return dm.heightPixels;
    }

    public static int getWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }
}
