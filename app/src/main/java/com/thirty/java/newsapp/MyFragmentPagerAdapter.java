package com.thirty.java.newsapp;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by 321yy on 2017/9/7.
 */

// ViewPager的适配器。
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    static public GetLatestNewsStream mNewsStream[];


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            BriefNews[] briefNewsArray = (BriefNews[]) message.getData().getParcelableArray("briefNewsArray");
            onReceiveNews(briefNewsArray);
        }
    };

    public void onReceiveNews(BriefNews[] briefNewsArray){
        if (briefNewsArray.length > 0) {
            int type = MyApplication.map.get(briefNewsArray[0].newsClassTag);
            myNewsdataset[type].addAll(Arrays.asList(briefNewsArray));
        }
    }

    public ArrayList<NewsFragment> fragments;
    static public String[] myInterestDataset = new String[]{
            "推荐", "科技", "教育", "军事", "国内",
            "社会", "文化", "汽车", "国际", "体育",
            "财经", "健康", "娱乐"
    };
    public static ArrayList<BriefNews>[] myNewsdataset = new ArrayList[MyApplication.interestDateSet.length];

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
        //如果流未被创建则创建流
        if (mNewsStream == null){
            for (int i = 1; i <= MyApplication.interestDateSet.length; ++i)
                mNewsStream[i] = new GetLatestNewsStream(MyApplication.interestDateSet[i]);
        }

        for (int i = 1; i <= MyApplication.interestDateSet.length; ++i)
            if (MyApplication.selected[i] == true && myNewsdataset[i] == null){
                myNewsdataset[i] = new ArrayList<>();
                mNewsStream[i].getNext(handler, 10);
            }

        //to do
        if (myNewsdataset[0] == null){
            myNewsdataset[0] = new ArrayList<>();
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
