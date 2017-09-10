package com.thirty.java.newsapp;

/**
 * Created by zyj on 2017/9/5.
 */

import java.net.*;
import java.io.*;
import java.util.*;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;


public class NewsApiCaller
{
    public static final Map<String, Integer> map = new HashMap<String, Integer>(){{
        put("科技", 1); put("教育", 2); put("军事", 3);
        put("国内", 4); put("社会", 5); put("文化", 6);
        put("汽车", 7); put("国际", 8); put("体育", 9);
        put("财经", 10); put("健康", 11); put("娱乐", 12);
    }};

    private static Bundle getBriefNewsArrayByUrl(String urlString) throws Exception
    {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String responseString = br.readLine();
            JSONObject newsInJson = new JSONObject(responseString);
            // now newsInJson holds the result
            JSONArray newsListInJson = newsInJson.getJSONArray("list");
            BriefNews[] briefNewsArray = new BriefNews[newsListInJson.length()];
            for (int i = 0; i < briefNewsArray.length; i++)
            {
                JSONObject jsonObject = newsListInJson.getJSONObject(i);
                briefNewsArray[i] = new BriefNews(jsonObject.getString("newsClassTag"),
                        jsonObject.getString("news_Author"),
                        jsonObject.getString("news_ID"),
                        jsonObject.getString("news_Pictures"),
                        jsonObject.getString("news_Source"),
                        jsonObject.getString("news_Time"),
                        jsonObject.getString("news_Title"),
                        jsonObject.getString("news_URL"),
                        jsonObject.getString("news_Intro"));
            }
            Bundle bundle = new Bundle();
            bundle.putParcelableArray("briefNewsArray", briefNewsArray);
            return bundle;
        }
        else
        { throw new Exception(); }
    }

    private static Bundle getDetailedNewsByUrl(String urlString) throws Exception
    {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String responseString = br.readLine();
            JSONObject jsonObject = new JSONObject(responseString);
            DetailedNews news = new DetailedNews(jsonObject.getString("newsClassTag"),
                    jsonObject.getString("news_Author"),
                    jsonObject.getString("news_ID"),
                    jsonObject.getString("news_Pictures"),
                    jsonObject.getString("news_Source"),
                    jsonObject.getString("news_Time"),
                    jsonObject.getString("news_Title"),
                    jsonObject.getString("news_URL"),
                    jsonObject.getString("news_Content"));
            Bundle bundle = new Bundle();
            bundle.putParcelable("detailedNews", news);
            return bundle;
        }
        else
        { throw new Exception(); }
    }

    public static Bundle getLatestNews(int pageNo, int pageSize) throws Exception
    {
        String param = "?pageNo=" + pageNo + "&pageSize=" + pageSize;
        return getBriefNewsArrayByUrl("http://166.111.68.66:2042/news/action/query/latest" + param);
    }
    public static Bundle getLatestNews(int pageNo, int pageSize, String category) throws Exception
    {
        String param = "?pageNo=" + pageNo + "&pageSize=" + pageSize + "&category=" + map.get(category);
        return getBriefNewsArrayByUrl("http://166.111.68.66:2042/news/action/query/latest" + param);
    }
    public static Bundle searchNewsByKeyword(String keyword, int pageNo, int pageSize) throws Exception
    {
        String param = "?keyword=" + java.net.URLEncoder.encode(keyword, "utf-8") + "&pageNo=" + pageNo + "&pageSize=" + pageSize;
        return getBriefNewsArrayByUrl("http://166.111.68.66:2042/news/action/query/search" + param);
    }
    public static Bundle searchNewsByKeyword(String keyword, int pageNo, int pageSize, String category) throws Exception
    {
        String param = "?keyword=" + java.net.URLEncoder.encode(keyword, "utf-8") + "&pageNo=" + pageNo + "&pageSize=" + pageSize + "&category=" + map.get(category);
        return getBriefNewsArrayByUrl("http://166.111.68.66:2042/news/action/query/search" + param);
    }
    public static Bundle getDetailedNews(String newsID) throws Exception
    {
        String param = "?newsId=" + newsID;
        return getDetailedNewsByUrl("http://166.111.68.66:2042/news/action/query/detail" + param);
    }

}


