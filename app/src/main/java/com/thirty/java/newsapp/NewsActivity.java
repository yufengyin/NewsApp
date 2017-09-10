package com.thirty.java.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by fansy on 2017/9/5.
 */

public class NewsActivity extends AppCompatActivity {
    private DetailedNews mDetailedNews = new DetailedNews();
    private String mNewsID;
    private TextView mNewsTitle, mNewsAuthor, mNewsTime, mNewsContent;
    private Button mBackButton, mReadButton, mCollectButton, mShareButton;
    private ImageView mImageView;
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

    private Handler PicHander = new Handler() {
        @Override
        public void handleMessage(Message message) {
            if (message.what == StorePictureRunnable.SUCCESS){
                String ID = message.getData().getString("newsID");
                mImageView.setImageBitmap(PictureApi.getBitmapFromID(ID));
            }
        }
    };

    public void onReceiveDetailedNews(DetailedNews detailedNews){
        mDetailedNews = detailedNews;
        mNewsTitle.setText(detailedNews.newsTitle);
        mNewsAuthor.setText(detailedNews.newsAuthor);
        mNewsTime.setText(detailedNews.newsTime);
        mNewsContent.setText(detailedNews.newsContent);
        //fsy todo
        if (PictureApi.hasLocalPicture(detailedNews.newsID)){
            mImageView.setImageBitmap(PictureApi.getBitmapFromID(detailedNews.newsID));
        }
        else {
            boolean no_image_mode = getSharedPreferences("settings", Context.MODE_PRIVATE).getBoolean("no_image_mode", false);
            if (!no_image_mode) {
                PictureApi.requestDownloadPictureToCache(PicHander, detailedNews);
                //image
                //mImageView.setImageBitmap();
            }
        }
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
        mImageView = (ImageView) findViewById(R.id.news_image);

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
            mCollectButton.setText(getResources().getString(R.string.collected));
        }
        else{
            mCollectButton.setText(getResources().getString(R.string.collect_name));
        }
        mCollectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCollectFeature(mNewsID);
                ifCollect = !ifCollect;
                if(ifCollect){
                    mCollectButton.setText(getResources().getString(R.string.collected));
                }
                else{
                    mCollectButton.setText(getResources().getString(R.string.collect_name));
                }
            }
        });

        //分享新闻
        mShareButton = (Button) findViewById(R.id.share_button);
        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                // 查询所有可以分享的Activity
                List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
                if (!resInfo.isEmpty()) {
                    List<Intent> targetedShareIntents = new ArrayList<Intent>();
                    for (ResolveInfo info : resInfo) {
                        Intent targeted = new Intent(Intent.ACTION_SEND);
                        targeted.setType("*/*");
                        ActivityInfo activityInfo = info.activityInfo;

                        if(PictureApi.tryToFindLocalPicture(mDetailedNews.newsID) != null){
                            File f = new File(PictureApi.tryToFindLocalPicture(mDetailedNews.newsID));
                            Uri mUri = Uri.fromFile(f);
                            targeted.putExtra(Intent.EXTRA_STREAM, mUri);
                        }
                        targeted.putExtra(Intent.EXTRA_TEXT, mDetailedNews.toBriefNews().newsIntro + "\n" + mDetailedNews.newsURL);

                        targeted.setPackage(activityInfo.packageName);
                        targeted.setClassName(activityInfo.packageName, info.activityInfo.name);
                        PackageManager pm = getApplication().getPackageManager();
                        if (info.activityInfo.applicationInfo.loadLabel(pm).toString().equals(getResources().getString(R.string.wechat))
                                || info.activityInfo.applicationInfo.loadLabel(pm).toString().equals(getResources().getString(R.string.weibo))) {
                            targetedShareIntents.add(targeted);
                        }
                    }
                    // 选择分享时的标题
                    Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), getResources().getString(R.string.choose_share));
                    if (chooserIntent == null) {
                        return;
                    }
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[] {}));
                    try {
                        startActivity(chooserIntent);
                    } catch (android.content.ActivityNotFoundException ex) {

                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_found), Toast.LENGTH_SHORT).show();
                    }
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
