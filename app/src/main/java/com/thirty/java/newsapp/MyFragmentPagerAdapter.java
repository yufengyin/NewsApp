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
import android.util.Pair;
import java.util.List;

/**
 * Created by 321yy on 2017/9/7.
 */

// ViewPager的适配器。
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    static public GetLatestNewsStream mNewsStream[];
    private LatestNewsDataDistributor mNewsDataDistributor = new LatestNewsDataDistributor();

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
            onReceiveNews(briefNewsArray, message.what);
        }
    };

    public void onReceiveNews(BriefNews[] briefNewsArray, int flag){
        if (briefNewsArray.length > 0) {
            int type;
            if (flag == LatestNewsDataDistributor.RECOMMEND)
                type = 0;
            else
                type = MyApplication.map.get(briefNewsArray[0].newsClassTag);
            myNewsdataset[type].addAll(Arrays.asList(briefNewsArray));
            for(int i = 0; i < myInterestDataset.length; ++i){
                if(myInterestDataset[i].equals(MyApplication.interestDateSet[type])){
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

    public void requestForNews(int mCategoryNo){
        int numberOfNews = 10;
        if (mCategoryNo == 0){
            //推荐
            double total_volume = 0;
            for (int i = 1; i < MyApplication.interestDateSet.length; ++i)
                total_volume += MyApplication.volumnOfCategory[i];

            double possibility[] = new double[MyApplication.interestDateSet.length];
            for (int i = 1; i < MyApplication.interestDateSet.length; ++i)
                possibility[i] = MyApplication.volumnOfCategory[i] / total_volume;

            int count[] = new int[MyApplication.interestDateSet.length];
            Arrays.fill(count, 0);
            for (int i = 1; i <= numberOfNews; ++i){
                double corona  = Math.random();
                for (int j = 1; j < MyApplication.interestDateSet.length; ++j){
                    if (corona <= possibility[j] || j == MyApplication.interestDateSet.length - 1){
                        ++count[j];
                        break;
                    }
                    corona -= possibility[j];
                }
            }

            List<Pair<String, Integer>> mList = new ArrayList<Pair<String, Integer>>();

            for (int i = 1; i < MyApplication.interestDateSet.length; ++i)
                if (count[i] > 0) {
                    mList.add(new Pair<String, Integer>(MyApplication.interestDateSet[i], count[i]));
                }
            mNewsDataDistributor.getNext(handler, mList);
        }
        else{
            //正常分类
            mNewsStream[mCategoryNo].getNext(handler, numberOfNews);
        }
    }

    public void initiateNewsData(){
        //如果流未被创建则创建流
        if (mNewsStream == null){
            mNewsStream = new GetLatestNewsStream[MyApplication.interestDateSet.length];
            for (int i = 1; i < MyApplication.interestDateSet.length; ++i)
                mNewsStream[i] = new GetLatestNewsStream(MyApplication.interestDateSet[i]);
        }

        for (int i = 0; i < MyApplication.interestDateSet.length; ++i) {
            if (MyApplication.selected[i] && myNewsdataset[i] == null) {
                myNewsdataset[i] = new ArrayList<>();
                requestForNews(i);
            }
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
