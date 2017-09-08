package com.thirty.java.newsapp;

import android.app.Application;

import java.util.Arrays;

/**
 * Created by fansy on 2017/9/8.
 */

public class MyApplication extends Application {
    static public boolean selected[] = new boolean[13];
    static public double volumnOfCategory[] = new double[13];
    public GetLatestNewsStream myNewsStream;

    public MyApplication(){
        super();

        for (int i = 1; i <= 12; ++i)
            volumnOfCategory[i] = 1.0;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Arrays.fill(selected, true);
    }
}