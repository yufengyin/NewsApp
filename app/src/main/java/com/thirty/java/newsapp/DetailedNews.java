package com.thirty.java.newsapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zyj on 2017/9/6.
 */

public class DetailedNews extends BriefNews {
    public String newsContent;
    public DetailedNews(String newsClassTag, String newsAuthor, String newsID, String unsplitNewsPictures, String newsSource,
                         String newsTime, String newsTitle, String newsURL, String newsContent
                         )
    {
        super(newsClassTag, newsAuthor, newsID, unsplitNewsPictures, newsSource,
                newsTime, newsTitle, newsURL, newsContent);
        this.newsContent = newsIntro; // rename
    }
    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        super.writeToParcel(out, flags);
    }
    public static final Parcelable.Creator<DetailedNews> CREATOR = new Creator<DetailedNews>()
    {
        @Override
        public DetailedNews[] newArray(int size)
        {
            return new DetailedNews[size];
        }

        @Override
        public DetailedNews createFromParcel(Parcel in)
        {
            return new DetailedNews(in);
        }
    };

    public DetailedNews(Parcel in)
    {
        super(in);
        newsContent = newsIntro; // rename
    }
}
