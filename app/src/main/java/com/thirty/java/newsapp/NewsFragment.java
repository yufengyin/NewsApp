package com.thirty.java.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 321yy on 2017/9/7.
 */

public class NewsFragment extends Fragment {
    private RecyclerView rv;

    private LatestNewsDataDistributor mNewsDataDistributor = new LatestNewsDataDistributor();
    public MyAdapter mFragmentAdapter = new MyAdapter(new BriefNews[]{});

    // 用一个id标明，否则难以识别效果。
    private static final String ID = "id";
    public String mCategory;


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
        rv = (RecyclerView) inflater.inflate(R.layout.my_index_view, container, false);
        rv.setAdapter(mFragmentAdapter);
        mFragmentAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view , int position){
                Intent intent = new Intent(getContext(), NewsActivity.class);
                intent.putExtra("NewsID", mFragmentAdapter.mDataset[position].newsID);
                startActivity(intent);
            }
        });
        return rv;
    }
}
