package com.thirty.java.newsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by zyj on 2017/9/7.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static final String DB_NAME = "test.db";
    private static final int DB_VERSION = 1;
    private Dao<NewsBean, String> cacheDao;
    private Dao<CollectionNewsBean, String> collectionDao;
    private static DBHelper instance;

    public static synchronized DBHelper getHelper() {
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null) {
                    instance = new DBHelper(MyApplication.getContext());
                }
            }
        }
        return instance;
    }
    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        Log.i("back", "onCreate()");
        try {
            TableUtils.createTable(connectionSource, NewsBean.class);
            TableUtils.createTable(connectionSource, CollectionNewsBean.class);
        } catch (SQLException e) {
            Log.i("back", "onCreate(): " + e.toString());
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion,
                          int newVersion) {
        Log.i("back", "onUpgrade()");
        try
        {
            TableUtils.dropTable(connectionSource, NewsBean.class, true);
            TableUtils.dropTable(connectionSource, CollectionNewsBean.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e)
        {
            Log.i("back", "onUpgrade(): " + e.toString());
        }
    }
    public synchronized Dao<NewsBean, String> getCacheDao() throws SQLException {
        Log.i("back", "getCacheDao()");
        if (cacheDao == null)
        {
            cacheDao = super.getDao(NewsBean.class);
        }
        return cacheDao;
    }
    public synchronized Dao<CollectionNewsBean, String> getCollectionDao() throws SQLException {
        Log.i("back", "getCollectionDao()");
        if (collectionDao == null)
        {
            collectionDao = super.getDao(CollectionNewsBean.class);
        }
        return collectionDao;
    }
    @Override
    public void close() {
        Log.i("back", "close()");
        super.close();
        cacheDao = null;
    }

    public void clearCacheTable() throws SQLException
    {
        TableUtils.clearTable(getConnectionSource(), NewsBean.class);
    }

    public void clearCollectionTable() throws SQLException
    {
        TableUtils.clearTable(getConnectionSource(), CollectionNewsBean.class);
    }
}