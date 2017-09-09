package com.thirty.java.newsapp;

import android.graphics.Bitmap;
import android.os.Bundle;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zyj on 2017/9/9.
 */

public class PictureApi
{
//
//    private static Bitmap getPictureByUrl(String urlString) throws Exception
//    {
//        URL url = new URL(urlString);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setConnectTimeout(5 * 1000);
//        conn.setRequestMethod("GET");
//
//        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
//        {
//            InputStream is = conn.getInputStream();
//        }
//        else
//        { throw new Exception(); }
//    }
}
