package com.thirty.java.newsapp;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;
import android.util.Log;


/**
 * Created by fansy on 2017/9/5.
 */

public class InterestSetActivity extends AppCompatActivity {

    private Button mCancelButton, mConfirmButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_interest_set_view);

        //取消设置
        mCancelButton = (Button) findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mConfirmButton = (Button) findViewById(R.id.confirm_button);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected[] = new boolean[13];

                CheckBox mCheckBox;

                mCheckBox = (CheckBox)findViewById(R.id.checkBox1);
                selected[1] = mCheckBox.isChecked();

                mCheckBox = (CheckBox)findViewById(R.id.checkBox2);
                selected[2] = mCheckBox.isChecked();

                mCheckBox = (CheckBox)findViewById(R.id.checkBox3);
                selected[3] = mCheckBox.isChecked();

                mCheckBox = (CheckBox)findViewById(R.id.checkBox4);
                selected[4] = mCheckBox.isChecked();

                mCheckBox = (CheckBox)findViewById(R.id.checkBox5);
                selected[5] = mCheckBox.isChecked();

                mCheckBox = (CheckBox)findViewById(R.id.checkBox6);
                selected[6] = mCheckBox.isChecked();

                mCheckBox = (CheckBox)findViewById(R.id.checkBox7);
                selected[7] = mCheckBox.isChecked();

                mCheckBox = (CheckBox)findViewById(R.id.checkBox8);
                selected[8] = mCheckBox.isChecked();

                mCheckBox = (CheckBox)findViewById(R.id.checkBox9);
                selected[9] = mCheckBox.isChecked();

                mCheckBox = (CheckBox)findViewById(R.id.checkBox10);
                selected[10] = mCheckBox.isChecked();

                mCheckBox = (CheckBox)findViewById(R.id.checkBox11);
                selected[11] = mCheckBox.isChecked();

                mCheckBox = (CheckBox)findViewById(R.id.checkBox12);
                selected[12] = mCheckBox.isChecked();

                for (int i = 1; i <= 12; ++i)
                    Log.i("fsy", "" + selected[i]);
            }
        });
    }
}
