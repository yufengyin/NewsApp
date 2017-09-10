package com.thirty.java.newsapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zyj on 2017/9/8.
 */

public class GetLatestNewsStream implements NewsStream
{
    Handler paramHandler;
    List<BriefNews> briefNewsList;

    int tempN;
    int currentPage;
    static final int pageSize = 10;
    String category;
    boolean END_OF_STREAM;
    boolean REQUESTING;

    static final int HAS_NEWS = 1;
    static final int NO_NEWS = 2;

    private Handler tranferHandler = new Handler() {
        @Override
        public void handleMessage(Message message) {
        BriefNews[] briefNewsArray = (BriefNews[]) message.getData().getParcelableArray("briefNewsArray");
        if (briefNewsArray.length == 0)
            END_OF_STREAM = true;
        briefNewsList.addAll(Arrays.asList(briefNewsArray));
        if (REQUESTING)
            getNext(paramHandler, tempN); // only when requesting


        }
    };
    public GetLatestNewsStream(String category)
    {
        this.REQUESTING = false;
        this.END_OF_STREAM = false;
        this.currentPage = 0;
        this.category = category;
        if (this.category == "")
            this.category = null;
        briefNewsList = new ArrayList<BriefNews>();
    }
    public GetLatestNewsStream()
    {
        this(null);
    }
    @Override
    synchronized public void getNext(Handler handler, int n) // n cannot be zero
    {
        paramHandler = handler;
        REQUESTING = true; // start
        tempN = n;
        if (briefNewsList.size() < n && !END_OF_STREAM)
        {
            currentPage++;
            Runnable runnable = new GetLatestNewsRunnable(tranferHandler, currentPage, pageSize, category);
            new Thread(runnable).start();
        }
        else if (briefNewsList.size() == 0 && END_OF_STREAM)
        {
            Bundle bundle = new Bundle();
            bundle.putParcelableArray("briefNewsArray", new BriefNews[0]);
            Message message = Message.obtain();
            message.setData(bundle);
            message.what = NO_NEWS;
            paramHandler.sendMessage(message);
            REQUESTING = false; // end
        }
        else
        {
            BriefNews[] briefNewsArray = briefNewsList.subList(0, n).toArray(new BriefNews[0]);
            briefNewsList = briefNewsList.subList(n, briefNewsList.size());
            Bundle bundle = new Bundle();
            bundle.putParcelableArray("briefNewsArray", briefNewsArray);
            Message message = Message.obtain();
            message.setData(bundle);
            message.what = HAS_NEWS;
            paramHandler.sendMessage(message);
            REQUESTING = false; // end
        }
    }
}
