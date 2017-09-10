package com.thirty.java.newsapp;

/**
 * Created by zyj on 2017/9/8.
 */

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LatestNewsDataDistributor
{
    static public final int RECOMMEND = 777;

    Handler paramHandler;
    List<BriefNews> briefNewsList;

    List<Pair<String, Integer>> requestList;
    Map<String, GetLatestNewsStream> categoryStreamMap;

    int currentIndex;

    private Handler tranferHandler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            BriefNews[] briefNewsArray = (BriefNews[]) message.getData().getParcelableArray("briefNewsArray");
            briefNewsList.addAll(Arrays.asList(briefNewsArray));
            if (currentIndex < requestList.size())
            {
                currentIndex++;
                getNext(paramHandler, requestList);
            }
        }
    };
    public LatestNewsDataDistributor()
    {
        categoryStreamMap = new HashMap<String, GetLatestNewsStream>();
        for (String key : NewsApiCaller.map.keySet())
        {
            categoryStreamMap.put(key, new GetLatestNewsStream(key));
        }
        currentIndex = 0;
        briefNewsList = new ArrayList<BriefNews>();
    }
    public void getNext(Handler handler, List<Pair<String, Integer>> list)
    {
        this.requestList = list;
        paramHandler = handler;
        if (currentIndex < requestList.size())
        {
            if ( list.get(currentIndex).second > 0
                && NewsApiCaller.map.containsKey(list.get(currentIndex).first))
            categoryStreamMap
                    .get(list.get(currentIndex).first)
                    .getNext(tranferHandler, list.get(currentIndex).second);
        }
        else
        {
            Bundle bundle = new Bundle();
            bundle.putParcelableArray("briefNewsArray", briefNewsList.toArray(new BriefNews[0]));
            Message message = Message.obtain();
            message.setData(bundle);
            message.what = RECOMMEND;
            paramHandler.sendMessage(message);
            currentIndex = 0;
            briefNewsList = new ArrayList<BriefNews>();
            // exit
        }
    }
}
