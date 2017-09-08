package com.thirty.java.newsapp;

import android.os.Handler;

/**
 * Created by zyj on 2017/9/8.
 */

public interface NewsStream {
    public void getNext(Handler handler, int n);
}
