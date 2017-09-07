package com.thirty.java.newsapp;

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
    public String unsplitNewsPictures;
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

        unsplitNewsPictures = "";
        if (detailedNews.newsPictures.length > 0)
        {
            unsplitNewsPictures = detailedNews.newsPictures[0];
            for (int i = 1; i < detailedNews.newsPictures.length; i++)
                unsplitNewsPictures += " " + detailedNews.newsPictures[i];
        }
    }

    public DetailedNews toDetailedNews()
    {
        DetailedNews news = new DetailedNews(newsClassTag, newsAuthor, newsID, unsplitNewsPictures,
                newsSource, newsTime, newsTitle, newsURL, newsContent);
        return news;
    };
}
