package com.java.thirty.newsapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by zyj on 2017/9/6.
 */

public class SearchNewsByKeywordRunnable implements Runnable{
    SearchNewsByKeywordRunnable(Handler handler, String keyword, int pageNo, int pageSize, String category)
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
        this.category = null;
    }
    @Override
    public void run()
    {
        try
        {
            Bundle bundle;
            if (category != null)
                bundle = NewsApiCaller.searchNewsByKeyword(keyword, pageNo, pageSize, category);
            else
                bundle = NewsApiCaller.searchNewsByKeyword(keyword, pageNo, pageSize);
            Message message = Message.obtain();
            message.setData(bundle);
            handler.sendMessage(message);
        }
        catch (Exception e)
        { e.printStackTrace(); }
    }
    Handler handler;
    String keyword;
    int pageNo;
    int pageSize;
    String category;
}
