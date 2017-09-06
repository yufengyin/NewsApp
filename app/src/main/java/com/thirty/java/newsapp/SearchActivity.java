package com.thirty.java.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;

/**
 * Created by 321yy on 2017/9/6.
 */

public class SearchActivity extends AppCompatActivity {
    private SearchView searchView;
    private Button mIndexButton, mSetButton;
    private String query;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message) {
            BriefNews[] briefNewsArray = (BriefNews[])message.getData().getParcelableArray("briefNewsArray");
            Log.i("back", String.valueOf(briefNewsArray.length));
            for (int i = 0; i < briefNewsArray.length; i++)
                Log.i("back", briefNewsArray[i].newsTitle);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_search_view);

        Intent intent = getIntent();
        query = intent.getStringExtra("query");
        Log.i("back", query);

        GetLatestNewsRunnable runnable = new GetLatestNewsRunnable(handler, 1, 5);
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //使用菜单填充器获取menu下的菜单资源文件
        getMenuInflater().inflate(R.menu.search_menu, menu);
        //获取搜索的菜单组件
        MenuItem menuItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        //设置搜索的事件
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast t = Toast.makeText(SearchActivity.this, query, Toast.LENGTH_SHORT);
                t.setGravity(Gravity.TOP, 0, 0);
                t.show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
