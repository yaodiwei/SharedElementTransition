package com.yao.sharedelementtransition.util;

import android.content.Context;

/**
 * Created by YAO on 2017/8/20.
 */

public class ScreenUtil {

    public static int getScreenWidth(Context ctx){
        return ctx.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context ctx){
        return ctx.getResources().getDisplayMetrics().heightPixels;
    }
}
