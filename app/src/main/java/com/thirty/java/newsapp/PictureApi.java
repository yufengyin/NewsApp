package com.thirty.java.newsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zyj on 2017/9/9.
 */

public class PictureApi
{
    // tryToFindLocalPicture: find local picture by newsID, return path if found, else null
    // storePictureFromUrlToCollection:

    static public String tryToFindLocalPicture(String newsID)
    {
        String collectionStr = getPictureNameFromCollection(newsID);
        File collectionFile = new File(collectionStr);
        if (collectionFile.exists()) return collectionStr;
        String cacheStr = getPictureNameFromCache(newsID);
        File cacheFile = new File(cacheStr);
        if (cacheFile.exists()) return cacheStr;
        return null;
    }
    static public void storePictureFromUrlToCollection(String urlString, String filename) throws Exception // called by a sub thread
    {
        Bitmap bitmap = getPictureByUrl(urlString);
        storePictureToCollection(bitmap, filename);
    }
    static public void storePictureFromUrlToCache(String urlString, String filename) throws Exception // called by a sub thread
    {
        Bitmap bitmap = getPictureByUrl(urlString);
        storePictureToCache(bitmap, filename);
    }

    static public void requestDownloadPictureToCache(Handler handler, DetailedNews detailedNews)
    {
        Runnable runnable = new StorePictureRunnable(handler, detailedNews, StorePictureRunnable.TO_CACHE);
        Thread thread = new Thread(runnable);
        thread.start();
    }
    static public void requestDownloadPictureToCollection(Handler handler, DetailedNews detailedNews)
    {
        Runnable runnable = new StorePictureRunnable(handler, detailedNews, StorePictureRunnable.TO_COLLECTION);
        Thread thread = new Thread(runnable);
        thread.start();
    }


    public static String getPictureNameFromCache(String newsID)
    {
        String tempString = getPictureCacheDirectory().toString() + "/" + newsID + ".jpg";
        Log.i("zyj", "getPictureNameFromCache(): " + tempString);
        return tempString;
    }
    public static String getPictureNameFromCollection(String newsID)
    {
        String tempString = getPictureCollectionDirectory().toString() + "/" + newsID + ".jpg";
        Log.i("zyj", "getPictureNameFromCollection(): " + tempString);
        return tempString;
    }

    private static void storePictureToCache(Bitmap bitmap, String filename) throws Exception
    {
        storePictureToDir(bitmap, getPictureCacheDirectory().toString(), filename);
    }
    private static void storePictureToCollection(Bitmap bitmap, String filename) throws Exception
    {
        storePictureToDir(bitmap, getPictureCollectionDirectory().toString(), filename);
    }


    public static File getPictureCollectionDirectory() // get the collection directory
    {
        return getCacheDirectory(Environment.DIRECTORY_DOWNLOADS);
    }
    public static File getPictureCacheDirectory() // get the cache directory
    {
        return getCacheDirectory(Environment.DIRECTORY_PICTURES);
    }








    private static void storePictureToDir(Bitmap bitmap, String dir, String filename) throws Exception
    {
        File dirFile = new File(dir);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        String joinedFilename = dir + "/" + filename;
        Log.i("zyj", "storePictureToDir(): " + joinedFilename);
        File file = new File(joinedFilename);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }
    private static File getCacheDirectory(String type) // only works when MyApplication.onCreate was called
    {
        File appCacheDir = getExternalCacheDirectory(MyApplication.getContext(), type);
        if (appCacheDir == null){
            appCacheDir = getInternalCacheDirectory(MyApplication.getContext(), type);
        }
        if (appCacheDir == null){
            Log.e("getCacheDirectory","getCacheDirectory fail ,the reason is mobile phone unknown exception !");
        }else {
            if (!appCacheDir.exists()&&!appCacheDir.mkdirs()){
                Log.e("getCacheDirectory","getCacheDirectory fail ,the reason is make directory fail !");
            }
        }
        return appCacheDir;
    }
    private static File getExternalCacheDirectory(Context context, String type) {
        File appCacheDir = null;
        if( Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            if (TextUtils.isEmpty(type)){
                appCacheDir = context.getExternalCacheDir();
            }else {
                appCacheDir = context.getExternalFilesDir(type);
            }

            if (appCacheDir == null){// 有些手机需要通过自定义目录
                appCacheDir = new File(Environment.getExternalStorageDirectory(),"Android/data/"+context.getPackageName()+"/cache/"+type);
            }

            if (appCacheDir == null){
                Log.e("getExternalDirectory","getExternalDirectory fail ,the reason is sdCard unknown exception !");
            }else {
                if (!appCacheDir.exists()&&!appCacheDir.mkdirs()){
                    Log.e("getExternalDirectory","getExternalDirectory fail ,the reason is make directory fail !");
                }
            }
        }else {
            Log.e("getExternalDirectory","getExternalDirectory fail ,the reason is sdCard nonexistence or sdCard mount fail !");
        }
        return appCacheDir;
    }
    private static File getInternalCacheDirectory(Context context,String type) {
        File appCacheDir = null;
        if (TextUtils.isEmpty(type)){
            appCacheDir = context.getCacheDir();// /data/data/app_package_name/cache
        }else {
            appCacheDir = new File(context.getFilesDir(),type);// /data/data/app_package_name/files/type
        }

        if (!appCacheDir.exists()&&!appCacheDir.mkdirs()){
            Log.e("getInternalDirectory","getInternalDirectory fail ,the reason is make directory fail !");
        }
        return appCacheDir;
    }

    static private Bitmap getPictureByUrl(String urlString) throws Exception
    {
        Log.i("zyj", "getPictureByUrl(): " + urlString);
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            InputStream is = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return bitmap;
        }
        else
        {
            throw new Exception();
        }
    }

}
