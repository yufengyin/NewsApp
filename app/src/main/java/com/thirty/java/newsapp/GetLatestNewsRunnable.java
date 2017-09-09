package com.thirty.java.newsapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by zyj on 2017/9/5.
 */

public class GetLatestNewsRunnable implements Runnable
{
    GetLatestNewsRunnable(Handler handler, int pageNo, int pageSize, String category)
    {
        this.handler = handler;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.category = category;
    }
    GetLatestNewsRunnable(Handler handler, int pageNo, int pageSize)
    { this(handler, pageNo, pageSize, null); }
    @Override
    synchronized public void run()
    {
        try
        {
            Bundle bundle;
            if (category != null)
                bundle = NewsApiCaller.getLatestNews(pageNo, pageSize, category);
            else
                bundle = NewsApiCaller.getLatestNews(pageNo, pageSize);
            Message message = Message.obtain();
            message.setData(bundle);
            message.what = 0;
            handler.sendMessage(message);
        }
        catch (Exception e)
        { Log.i("back", "failure in latestNewsRunnable(): " + e.toString()); }
    }
    Handler handler;
    int pageNo;
    int pageSize;
    String category;
}








