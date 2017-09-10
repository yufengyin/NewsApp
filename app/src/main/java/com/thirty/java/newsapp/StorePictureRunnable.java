package com.thirty.java.newsapp;

/**
 * Created by zyj on 2017/9/9.
 */

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class StorePictureRunnable implements Runnable
{
    public static final int NO_PICTURE = 111;
    public static final int DOWNLOAD_FAILED = 222;
    public static final int SUCCESS = 333;

    public static final int TO_CACHE = 0;
    public static final int TO_COLLECTION = 1;

    public StorePictureRunnable(Handler handler, DetailedNews detailedNews, int DIR)
    {
        this.handler = handler;
        this.urlString = detailedNews.newsPicture;
        this.newsID = detailedNews.newsID;
        this.DIR = DIR;
    }
    public StorePictureRunnable(Handler handler, String urlString, String newsID, int DIR)
    {
        this.handler = handler;
        this.urlString = urlString;
        this.newsID = newsID;
        this.DIR = DIR;
    }
    @Override
    public void run()
    {
        Bundle bundle = new Bundle();
        Message message = Message.obtain();
        if (urlString == null || urlString.length() == 0)
            message.what = NO_PICTURE;
        else {
            try {
                if (this.DIR == TO_CACHE) {
                    PictureApi.storePictureFromUrlToCache(urlString, newsID + ".jpg");
                    bundle.putString("filename", PictureApi.getPictureNameFromCache(newsID));
                    bundle.putString("newsID", newsID);
                }
                else {
                    PictureApi.storePictureFromUrlToCollection(urlString, newsID + ".jpg");
                    bundle.putString("filename", PictureApi.getPictureNameFromCollection(newsID));
                    bundle.putString("newsID", newsID);
                }
                message.what = SUCCESS;
            } catch (Exception e) {
                message.what = DOWNLOAD_FAILED;
            }
        }
        message.setData(bundle);
        handler.sendMessage(message);
    }
    private Handler handler;
    private String newsID;
    private String urlString;

    private int DIR;
}
