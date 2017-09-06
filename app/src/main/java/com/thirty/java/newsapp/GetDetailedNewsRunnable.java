package com.thirty.java.newsapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by zyj on 2017/9/6.
 */

class GetDetailedNewsRunnable implements Runnable
{
    GetDetailedNewsRunnable(Handler handler, String newsID)
    {
        this.handler = handler;
        this.newsID = newsID;
    }
    @Override
    public void run()
    {
        try
        {
            Bundle bundle = NewsApiCaller.getDetailedNews(newsID);
            Message message = Message.obtain();
            message.setData(bundle);
            handler.sendMessage(message);
        }
        catch (Exception e)
        { Log.i("back", "failure in latestNewsRunnable(): " + e.toString()); }
    }
    Handler handler;
    String newsID;
}


