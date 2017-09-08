package com.thirty.java.newsapp;

import android.app.Application;

import java.util.Arrays;

/**
 * Created by fansy on 2017/9/8.
 */

public class MyApplication extends Application {
    static public boolean selected[] = new boolean[MyFragmentPagerAdapter.myInterestDataset.length];
    static public String[] interestDateSet = new String[]{
            "推荐", "科技", "教育", "军事", "国内",
            "社会", "文化", "汽车", "国际", "体育",
            "财经", "健康", "娱乐"
    };
    static int focusPage = 0;
    public MyApplication(){
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Arrays.fill(selected, true);
    }
}
