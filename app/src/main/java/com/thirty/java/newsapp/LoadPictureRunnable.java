package com.thirty.java.newsapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zyj on 2017/9/9.
 */

class LoadPictureRunnable implements Runnable
{
    LoadPictureRunnable(Handler handler, String urlString)
    {
        this.handler = handler;
        this.urlString = urlString;
    }
    @Override
    public void run()
    {
        try
        {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                InputStream is = conn.getInputStream();

            }
            else
            {
                throw new Exception();
            }
        }
        catch (Exception e)
        {}
    }
    Handler handler;
    String urlString;
}





