package com.thirty.java.newsapp;

import android.app.Application;

import java.util.Arrays;

/**
 * Created by fansy on 2017/9/8.
 */

public class MyApplication extends Application {
    static public String[] interestDateSet = new String[]{
            "推荐", "科技", "教育", "军事", "国内",
            "社会", "文化", "汽车", "国际", "体育",
            "财经", "健康", "娱乐"
    };
    static public boolean selected[] = new boolean[interestDateSet.length];
    static public double volumnOfCategory[] = new double[interestDateSet.length];
    static int focusPage = 0;
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
