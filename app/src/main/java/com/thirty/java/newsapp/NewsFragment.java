package com.thirty.java.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 321yy on 2017/9/7.
 */

public class NewsFragment extends Fragment {
    final String TYPE_RECOMMEND = "推荐";
    static public String[] categories = new String[]{
            "推荐", "科技", "教育", "军事", "国内",
            "社会", "文化", "汽车", "国际", "体育",
            "财经", "健康", "娱乐"
    };
    static public GetLatestNewsStream mNewsStream[];

    private MyAdapter mFragmentAdapter;
    // 用一个id标明，否则难以识别效果。
    private static final String ID = "id";
    public String mCategory;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            BriefNews[] briefNewsArray = (BriefNews[]) message.getData().getParcelableArray("briefNewsArray");
            Log.i("zyj", "length in handler: " + briefNewsArray.length);
            onReceiveNews(briefNewsArray);
        }
    };

    public void onReceiveNews(BriefNews[] briefNewsArray){
        ArrayList<BriefNews> tempList = new ArrayList<BriefNews>();
        tempList.addAll(Arrays.asList(mFragmentAdapter.mDataset));
        tempList.addAll(Arrays.asList(briefNewsArray));
        mFragmentAdapter.mDataset = tempList.toArray(new BriefNews[0]);

        //mFragmentAdapter.mDataset = briefNewsArray;
        mFragmentAdapter.notifyDataSetChanged();
    }

    private Handler newsHandler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            DetailedNews detailedNews = (DetailedNews)message.getData().getParcelable("detailedNews");
            onReceiveDetailedNews(detailedNews);
        }
    };

    public void onReceiveDetailedNews(DetailedNews detailedNews){
        Intent intent = new Intent(getContext(), NewsActivity.class);
        intent.putExtra("News", detailedNews);
        this.startActivity(intent);
    }

    public static NewsFragment newInstance(int id) {
        NewsFragment f = new NewsFragment();
        Bundle b = new Bundle();
        b.putInt(ID, id);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(R.layout.my_index_view, container, false);

        if (mNewsStream == null){
            mNewsStream = new GetLatestNewsStream[13];
            for (int i = 1; i <= 12; ++i)
                mNewsStream[i] = new GetLatestNewsStream(categories[i]);
        }

        //加载新闻
        if (mFragmentAdapter == null) {
            mFragmentAdapter = new MyAdapter(new BriefNews[]{});
            rv.setAdapter(mFragmentAdapter);

            mFragmentAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view , int position){
                    GetDetailedNewsRunnable runnable = new GetDetailedNewsRunnable(newsHandler, mFragmentAdapter.mDataset[position].newsID);
                    Thread thread = new Thread(runnable);
                    thread.start();
                }
            });
            if (mCategory != TYPE_RECOMMEND) {
                //GetLatestNewsRunnable runnable = new GetLatestNewsRunnable(handler, 1, 10, mCategory);
                //Thread thread = new Thread(runnable);
                //thread.start();
                mNewsStream[NewsApiCaller.map.get(mCategory)].getNext(handler, 10);
            } else {
                //获取推荐
                double total_volume = 0;
                for (int i = 1; i <= 12; ++i)
                    total_volume += MyApplication.volumnOfCategory[i];

                double possibility[] = new double[13];
                for (int i = 1; i <= 12; ++i)
                    possibility[i] = MyApplication.volumnOfCategory[i] / total_volume;

                int count[] = new int[13];
                Arrays.fill(count, 0);
                for (int i = 1; i <= 10; ++i){
                    double corona  = Math.random();
                    for (int j = 1; j <= 12; ++j){
                        if (corona <= possibility[j] || j == 12){
                            ++count[j];
                            break;
                        }
                        corona -= possibility[j];
                    }
                }

                for (int i = 1; i <= 12; ++i)
                    if (count[i] > 0)
                        mNewsStream[i].getNext(handler, count[i]);
            }
        }
        else
            rv.setAdapter(mFragmentAdapter);
        return rv;
    }
}
