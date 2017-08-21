package com.yao.sharedelementtransition.util;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.yao.sharedelementtransition.data.SharedElement;

import java.util.ArrayList;

/**
 * Created by YAO on 2017/8/20.
 */

public class ViewUtil {

    public static SharedElement getSharedElement(Activity aty, View view) {
        SharedElement element = new SharedElement();
        element.width = view.getWidth();
        element.height = view.getHeight();
        int []location = new int[2];
        view.getLocationInWindow(location);
        element.x = location[0];//获取当前位置的横坐标
        element.y = location[1];//获取当前位置的纵坐标
        Log.e("YAO", "ViewUtil.java - getSharedElement() ----- x:" + element.x + " y:" + element.y);
        return element;
    }

    public static ArrayList<SharedElement> getSharedElement(Activity aty, View ...views) {
        ArrayList<SharedElement> elements = new ArrayList<>();
        for (View view : views) {
            SharedElement element = new SharedElement();
            element.resId = view.getId();
            element.width = view.getWidth();
            element.height = view.getHeight();
            int []location = new int[2];
            view.getLocationInWindow(location);
            element.x = location[0];
            element.y = location[1];
            elements.add(element);
        }
        return elements;
    }
}
