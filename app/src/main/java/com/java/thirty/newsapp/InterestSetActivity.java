package com.java.thirty.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.CheckBox;


/**
 * Created by fansy on 2017/9/5.
 */

public class InterestSetActivity extends AppCompatActivity {

    private Button mCancelButton, mConfirmButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_interest_set_view);
        CheckBox tempCheckBox;

        //给CheckBox赋初值
        tempCheckBox = (CheckBox)findViewById(R.id.checkBox1);
        tempCheckBox.setChecked(MyApplication.selected[1]);

        tempCheckBox = (CheckBox)findViewById(R.id.checkBox2);
        tempCheckBox.setChecked(MyApplication.selected[2]);

        tempCheckBox = (CheckBox)findViewById(R.id.checkBox3);
        tempCheckBox.setChecked(MyApplication.selected[3]);

        tempCheckBox = (CheckBox)findViewById(R.id.checkBox4);
        tempCheckBox.setChecked(MyApplication.selected[4]);

        tempCheckBox = (CheckBox)findViewById(R.id.checkBox5);
        tempCheckBox.setChecked(MyApplication.selected[5]);

        tempCheckBox = (CheckBox)findViewById(R.id.checkBox6);
        tempCheckBox.setChecked(MyApplication.selected[6]);

        tempCheckBox = (CheckBox)findViewById(R.id.checkBox7);
        tempCheckBox.setChecked(MyApplication.selected[7]);

        tempCheckBox = (CheckBox)findViewById(R.id.checkBox8);
        tempCheckBox.setChecked(MyApplication.selected[8]);

        tempCheckBox = (CheckBox)findViewById(R.id.checkBox9);
        tempCheckBox.setChecked(MyApplication.selected[9]);

        tempCheckBox = (CheckBox)findViewById(R.id.checkBox10);
        tempCheckBox.setChecked(MyApplication.selected[10]);

        tempCheckBox = (CheckBox)findViewById(R.id.checkBox11);
        tempCheckBox.setChecked(MyApplication.selected[11]);

        tempCheckBox = (CheckBox)findViewById(R.id.checkBox12);
        tempCheckBox.setChecked(MyApplication.selected[12]);

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
                selected[0] = true;

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

                MyApplication.selected = selected;
                PreferenceStorage.storePreference(selected);
                finish();
            }
        });
    }
}
