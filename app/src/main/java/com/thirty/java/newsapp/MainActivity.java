package com.thirty.java.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView, mInterestView;
    private RecyclerView.Adapter mAdapter, mInterestAdapter;
    private RecyclerView.LayoutManager mLayoutManager, mInterestLayoutManager;
    private Button mCollectButton, mSetButton;
    private News[] myDataset = new News[]{
            new News("fsy","tai qiang la"), new News("yyf","tai ruo la"),
            new News("fsy","tai qiang la"), new News("yyf","tai ruo la"),
            new News("fsy","tai qiang la"), new News("yyf","tai ruo la")
    };
    private String[] myInterestDataset = new String[]{
            "推荐", "科技", "教育", "军事", "国内", "社会", "文化"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_index_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mInterestView = (RecyclerView) findViewById(R.id.my_interest_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mInterestView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mInterestLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mInterestView.setLayoutManager(mInterestLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
        mInterestAdapter = new MyInterestAdapter(myInterestDataset);
        mInterestView.setAdapter(mInterestAdapter);

        //我的收藏切换
        mCollectButton = (Button) findViewById(R.id.collect_button);
        mCollectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, CollectActivity.class);
                startActivity(intent);
            }
        });

        //设置界面切换
        mSetButton = (Button) findViewById(R.id.set_button);
        mSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, SetActivity.class);
                startActivity(intent);
            }
        });

    }
}
