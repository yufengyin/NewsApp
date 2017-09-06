package com.thirty.java.newsapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.util.Log;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView, mInterestView;
    private RecyclerView.Adapter mAdapter, mInterestAdapter;
    private RecyclerView.LayoutManager mLayoutManager, mInterestLayoutManager;
    private Button mCollectButton, mSetButton;
    private SearchView searchView;
    private ViewPager viewPager;

    private News[] myDataset = new News[]{
            new News("fsy", "tai qiang la"), new News("yyf", "tai ruo la"),
            new News("fsy", "tai qiang la"), new News("yyf", "tai ruo la"),
            new News("fsy", "tai qiang la"), new News("yyf", "tai ruo la")
    };
    private String[] myInterestDataset = new String[]{
            "推荐", "科技", "教育", "军事", "国内", "社会", "文化"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_index_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        //mInterestView = (RecyclerView) findViewById(R.id.my_interest_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        //mInterestView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //mInterestLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //mInterestView.setLayoutManager(mInterestLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
        //mInterestAdapter = new MyInterestAdapter(myInterestDataset);
        //mInterestView.setAdapter(mInterestAdapter);

        //我的收藏切换
        mCollectButton = (Button) findViewById(R.id.collect_button);
        mCollectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CollectActivity.class);
                startActivity(intent);
            }
        });

        //设置界面切换
        mSetButton = (Button) findViewById(R.id.set_button);
        mSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SetActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //使用菜单填充器获取menu下的菜单资源文件
        getMenuInflater().inflate(R.menu.search_menu, menu);
        //获取搜索的菜单组件
        MenuItem menuItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("back", "clicked");
            }
        });
        //设置搜索的事件
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast t = Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT);
                //t.setGravity(Gravity.TOP, 0, 0);
                //t.show();

                Log.i("back", "search");
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("query", query);
                startActivity(intent);
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
