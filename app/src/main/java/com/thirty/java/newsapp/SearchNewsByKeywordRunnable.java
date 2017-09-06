package com.thirty.java.newsapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by zyj on 2017/9/6.
 */

public class SearchNewsByKeywordRunnable implements Runnable{
    SearchNewsByKeywordRunnable(Handler handler, String keyword, int pageNo, int pageSize, int category)
    {
        this.handler = handler;
        this.keyword = keyword;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.category = category;
    }
    SearchNewsByKeywordRunnable(Handler handler, String keyword, int pageNo, int pageSize)
    {
        this.handler = handler;
        this.keyword = keyword;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.category = 0;
    }
    @Override
    public void run()
    {
        try
        {
            Bundle bundle;
            if (category != 0)
                bundle = NewsApiCaller.searchNewsByKeyword(keyword, pageNo, pageSize, category);
            else
                bundle = NewsApiCaller.searchNewsByKeyword(keyword, pageNo, pageSize);
            Message message = Message.obtain();
            message.setData(bundle);
            handler.sendMessage(message);
        }
        catch (Exception e)
        { Log.i("back", "SearchNewsByKeywordRunnable(): " + e.toString()); }
    }
    Handler handler;
    String keyword;
    int pageNo;
    int pageSize;
    int category;
}
