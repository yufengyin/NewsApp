package com.thirty.java.newsapp;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button mCollectButton, mSetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

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
