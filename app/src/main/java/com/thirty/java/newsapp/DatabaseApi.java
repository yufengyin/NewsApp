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
    {
        NewsBean newsBean = new NewsDao().queryById(newsID);
        if (newsBean == null)
            return null;
        else return newsBean.toDetailedNews();
    }
    static public void updateDetailedNewsInCache(DetailedNews detailedNews)
    { new NewsDao().update(new NewsBean(detailedNews)); }
    static public List<BriefNews> getAllInCache()
    {
        List<NewsBean> newsBeanList = new NewsDao().queryForAll();
        ArrayList<DetailedNews> arrayList = new ArrayList<DetailedNews>();
        for (int i = 0; i < newsBeanList.size(); i++)
            arrayList.add(newsBeanList.get(i).toDetailedNews());
        ArrayList<BriefNews> briefNewsArrayList = new ArrayList<BriefNews>();
        for (int i = 0; i < arrayList.size(); i++)
            briefNewsArrayList.add(arrayList.get(i).toBriefNews());
        return briefNewsArrayList;
    }

    static public void insertDetailedNewsIntoCollection(DetailedNews detailedNews) // insert if not exist (else print Error)
    { new CollectionNewsDao().insert(new CollectionNewsBean(detailedNews)); }
    static public void deleteByIDInCollection(String newsID) // delete if exists
    { new CollectionNewsDao().deleteById(newsID); }
    static public DetailedNews queryByIDInCollection(String newsID) // if not exists, return null
    {
        CollectionNewsBean collectionNewsBean = new CollectionNewsDao().queryById(newsID);
        if (collectionNewsBean == null)
            return null;
        else return collectionNewsBean.toDetailedNews();
    }
    static public void updateDetailedNewsInCollection(DetailedNews detailedNews)
    { new CollectionNewsDao().update(new CollectionNewsBean(detailedNews)); }
    static public List<BriefNews> getAllInCollection()
    {
        List<CollectionNewsBean> newsBeanList = new CollectionNewsDao().queryForAll();
        ArrayList<DetailedNews> arrayList = new ArrayList<DetailedNews>();
        for (int i = 0; i < newsBeanList.size(); i++)
            arrayList.add(newsBeanList.get(i).toDetailedNews());
        ArrayList<BriefNews> briefNewsArrayList = new ArrayList<BriefNews>();
        for (int i = 0; i < arrayList.size(); i++)
            briefNewsArrayList.add(arrayList.get(i).toBriefNews());
        return briefNewsArrayList;
    }

    static public boolean isRead(String newsID) {
        if (queryByIDInCache(newsID) == null)
            return false;
        return true;
    }

}
