package com.thirty.java.newsapp;

import android.os.Parcelable;
import android.os.Parcel;
import android.util.Log;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zyj on 2017/9/5.
 */

public class BriefNews implements Parcelable{
    final static Pattern pattern = Pattern.compile("http://.*\\.jpg");
    public String newsClassTag;
    public String newsAuthor;
    public String newsID;
    public String[] newsPictures;
    public String newsSource;
    public String newsTime;
    public String newsTitle;
    public String newsURL;
    public String newsIntro;
    public BriefNews(String newsClassTag, String newsAuthor, String newsID, String unsplitNewsPictures, String newsSource,
                     String newsTime, String newsTitle, String newsURL, String newsIntro)
    {
        Matcher matcher = pattern.matcher(unsplitNewsPictures);
        Vector<String> vector = new Vector<String>();
        while (matcher.find())
        { vector.add(matcher.group()); }
        this.newsClassTag = newsClassTag;
        this.newsAuthor = newsAuthor;
        this.newsID = newsID;
        this.newsPictures = (String[]) vector.toArray(new String[0]);
        this.newsSource = newsSource;
        this.newsTime = newsTime;
        this.newsTitle = newsTitle;
        this.newsURL = newsURL;
        this.newsIntro = newsIntro;
    }
    @Override
    public int describeContents()
    {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        out.writeString(newsClassTag);
        out.writeString(newsAuthor);
        out.writeString(newsID);
        out.writeStringArray(newsPictures);
        out.writeString(newsSource);
        out.writeString(newsTime);
        out.writeString(newsTitle);
        out.writeString(newsURL);
        out.writeString(newsIntro);
    }
    public static final Parcelable.Creator<BriefNews> CREATOR = new Creator<BriefNews>()
    {
        @Override
        public BriefNews[] newArray(int size)
        {
            return new BriefNews[size];
        }

        @Override
        public BriefNews createFromParcel(Parcel in)
        {
            return new BriefNews(in);
        }
    };

    public BriefNews(Parcel in)
    {
        newsClassTag = in.readString();
        newsAuthor = in.readString();
        newsID = in.readString();
        in.readStringArray(newsPictures);
        newsSource = in.readString();
        newsTime = in.readString();
        newsTitle = in.readString();
        newsURL = in.readString();
        newsIntro = in.readString();
    }
}
