package com.thirty.java.newsapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.iflytek.cloud.*;

import org.w3c.dom.Text;


/**
 * Created by fansy on 2017/9/5.
 */

public class NewsActivity extends AppCompatActivity {
    private DetailedNews mDetailedNews = new DetailedNews();
    private String mNewsID;
    private TextView mNewsTitle, mNewsAuthor, mNewsTime, mNewsContent;
    private Button mBackButton, mReadButton, mCollectButton;
    private SpeechSynthesizer mTts = null;
    private boolean ifCollect;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            DetailedNews detailedNews = (DetailedNews)message.getData().getParcelable("detailedNews");
            if (detailedNews != null)
                onReceiveDetailedNews(detailedNews);
        }
    };

    public void onReceiveDetailedNews(DetailedNews detailedNews){
        mDetailedNews = detailedNews;
        mNewsTitle.setText(detailedNews.newsTitle);
        mNewsAuthor.setText(detailedNews.newsAuthor);
        mNewsTime.setText(detailedNews.newsTime);
        mNewsContent.setText(detailedNews.newsContent);
        MyApplication.volumnOfCategory[MyApplication.map.get(mDetailedNews.newsClassTag)] += MyApplication.readDelta;

        DatabaseApi.insertDetailedNewsIntoCache(detailedNews);
    }

    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
        }
        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }
        //开始播放
        public void onSpeakBegin() {
        }
        //暂停播放
        public void onSpeakPaused() {
        }
        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }
        //恢复播放回调接口
        public void onSpeakResumed() {
        }
        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_news_view);

        mNewsTitle = (TextView) findViewById(R.id.news_name);
        mNewsAuthor = (TextView) findViewById(R.id.news_author);
        mNewsTime = (TextView) findViewById(R.id.news_time);
        mNewsContent = (TextView) findViewById(R.id.news_text);

        mNewsID = (String) getIntent().getStringExtra("NewsID");

        if (DatabaseApi.isRead(mNewsID)){
            onReceiveDetailedNews(DatabaseApi.queryByIDInCache(mNewsID));
        }
        else if (DatabaseApi.isCollected(mNewsID)) {
            onReceiveDetailedNews(DatabaseApi.queryByIDInCollection(mNewsID));
        }
        else {
                GetDetailedNewsRunnable runnable = new GetDetailedNewsRunnable(handler, mNewsID);
                Thread thread = new Thread(runnable);
                thread.start();
            }

        //退出新闻界面
        mBackButton = (Button) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTts != null)
                    mTts.stopSpeaking();
                finish();
            }
        });

        //朗读新闻
        mReadButton = (Button) findViewById(R.id.read_button);
        mReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //讯飞初始化
                SpeechUtility.createUtility(NewsActivity.this, SpeechConstant.APPID + "=59b0ae8e");

                //语音合成
                mTts = SpeechSynthesizer.createSynthesizer(NewsActivity.this, null);
                mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
                mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
                mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
                mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端

                mTts.startSpeaking(mNewsContent.getText().toString(), mSynListener);
            }
        });

        //收藏新闻
        mCollectButton = (Button) findViewById(R.id.collect_button);
        ifCollect = DatabaseApi.isCollected(mNewsID);
        if(ifCollect){
            mCollectButton.setText("已收藏");
        }
        else{
            mCollectButton.setText("收藏");
        }
        mCollectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCollectFeature(mNewsID);
                ifCollect = !ifCollect;
                if(ifCollect){
                    mCollectButton.setText("已收藏");
                }
                else{
                    mCollectButton.setText("收藏");
                }
            }
        });
    }

    void changeCollectFeature(String NewsID){
        if (ifCollect){
            //收藏到不收藏
            DatabaseApi.deleteByIDInCollection(mNewsID);
        }
        else{
            //不收藏到收藏
            DatabaseApi.insertDetailedNewsIntoCollection(mDetailedNews);
        }
    }
}
