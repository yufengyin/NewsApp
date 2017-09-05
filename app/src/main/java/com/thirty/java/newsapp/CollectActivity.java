package com.thirty.java.newsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;


/**
 * Created by fansy on 2017/9/5.
 */

public class CollectActivity extends AppCompatActivity {
    private Button mIndexButton, mSetButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_collect_view);

        //首页切换
        mIndexButton = (Button) findViewById(R.id.index_button);
        mIndexButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });

        //设置界面切换
        mSetButton = (Button) findViewById(R.id.set_button);
        mSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(CollectActivity.this, SetActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
