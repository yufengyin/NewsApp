package com.thirty.java.newsapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.util.Log;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import com.iflytek.cloud.*;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView, mInterestView;
    private RecyclerView.Adapter mAdapter, mInterestAdapter;
    private RecyclerView.LayoutManager mLayoutManager, mInterestLayoutManager;
    private Button mCollectButton, mSetButton;
    private SearchView searchView;
    private ViewPager viewPager;

    private News[] myDataset = new News[]{
            new News("fsy", "tai qiang la"), new News("yyf", "tai ruo la"),
            new News("fsy", "tai qiang la"), new News("yyf", "tai ruo la"),
            new News("fsy", "tai qiang la"), new News("yyf", "tai ruo la")
    };
    private String[] myInterestDataset = new String[]{
            "推荐", "科技", "教育", "军事", "国内", "社会", "文化"
    };

    //讯飞语音合成器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
            Log.i("xunfei", "" + error);
        }
        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
            Log.i("xunfei", "onBufferProgress");
        }
        //开始播放
        public void onSpeakBegin() {
            Log.i("xunfei", "onSpeakBegin");
        }
        //暂停播放
        public void onSpeakPaused() {
            Log.i("xunfei", "onSpeakPaused");
        }
        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            Log.i("xunfei", "onSpeakProgress");
        }
        //恢复播放回调接口
        public void onSpeakResumed() {
            Log.i("xunfei", "onSpeakResumed");
        }
        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
            Log.i("xunfei", "onEvent");
        }
    };

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_index_view);

            Log.i("xunfei", "start");
        //讯飞初始化
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=59b0ae8e");

        //语音合成
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(MainActivity.this, null);
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端

        mTts.startSpeaking("膜峰膜峰膜峰", mSynListener);
            Log.i("xunfei", "end");

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        //mInterestView = (RecyclerView) findViewById(R.id.my_interest_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        //mInterestView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //mInterestLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //mInterestView.setLayoutManager(mInterestLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
        //mInterestAdapter = new MyInterestAdapter(myInterestDataset);
        //mInterestView.setAdapter(mInterestAdapter);

        //我的收藏切换
        mCollectButton = (Button) findViewById(R.id.collect_button);
        mCollectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CollectActivity.class);
                startActivity(intent);
            }
        });

        //设置界面切换
        mSetButton = (Button) findViewById(R.id.set_button);
        mSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SetActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //使用菜单填充器获取menu下的菜单资源文件
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search:
                Log.i("cancel","search");
                Intent intent = new Intent(this,SearchActivity.class);
                this.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
