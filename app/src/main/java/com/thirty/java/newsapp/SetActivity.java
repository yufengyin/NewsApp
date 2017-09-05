package com.thirty.java.newsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;


/**
 * Created by fansy on 2017/9/5.
 */

public class SetActivity extends AppCompatActivity {
    private Button mIndexButton, mCollectButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_set_view);

        //首页切换
        mIndexButton = (Button) findViewById(R.id.index_button);
        mIndexButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });

        //我的收藏切换
        mCollectButton = (Button) findViewById(R.id.collect_button);
        mCollectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SetActivity.this, CollectActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
