package com.thirty.java.newsapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by 321yy on 2017/9/7.
 */

// ViewPager的适配器。
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    public ArrayList<NewsFragment> fragments;
    static public String[] myInterestDataset = new String[]{
            "推荐", "科技", "教育", "军事", "国内",
            "社会", "文化", "汽车", "国际", "体育",
            "财经", "健康", "娱乐"
    };
    public ArrayList<BriefNews>[] myNewsdataset;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        initiateNewsData();
        fragments = new ArrayList<NewsFragment>();
        for (int i = 0; i < myInterestDataset.length; i++) {
            NewsFragment f = NewsFragment.newInstance(i);
            f.mCategory = myInterestDataset[i];
            fragments.add(f);
        }
    }

    public void initiateNewsData(){

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
