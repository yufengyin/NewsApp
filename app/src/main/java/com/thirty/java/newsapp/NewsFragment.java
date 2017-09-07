package com.thirty.java.newsapp;

import android.os.Bundle;
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
    private MyAdapter mFragmentAdapter;
    private News[] myDataset = new News[]{
            new News("fsy", "tai qiang la"), new News("yyf", "tai ruo la"),
            new News("fsy", "tai qiang la"), new News("yyf", "tai ruo la"),
            new News("fsy", "tai qiang la"), new News("yyf", "tai ruo la")
    };
    // 用一个id标明，否则难以识别效果。
    private static final String ID = "id";

    public static Fragment newInstance(int id) {
        Fragment f = new NewsFragment();
        Bundle b = new Bundle();
        b.putInt(ID, id);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(R.layout.my_index_view, container, false);
        mFragmentAdapter = new MyAdapter(myDataset);
        rv.setAdapter(mFragmentAdapter);
        return rv;
    }
}
