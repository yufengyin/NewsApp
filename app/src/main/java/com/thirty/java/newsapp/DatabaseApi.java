package com.thirty.java.newsapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyj on 2017/9/7.
 */

public class DatabaseApi
{
    static public void insertDetailedNews(Context context,DetailedNews detailedNews) // insert if not exist (else print Error)
    { new NewsDao(context).insert(new NewsBean(detailedNews)); }
    static public void deleteByID(Context context,String newsID) // delete if exists
    { new NewsDao(context).deleteById(newsID); }
    static public DetailedNews queryByID(Context context,String newsID) // update if exists
    { return new NewsDao(context).queryById(newsID).toDetailedNews(); }
    static public void updateDetailedNews(Context context,DetailedNews detailedNews) // if not exists, return null
    { new NewsDao(context).update(new NewsBean(detailedNews)); }
    static public List<DetailedNews> getAll(Context context)
    {
        List<NewsBean> newsBeanList = new NewsDao(context).queryForAll();
        ArrayList<DetailedNews> arrayList = new ArrayList<DetailedNews>();
        for (int i = 0; i < newsBeanList.size(); i++)
            arrayList.add(newsBeanList.get(i).toDetailedNews());
        return arrayList;
    }
}
