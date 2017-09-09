package com.thirty.java.newsapp;

/**
 * Created by fansy on 2017/9/9.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {
    private Button mAddButton, mBachButton;
    private EditText mEditText;
    private MyFilterAdapter mFilterAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_filter_view);

        mAddButton = (Button)findViewById(R.id.add_button);
        mBachButton = (Button)findViewById(R.id.back_button);
        mEditText = (EditText)findViewById(R.id.edit_text);

        mFilterAdapter = new MyFilterAdapter(MyApplication.filterWords);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_filter_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mFilterAdapter);

        //放弃焦点
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditText.getText().length() > 0) {
                    String temp = mEditText.getText().toString();
                    if (!MyApplication.filterWords.contains(temp)) {
                        mFilterAdapter.mFilterDataSet.add(temp);
                        Log.i("yyf", mFilterAdapter.mFilterDataSet.toString());
                        mFilterAdapter.notifyDataSetChanged();
                    }
                    mEditText.setText("");
                }
            }
        });

        mBachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
