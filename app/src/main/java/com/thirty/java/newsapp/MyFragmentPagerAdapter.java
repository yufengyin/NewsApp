package com.thirty.java.newsapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by 321yy on 2017/9/7.
 */

// ViewPager的适配器。
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;
    static public int SIZE = 7;
    static public String[] myInterestDataset = new String[]{
            "推荐", "科技", "教育", "军事", "国内", "社会", "文化"
    };

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<Fragment>();
        for (int i = 0; i < SIZE; i++) {
            Fragment f = NewsFragment.newInstance(i);
            fragments.add(f);
        }
    }

    @Override
    public Fragment getItem(int pos) {
        return fragments.get(pos);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
