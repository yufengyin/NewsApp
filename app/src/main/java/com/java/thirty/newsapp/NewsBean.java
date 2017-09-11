package com.java.thirty.newsapp;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by zyj on 2017/9/7.
 */

@DatabaseTable
public class NewsBean implements Serializable {
    @DatabaseField(id = true)
    public String newsID;
    @DatabaseField
    public String newsClassTag;
    @DatabaseField
    public String newsAuthor;
    @DatabaseField
    public String newsPicture;
    @DatabaseField
    public String newsSource;
    @DatabaseField
    public String newsTime;
    @DatabaseField
    public String newsTitle;
    @DatabaseField
    public String newsURL;
    @DatabaseField
    public String newsContent;

    public NewsBean() {}
    public NewsBean(DetailedNews detailedNews)
    {
        newsID = detailedNews.newsID;
        newsClassTag = detailedNews.newsClassTag;
        newsAuthor = detailedNews.newsAuthor;
        newsSource = detailedNews.newsSource;
        newsTime = detailedNews.newsTime;
        newsTitle = detailedNews.newsTitle;
        newsURL = detailedNews.newsURL;
        newsContent = detailedNews.newsContent;
        newsPicture = detailedNews.newsPicture;
    }

    public DetailedNews toDetailedNews()
    {
        DetailedNews news = new DetailedNews(newsClassTag, newsAuthor, newsID, newsPicture,
                newsSource, newsTime, newsTitle, newsURL, newsContent);
        return news;
    };
}
