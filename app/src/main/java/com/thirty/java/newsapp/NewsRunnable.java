package com.thirty.java.newsapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by zyj on 2017/9/5.
 */

class NewsRunnable implements Runnable
{
    NewsRunnable(Handler handler, int pageNo, int pageSize)
    {
        this.handler = handler;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
    @Override
    public void run()
    {
        try
        {
            Bundle bundle = NewsApiCaller.getLatestNews(pageNo, pageSize);
            Message message = Message.obtain();
            message.setData(bundle);
            handler.sendMessage(message);
        }
        catch (Exception e)
        { Log.i("back", "failure in latestNewsRunnable()" + e.toString()); }
    }
    Handler handler;
    int pageNo;
    int pageSize;
}

