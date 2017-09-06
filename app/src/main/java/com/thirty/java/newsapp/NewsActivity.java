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
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import org.w3c.dom.Text;


/**
 * Created by fansy on 2017/9/5.
 */

public class NewsActivity extends AppCompatActivity {
    private TextView mNewsTitle, mNewsAuthor, mNewsTime, mNewsContent;
    private Button mBackButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_news_view);

        BriefNews briefNews = (BriefNews) getIntent().getParcelableExtra("News");

        mNewsTitle = (TextView) findViewById(R.id.news_name);
        mNewsTitle.setText(briefNews.newsTitle);

        mNewsAuthor = (TextView) findViewById(R.id.news_author);
        mNewsAuthor.setText(briefNews.newsAuthor);

        mNewsTime = (TextView) findViewById(R.id.news_time);
        mNewsTime.setText(briefNews.newsTime);

        mNewsContent = (TextView) findViewById(R.id.news_text);
        mNewsContent.setText(briefNews.newsIntro);

        //退出新闻界面
        mBackButton = (Button) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Log.i("News", briefNews.newsTitle);
    }
}
