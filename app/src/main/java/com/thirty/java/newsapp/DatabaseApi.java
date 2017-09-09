package com.thirty.java.newsapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyj on 2017/9/7.
 */

public class DatabaseApi
{
    static public void insertDetailedNewsIntoCache(DetailedNews detailedNews) // insert if not exist (else print Error)
    { new NewsDao().insert(new NewsBean(detailedNews)); }
    static public void deleteByIDInCache(String newsID) // delete if exists
    { new NewsDao().deleteById(newsID); }
    static public DetailedNews queryByIDInCache(String newsID) // if not exists, return null
    { return new NewsDao().queryById(newsID).toDetailedNews(); }
    static public void updateDetailedNewsInCache(DetailedNews detailedNews)
    { new NewsDao().update(new NewsBean(detailedNews)); }
    static public List<DetailedNews> getAllInCache()
    {
        List<NewsBean> newsBeanList = new NewsDao().queryForAll();
        ArrayList<DetailedNews> arrayList = new ArrayList<DetailedNews>();
        for (int i = 0; i < newsBeanList.size(); i++)
            arrayList.add(newsBeanList.get(i).toDetailedNews());
        return arrayList;
    }

    static public void insertDetailedNewsIntoCollection(DetailedNews detailedNews) // insert if not exist (else print Error)
    { new CollectionNewsDao().insert(new CollectionNewsBean(detailedNews)); }
    static public void deleteByIDInCollection(String newsID) // delete if exists
    { new CollectionNewsDao().deleteById(newsID); }
    static public DetailedNews queryByIDInCollection(String newsID) // update if exists
    { return new CollectionNewsDao().queryById(newsID).toDetailedNews(); }
    static public void updateDetailedNewsInCollection(DetailedNews detailedNews) // if not exists, return null
    { new CollectionNewsDao().update(new CollectionNewsBean(detailedNews)); }
    static public List<DetailedNews> getAllInCollection()
    {
        List<CollectionNewsBean> newsBeanList = new CollectionNewsDao().queryForAll();
        ArrayList<DetailedNews> arrayList = new ArrayList<DetailedNews>();
        for (int i = 0; i < newsBeanList.size(); i++)
            arrayList.add(newsBeanList.get(i).toDetailedNews());
        return arrayList;
    }

    static public boolean isRead(String newsID) {
        if (queryByIDInCache(newsID) == null)
            return false;
        return true;
    }

}
