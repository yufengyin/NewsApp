package com.thirty.java.newsapp;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;


/**
 * Created by fansy on 2017/9/5.
 */

public class SetActivity extends AppCompatActivity {
    private Button mIndexButton, mCollectButton, mInterestButton, mFilterButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_set_view);

        mIndexButton = (Button) findViewById(R.id.index_button);
        mCollectButton = (Button) findViewById(R.id.collect_button);
        mInterestButton = (Button) findViewById(R.id.my_interest);
        mFilterButton = (Button)findViewById(R.id.my_filter);

        //首页切换
        mIndexButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //我的收藏切换
        mCollectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetActivity.this, CollectActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //我的关注
        mInterestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetActivity.this, InterestSetActivity.class);
                startActivity(intent);
            }
        });

        //屏蔽词
        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetActivity.this, FilterActivity.class);
                startActivity(intent);
            }
        });
    }
}
