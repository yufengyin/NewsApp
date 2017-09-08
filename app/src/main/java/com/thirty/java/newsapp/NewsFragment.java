package com.thirty.java.newsapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 321yy on 2017/9/7.
 */

public class NewsFragment extends Fragment {
    final String TYPE_RECOMMEND = "推荐";
    private MyAdapter mFragmentAdapter;
    // 用一个id标明，否则难以识别效果。
    private static final String ID = "id";
    public String mCategory;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            BriefNews[] briefNewsArray = (BriefNews[]) message.getData().getParcelableArray("briefNewsArray");
            onReceiveNews(briefNewsArray);
        }
    };

    public void onReceiveNews(BriefNews[] briefNewsArray){
        mFragmentAdapter.mDataset = briefNewsArray;
        mFragmentAdapter.notifyDataSetChanged();
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

        //加载新闻
        if (mFragmentAdapter == null) {
            mFragmentAdapter = new MyAdapter(new BriefNews[]{});
            rv.setAdapter(mFragmentAdapter);
            if (mCategory != TYPE_RECOMMEND) {
                GetLatestNewsRunnable runnable = new GetLatestNewsRunnable(handler, 1, 10, mCategory);
                Thread thread = new Thread(runnable);
                thread.start();
            } else {
                //获取推荐

            }
        }
        else
            rv.setAdapter(mFragmentAdapter);
        return rv;
    }
}
