package com.thirty.java.newsapp;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 321yy on 2017/9/7.
 */

// ViewPager的适配器。
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    static public GetLatestNewsStream mNewsStream[];

    public ArrayList<NewsFragment> fragments;
    static public String[] myInterestDataset = new String[]{
            "推荐", "科技", "教育", "军事", "国内",
            "社会", "文化", "汽车", "国际", "体育",
            "财经", "健康", "娱乐"
    };

    static public ArrayList<BriefNews>[] myNewsdataset = new ArrayList[MyApplication.interestDateSet.length];

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
            for(int i = 0; i < myInterestDataset.length; ++i){
                if(myInterestDataset[i].equals(briefNewsArray[0].newsClassTag)){
                    fragments.get(i).mFragmentAdapter.mDataset = myNewsdataset[type].toArray(new BriefNews[0]);
                    fragments.get(i).mFragmentAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<NewsFragment>();
        for (int i = 0; i < myInterestDataset.length; i++) {
            NewsFragment f = NewsFragment.newInstance(i);
            f.mCategory = myInterestDataset[i];
            fragments.add(f);
        }
        initiateNewsData();
    }

    public void initiateNewsData(){
        //如果流未被创建则创建流
        if (mNewsStream == null){
            mNewsStream = new GetLatestNewsStream[MyApplication.interestDateSet.length];
            for (int i = 1; i < MyApplication.interestDateSet.length; ++i)
                mNewsStream[i] = new GetLatestNewsStream(MyApplication.interestDateSet[i]);
        }

        for (int i = 1; i < MyApplication.interestDateSet.length; ++i) {
            if (MyApplication.selected[i] && myNewsdataset[i] == null) {
                myNewsdataset[i] = new ArrayList<>();
                mNewsStream[i].getNext(handler, 10);
            }
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
