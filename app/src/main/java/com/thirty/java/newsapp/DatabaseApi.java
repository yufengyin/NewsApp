package com.thirty.java.newsapp;

import android.content.Context;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyj on 2017/9/7.
 */

public class DatabaseApi
{
    public static void clearAll()
    {
        PictureApi.clearCachePicture();
        PictureApi.clearCollectionPicture();
        DatabaseApi.clearCache();
        DatabaseApi.clearCollection();
    }



    public static void clearCache()
    {
        try
        { DBHelper.getHelper().clearCacheTable(); }
        catch (SQLException e)
        { Log.i("back", e.toString()); }
    }
    public static void clearCollection()
    {
        try
        { DBHelper.getHelper().clearCollectionTable(); }
        catch (SQLException e)
        { Log.i("back", e.toString()); }
    }
    public static void insertDetailedNewsIntoCache(DetailedNews detailedNews) // insert if not exist (else print Error)
    { new NewsDao().insert(new NewsBean(detailedNews)); }
    public static void deleteByIDInCache(String newsID) // delete if exists
    { new NewsDao().deleteById(newsID); }
    public static DetailedNews queryByIDInCache(String newsID) // if not exists, return null
    {
        NewsBean newsBean = new NewsDao().queryById(newsID);
        if (newsBean == null)
            return null;
        else return newsBean.toDetailedNews();
    }
    public static void updateDetailedNewsInCache(DetailedNews detailedNews)
    { new NewsDao().update(new NewsBean(detailedNews)); }
    public static List<BriefNews> getAllInCache()
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

    public static void insertDetailedNewsIntoCollection(DetailedNews detailedNews) // insert if not exist (else print Error)
    { new CollectionNewsDao().insert(new CollectionNewsBean(detailedNews)); }
    public static void deleteByIDInCollection(String newsID) // delete if exists
    { new CollectionNewsDao().deleteById(newsID); }
    public static DetailedNews queryByIDInCollection(String newsID) // if not exists, return null
    {
        CollectionNewsBean collectionNewsBean = new CollectionNewsDao().queryById(newsID);
        if (collectionNewsBean == null)
            return null;
        else return collectionNewsBean.toDetailedNews();
    }
    public static void updateDetailedNewsInCollection(DetailedNews detailedNews)
    { new CollectionNewsDao().update(new CollectionNewsBean(detailedNews)); }
    public static List<BriefNews> getAllInCollection()
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

    public static boolean isRead(String newsID) {
        if (queryByIDInCache(newsID) == null)
            return false;
        return true;
    }

    public static boolean isCollected(String newsID) {
        if (queryByIDInCollection(newsID) == null)
            return false;
        return true;
    }

}
