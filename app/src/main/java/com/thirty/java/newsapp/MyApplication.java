package com.thirty.java.newsapp;

import android.app.Application;

import java.util.Arrays;

/**
 * Created by fansy on 2017/9/8.
 */

public class MyApplication extends Application {
    static public boolean selected[] = new boolean[13];

    public MyApplication(){
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Arrays.fill(selected, true);
    }
}