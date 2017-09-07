package com.thirty.java.newsapp;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import com.iflytek.cloud.*;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View.OnFocusChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button mCollectButton, mSetButton;

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
        setContentView(R.layout.index);

        // 将在tabs_LinearLayout里面添加需要的若干选项卡片。
        final LinearLayout tabs_LinearLayout = (LinearLayout) findViewById(R.id.tabs_LinearLayout);

        final ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);

        for (int i = 0; i < MyFragmentPagerAdapter.SIZE; i++) {
            View v = LayoutInflater.from(this).inflate(R.layout.view, null);
            TextView tv = (TextView) v;
            tv.setText(MyFragmentPagerAdapter.myInterestDataset[i]);

            v.setOnFocusChangeListener(new OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    // 当用户选择了tab选项卡上面的子元素时候，相应的把ViewPager显示的页面调整到相应位置。

                    int count = tabs_LinearLayout.getChildCount();
                    for (int i = 0; i < count; i++) {
                        View cv = tabs_LinearLayout.getChildAt(i);
                        if (v == cv) {
                            if (hasFocus) {
                                mViewPager.setCurrentItem(i);
                                break;
                            }
                        }
                    }
                }
            });
            tabs_LinearLayout.addView(v, i);
        }

        mViewPager.setAdapter(new MyFragmentPagerAdapter(this
                .getSupportFragmentManager()));

        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageSelected(int pos) {
                // 在这里，当用户翻动ViewPager页面时候，相应的把选项卡显示对应的位置。
                // 最轻巧的实现就是让tab选项卡栏中的子元素获得焦点即可。
                View v = tabs_LinearLayout.getChildAt(pos);
                v.requestFocus();
            }
        });

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
